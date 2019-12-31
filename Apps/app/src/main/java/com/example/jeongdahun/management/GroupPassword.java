package com.example.jeongdahun.management;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class GroupPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grouppassword);

        final EditText gPassword = (EditText)findViewById(R.id.groupPassword);
        final Button verification = (Button)findViewById(R.id.verification);

        Intent intent = getIntent();
        final String gName = intent.getStringExtra("groupName");
        final String UserID = intent.getStringExtra("userID");

        final String code = "2";

        verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String groupPassword = gPassword.getText().toString();
                String groupName = gName;


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(GroupPassword.this);
                                builder.setMessage("그룹에 입장하였습니다.")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();

                                //String userID = jsonResponse.getString("userID");
                                String groupName = jsonResponse.getString("groupName");
                                String groupCount = jsonResponse.getString("groupCount");

                                int count = Integer.parseInt(groupCount);

                                Intent intent = new Intent(GroupPassword.this, Group.class);
                                intent.putExtra("groupName", groupName);
                                intent.putExtra("groupCount", groupCount);
                                intent.putExtra("UserID", UserID);

                                for(int i = 0; i < count; i++) {
                                    String userID = jsonResponse.getString("userID"+Integer.toString(i));
                                    intent.putExtra("userID"+Integer.toString(i), userID);
                                }

                                startActivity(intent);
                                finish();

                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(GroupPassword.this);
                                builder.setMessage("그룹 입장에 실패하였습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                GroupRequest groupRequest = new GroupRequest(groupName, groupPassword, code, responseListener);
                RequestQueue queue = Volley.newRequestQueue(GroupPassword.this);
                queue.add(groupRequest);
            }
        });
    }
}
