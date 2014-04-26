package com.nosqlrevolution.service;

import com.nosqlrevolution.enums.Field;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.action.search.SearchResponse;
import com.nosqlrevolution.model.SearchQuery;
import com.nosqlrevolution.model.SearchResult;
import com.nosqlrevolution.util.QueryUtil;
import java.io.Serializable;
import java.util.logging.Logger;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;

/**
 * 
 * @author cbrown
 */
public class MoreLikeThisService implements Serializable {
    private static final Logger log = Logger.getLogger(MoreLikeThisService.class.getName());
    private final Client client = ClientService.getClient();
    
    public SearchResult search(SearchQuery sq) {
        // Set page sizes to passed in values if not already set in query parser
        if (sq.getPageSize() == -1) {
            sq.setPageSize(ClientService.SIZE);
        }
        sq.setPageFrom((sq.getPage() -1)  * sq.getPageSize());

        // Need to perform a get for the original document
        GetRequestBuilder get = client.prepareGet(ClientService.INDEX, ClientService.TYPE, sq.getDocId());
        GetResponse getR = get.execute().actionGet();
        if (getR == null) {
            return null;
        }
        String text = (String)getR.getSource().get(Field.TEXT.getName());
        
        // Do a little bit of cleanup of the terms
        String[] terms = text.split("\\s|,");
        StringBuilder sb = new StringBuilder();
        for (String term: terms) {
            if (! (term.startsWith("#") 
                    || term.startsWith("@") 
                    || term.startsWith("http:") 
                    || term.toLowerCase().equals("rt") 
                    || term.toLowerCase().equals("oh"))) {
                sb.append(term).append(" ");
            }
        }
        
        SortBuilder sort = new FieldSortBuilder(Field.CREATED_AT.getName())
                .order(SortOrder.DESC);

        SortBuilder rel = new ScoreSortBuilder()
                .order(SortOrder.DESC);

        SearchRequestBuilder builder = client.prepareSearch(ClientService.INDEX)
                .setSearchType(SearchType.QUERY_THEN_FETCH)
                .setQuery(QueryUtil.getTextQuery(Field.TEXT.getName(), sb.toString()))
                .setFrom(sq.getPageFrom())
                .setSize(sq.getPageSize())
                .addSort(rel)
                .addSort(sort);
        
        System.out.println("Builder=" + builder.toString());
        SearchResponse response = builder.execute().actionGet();
        
        // Update the SearchQuery results
        SearchHits h = response.getHits();
        sq.setTimeMillis(Long.toString(response.getTookInMillis()));
        sq.setTotalResults(h.getTotalHits());
        sq.setAvailableResults(h.getHits().length);
        
        System.out.println("MoreLikeThisService " + response.getHits().getTotalHits());
        return new SearchResult(sq, SearchResultService.generateSearchOutput(h.getHits()));
    }
}
