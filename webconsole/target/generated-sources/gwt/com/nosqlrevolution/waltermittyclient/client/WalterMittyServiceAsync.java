package com.nosqlrevolution.waltermittyclient.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface WalterMittyServiceAsync
{

    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.nosqlrevolution.waltermittyclient.client.WalterMittyService
     */
    void get( java.lang.String memberId, AsyncCallback<com.nosqlrevolution.waltermittyclient.client.model.SearchQuery> callback );


    /**
     * GWT-RPC service  asynchronous (client-side) interface
     * @see com.nosqlrevolution.waltermittyclient.client.WalterMittyService
     */
    void post( com.nosqlrevolution.waltermittyclient.client.model.SearchQuery searchQuery, AsyncCallback<com.nosqlrevolution.waltermittyclient.client.model.SearchQuery> callback );


    /**
     * Utility class to get the RPC Async interface from client-side code
     */
    public static final class Util 
    { 
        private static WalterMittyServiceAsync instance;

        public static final WalterMittyServiceAsync getInstance()
        {
            if ( instance == null )
            {
                instance = (WalterMittyServiceAsync) GWT.create( WalterMittyService.class );
            }
            return instance;
        }

        private Util()
        {
            // Utility class should not be instanciated
        }
    }
}
