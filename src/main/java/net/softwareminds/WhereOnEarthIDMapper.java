package net.softwareminds;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class WhereOnEarthIDMapper {

    final private static Map<String,Long> woeIDMap = new HashMap<>();

    static{
        woeIDMap.put("frankfurt", 650272l);
        woeIDMap.put("hamburg", 656958l);
        woeIDMap.put("berlin", 638242l);
    }

    public Long getWhereOnEarthID(String city){
        return woeIDMap.get(city.toLowerCase());
    };
}
