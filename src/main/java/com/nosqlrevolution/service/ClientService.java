package com.nosqlrevolution.service;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.settings.ImmutableSettings;
import java.util.logging.Logger;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.node.Node;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

/**
 *
 * @author cbrown
 */
public class ClientService {
    private static final Logger log = Logger.getLogger(ClientService.class.getName());
    public final static String INDEX = "member";
    public final static String TYPE = "status";
    public final static int FROM = 0;
    public final static int SIZE = 25;

    private static Client client = null;

    private ClientService() {}
    
    private void init() {
        System.out.println("Running with options " + INDEX + " " + TYPE);
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("cluster.name", "nosqlrevolution")
                .put("discovery.zen.ping.unicast.hosts", "10.1.10.100")
                .put("discovery.zen.ping.timeout", "10s")
                .put("discovery.zen.ping.multicast.enabled", "false")
                .build();
        
//        client = new TransportClient(settings)
//                .addTransportAddress(new InetSocketTransportAddress("10.1.10.100", 9300));
        Node node = nodeBuilder().settings(settings).client(true).data(false).node();
        client = node.client();
        client.admin().cluster().prepareHealth().setWaitForGreenStatus().execute().actionGet();
    }

    public static Client getClient() {
        if (client == null) {
            new ClientService().init();
        }
        
        return client;
    } 
}
