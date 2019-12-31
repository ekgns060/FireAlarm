package com.example.jeongdahun.management;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SensorRequest extends StringRequest {

    //final static private String URL = "";
    final static private String URL = "";
    private Map<String, String> parameters;

    public SensorRequest(String userID, String sensorName, String sensorLocation, String code, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("sensorName", sensorName);
        parameters.put("sensorLocation", sensorLocation);
        parameters.put("code", code);
    }

    @Override
    public Map<String, String> getParams() {

        return parameters;
    }
}