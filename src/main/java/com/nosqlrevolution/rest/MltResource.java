package com.nosqlrevolution.rest;

import com.nosqlrevolution.model.SearchQuery;
import com.nosqlrevolution.service.MoreLikeThisService;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * REST Web Service
 *
 * @author cbrown
 */
@Path("mlt")
public class MltResource {
    private static final Logger log = Logger.getLogger(MltResource.class.getName());

    @Context
    private UriInfo context;
    
    private final MoreLikeThisService mlt = new MoreLikeThisService();
    
    @GET
    @Produces("application/json")
    public SearchQuery search(@QueryParam("memberId") String memberId) {
        SearchQuery query = new SearchQuery();
        query.setMemberId(memberId);
        return mlt.search(query);    
    }

    @POST
    @Consumes("application/json")    
    @Produces("application/json")    
    public SearchQuery search(SearchQuery query) {
        
        return mlt.search(query);
    }
}

