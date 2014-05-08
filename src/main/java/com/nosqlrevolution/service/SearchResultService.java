package com.nosqlrevolution.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nosqlrevolution.enums.FacetField;
import com.nosqlrevolution.enums.SearchField;
import com.nosqlrevolution.model.FacetRequest;
import com.nosqlrevolution.model.Result;
import com.nosqlrevolution.model.SelectableFacet;
import com.nosqlrevolution.model.data.Member;
import com.nosqlrevolution.util.FacetUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.facet.Facet;

/**
 *
 * @author cbrown
 */
public class SearchResultService {
    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * Generate the search results to send to the client.
     * 
     * @param hits
     * @return 
     */
    public static List<Result> generateSearchOutput(SearchHit[] hits) {
        List<Result> values = new ArrayList<>(hits.length);
        for (SearchHit hit : hits) {
            //DisplayValue item = mapper.readValue((String) fields.get(Field.DISPLAY_VALUES.getName()), Result.class);
            Map<String, Object> fields = hit.sourceAsMap();
            //Map<String, String> display = (HashMap) fields.get(Field.DISPLAY_VALUES.getName());
            Result result = new Result();
            result.setMemberId(getMemberId(fields.get(SearchField.MEMBER_ID.getName())));
            result.setState((String) fields.get(SearchField.STATE.getName()));
            result.setZip((String) fields.get(SearchField.ZIP.getName()));
            result.setBirthYear((String) fields.get(SearchField.BIRTH_YEAR.getName()));
            result.setGender((String) fields.get(SearchField.GENDER.getName()));
            result.setNumDependents((Integer) fields.get(SearchField.NUM_DEPENDENTS.getName()));
            result.setNumPayments((Integer) fields.get(SearchField.NUM_PAYMENTS.getName()));
            result.setNumClaims((Integer) fields.get(SearchField.NUM_CLAIMS.getName()));
            result.setScore(hit.getScore());
            values.add(result);           
        }
        
        return values;
    }
    
    /**
     * Generate all of the facets for the UI.
     * 
     * @param facets
     * @param previousRequests
     * @return 
     */
    public static List<FacetRequest> generateFacetOutput(List<Facet> facets, List<FacetRequest> previousRequests) {
        List<FacetRequest> values = new ArrayList<>(facets.size());
        for (Facet facet: facets) {
            String facetName = facet.getName();
            FacetField facetField = FacetField.valueOf(facetName);
            List<SelectableFacet> previousSelections = FacetUtil.getPreviousSelections(facetField, previousRequests);
            List<SelectableFacet> selectables = FacetUtil.parseSingleFacet(facet, facetField, previousSelections);
            
            values.add(new FacetRequest()
                .setField(facetField)
                .setSelectables(selectables)
            );
        }
        
        return values;
    }

    /**
     * Get the member id from the source.
     * 
     * @param source
     * @return 
     */
    public static String getMemberId(String source) {
        Member member;
        try {
            member = mapper.readValue(source, Member.class);
            return Integer.toString(member.getNewMemberID());
        } catch (IOException ex) {
            Logger.getLogger(SearchResultService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    /**
     * Get the member id from the source.
     * 
     * @param id
     * @return 
     */
    public static String getMemberId(Object id) {
        try {
            return Integer.toString((Integer) id);
        } catch (NumberFormatException ex) {
            Logger.getLogger(SearchResultService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
