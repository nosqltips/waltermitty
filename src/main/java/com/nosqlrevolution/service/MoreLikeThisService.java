package com.nosqlrevolution.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nosqlrevolution.enums.AggregationField;
import com.nosqlrevolution.enums.SearchField;
import com.nosqlrevolution.model.BuilderModel;
import static com.nosqlrevolution.model.BuilderModel.QueryType.QUERY;
import com.nosqlrevolution.model.SearchQuery;
import com.nosqlrevolution.model.data.Member;
import com.nosqlrevolution.util.AggregationUtil;
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
import org.elasticsearch.client.Client;

/**
 * 
 * @author cbrown
 */
public class MoreLikeThisService implements Serializable {
    private static final Logger log = Logger.getLogger(MoreLikeThisService.class.getName());
    private final Client client = ClientService.getClient();
    private final ObjectMapper mapper = new ObjectMapper();
    
    public SearchQuery search(SearchQuery sq) {
        // Add facet requests here to build chart data.
        SearchRequestBuilder builder = client.prepareSearch(ClientService.INDEX)
                .setQuery(QueryUtil.getTermBuilder(SearchField.MEMBER_ID.getName(), sq.getMemberId()))
                .setFrom(sq.getPageFrom())
                .setSize(sq.getPageSize());
        
        SearchResponse response = builder.execute().actionGet();
        
        // If there are no results, then just send back the original query.
        if (response.getHits().getHits().length == 0) {
            return sq;
        }
        
        // Prepare custom MLT query. We'll build the sq object, then send to the regular search results.
        sq.setPageFrom(0);
        sq.setPageSize(100);
        
        try {
            // Objectify the Member object so we can work with it.
            Member member = mapper.readValue(response.getHits().getAt(0).getSourceAsString(), Member.class);
            
            // Extract field data from the selected member and add facet selections.
            sq.addFacet(AggregationUtil.getFacetRequest(AggregationField.BIRTH_YEAR, member.getBirthYear()));
            sq.addFacet(AggregationUtil.getFacetRequest(AggregationField.ZIP, member.getZip()));
            sq.addFacet(AggregationUtil.getFacetRequest(AggregationField.GENDER, member.getGender()));
            sq.addFacet(AggregationUtil.getFacetRequest(AggregationField.STATE, member.getState()));
            sq.addFacet(AggregationUtil.getFacetRequest(AggregationField.CPT_CODES_UNIQUE, member.getCptCodesUnique()));
            sq.addFacet(AggregationUtil.getFacetRequest(AggregationField.NUM_BALANCES, member.getNumberOfBalances()));
            sq.addFacet(AggregationUtil.getFacetRequest(AggregationField.NUM_CLAIMS, member.getNumberOfClaims()));
            sq.addFacet(AggregationUtil.getFacetRequest(AggregationField.NUM_CONTRIBUTIONS, member.getNumberOfContributionsAndPayments()));
            sq.addFacet(AggregationUtil.getFacetRequest(AggregationField.NUM_DEPENDENTS, member.getNumberOfDependents()));
            sq.addFacet(AggregationUtil.getFacetRequest(AggregationField.NUM_EMPLOYEE_CONTRIBUTIONS, member.getNumberOfEmployeeContributions()));
            sq.addFacet(AggregationUtil.getFacetRequest(AggregationField.NUM_EMPOYER_CONTRIBUTIONS, member.getNumberOfEmployerContributions()));
            sq.addFacet(AggregationUtil.getFacetRequest(AggregationField.NUM_PAYMENTS, member.getNumberofPayments()));
            sq.addFacet(AggregationUtil.getFacetRequest(AggregationField.TOTAL_BALANCES, member.getTotalOfBalances()));
//            sq.addFacet(AggregationUtil.getFacetRequest(AggregationField.TOTAL_CLAIMS, member.getTotalClaimsRepricedAmount()));
//            sq.addFacet(AggregationUtil.getFacetRequest(AggregationField.TOTAL_CLAIMS_PATIENT, member.getTotalClaimsPatientResponsibilityAmount()));
            sq.addFacet(AggregationUtil.getFacetRequest(AggregationField.TOTAL_CONTRIBUTIONS, member.getTotalContributionsAndPayments()));
            sq.addFacet(AggregationUtil.getFacetRequest(AggregationField.TOTAL_EMPLOYEE_CONTRIBUTIONS, member.getTotalEmployeeContributions()));
            sq.addFacet(AggregationUtil.getFacetRequest(AggregationField.TOTAL_EMPLOYER_CONTRIBUTIONS, member.getTotalEmployerContributions()));
            sq.addFacet(AggregationUtil.getFacetRequest(AggregationField.TOTAL_PAYMENTS, member.getTotalPayments()));
            
            // Get all builder models
            List<BuilderModel> builders = QueryUtil.addAllSelectionsMLT(new ArrayList<BuilderModel>(), sq.getFacets(), BuilderModel.BooleanType.SHOULD);
            // Remove the selected user from the MLT results
            builders.add(new BuilderModel(getQueryBuilder(SearchField.MEMBER_ID.getName(), Integer.toString(member.getNewMemberID())), QUERY, BuilderModel.BooleanType.MUST_NOT));
            
            return new SearchService().search(sq, builders);
        } catch (IOException ex) {
            Logger.getLogger(MoreLikeThisService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sq;
   }
}
