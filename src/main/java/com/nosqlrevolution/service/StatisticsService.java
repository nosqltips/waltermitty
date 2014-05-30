package com.nosqlrevolution.service;

import com.nosqlrevolution.model.Chart;
import com.nosqlrevolution.model.ChartValue;
import com.nosqlrevolution.model.StatsValues;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cbrown
 */
public class StatisticsService {
    DecimalFormat df = new DecimalFormat("#################.##");
    
    /**
     * Take one of the date charts and compute some statistics and projections from it.
     * 
     * @param chart 
     * @param totalMembers 
     * @param latestYear 
     * @return  
     */
    public static StatsValues calculateStats(Chart chart, double totalMembers, String latestYear) {
        List<ChartValue> chartValues = chart.getValues();
        List<ChartValue> monthlyAverages = new ArrayList<>();
        Double totalSum = 0.0D;
        Double sumLatestYear = 0.0D;
        int latestYearContributions = 0;
        // First, sum up all of the values
        for (ChartValue value: chartValues) {
            Double sum = getSum(value.getChartValues());
            // Looking for the average per member.
            sum = sum/totalMembers;
            monthlyAverages.add(new ChartValue(value.getName(), sum));
            totalSum += sum;
            
            // Look for the 2014 values and sum
            if (value.getName().contains(latestYear)) {
                sumLatestYear += sum;
                latestYearContributions ++;
            }
        }
        
        Double overallAverage = (chartValues.size() > 0) ? totalSum/chartValues.size() : 0.0D;
        Double projectedYearEndTotalContribution = sumLatestYear + ((12 - latestYearContributions) * overallAverage);

        return new StatsValues()
                .setName(chart.getField().toString())
                .setMonthlyAverages(monthlyAverages)
                .setOverallAverage(round(overallAverage))
                .setProjectedYearEndTotal(round(projectedYearEndTotalContribution));
    }
    
    public static Double getSum(List<ChartValue> subValues) {
        for (ChartValue value: subValues) {
            if (value.getName().equals("sum")) {
                return value.getValue();
            }
        }
        
        return 0.0D;
    }

    private static Double round(Double d) {
        return (double)Math.round(d * 100) / 100;        
    }
}
