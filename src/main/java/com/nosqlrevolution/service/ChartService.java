package com.nosqlrevolution.service;

import com.nosqlrevolution.enums.ChartField;
import com.nosqlrevolution.model.ChartRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * 
 * @author cbrown
 */
public class ChartService implements Serializable {
    private static final Logger log = Logger.getLogger(ChartService.class.getName());

    // TODO: can make this static
    public static List<ChartRequest> getCharts() {
        List<ChartRequest> chartRequests = new ArrayList<>();
//            chartRequests.add(new ChartRequest(ChartField.BIRTH_YEAR, 10));
            chartRequests.add(new ChartRequest(ChartField.GENDER, 3));
            chartRequests.add(new ChartRequest(ChartField.STATE, 52));
            chartRequests.add(new ChartRequest(ChartField.MEMBER_CONTRIBUTIONS));
            chartRequests.add(new ChartRequest(ChartField.COMPANY_CONTRIBUTIONS));
            chartRequests.add(new ChartRequest(ChartField.MEMBER_PAYMENTS));

            return chartRequests;
    }
}
