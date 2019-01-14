package com.swing.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {


    public static final boolean isJSONArrayValid(String jsonString) {
        try {
            new JSONArray(jsonString);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }


    // 判断能否转成JSONObject
    public static final boolean isJSONObjectValid(String jsonString) {
        try {
            new JSONObject(jsonString);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    public static final String getString(JSONObject jsonObject, String key) {
        try {
            String result = jsonObject.getString(key);
            return result;
        } catch (JSONException e) {
            return "";
        }
    }
}
