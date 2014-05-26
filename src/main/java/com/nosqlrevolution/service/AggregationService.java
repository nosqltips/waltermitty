package com.nosqlrevolution.service;

import com.nosqlrevolution.enums.AggregationField;
import com.nosqlrevolution.model.FacetRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * 
 * @author cbrown
 */
public class AggregationService implements Serializable {
    private static final Logger log = Logger.getLogger(AggregationService.class.getName());

    // TODO: can make this static
    public static List<FacetRequest> getDefaultFacets() {
        List<FacetRequest> facetRequests = new ArrayList<>();
            facetRequests.add(new FacetRequest(AggregationField.BIRTH_YEAR));
            facetRequests.add(new FacetRequest(AggregationField.GENDER));
            facetRequests.add(new FacetRequest(AggregationField.STATE));
            facetRequests.add(new FacetRequest(AggregationField.ZIP));
            facetRequests.add(new FacetRequest(AggregationField.NUM_BALANCES));
            facetRequests.add(new FacetRequest(AggregationField.NUM_CLAIMS));
            facetRequests.add(new FacetRequest(AggregationField.NUM_CONTRIBUTIONS));
            facetRequests.add(new FacetRequest(AggregationField.NUM_DEPENDENTS));
            facetRequests.add(new FacetRequest(AggregationField.NUM_EMPLOYEE_CONTRIBUTIONS));
            facetRequests.add(new FacetRequest(AggregationField.NUM_EMPOYER_CONTRIBUTIONS));
            facetRequests.add(new FacetRequest(AggregationField.NUM_PAYMENTS));
            facetRequests.add(new FacetRequest(AggregationField.TOTAL_BALANCES));
            facetRequests.add(new FacetRequest(AggregationField.TOTAL_CLAIMS));
            facetRequests.add(new FacetRequest(AggregationField.TOTAL_CLAIMS_PATIENT));
            facetRequests.add(new FacetRequest(AggregationField.TOTAL_CONTRIBUTIONS));
            facetRequests.add(new FacetRequest(AggregationField.TOTAL_EMPLOYEE_CONTRIBUTIONS));
            facetRequests.add(new FacetRequest(AggregationField.TOTAL_EMPLOYER_CONTRIBUTIONS));
            facetRequests.add(new FacetRequest(AggregationField.TOTAL_PAYMENTS));
            facetRequests.add(new FacetRequest(AggregationField.CPT_CODES_ALL, 20));
            facetRequests.add(new FacetRequest(AggregationField.CPT_CODES_UNIQUE, 20));
            facetRequests.add(new FacetRequest(AggregationField.CLAIM_TYPE));

            return facetRequests;
    }
}
