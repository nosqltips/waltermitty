package com.nosqlrevolution.util;

import com.nosqlrevolution.enums.AggregationField;
import com.nosqlrevolution.model.SelectableFacet;
import java.util.ArrayList;
import java.util.List;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.global.Global;
import org.elasticsearch.search.aggregations.bucket.histogram.DateHistogram;
import org.elasticsearch.search.aggregations.bucket.histogram.Histogram;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.significant.SignificantTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.aggregations.metrics.cardinality.Cardinality;
import org.elasticsearch.search.aggregations.metrics.percentiles.Percentiles;
import org.elasticsearch.search.aggregations.metrics.percentiles.Percentiles.Percentile;
import org.elasticsearch.search.aggregations.metrics.stats.Stats;
import static org.elasticsearch.search.aggregations.AggregationBuilders.*;

/**
 *
 * @author cbrown
 */
public class AggregationUtil {
    public static AbstractAggregationBuilder getTerms(String name, String field, Integer size) {
        if (size == null) { size = 10; }
        return terms(name)
                .field(field)
                .size(size);
    }

    public static AbstractAggregationBuilder getAverage(String name, String field) {
        return avg(name)
                .field(field);
    }

    public static AbstractAggregationBuilder getCount(String name, String field) {
        return count(name)
                .field(field);
    }

    public static AbstractAggregationBuilder getStats(String name, String field) {
        return stats(name)
                .field(field);
    }

    public static AbstractAggregationBuilder getSignificantTerms(String name, String field, Integer size) {
        if (size == null) { size = 10; }
        return significantTerms(name)
                .field(field)
                .size(size);
    }

    public static AbstractAggregationBuilder getPercentiles(String name, String field, double[] percentiles) {
        if (percentiles == null || percentiles.length == 0) {
            return percentiles(name)
                    .field(field);
        } else {
            return percentiles(name)
                    .field(field)
                    .percentiles(percentiles);
        }
    }

    public static AbstractAggregationBuilder getUnique(String name, String field) {
        return cardinality(name)
                .field(field);
    }

    public static AbstractAggregationBuilder getHistogram(String name, String field, long interval) {
        return histogram(name)
                .field(field)
                .interval(interval)
                .minDocCount(0);
    }

    public static AbstractAggregationBuilder getDateHistogram(String name, String field, long interval) {
        return dateHistogram(name)
                .field(field)
                .interval(interval)
                .minDocCount(0);
    }

    public static AbstractAggregationBuilder getNested(String name, String path, AbstractAggregationBuilder subAggregation) {
        return nested(name)
                .path(path)
                .subAggregation(subAggregation);
    }


    public static void parseFacets(Aggregations aggregations) {
        for (Aggregation aggregation: aggregations.asList()) {
            try {
                // Possibly need to parse name since we're piggy-backing additional data for date-based facets.
                String aggName = aggregation.getName();
                AggregationField aggField = AggregationField.valueOf(aggName);

                List<SelectableFacet> nodes = parseSingleAggregation(aggregation, aggField);
            } catch (Exception e) {                    
//                LOG.warn("Could not add aggregation for type " + aggregation.getName());
                e.printStackTrace();
            }
        }
    }
    
    public static List<SelectableFacet> parseSingleAggregation(Aggregation aggregation, AggregationField aggField) {
        if (aggregation == null) {
            return null;
        }

        if (aggregation instanceof Terms) {
            parseTerms((Terms) aggregation, aggField);
        } else if (aggregation instanceof SignificantTerms) {
            parseSignificantTerms((SignificantTerms) aggregation, aggField);
        } else if (aggregation instanceof Histogram) {
            parseHistogram((Histogram) aggregation, aggField);
        } else if (aggregation instanceof DateHistogram) {
            parseDateHistogram((DateHistogram) aggregation, aggField);
        } else if (aggregation instanceof Range) {
            parseRange((Range) aggregation, aggField);
        } else if (aggregation instanceof Global) {
            parseGlobal((Global) aggregation, aggField);
        } else if (aggregation instanceof Avg) {
            parseAvg((Avg) aggregation, aggField);
        } else if (aggregation instanceof Cardinality) {
            parseCardinality((Cardinality) aggregation, aggField);
        } else if (aggregation instanceof Percentiles) {
            parsePercentiles((Percentiles) aggregation, aggField);
        } else if (aggregation instanceof Stats) {
            parseStats((Stats) aggregation, aggField);
        }

        return null;
    }

