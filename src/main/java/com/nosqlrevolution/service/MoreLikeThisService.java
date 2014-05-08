package com.nosqlrevolution.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nosqlrevolution.enums.FacetField;
import com.nosqlrevolution.enums.SearchField;
import com.nosqlrevolution.model.BuilderModel;
import static com.nosqlrevolution.model.BuilderModel.QueryType.QUERY;
import com.nosqlrevolution.model.FacetRequest;
import org.elasticsearch.action.search.SearchResponse;
import com.nosqlrevolution.model.SearchQuery;
import com.nosqlrevolution.model.SelectableFacet;
import com.nosqlrevolution.model.data.Member;
import com.nosqlrevolution.util.QueryUtil;
import static com.nosqlrevolution.util.QueryUtil.getQueryBuilder;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.elasticsearch.client.Client;
import org.elasticsearch.action.search.SearchRequestBuilder;

/**
 * 
 * @author cbrown
 */
public class MoreLikeThisService implements Serializable {
    private static final Logger log = Logger.getLogger(MoreLikeThisService.class.getName());
    private final Client client = ClientService.getClient();
    private final ObjectMapper mapper = new ObjectMapper();
    
    public SearchQuery search(SearchQuery sq) {
        // TODO: Try this as an id query
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
            
            sq.addFacet(getFacetRequest(FacetField.BIRTH_YEAR, member.getBirthYear()));
            sq.addFacet(getFacetRequest(FacetField.ZIP, member.getZip()));
            sq.addFacet(getFacetRequest(FacetField.GENDER, member.getGender()));
            sq.addFacet(getFacetRequest(FacetField.STATE, member.getState()));
            sq.addFacet(getFacetRequest(FacetField.CPT_CODES_UNIQUE, member.getCptCodesUnique()));
            sq.addFacet(getFacetRequest(FacetField.NUM_BALANCES, member.getNumberOfBalances()));
            sq.addFacet(getFacetRequest(FacetField.NUM_CLAIMS, member.getNumberOfClaims()));
            sq.addFacet(getFacetRequest(FacetField.NUM_CONTRIBUTIONS, member.getNumberOfContributionsAndPayments()));
            sq.addFacet(getFacetRequest(FacetField.NUM_DEPENDENTS, member.getNumberOfDependents()));
            sq.addFacet(getFacetRequest(FacetField.NUM_EMPLOYEE_CONTRIBUTIONS, member.getNumberOfEmployeeContributions()));
            sq.addFacet(getFacetRequest(FacetField.NUM_EMPOYER_CONTRIBUTIONS, member.getNumberOfEmployerContributions()));
            sq.addFacet(getFacetRequest(FacetField.NUM_PAYMENTS, member.getNumberofPayments()));
            sq.addFacet(getFacetRequest(FacetField.TOTAL_BALANCES, member.getTotalOfBalances()));
            sq.addFacet(getFacetRequest(FacetField.TOTAL_CLAIMS, member.getTotalClaimsRepricedAmount()));
            sq.addFacet(getFacetRequest(FacetField.TOTAL_CLAIMS_PATIENT, member.getTotalClaimsPatientResponsibilityAmount()));
            sq.addFacet(getFacetRequest(FacetField.TOTAL_CONTRIBUTIONS, member.getTotalContributionsAndPayments()));
            sq.addFacet(getFacetRequest(FacetField.TOTAL_EMPLOYEE_CONTRIBUTIONS, member.getTotalEmployeeContributions()));
            sq.addFacet(getFacetRequest(FacetField.TOTAL_EMPLOYER_CONTRIBUTIONS, member.getTotalEmployerContributions()));
            sq.addFacet(getFacetRequest(FacetField.TOTAL_PAYMENTS, member.getTotalPayments()));
            
            List<BuilderModel> builders = QueryUtil.addAllSelections(new ArrayList<BuilderModel>(), sq.getFacets(), BuilderModel.BooleanType.SHOULD);
            builders.add(new BuilderModel(getQueryBuilder(SearchField.MEMBER_ID.getName(), Integer.toString(member.getNewMemberID())), QUERY, BuilderModel.BooleanType.MUST_NOT));
            
            return new SearchService().search(sq, builders);
        } catch (IOException ex) {
            Logger.getLogger(MoreLikeThisService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return sq;
   }

    public FacetRequest getFacetRequest(FacetField facetField, Object value) {
        List<Object> values = new ArrayList<>();
        values.add(value);

        return getFacetRequest(facetField, values);
    }
    
    public FacetRequest getFacetRequest(FacetField facetField, Collection<Object> values) {
        List<SelectableFacet> selectables = new ArrayList<>();
        for (Object value: values) {
            if (value != null) {
                selectables.add(new SelectableFacet()
                        .setName(value.toString())
                        .setSelected(Boolean.TRUE)
                );
            }
        }
        
        FacetRequest request = new FacetRequest()
                .setField(facetField)
                .setSelectables(selectables);
        
        return request;
    }
}
