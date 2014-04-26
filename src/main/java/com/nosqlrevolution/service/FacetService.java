package com.nosqlrevolution.service;

import com.nosqlrevolution.enums.Field;
import com.nosqlrevolution.model.SimpleDateHistogramFacet;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.search.SearchResponse;
import com.nosqlrevolution.model.SimpleFacet;
import com.nosqlrevolution.util.FacetUtil;
import com.nosqlrevolution.util.QueryUtil;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

/**
 * 
 * @author cbrown
 */
public class FacetService implements Serializable {
    private static final Logger log = Logger.getLogger(FacetService.class.getName());
    private final Client client = ClientService.getClient();

    public List<SimpleFacet> trending() {
        SortBuilder sort = new FieldSortBuilder(Field.FACET_COUNT.getName())
                .order(SortOrder.DESC);
        
        SearchRequestBuilder builder = client.prepareSearch(ClientService.TRENDINGINDEX)
            .setSearchType(SearchType.QUERY_THEN_FETCH)
            .setQuery(QueryUtil.getMatchAllQuery())
//            .addSort(sort)
            .setFrom(0)
            .setSize(1000);
        
        SearchResponse response = builder.execute().actionGet();
        
        List<SimpleFacet> simpleFacets = SearchResultService.generateFacetOutput(response.getHits().getHits());
        Collections.sort(simpleFacets);
        return simpleFacets.subList(0, 20);
    }

    public List<SimpleFacet> topTags() {
        SortBuilder sort = new FieldSortBuilder(Field.FACET_COUNT.getName())
                .order(SortOrder.DESC);
        
        SearchRequestBuilder builder = client.prepareSearch(ClientService.TOPTAGSINDEX)
            .setSearchType(SearchType.QUERY_THEN_FETCH)
            .setQuery(QueryUtil.getMatchAllQuery())
//            .addSort(sort)
            .setFrom(0)
            .setSize(1000);
                
        SearchResponse response = builder.execute().actionGet();
        
        List<SimpleFacet> simpleFacets = SearchResultService.generateFacetOutput(response.getHits().getHits());
        Collections.sort(simpleFacets);
        return simpleFacets.subList(0, 20);
    }
    
    public List<SimpleFacet> topTweeters() {
        SearchRequestBuilder builder = client.prepareSearch(ClientService.INDEX)
            .setSearchType(SearchType.QUERY_THEN_FETCH)
            .setPostFilter(QueryUtil.getMatchAllFilter())
            .setFrom(0)
            .setSize(0)
            .addFacet(FacetUtil.getTermsFacet(Field.USER_SCREEN_NAME.getName(), 10, "tweeters"));
        
        SearchResponse response = builder.execute().actionGet();
        List<SimpleFacet> facets = null;
        if (response.getFacets() != null) {
            facets = FacetUtil.generateTermFacetOutput(response.getFacets().facets());
        }
        
        return facets;
    }

    public List<SimpleFacet> todaysTopTweeters() {
        SearchRequestBuilder builder = client.prepareSearch(ClientService.INDEX)
            .setSearchType(SearchType.QUERY_THEN_FETCH)
            .setQuery(QueryUtil.getRangeQueryBuilder(Field.CREATED_AT.getName(), DateTime.now().minusHours(24).toString(ISODateTimeFormat.dateTime()), null))
            .setFrom(0)
            .setSize(0)
            .addFacet(FacetUtil.getTermsFacet(Field.USER_SCREEN_NAME.getName(), 10, "tweeters"));
        
        SearchResponse response = builder.execute().actionGet();
        List<SimpleFacet> facets = null;
        if (response.getFacets() != null) {
            facets = FacetUtil.generateTermFacetOutput(response.getFacets().facets());
        }
        
        return facets;
    }

    public List<SimpleDateHistogramFacet> tweetsByHour() {
        SearchRequestBuilder builder = client.prepareSearch(ClientService.INDEX)
            .setSearchType(SearchType.QUERY_THEN_FETCH)
            .setFrom(0)
            .setSize(0)
            .addFacet(FacetUtil.getDateHistoramFacet(Field.CREATED_AT.getName()));
        
        SearchResponse response = builder.execute().actionGet();
        List<SimpleDateHistogramFacet> facets = null;
        if (response.getFacets() != null) {
            facets = FacetUtil.generateDateHistogramFacetOutput(response.getFacets().facets());
        }
        
        return facets;
    }
}
