package com.example.jeongdahun.management;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class Group extends AppCompatActivity {

    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    ListView lv;
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);

        final TextView gName = (TextView) findViewById(R.id.groupName);
        final Button RButton = (Button) findViewById(R.id.RButton);
        final Button TButton = (Button) findViewById(R.id.TButton);

        Intent intent = getIntent();
        final String UserID = intent.getStringExtra("UserID");
        final String groupCount = intent.getStringExtra("groupCount");
        int count = Integer.parseInt(groupCount);
        //final String code = "3";

        final String groupname = intent.getStringExtra("groupName");
        gName.setText(groupname);

        final String GroupName = gName.getText().toString();

        list = new ArrayList<String>();
        for(int i = 0; i < count; i++) {
            String userList = intent.getStringExtra("userID"+i);
            list.add(userList);
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, list);

        lv = (ListView) findViewById(R.id.lv);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        lv.setAdapter(adapter);

        RButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String code = "3";

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Group.this);
                                builder.setMessage("그룹 등록에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                                //Intent intent = new Intent(Group.this, Group.class);
                                //Group.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Group.this);
                                builder.setMessage("이미 등록된 그룹입니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                GRequest groupRequest = new GRequest(GroupName, UserID, code, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Group.this);
                queue.add(groupRequest);
            }
        });

        TButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String code = "4";

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Group.this);
                                builder.setMessage("해지 신청이 완료되었습니다.")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                                //Intent intent = new Intent(Group.this, Group.class);
                                //Group.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Group.this);
                                builder.setMessage("해지신청 중입니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                GRequest groupRequest = new GRequest(GroupName, UserID, code, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Group.this);
                queue.add(groupRequest);
            }
        });
    }
}
