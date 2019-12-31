package com.example.jeongdahun.management;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchSensor extends AppCompatActivity {

    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    ListView listView;
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchsensor);

        list = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, list);

        listView = (ListView) findViewById(R.id.lv);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        //listView.setAdapter(adapter);

        final EditText searchText = (EditText) findViewById(R.id.searchText);
        final Button SearchButton = (Button) findViewById(R.id.SearchButton);
        final String code = "2";
        final String sensorName = "";
        final String userID = "";


        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String sensorLocation = searchText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                String sensorCount = jsonResponse.getString("sensorCount");
                                int count = Integer.parseInt(sensorCount);

                                list.clear();
                                for(int i = 0; i < count; i++) {
                                    String sensorLocation = jsonResponse.getString("sensorLocation"+Integer.toString(i));
                                    list.add(sensorLocation);
                                }

                                //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, list);

                                listView = (ListView) findViewById(R.id.listView);
                                imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                listView.setAdapter(adapter);
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SearchSensor.this);
                                builder.setMessage("검색에 실패하였습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                };
                SensorRequest sensorRequest = new SensorRequest(userID, sensorName, sensorLocation, code, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SearchSensor.this);
                queue.add(sensorRequest);
            }
        });
    }
}
