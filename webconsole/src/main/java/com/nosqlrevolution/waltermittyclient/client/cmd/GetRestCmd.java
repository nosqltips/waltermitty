package com.nosqlrevolution.waltermittyclient.client.cmd;

import com.google.gwt.core.client.Scheduler;

import java.util.Map;

/**
 * Created by noSqlOrBust on 5/25/2014.
 */
public class GetRestCmd implements Scheduler.ScheduledCommand {
    private String memberId;
    private Map<String, String> queryParams;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    public void clear() {
        this.memberId = null;
        this.queryParams = null;
    }

    @Override
    public void execute() {
    }
}
