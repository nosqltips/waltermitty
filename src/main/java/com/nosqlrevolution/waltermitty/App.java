package com.nosqlrevolution.waltermitty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nosqlrevolution.service.DataBaseService;
import com.nosqlrevolution.model.data.Member;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * App - get data from mysql DB, build related object, then output as json file
 *
 * Queries member data and builds java objects that are output as json to a file.
 */
public class App 
{
    public static final String MYSQL_CONNECT_STRING = "jdbc:mysql://localhost/data?user=data&password=data";
    public static final String JSON_OUTPUT_FILE_PATH = "flattened-NotSoPretty.json";

    public static void main( String[] args )
    {
        System.out.println( "Getting member info... :-)" );
        if (JSON_OUTPUT_FILE_PATH == null && JSON_OUTPUT_FILE_PATH.isEmpty())
        {
            System.out.println("Warning: App.JSON_OUTPUT_FILE_PATH is not configured");
        }

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
        try {
            if (JSON_OUTPUT_FILE_PATH != null && !JSON_OUTPUT_FILE_PATH.isEmpty())
            {
                System.out.println("Begin writing json to " + JSON_OUTPUT_FILE_PATH);
                mapper.writeValue(new File(JSON_OUTPUT_FILE_PATH), members);
                System.out.println("Completed writing: " + JSON_OUTPUT_FILE_PATH);
            }
            else
            {
                System.out.println("Warning: App.JSON_OUTPUT_FILE_PATH is not configured");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Process completed");
    }
}
