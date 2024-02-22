package com.qa.util;

import org.json.JSONArray;
import org.json.JSONObject;

public class TestUtil {

    public static String getValueByJSONPath(JSONObject responseJson, String JSONPath) {
        Object obj = responseJson;
        for (String s : JSONPath.split("/"))
            if (!s.isEmpty())
                if(!(s.contains("[") || s.contains("]")))
                    obj = ((JSONObject) obj).get(s);
                else if (s.contains("[") || s.contains("]"))
                    obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0])).get(Integer.parseInt(s.split("\\[")[1].replace("]", "")));
        return obj.toString();
    }

}
