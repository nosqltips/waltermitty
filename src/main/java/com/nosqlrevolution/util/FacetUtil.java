package com.nosqlrevolution.util;

import com.nosqlrevolution.enums.Field;
import com.nosqlrevolution.model.SimpleDateHistogramFacet;
import com.nosqlrevolution.model.SimpleFacet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.search.facet.Facet;
import org.elasticsearch.search.facet.FacetBuilder;
import static org.elasticsearch.search.facet.FacetBuilders.*;
import org.elasticsearch.search.facet.datehistogram.DateHistogramFacet;
import org.elasticsearch.search.facet.terms.TermsFacet;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

/**
 *
 * @author cbrown
 */
public class FacetUtil {
    public static FacetBuilder getTermsFacet(String field, int size, String name) {
        return termsFacet(name)
                .field(field)
                .size(size);
    }

    public static FacetBuilder getHistogramFacet(String field) {
        return histogramFacet("histogramFacet")
                .field(field)
                .interval(50);
    }

    public static FacetBuilder getDateHistoramFacet(String field) {
        return dateHistogramFacet("dateFacet")
                .field(field)
                .interval("hour")
                .facetFilter(QueryUtil.getRangeFilterBuilder(Field.CREATED_AT.getName(), DateTime.now().minusHours(23).toString(ISODateTimeFormat.dateTime()), null));
    }

    public static List<SimpleFacet> generateTermFacetOutput(List<org.elasticsearch.search.facet.Facet> facets) {
        List<SimpleFacet> simple = new ArrayList<>();
        for (Facet facet: facets) {
             if (facet instanceof TermsFacet) {
                 TermsFacet termsFacet = (TermsFacet) facet;
                 List<org.elasticsearch.search.facet.terms.TermsFacet.Entry> entries = (List<org.elasticsearch.search.facet.terms.TermsFacet.Entry>) termsFacet.getEntries();
                 for (org.elasticsearch.search.facet.terms.TermsFacet.Entry entry: entries) {
                     if (! entry.getTerm().toString().equals("http") && ! entry.getTerm().toString().equals("t.co")) {
                        simple.add(new SimpleFacet(entry.getTerm().toString(), entry.getCount()));
                     }
                 }
             }
        }
        return simple;
    }

    public static Map<String, List<SimpleFacet>> generateMultipleTermFacetOutput(List<org.elasticsearch.search.facet.Facet> facets) {
        Map<String, List<SimpleFacet>> multipleFacets = new HashMap<>();
        for (Facet facet: facets) {
             List<SimpleFacet> simple = new ArrayList<>();
             if (facet instanceof TermsFacet) {
                 TermsFacet termsFacet = (TermsFacet) facet;
                 List<org.elasticsearch.search.facet.terms.TermsFacet.Entry> entries = (List<org.elasticsearch.search.facet.terms.TermsFacet.Entry>) termsFacet.getEntries();
                 for (org.elasticsearch.search.facet.terms.TermsFacet.Entry entry: entries) {
                     if (! entry.getTerm().toString().equals("http") && ! entry.getTerm().toString().equals("t.co")) {
                        simple.add(new SimpleFacet(entry.getTerm().toString(), entry.getCount()));
                     }
                 }
             }
             multipleFacets.put(facet.getName(), simple);
        }
        return multipleFacets;
    }

    public static List<SimpleDateHistogramFacet> generateDateHistogramFacetOutput(List<org.elasticsearch.search.facet.Facet> facets) {
        List<SimpleDateHistogramFacet> simple = new ArrayList<>();
        for (Facet facet: facets) {
             if (facet instanceof DateHistogramFacet) {
                 DateHistogramFacet termsFacet = (DateHistogramFacet) facet;
                 List<org.elasticsearch.search.facet.datehistogram.DateHistogramFacet.Entry> entries = (List<org.elasticsearch.search.facet.datehistogram.DateHistogramFacet.Entry>) termsFacet.getEntries();
                 for (org.elasticsearch.search.facet.datehistogram.DateHistogramFacet.Entry entry: entries) {
                     simple.add(new SimpleDateHistogramFacet(entry.getTime(), entry.getCount()));
                 }
             }
        }
        return Lists.reverse(simple);
    }
}
