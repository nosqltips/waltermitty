package com.nosqlrevolution.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nosqlrevolution.enums.AggregationField;
import static com.nosqlrevolution.enums.ChartType.LINE;
import com.nosqlrevolution.enums.SearchField;
import com.nosqlrevolution.model.BuilderModel;
import static com.nosqlrevolution.model.BuilderModel.QueryType.QUERY;
import com.nosqlrevolution.model.Chart;
import com.nosqlrevolution.model.ChartValue;
import com.nosqlrevolution.model.LineChart;
import com.nosqlrevolution.model.LineChartHeader;
import com.nosqlrevolution.model.LineChartValue;
import com.nosqlrevolution.model.Result;
import com.nosqlrevolution.model.SearchQuery;
import com.nosqlrevolution.model.StatsValues;
import com.nosqlrevolution.model.data.Member;
import com.nosqlrevolution.util.AggregationUtil;
import com.nosqlrevolution.util.ChartUtil;
import com.nosqlrevolution.util.QueryUtil;
import static com.nosqlrevolution.util.QueryUtil.getQueryBuilder;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;

/**
 * 
 * @author cbrown
 */
public class MoreLikeThisService implements Serializable {
    private static final Logger log = Logger.getLogger(MoreLikeThisService.class.getName());
    private final Client client = ClientService.getClient();
    private final ObjectMapper mapper = new ObjectMapper();
    private final int totalMembers = 100;
    private final Double yearlyContribLimit = 6450.0D;
    private final Double monthlyContribLimit = yearlyContribLimit/12.0D;
    
