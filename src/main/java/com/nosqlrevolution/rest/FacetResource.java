package com.nosqlrevolution.rest;

import com.nosqlrevolution.model.SimpleDateHistogramFacet;
import com.nosqlrevolution.model.SimpleFacet;
import com.nosqlrevolution.service.FacetService;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

/**
 * @author cbrown
 */
@Path("facet")
public class FacetResource {

    @Context
    private UriInfo context;

    private final FacetService service = new FacetService();
    
    @GET
    @Path("trending")
    @Produces("application/json")
    public List<SimpleFacet> trending() {
        return service.trending();
    }

    @GET
    @Path("topTags")
    @Produces("application/json")
    public List<SimpleFacet> topTags() {
        return service.topTags();
    }
    
    @GET
    @Path("topTweeters")
    @Produces("application/json")
    public List<SimpleFacet> topTweeters() {
        return service.topTweeters();
    }

    @GET
    @Path("todaysTopTweeters")
    @Produces("application/json")
    public List<SimpleFacet> todaysTopTweeters() {
        return service.todaysTopTweeters();
    }

    @GET
    @Path("tweetsByHour")
    @Produces("application/json")
    public List<SimpleDateHistogramFacet> tweetsByHour() {
        return service.tweetsByHour();
    }
}
