package com.nosqlrevolution.waltermittyclient.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.nosqlrevolution.waltermittyclient.client.model.SearchQuery;

/**
 * The client side stub for the RPC service.
 *
 * @author noSqlOrBust
 */
@RemoteServiceRelativePath("greet")
public interface WalterMittyService extends RemoteService {
    com.nosqlrevolution.waltermittyclient.client.model.SearchQuery get(String memberId) throws IllegalArgumentException;
    SearchQuery post(SearchQuery searchQuery) throws IllegalArgumentException;
}
