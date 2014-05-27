package com.nosqlrevolution.service;

import com.nosqlrevolution.enums.SearchField;
import com.nosqlrevolution.model.BuilderModel;
import com.nosqlrevolution.model.BuilderModel.BooleanType;
import com.nosqlrevolution.model.FacetRequest;
import com.nosqlrevolution.model.SearchQuery;
import com.nosqlrevolution.util.AggregationUtil;
import com.nosqlrevolution.util.QueryUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
public class SearchService implements Serializable {
    private static final Logger log = Logger.getLogger(SearchService.class.getName());
    private final Client client = ClientService.getClient();
    
    /**
     * Stock search method.
     * 
     * @param sq
     * @return 
     */
    public SearchQuery search(SearchQuery sq) {
        // Set page sizes to passed in values if not already set in query parser
        // Try and verify paging parameters
        if (sq.getPageFrom() == null || sq.getPageFrom() < 0) {
            sq.setPageFrom(0);
        }
        if (sq.getPageSize() == null || sq.getPageSize() < 1) {
            sq.setPageSize(20);
        }

        List<BuilderModel> builders = QueryUtil.addAllSelections(new ArrayList<BuilderModel>(), sq.getFacets(), BooleanType.MUST);

        return search(sq, builders);
    }
    
    public SearchQuery search(SearchQuery sq, List<BuilderModel> builders) {
        QueryBuilder qb;
        
        // If there is nothing interesting, then send mack all facets with no results.
        // This is good for populating the first page.
        if (sq.getMemberId() == null && builders == null) {
            qb = QueryUtil.getMatchAllQuery();
            sq.setPageFrom(0);
            sq.setPageSize(0);
        } else if (sq.getMemberId() != null && builders == null) {
            qb = QueryUtil.getTermBuilder(SearchField.MEMBER_ID.getName(), sq.getMemberId());
        } else {
            if ((builders.size() > 1) || (builders.get(0).getBooleanType() != BooleanType.MUST)) {
                qb = QueryUtil.getBooleanQuery(builders);
            } else {
                qb = builders.get(0).getQueryBuilder();
            }
        }

        SearchRequestBuilder builder = client.prepareSearch(ClientService.INDEX)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(qb)
                .setFrom(sq.getPageFrom())
                .setSize(sq.getPageSize());
        
        // Add all Aggregations
        List<AbstractAggregationBuilder> aggBuilders = AggregationUtil.addAllAggregations(AggregationService.getDefaultFacets());
        if (aggBuilders != null) {
            for (AbstractAggregationBuilder aggBuilder: aggBuilders) {
                builder.addAggregation(aggBuilder);
            }
        }
        System.out.println("Builder=" + builder.toString());
        
        SearchResponse response = builder.execute().actionGet();
        List<FacetRequest> facets = null;
        if (response.getAggregations() != null) {
            facets = AggregationUtil.parseAggregations(response.getAggregations(), sq.getFacets());
        }
        
        // Update the SearchQuery results
        SearchHits h = response.getHits();
        sq.setTimeMillis(Long.toString(response.getTookInMillis()));
        sq.setTotalResults(h.getTotalHits());
        sq.setAvailableResults(h.getHits().length);
        
        System.out.println("SearchService " + response.getHits().getTotalHits());
        sq.setResults(SearchResultService.generateSearchOutput(h.getHits()));
        sq.setFacets(facets);
        return sq;
    }
}
