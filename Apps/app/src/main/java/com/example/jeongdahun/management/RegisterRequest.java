package com.example.jeongdahun.management;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    //final static private String URL = "";
    final static private String URL = "";
    private Map<String, String> parameters;

    public RegisterRequest(String userID, String userPassword, String userName, int userAge, String userAddress, String userEmail, String userPhone, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("userName", userName);
        parameters.put("userAge", userAge + "");
        parameters.put("userAddress", userAddress);
        parameters.put("userEmail", userEmail);
        parameters.put("userPhone", userPhone);
    }

    @Override
    public Map<String, String> getParams() {

        return parameters;
    }
}