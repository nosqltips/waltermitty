package com.nosqlrevolution.waltermittyclient.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.nosqlrevolution.waltermittyclient.client.WalterMittyService;
import com.nosqlrevolution.waltermittyclient.model.SearchQuery;
import com.nosqlrevolution.waltermittyclient.shared.FieldVerifier;

/**
 * The server side implementation of the RPC service.
 *
 * @author noSqlOrBust
 */
@SuppressWarnings("serial")
public class WalterMittyServiceImpl extends RemoteServiceServlet implements
        WalterMittyService {

    /**
   * Escape an html string. Escaping data received from the client helps to
   * prevent cross-site script vulnerabilities.
   *
   * @param html the html string to escape
   * @return the escaped string
   */
  private String escapeHtml(String html) {
    if (html == null) {
      return null;
    }
    return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(
        ">", "&gt;");
  }

    public SearchQuery get(String memberId) throws IllegalArgumentException
    {
//        // Verify that the input is valid.
//        if (!FieldVerifier.isValidName(input)) {
//            // If the input is not valid, throw an IllegalArgumentException back to
//            // the client.
//            throw new IllegalArgumentException(
//                    "Name must be at least 4 characters long");
//        }

        RestService restService = new RestService();

        return restService.get(memberId);
    }


    public SearchQuery post(SearchQuery searchQuery) throws IllegalArgumentException {
        RestService restService = new RestService();
        return restService.post(searchQuery);
    }
}
