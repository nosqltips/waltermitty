package com.nosqlrevolution.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nosqlrevolution.enums.Field;
import com.nosqlrevolution.model.Member;
import com.nosqlrevolution.model.Result;
import com.nosqlrevolution.model.SimpleFacet;
import com.nosqlrevolution.util.DateUtil;
import com.nosqlrevolution.util.RegexUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.elasticsearch.search.SearchHit;

/**
 *
 * @author cbrown
 */
public class SearchResultService {
    /**
     * Generate the search results to send to the client.
     * 
     * @param hits
     * @return 
     */
    public static List<Result> generateSearchOutput(SearchHit[] hits) {
        List<Result> values = new ArrayList<>(hits.length);
        for (SearchHit hit : hits) {
            //DisplayValue item = mapper.readValue((String) fields.get(Field.DISPLAY_VALUES.getName()), Result.class);
            Map<String, Object> fields = hit.sourceAsMap();
            //Map<String, String> display = (HashMap) fields.get(Field.DISPLAY_VALUES.getName());
            Result result = new Result();
            result.setDocId(hit.getId());
            String text = (String) fields.get(Field.TEXT.getName());
            result.setText(mangle(text));
            result.setSource((String) fields.get(Field.SOURCE.getName()));
            result.setCreatedAt(DateUtil.formatRelativeTime((String) fields.get(Field.CREATED_AT.getName())));
            result.setSource((String) fields.get(Field.SOURCE.getName()));
            Map<String, Object> user = (HashMap) fields.get(Field.USER.getName());
            result.setUserId(safeUserId(user.get(Field.ID.getName())));
            result.setScreenName((String) user.get(Field.SCREEN_NAME.getName()));
            result.setScore(hit.getScore());
            values.add(result);           
        }
        return values;
    }
        
    public static String mangle(String text) {
        // Replace URLS with HREFs
        text = RegexUtil.url.matcher(text)
                .replaceAll("<a href=\"$1\" target=\"_blank\" onmouseout=\"this.className=''\" onmouseover=\"this.className='ui-state-hover'\">$1</a>");
        // Replace @Refs with HREFS
        text = RegexUtil.atRef.matcher(text)
                .replaceAll("<a href=\"http://www.twitter.com/$2\" target=\"_blank\" onmouseout=\"this.className=''\" onmouseover=\"this.className='ui-state-hover'\">$1</a>");

        text = RegexUtil.temp.matcher(text)
                .replaceAll("<a href=\"http://www.twitter.com/search?q=$2\" target=\"_blank\" onmouseout=\"this.className=''\" onmouseover=\"this.className='ui-state-hover'\">$1</a>");
        
        return text;
    }
    
    public static List<SimpleFacet> generateFacetOutput(SearchHit[] hits) {
        List<SimpleFacet> values = new ArrayList<>(hits.length);
        for (SearchHit hit : hits) {
            Map<String, Object> fields = hit.sourceAsMap();
            
            String name = (String) fields.get(Field.FACET_NAME.getName());
            Integer count = (Integer) fields.get(Field.FACET_COUNT.getName());
            SimpleFacet result = new SimpleFacet(name, count);
            values.add(result);           
        }
        
        return values;
    }

    public static List<Member> generateTweets(SearchHit[] hits) {
        ObjectMapper mapper = new ObjectMapper();
        List<Member> values = new ArrayList<>(hits.length);
        for (SearchHit hit: hits) {
            try {
                Member m = mapper.readValue(hit.sourceAsString(), Member.class);
                //m.setId(hit.getId());
                values.add(m);
            } catch (IOException ex) {
                System.out.println(hit.sourceAsString());
                Logger.getLogger(SearchResultService.class.getName()).log(Level.SEVERE, null, ex);
            }           
        }
        
        return values;
    }
    
    private static Long safeUserId(Object userId) {
        if (userId instanceof Integer) {
            Integer intUserId = (Integer) userId;
            return intUserId.longValue();
        } else if (userId instanceof Long) {
            return (Long) userId;
        } else {
            return Long.parseLong(userId.toString());
        }
    }
}
