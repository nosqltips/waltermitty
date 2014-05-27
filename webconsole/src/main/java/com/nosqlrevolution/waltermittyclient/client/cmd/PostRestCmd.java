package com.nosqlrevolution.waltermittyclient.client.cmd;

import com.google.gwt.core.client.Scheduler;
import com.nosqlrevolution.waltermittyclient.model.SearchQuery;

import java.util.Map;

/**
 * Created by noSqlOrBust on 5/25/2014.
 */
public class PostRestCmd  implements Scheduler.ScheduledCommand {
    private SearchQuery searchQuery;
    private Map<String, String> queryParams;

    public SearchQuery getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(SearchQuery searchQuery) {
        this.searchQuery = searchQuery;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    @Override
    public void execute() {
    }
}
