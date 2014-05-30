package com.nosqlrevolution.service;

import com.nosqlrevolution.model.Chart;
import com.nosqlrevolution.model.ChartValue;
import com.nosqlrevolution.model.StatsValues;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cbrown
 */
public class StatisticsService {

    /**
     * Take one of the date charts and compute some statistics and projections from it.
     * 
     * @param chart 
     * @param totalMembers 
     * @param latestYear 
     * @return  
     */
    public static StatsValues calculateStats(Chart chart, int totalMembers, String latestYear) {
        List<ChartValue> chartValues = chart.getValues();
        List<Double> monthlyAverages = new ArrayList<>();
        Double totalSum = 0.0D;
        Double sumLatestYear = 0.0D;
        int latestYearContributions = 0;
        
        // First, sum up all of the values
        for (ChartValue value: chartValues) {
            Double sum = getSum(value.getChartValues());
            monthlyAverages.add(sum/totalMembers);
            totalSum += sum;
            
            // Look for the 2014 values and sum
            if (value.getName().contains(latestYear)) {
                sumLatestYear += sum;
                latestYearContributions ++;
            }
        }
        
        Double overallAverage = (chartValues.size() > 0) ? totalSum/chartValues.size() : 0.0D;
        Double projectedYearEndTotalContribution = sumLatestYear * ((12 - latestYearContributions) * overallAverage);
        
        return new StatsValues()
                .setName(chart.getName())
                .setMonthlyAverages(monthlyAverages)
                .setOverallAverage(overallAverage)
                .setProjectedYearEndTotal(projectedYearEndTotalContribution);
    }
    
    public static Double getSum(List<ChartValue> subValues) {
        for (ChartValue value: subValues) {
            if (value.getName().equals("sum")) {
                return value.getValue();
            }
        }
        
        return 0.0D;
    }
}
