package com.nosqlrevolution.rest;

import com.nosqlrevolution.model.SearchQuery;
import com.nosqlrevolution.service.SearchService;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
     * @param memberId
     * @return 
     */
    @GET
    @Produces("application/json")    
    public SearchQuery search(
            @QueryParam("memberId") String memberId) {

        SearchQuery query = new SearchQuery();
        query.setMemberId(memberId);
        
        return search.search(query);
    }

    /**
     * Standard search method call.
     * 
     * @param query
     * @return 
     */
    @POST
    @Produces("application/json")    
    public SearchQuery search(
            @QueryParam("searchQuery") SearchQuery query) {
        
        return search.search(query);
    }
}
