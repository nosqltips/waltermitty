package com.nosqlrevolution.service;

import com.nosqlrevolution.enums.FacetField;
import com.nosqlrevolution.model.FacetRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * 
 * @author cbrown
 */
public class FacetService implements Serializable {
    private static final Logger log = Logger.getLogger(FacetService.class.getName());

    // TODO: can make this static
    public static List<FacetRequest> getDefaultFacets() {
        List<FacetRequest> facetRequests = new ArrayList<>();
            facetRequests.add(new FacetRequest(FacetField.BIRTH_YEAR));
            facetRequests.add(new FacetRequest(FacetField.GENDER));
            facetRequests.add(new FacetRequest(FacetField.STATE));
            facetRequests.add(new FacetRequest(FacetField.ZIP));
            facetRequests.add(new FacetRequest(FacetField.NUM_BALANCES));
            facetRequests.add(new FacetRequest(FacetField.NUM_CLAIMS));
            facetRequests.add(new FacetRequest(FacetField.NUM_CONTRIBUTIONS));
            facetRequests.add(new FacetRequest(FacetField.NUM_DEPENDENTS));
            facetRequests.add(new FacetRequest(FacetField.NUM_EMPLOYEE_CONTRIBUTIONS));
            facetRequests.add(new FacetRequest(FacetField.NUM_EMPOYER_CONTRIBUTIONS));
            facetRequests.add(new FacetRequest(FacetField.NUM_PAYMENTS));
            facetRequests.add(new FacetRequest(FacetField.TOTAL_BALANCES));
            facetRequests.add(new FacetRequest(FacetField.TOTAL_CLAIMS));
            facetRequests.add(new FacetRequest(FacetField.TOTAL_CLAIMS_PATIENT));
            facetRequests.add(new FacetRequest(FacetField.TOTAL_CONTRIBUTIONS));
            facetRequests.add(new FacetRequest(FacetField.TOTAL_EMPLOYEE_CONTRIBUTIONS));
            facetRequests.add(new FacetRequest(FacetField.TOTAL_EMPLOYER_CONTRIBUTIONS));
            facetRequests.add(new FacetRequest(FacetField.TOTAL_PAYMENTS));
            facetRequests.add(new FacetRequest(FacetField.CPT_CODES_ALL));
            facetRequests.add(new FacetRequest(FacetField.CPT_CODES_UNIQUE));

            return facetRequests;
    }
}
