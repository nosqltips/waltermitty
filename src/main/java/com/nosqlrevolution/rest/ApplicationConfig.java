package com.nosqlrevolution.rest;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author cbrown
 */
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.nosqlrevolution.rest.CountResource.class);
        resources.add(com.nosqlrevolution.rest.CrossOriginResourceSharingFilter.class);
        resources.add(com.nosqlrevolution.rest.FacetResource.class);
        resources.add(com.nosqlrevolution.rest.MltResource.class);
        resources.add(com.nosqlrevolution.rest.SearchResource.class);
    }
    
}
