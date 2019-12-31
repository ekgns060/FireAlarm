package com.example.jeongdahun.management;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchGroup extends AppCompatActivity {

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
        final String groupPassword = "";
        final String code = "1";

        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String groupName = searchText.getText().toString();
                //final String groupPassword = "1";

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                String groupCount = jsonResponse.getString("groupCount");
                                int count = Integer.parseInt(groupCount);

                                list.clear();
                                for(int i = 0; i < count; i++) {
                                    String groupName = jsonResponse.getString("groupName"+Integer.toString(i));
                                    list.add(groupName);
                                }

                                //adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, list);

                                listView = (ListView) findViewById(R.id.listView);
                                imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                listView.setAdapter(adapter);

                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View clickedView, int position, long id) {
                                        Intent intent = new Intent(SearchGroup.this, GroupPassword.class);
                                        intent.putExtra("groupName", groupName);
                                        intent.putExtra("userID", userID);
                                        startActivity(intent);
                                        finish();
                                    }
                                });

                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SearchGroup.this);
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
                GroupRequest groupRequest = new GroupRequest(groupName, groupPassword, code, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SearchGroup.this);
                queue.add(groupRequest);
            }
        });


    }
}
