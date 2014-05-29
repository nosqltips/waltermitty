package com.nosqlrevolution.service;

import com.nosqlrevolution.enums.BoostField;
import com.nosqlrevolution.model.Boost;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * 
 * @author cbrown
 */
public class BoostService implements Serializable {
    private static final Logger log = Logger.getLogger(BoostService.class.getName());

    public static List<Boost> getDefaultBoosts() {
        List<Boost> facetRequests = new ArrayList<>();
            facetRequests.add(new Boost(BoostField.BIRTH_YEAR));
            facetRequests.add(new Boost(BoostField.STATE));
            facetRequests.add(new Boost(BoostField.GENDER));
            facetRequests.add(new Boost(BoostField.NUM_CLAIMS));
            facetRequests.add(new Boost(BoostField.NUM_CONTRIBUTIONS));
            facetRequests.add(new Boost(BoostField.NUM_DEPENDENTS));
            facetRequests.add(new Boost(BoostField.NUM_PAYMENTS));
            facetRequests.add(new Boost(BoostField.TOTAL_BALANCES));
            facetRequests.add(new Boost(BoostField.TOTAL_CLAIMS));
            facetRequests.add(new Boost(BoostField.TOTAL_CONTRIBUTIONS));
            facetRequests.add(new Boost(BoostField.TOTAL_PAYMENTS));
            facetRequests.add(new Boost(BoostField.CPT_CODES_UNIQUE));

            return facetRequests;
    }
}
