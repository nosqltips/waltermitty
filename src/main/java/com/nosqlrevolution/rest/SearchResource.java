package com.nosqlrevolution.rest;

import com.nosqlrevolution.model.SearchQuery;
import com.nosqlrevolution.model.SearchResult;
import com.nosqlrevolution.service.SearchService;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * @author cbrown
 */
@Path("search")
public class SearchResource {
    private static final Logger log = Logger.getLogger(SearchResource.class.getName());

    @Context
    private UriInfo context;
    
    private final SearchService search = new SearchService();
    
    /**
     * Standard search method call.
     * 
     * @param terms
     * @param screenName
     * @param pageFrom
     * @param pageSize
     * @return 
     */
    @GET
    @Produces("application/json")    
    public SearchResult search(
            @QueryParam("terms") String terms, 
            @QueryParam("screenName") String screenName, 
            @QueryParam("pageFrom") Integer pageFrom, 
            @QueryParam("pageSize") Integer pageSize) {

        SearchQuery query = new SearchQuery();
        query.setTerms(terms);
        query.setScreenName(screenName);
        if (pageFrom != null) {
            query.setPageFrom(pageFrom);
        }
        if (pageSize != null) {
            query.setPageSize(pageSize);
        }
        
        return search.search(query);
    }

    /**
     * Used to retrieve the top N tweets ordered by most recent.
     * 
     * @param pageFrom
     * @param pageSize
     * @return 
     */
    @GET
    @Path("top")
    @Produces("application/json")
    public SearchResult search(
            @QueryParam("pageFrom") Integer pageFrom, 
            @QueryParam("pageSize") Integer pageSize) {
        
        SearchQuery query = new SearchQuery();
        if (pageFrom != null) {
            query.setPageFrom(pageFrom);
        }
        if (pageSize != null) {
            query.setPageSize(pageSize);
        }
        
        return search.searchTopN(query);
    }
}

