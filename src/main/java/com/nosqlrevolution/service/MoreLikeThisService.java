package com.nosqlrevolution.service;

import com.nosqlrevolution.enums.SearchField;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.action.search.SearchResponse;
import com.nosqlrevolution.model.SearchQuery;
import java.io.Serializable;
import java.util.logging.Logger;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.action.mlt.MoreLikeThisRequestBuilder;

/**
 * 
 * @author cbrown
 */
public class MoreLikeThisService implements Serializable {
    private static final Logger log = Logger.getLogger(MoreLikeThisService.class.getName());
    private final Client client = ClientService.getClient();
    
    public SearchQuery search(SearchQuery sq) {
        // Set page sizes to passed in values if not already set in query parser
        if (sq.getPageFrom() == null || sq.getPageFrom() < 0) {
            sq.setPageFrom(0);
        }
        if (sq.getPageSize() == null || sq.getPageSize() < 1) {
            sq.setPageSize(20);
        }

        MoreLikeThisRequestBuilder builder = 
                client.prepareMoreLikeThis(ClientService.INDEX, ClientService.TYPE, sq.getMemberId())
                .setSearchType(SearchType.DFS_QUERY_AND_FETCH)
                .setSearchFrom(sq.getPageFrom())
                .setSearchSize(sq.getPageSize())
                .setField(SearchField.getAllFields());
        
        System.out.println("Builder=" + builder.toString());
        SearchResponse response = builder.execute().actionGet();
        
        // Update the SearchQuery results
        SearchHits h = response.getHits();
        sq.setTimeMillis(Long.toString(response.getTookInMillis()));
        sq.setTotalResults(h.getTotalHits());
        sq.setAvailableResults(h.getHits().length);
        
        System.out.println("MoreLikeThisService " + response.getHits().getTotalHits());
        return new SearchQuery(SearchResultService.generateSearchOutput(h.getHits()), null);
    }
}
