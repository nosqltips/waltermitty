package com.nosqlrevolution.service;

import com.nosqlrevolution.enums.Field;
import com.nosqlrevolution.model.BuilderModel;
import com.nosqlrevolution.model.BuilderModel.BooleanType;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import java.util.ArrayList;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.search.SearchResponse;
import com.nosqlrevolution.model.SearchQuery;
import com.nosqlrevolution.model.SearchResult;
import com.nosqlrevolution.model.SimpleFacet;
import com.nosqlrevolution.util.FacetUtil;
import com.nosqlrevolution.util.QueryUtil;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;

/**
 * 
 * @author cbrown
 */
public class SearchService implements Serializable {
    private static final Logger log = Logger.getLogger(SearchService.class.getName());
    private final Client client = ClientService.getClient();
    
    /**
     * Stock search method.
     * 
     * @param sq
     * @return 
     */
    public SearchResult search(SearchQuery sq) {
        // Set page sizes to passed in values if not already set in query parser
        if (sq.getPageSize() == -1) {
            sq.setPageSize(ClientService.SIZE);
        }
        //if (sq.getPageFrom() == -1) {
            //sq.setPageFrom(ClientService.FROM);
            sq.setPageFrom((sq.getPage() -1)  * sq.getPageSize());
        //}

        ArrayList<BuilderModel> builders = new ArrayList<>();
        if (sq.getTerms() != null) {
            QueryUtil.addAllTerms(builders, sq.getTerms(), Field.TEXT.getName());
        } else if (sq.getScreenName() != null) {
            QueryUtil.addAllScreenNames(builders, sq.getScreenName(), "user.screen_name");
        } else {
            return null;
        }

        QueryBuilder qb;
        if ((builders.size() > 1) || (builders.get(0).getBooleanType() != BooleanType.MUST)) {
            qb = QueryUtil.getBooleanQuery(builders);
        } else {
//            if (builders.get(0).getQueryType().equals(QueryType.QUERY)) {
                qb = builders.get(0).getQueryBuilder();
//            } else {
//                qb = QueryUtil.getFilteredQuery(null, builders.get(0).getFilterBuilder());
//            }
        }

        SortBuilder sort = new FieldSortBuilder(Field.CREATED_AT.getName());
        sort.order(SortOrder.DESC);
        
        SearchRequestBuilder builder = client.prepareSearch(ClientService.INDEX)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setQuery(qb)
                .setFrom(sq.getPageFrom())
                .setSize(sq.getPageSize())
                .addSort(sort);
        
        // Add facets
        builder.addFacet(FacetUtil.getTermsFacet(Field.TEXT.getName(), 10, "terms"));
        builder.addFacet(FacetUtil.getTermsFacet(Field.USER_SCREEN_NAME.getName(), 10, "tweeters"));
        System.out.println("Builder=" + builder.toString());
        
        SearchResponse response = builder.execute().actionGet();
        Map<String, List<SimpleFacet>> facets = null;
        if (response.getFacets() != null) {
            facets = FacetUtil.generateMultipleTermFacetOutput(response.getFacets().facets());
        }
        
        // Update the SearchQuery results
        SearchHits h = response.getHits();
        sq.setTimeMillis(Long.toString(response.getTookInMillis()));
        sq.setTotalResults(h.getTotalHits());
        sq.setAvailableResults(h.getHits().length);
        
        System.out.println("SearchService " + response.getHits().getTotalHits());
        return new SearchResult(sq, SearchResultService.generateSearchOutput(h.getHits()), facets);
    }

    /**
     * Grab the top N results ordered by most recent.
     * 
     * @param sq
     * @return 
     */
    public SearchResult searchTopN(SearchQuery sq) {
        // Set page sizes to passed in values if not already set in query parser
        if (sq.getPageSize() == -1) {
            sq.setPageSize(ClientService.SIZE);
        }
        if (sq.getPageFrom() == -1) {
            sq.setPageFrom(ClientService.FROM);
        }

        SortBuilder sort = new FieldSortBuilder(Field.CREATED_AT.getName());
        sort.order(SortOrder.DESC);
        
        SearchRequestBuilder builder = client.prepareSearch(ClientService.INDEX)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setQuery(QueryUtil.getMatchAllQuery())
                .setFrom(sq.getPageFrom())
                .setSize(sq.getPageSize())
                .addSort(sort);
        
        SearchResponse response = builder.execute().actionGet();
        
        // Update the SearchQuery results
        SearchHits h = response.getHits();
        sq.setTimeMillis(Long.toString(response.getTookInMillis()));
        sq.setTotalResults(h.getTotalHits());
        sq.setAvailableResults(h.getHits().length);
        
        System.out.println("TopN " + response.getHits().getTotalHits());
        return new SearchResult(sq, SearchResultService.generateSearchOutput(h.getHits()));
    }
}
