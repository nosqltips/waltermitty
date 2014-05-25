package com.nosqlrevolution.waltermittyclient.server;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.nosqlrevolution.waltermittyclient.client.Messages;
import com.nosqlrevolution.waltermittyclient.model.SearchQuery;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 *
 * @author noSqlOrBust
 */
public class RestService {

    private final String baseUrl = "http://www.wowcubed.com/api/";
    private final String searchUrl = baseUrl + "search";
    private final String mltUrl = baseUrl + "mlt";

    public RestService() {
    }

    // http://localhost:8080/RESTfulExample/json/product/get
    public SearchQuery get(String memberId)
    {
        StringBuilder searchQueryResults = new StringBuilder();

        BufferedReader br = null;
        try {
            URL url;
            if (memberId != null && !memberId.isEmpty())
            {
                url = new URL(searchUrl + "?memberId=" + memberId);
                System.out.println(searchUrl + "?memberId=" + memberId);
            }
            else
            {
                url = new URL(searchUrl );
                System.out.println(searchUrl);
            }

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                searchQueryResults.append(output);

                System.out.println(output);
            }

            conn.disconnect();

        } catch (IOException e) {

            e.printStackTrace();
        }

        SearchQuery searchResults = null;
        String resData = searchQueryResults.toString();
        if (!resData.equals(""))
        {
            // Convert the json to java bean object
            ObjectMapper mapper = new ObjectMapper();
            try {
                searchResults = mapper.readValue(resData.getBytes("UTF-8"), SearchQuery.class);
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return searchResults;
    }

    public SearchQuery post(SearchQuery searchQuery)
    {
        SearchQuery searchQueryResults = null;

        try {
            ClientConfig clientConfig = new DefaultClientConfig();
            clientConfig.getFeatures().put(
                    JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
            Client client = Client.create(clientConfig);
            WebResource webResource = client.resource(searchUrl);
            ClientResponse response = webResource.accept("application/json").type("application/json").post(ClientResponse.class, searchQuery);
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }

            searchQueryResults = response.getEntity(SearchQuery.class);
            System.out.println("Server response .... \n");
            printSearchQueryAsJson(searchQueryResults);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return searchQueryResults;
   }

    private void printSearchQueryAsJson(SearchQuery searchQueryResults)
    {
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(mapper.writeValueAsString(searchQueryResults));

        } catch (JsonGenerationException e) {

            e.printStackTrace();

        } catch (JsonMappingException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
    }
}