    public SearchQuery search(SearchQuery sq) {
        long startTime = System.currentTimeMillis();
        
        // Add facet requests here to build chart data.
        SearchRequestBuilder builder = client.prepareSearch(ClientService.INDEX)
                .setQuery(QueryUtil.getTermBuilder(SearchField.MEMBER_ID.getName(), sq.getMemberId()))
                .setFrom(0)
                .setSize(1);

        // Add all Aggregations
        List<AbstractAggregationBuilder> aggBuilders = ChartUtil.addAllCharts(ChartService.getMemberCharts());
        if (aggBuilders != null) {
            for (AbstractAggregationBuilder aggBuilder: aggBuilders) {
                builder.addAggregation(aggBuilder);
            }
        }
        
        SearchResponse response = builder.execute().actionGet();
        
        // If there are no results, then just send back the original query.
        if (response.getHits().getHits().length == 0) {
            return sq;
        }        
        
        List<Chart> memberCharts = null;
        if (response.getAggregations() != null) {
            memberCharts = ChartUtil.parseCharts(response.getAggregations());
        }

        // *****************************************************************
        
        // Prepare custom MLT query. We'll build the sq object, then send to the regular search results.
        sq.setPageFrom(0);
        sq.setPageSize(totalMembers);
        
        if (sq.getBoosts() == null) {
            sq.setBoosts(BoostService.getDefaultBoosts());
        }
        
        try {
            // Objectify the Member object so we can work with it.
            Member member = mapper.readValue(response.getHits().getAt(0).getSourceAsString(), Member.class);
            // Get the single member result
            List<Result> memberResults = SearchResultService.generateSearchOutput(response.getHits().getHits());
            
            // Extract field data from the selected member and add facet selections.
            sq.addFacet(AggregationUtil.getFacetRequest(AggregationField.STATE, member.getState()));
            sq.addFacet(AggregationUtil.getFacetRequest(AggregationField.GENDER, member.getGender()));
            sq.addFacet(AggregationUtil.getFacetRequest(AggregationField.BIRTH_YEAR, member.getBirthYear()));
            sq.addFacet(AggregationUtil.getFacetRequest(AggregationField.NUM_DEPENDENTS, member.getNumberOfDependents()));
            sq.addFacet(AggregationUtil.getFacetRequest(AggregationField.NUM_CONTRIBUTIONS, member.getNumberOfContributionsAndPayments()));
            sq.addFacet(AggregationUtil.getFacetRequest(AggregationField.NUM_PAYMENTS, member.getNumberofPayments()));
            sq.addFacet(AggregationUtil.getFacetRequest(AggregationField.NUM_CLAIMS, member.getNumberOfClaims()));
            sq.addFacet(AggregationUtil.getFacetRequest(AggregationField.TOTAL_PAYMENTS, member.getTotalPayments()));
            sq.addFacet(AggregationUtil.getFacetRequest(AggregationField.TOTAL_CONTRIBUTIONS, member.getTotalContributionsAndPayments()));
            sq.addFacet(AggregationUtil.getFacetRequest(AggregationField.TOTAL_BALANCES, member.getTotalOfBalances()));
            sq.addFacet(AggregationUtil.getFacetRequest(AggregationField.TOTAL_CLAIMS, member.getTotalClaimsRepricedAmount()));
            sq.addFacet(AggregationUtil.getFacetRequest(AggregationField.CPT_CODES_UNIQUE, member.getCptCodesUnique()));
            
            // Get all builder models
            List<BuilderModel> builders = QueryUtil.addAllSelectionsMLT(new ArrayList<BuilderModel>(), sq.getFacets(), BuilderModel.BooleanType.SHOULD, sq.getBoosts());
            // Remove the selected user from the MLT results
            builders.add(new BuilderModel(getQueryBuilder(SearchField.MEMBER_ID.getName(), Integer.toString(member.getNewMemberID())), QUERY, BuilderModel.BooleanType.MUST_NOT));
            
            // Get our set of search results.
            sq = search(sq, builders, memberResults);
            
            // Get the list of memberIds to constrain the aggregations.
            List<String> memberIds = getMemberIds(sq.getResults());

            // Retrieve the charting data constrained to the list of returned matches.
            List<Chart> groupCharts = chart(memberIds);
            
            sq.setLineChart(generateLineChartDate(memberCharts, groupCharts, totalMembers));
            
            // Need to remove the line charts before adding
            sq.setCharts(groupCharts);
//            sq.setCharts(removeLineCharts(groupCharts));                    
            
            // Calculate the elapsed time of all of the calls.
            long elapsedTime = System.currentTimeMillis() - startTime;
            sq.setTimeMillis(Long.toString(elapsedTime));
        } catch (IOException ex) {
            Logger.getLogger(MoreLikeThisService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sq;
    }
    
    /**
     * First search to get query results.
     * 
     * @param sq
     * @param builders
     * @param memberResults
     * @return 
     */
    public SearchQuery search(SearchQuery sq, List<BuilderModel> builders, List<Result> memberResults) {
        QueryBuilder qb;
        
        SearchRequestBuilder builder = client.prepareSearch(ClientService.INDEX)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryUtil.getBooleanQuery(builders))
                .setFrom(sq.getPageFrom())
                .setSize(sq.getPageSize());
        
        //System.out.println("Builder=" + builder.toString());
        
        SearchResponse response = builder.execute().actionGet();
        // Update the SearchQuery results
        SearchHits h = response.getHits();
        sq.setTimeMillis(Long.toString(response.getTookInMillis()));
        sq.setTotalResults(h.getTotalHits());
        sq.setAvailableResults(h.getHits().length);
        
        System.out.println("MltService " + response.getHits().getTotalHits());
        memberResults.addAll(SearchResultService.generateSearchOutput(h.getHits()));
        sq.setResults(memberResults);
        sq.setFacets(null);
        return sq;
    }
    
    /**
     * Second search to get charting results
     * 
     * @param memberIds
     * @return 
     */
    public List<Chart> chart(List<String> memberIds) {
        SearchRequestBuilder builder = client.prepareSearch(ClientService.INDEX)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryUtil.getTermsBuilder(SearchField.MEMBER_ID.getName(), memberIds))
                .setFrom(0)
                .setSize(100);
        
        // Add all Aggregations
        List<AbstractAggregationBuilder> aggBuilders = ChartUtil.addAllCharts(ChartService.getCharts());
        if (aggBuilders != null) {
            for (AbstractAggregationBuilder aggBuilder: aggBuilders) {
                builder.addAggregation(aggBuilder);
            }
        }
        System.out.println("Builder=" + builder.toString());
        
        SearchResponse response = builder.execute().actionGet();
        
        System.out.println("chart response=" + response.getHits().getTotalHits()); 
        List<Chart> charts = null;
        if (response.getAggregations() != null) {
            charts = ChartUtil.parseCharts(response.getAggregations());
        }
        
        return charts;
    }

