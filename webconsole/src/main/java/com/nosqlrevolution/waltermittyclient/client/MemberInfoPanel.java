package com.nosqlrevolution.waltermittyclient.client;

import com.google.gwt.user.client.ui.FlowPanel;

/**
 * Created by noSqlOrBust on 5/25/2014.
 */
public class MemberInfoPanel extends FlowPanel {
    public MemberInfoPanel(String memberId) {
        buildUI();

        // Get Member Info
        initializeUI(memberId);
    }

    private void buildUI() {

    }

    private void initializeUI(String memberId) {

//        walterMittyService.get(textToServer, new AsyncCallback<SearchQuery>() {
//            @Override
//            public void onFailure(Throwable throwable) {
//            }
//
//            @Override
//            public void onSuccess(SearchQuery searchQuery) {
//                if (searchQuery == null) {
//
//                }
//            }
//
//            );
//        }
    }
}
