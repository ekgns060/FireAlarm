package com.example.jeongdahun.management;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class NaverRequest extends StringRequest {

    //final static private String URL = "";
    final static private String URL = "";
    private Map<String, String> parameters;

    public NaverRequest(String userToken , Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("token", userToken);
    }
    @Override
    public Map<String, String> getParams() {

        return parameters;
    }
}