    /**
     * Return a list of just the member Ids.
     * 
     * @param results
     * @return 
     */
    private List<String> getMemberIds(List<Result> results) {
        List<String> memberIds = new ArrayList<>();
        for (Result result: results) {
            memberIds.add(result.getMemberId());
        }
        
        return memberIds;
    }

    /**
     * Generate all of the line chart data.
     * 
     * @param memberCharts
     * @param groupCharts
     * @param totalMembers 
     */
    private LineChart generateLineChartDate(List<Chart> memberCharts, List<Chart> groupCharts, int totalMembers) {
        StatsValues memberMemberContrib = null;
        StatsValues memberCompanyContrib = null;
        StatsValues memberMemberPayments = null;

        StatsValues groupMemberContrib = null;
        StatsValues groupCompanyContrib = null;
        StatsValues groupMemberPayments = null;
        List<String> headers = null;
        List<LineChartValue> lineChartValues = new ArrayList<>();
        
        System.out.println("number of MEMBER charts=" + memberCharts.size());
        System.out.println("number of GROUP charts=" + groupCharts.size());
        for (Chart chart: memberCharts) {
            switch (chart.getField()) {
                case MEMBER_CONTRIBUTIONS:
                    memberMemberContrib = StatisticsService.calculateStats(chart, 1, "2014");
                    break;
                case COMPANY_CONTRIBUTIONS:
                    memberCompanyContrib = StatisticsService.calculateStats(chart, 1, "2014");
                    break;
                case MEMBER_PAYMENTS:
                    memberMemberPayments = StatisticsService.calculateStats(chart, 1, "2014");
                    break;
            }
        }

        for (Chart chart: groupCharts) {
            switch (chart.getField()) {
                case MEMBER_CONTRIBUTIONS:
                    groupMemberContrib = StatisticsService.calculateStats(chart, totalMembers, "2014");
                    // Get headers from this chart as all values should be populated.
                    headers = generateHeaders(chart);
                    break;
                case COMPANY_CONTRIBUTIONS:
                    groupCompanyContrib = StatisticsService.calculateStats(chart, totalMembers, "2014");
                    break;
                case MEMBER_PAYMENTS:
                    groupMemberPayments = StatisticsService.calculateStats(chart, totalMembers, "2014");
                    break;
            }
        }

        lineChartValues.add(generateChartValues(memberMemberContrib, headers, "MEMBER_"));
        lineChartValues.add(generateChartValues(memberCompanyContrib, headers, "MEMBER_"));
        lineChartValues.add(generateChartValues(memberMemberPayments, headers, "MEMBER_"));
        lineChartValues.add(generateChartValues(groupMemberContrib, headers, "GROUP_"));
        lineChartValues.add(generateChartValues(groupCompanyContrib, headers, "GROUP_"));
        lineChartValues.add(generateChartValues(groupMemberPayments, headers, "GROUP_"));

        // Member calculations
        Double memberTotalYearContrib = memberMemberContrib.getProjectedYearEndTotal() +
                memberCompanyContrib.getProjectedYearEndTotal();
        Double memberYearEndBalance = memberTotalYearContrib + memberMemberPayments.getProjectedYearEndTotal();
        Double memberMonthlyContributionIncrease = (yearlyContribLimit - memberTotalYearContrib) / 12.0D;
        if (memberMonthlyContributionIncrease < 0.0D) {
            memberMonthlyContributionIncrease = 0.0D;
        }

        // Group calculations
        Double groupTotalYearContrib = groupMemberContrib.getProjectedYearEndTotal() +
                groupCompanyContrib.getProjectedYearEndTotal();
        Double groupYearEndBalance = groupTotalYearContrib + groupMemberPayments.getProjectedYearEndTotal();
        Double groupMonthlyContributionIncrease = (yearlyContribLimit - groupTotalYearContrib) / 12.0D;
        if (groupMonthlyContributionIncrease < 0.0D) {
            groupMonthlyContributionIncrease = 0.0D;
        }
        
        LineChart lineChart = new LineChart()
                .setMemberMonthlyContributionIncrease(memberMonthlyContributionIncrease)
                .setMemberYearlyContributionIncrease(memberMonthlyContributionIncrease * 12)
                .setMemberTotalYearContrib(memberTotalYearContrib)
                .setMemberYearEndBalance(memberYearEndBalance)
                .setGroupMonthlyContributionIncrease(groupMonthlyContributionIncrease)
                .setGroupYearlyContributionIncrease(groupMonthlyContributionIncrease * 12)
                .setGroupTotalYearContrib(groupTotalYearContrib)
                .setGroupYearEndBalance(groupYearEndBalance);
        
        lineChart.setHeader(new LineChartHeader()
                .setName("Date")
                .setValues(headers)
            );
        
        lineChart.setValues(lineChartValues);
        
//      function drawChart() {
//        var data = google.visualization.arrayToDataTable([
//          ['Year', 'Sales', 'Expenses'],
//          ['2004',  1000,      400],
//          ['2005',  1170,      460],
//          ['2006',  660,       1120],
//          ['2007',  1030,      540]
//        ]);
        
        return lineChart;
    }
    
