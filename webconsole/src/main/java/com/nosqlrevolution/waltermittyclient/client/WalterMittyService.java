package com.nosqlrevolution.waltermittyclient.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.nosqlrevolution.waltermittyclient.model.SearchQuery;

/**
 * The client side stub for the RPC service.
 *
 * @author noSqlOrBust
 */
@RemoteServiceRelativePath("greet")
public interface WalterMittyService extends RemoteService {
    SearchQuery get(String memberId) throws IllegalArgumentException;
    SearchQuery post(SearchQuery searchQuery) throws IllegalArgumentException;
}