    public static List<SelectableFacet> parseTerms(Terms terms, AggregationField aggField) {
        List<SelectableFacet> simpleFacets = new ArrayList<>();
        for (org.elasticsearch.search.aggregations.bucket.terms.Terms.Bucket bucket: terms.getBuckets()) {
            simpleFacets.add(
                    new SelectableFacet()
                    .setName(bucket.getKey())
                    .setCount(bucket.getDocCount())
            );
        }

        return simpleFacets.isEmpty() ? null : simpleFacets;
    }

    public static List<SelectableFacet> parseSignificantTerms(SignificantTerms sigTerms, AggregationField aggField) {
        List<SelectableFacet> simpleFacets = new ArrayList<>();
        for (org.elasticsearch.search.aggregations.bucket.significant.SignificantTerms.Bucket bucket: sigTerms.getBuckets()) {
            simpleFacets.add(
                    new SelectableFacet()
                    .setName(bucket.getKey())
                    .setCount(bucket.getDocCount())
            );
        }

        return simpleFacets.isEmpty() ? null : simpleFacets;
    }

    public static List<SelectableFacet> parseHistogram(Histogram histogram, AggregationField aggField) {
        List<SelectableFacet> simpleFacets = new ArrayList<>();
        for (org.elasticsearch.search.aggregations.bucket.histogram.Histogram.Bucket bucket: histogram.getBuckets()) {
            simpleFacets.add(
                    new SelectableFacet()
                    .setName(bucket.getKey())
                    .setCount(bucket.getDocCount())
            );
        }

        return simpleFacets.isEmpty() ? null : simpleFacets;
    }

    public static List<SelectableFacet> parseDateHistogram(DateHistogram dateHistogram, AggregationField aggField) {
        List<SelectableFacet> simpleFacets = new ArrayList<>();
        for (org.elasticsearch.search.aggregations.bucket.histogram.DateHistogram.Bucket bucket: dateHistogram.getBuckets()) {
            simpleFacets.add(
                    new SelectableFacet()
                    .setName(bucket.getKey())
                    .setCount(bucket.getDocCount())
            );
        }

        return simpleFacets.isEmpty() ? null : simpleFacets;
    }

    public static List<SelectableFacet> parseRange(Range range, AggregationField aggField) {
        List<SelectableFacet> simpleFacets = new ArrayList<>();
        for (org.elasticsearch.search.aggregations.bucket.range.Range.Bucket bucket: range.getBuckets()) {
            simpleFacets.add(
                    new SelectableFacet()
                    .setName(bucket.getFrom() + "-" + bucket.getTo())
                    .setCount(bucket.getDocCount())
            );
        }

        return simpleFacets.isEmpty() ? null : simpleFacets;
    }

    public static List<SelectableFacet> parseGlobal(Global global, AggregationField aggField) {
        List<SelectableFacet> simpleFacets = new ArrayList<>();
        simpleFacets.add(
                new SelectableFacet()
                .setName(global.getName())
                .setCount(global.getDocCount())
        );

        return simpleFacets.isEmpty() ? null : simpleFacets;
    }

    public static List<SelectableFacet> parseAvg(Avg avg, AggregationField aggField) {
        List<SelectableFacet> simpleFacets = new ArrayList<>();
        simpleFacets.add(
                new SelectableFacet()
                .setName(avg.getName())
                .setValue(avg.getValue())
        );

        return simpleFacets.isEmpty() ? null : simpleFacets;
    }

    public static List<SelectableFacet> parseCardinality(Cardinality cardinality, AggregationField aggField) {
        List<SelectableFacet> simpleFacets = new ArrayList<>();
        simpleFacets.add(
                new SelectableFacet()
                .setName(cardinality.getName())
                .setCount(cardinality.getValue())
        );

        return simpleFacets.isEmpty() ? null : simpleFacets;
    }

    public static List<SelectableFacet> parsePercentiles(Percentiles percentiles, AggregationField aggField) {
        List<SelectableFacet> simpleFacets = new ArrayList<>();
        for (Percentile percentile: percentiles) {
            simpleFacets.add(
                    new SelectableFacet()
                    .setName(Double.toString(percentile.getPercent()))
                    .setValue(percentile.getValue())
            );
        }

        return simpleFacets.isEmpty() ? null : simpleFacets;
    }

    public static List<SelectableFacet> parseStats(Stats stats, AggregationField aggField) {
        List<SelectableFacet> simpleFacets = new ArrayList<>();
        simpleFacets.add(
                new SelectableFacet()
                .setName(stats.getName())
                .setCount(stats.getCount())
        );

        return simpleFacets.isEmpty() ? null : simpleFacets;
    }
}