    /**
     * Get a list of the values that we'll use for the chart headers.
     * 
     * @param chart
     * @return 
     */
    private List<String> generateHeaders(Chart chart) {
        List<String> headers = new ArrayList<>();
        for (ChartValue value: chart.getValues()) {
            headers.add(value.getName());
        }
        
        return headers;
    }
    
    /**
     * Generate the ChartValue that will be used to generate the chart.
     * 
     * @param chart
     * @param headers
     * @param prefix
     * @return 
     */
    private LineChartValue generateChartValues(StatsValues statsValue, List<String> headers, String prefix) {
        LineChartValue value = new LineChartValue();
        value.setName(prefix + statsValue.getName());
        
        List<Double> values = new ArrayList<>();
        
        // Presumably all the header values are in order and this should enforce that order.
        for (String header: headers) {
            values.add(getValueFromStatsEntry(statsValue, header));
        }
        
        value.setValues(values);
        return value;
    }

    /**
     * Retrieve the correct chart entry or 0.0D if empty.
     * 
     * @param chart
     * @param header
     * @return 
     */
    private Double getValueFromStatsEntry(StatsValues statsValue, String header) {
        if (statsValue.getMonthlyAverages() == null) { return 0.0D; }
        
        for (ChartValue value: statsValue.getMonthlyAverages()) {
            if (value.getName().equals(header)) {
                return value.getValue();
            }
        }
        
        return 0.0D;
    }

    /**
     * We need to remove all of the LINE charts as we done something special with them.
     * 
     * @param charts
     * @return 
     */
    private List<Chart> removeLineCharts(List<Chart> charts) {
        List<Chart> returnCharts = new ArrayList<>();
        for (Chart chart: charts) {
            if (chart.getType() != LINE){
                returnCharts.add(chart);
            }
        }

        return returnCharts;
    }
}