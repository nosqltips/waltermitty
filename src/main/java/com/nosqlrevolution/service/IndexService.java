package com.nosqlrevolution.service;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;

/**
 * 
 * @author cbrown
 */
public class IndexService implements Serializable {
    private static final Logger log = Logger.getLogger(IndexService.class.getName());
    private final Client client = ClientService.getClient();
    
    public boolean bulkInsert(List<String> sources) {
        BulkRequestBuilder builder = client.prepareBulk();

        for (String source: sources) {
            builder.add(client.prepareIndex(ClientService.INDEX, ClientService.TYPE)
                    .setId(SearchResultService.getMemberId(source))
                    .setSource(source)
                );
        }
        
        BulkResponse response = builder.execute().actionGet();
        
        if (response.hasFailures()) {
            log.warning(response.buildFailureMessage());
        }
        
        return ! response.hasFailures();
    }
}