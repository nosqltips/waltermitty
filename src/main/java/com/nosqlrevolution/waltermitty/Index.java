package com.nosqlrevolution.waltermitty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nosqlrevolution.model.data.Member;
import com.nosqlrevolution.service.DataBaseService;
import com.nosqlrevolution.service.IndexService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * App - get data from mysql DB, build related object, then output as json file
 *
 * Queries member data and builds java objects that are output as json to a file.
 */
public class Index 
{
    public static final String MYSQL_CONNECT_STRING = "jdbc:mysql://localhost/data?user=root&password=admin";

    public static void main( String[] args )
    {

        DataBaseService dbService = new DataBaseService();

        ArrayList<Member> members = null;
        if (dbService != null)
        {
            System.out.println( "Begin querying database tables and building java objects..." );
            members = dbService.getMembers();
            System.out.println( "Completed querying database tables and building java objects..." );
        }
        System.out.println("Number of members found: " + members.size());

        ObjectMapper mapper = new ObjectMapper();
        IndexService service = new IndexService();
        int count = 0;
        try {
            List<String> out = new ArrayList();
            for (Member member: members) {
                out.add(mapper.writeValueAsString(member));
                count ++;
                if (count % 100 == 0) {
                    service.bulkInsert(out);
                    System.out.println("Inserting " + out.size() + " documents. Total=" + count);
                    count = 0;
                    out.clear();
                }
            }

            // Insert the last of the data
            service.bulkInsert(out);
            System.out.println("Inserting " + out.size() + " documents");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Process completed");
    }
}
