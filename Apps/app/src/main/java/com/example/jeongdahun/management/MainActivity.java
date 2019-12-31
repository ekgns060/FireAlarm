package com.example.jeongdahun.management;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.nhn.android.naverlogin.OAuthLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    ListView lv;
    InputMethodManager imm;
    private Button sms;
    OAuthLogin mOAuthLoginModule;
    Context mContext;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");

        FirebaseMessaging.getInstance().subscribeToTopic("news");
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(MainActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("newToken",newToken);
                updateToken(newToken, userID);
            }
        });

        Button refresh = (Button) findViewById(R.id.refreshButton);
        Button logout = (Button) findViewById(R.id.logoutButton);
        Button sensorRegister = (Button) findViewById(R.id.sensorRegister);
        Button sensorSearch = (Button) findViewById(R.id.sensorSearch);
        Button sensorModify = (Button) findViewById(R.id.sensorModify);
        Button sensorDelete = (Button) findViewById(R.id.sensorDelete);
        Button searchGroup = (Button) findViewById(R.id.searchGroup);
        sms = (Button) findViewById(R.id.sms);


        //Intent intent = getIntent();
        //final String userID = intent.getStringExtra("userID");
        final String userPassword = intent.getStringExtra("userPassword");
        String sensorCount = intent.getStringExtra("sensorCount");
        int count = Integer.parseInt(sensorCount);

        list = new ArrayList<String>();


        for(int i = 0; i < count; i++) {
            String sensorName = intent.getStringExtra("sensorName"+i);
            list.add(sensorName);
        }


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, list);


        lv = (ListView) findViewById(R.id.lv);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        lv.setAdapter(adapter);

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SMSActivity.class);
                startActivity(intent);
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                String userID = jsonResponse.getString("userID");
                                String userPassword = jsonResponse.getString("userPassword");
                                String sensorCount = jsonResponse.getString("sensorCount");
                                int count = Integer.parseInt(sensorCount);

                                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                intent.putExtra("userID", userID);
                                intent.putExtra("userPassword", userPassword);
                                intent.putExtra("sensorCount", sensorCount);

                                for (int i = 0; i < count; i++) {
                                    String sensorName = jsonResponse.getString("sensorName" + Integer.toString(i));
                                    intent.putExtra("sensorName" + Integer.toString(i), sensorName);
                                }
                                MainActivity.this.startActivity(intent);

                            }
                        } catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOAuthLoginModule = OAuthLogin.getInstance();
                mOAuthLoginModule.init(
                        MainActivity.this
                        ,""
                        ,""
                        ,"네이버 아이디로 로그인"
                );
                //mOAuthLoginModule.logoutAndDeleteToken(MainActivity.this);
                boolean isSuccessDeleteToken = mOAuthLoginModule.logoutAndDeleteToken(MainActivity.this);
                if (!isSuccessDeleteToken) {
                    // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태입니다.
                    // 클라이언트에 토큰 정보가 없기 때문에 추가로 처리할 수 있는 작업은 없습니다.
                    Log.d("TAG", "errorCode:" + mOAuthLoginModule.getLastErrorCode(MainActivity.this));
                    Log.d("TAG", "errorDesc:" + mOAuthLoginModule.getLastErrorDesc(MainActivity.this));
                }
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        sensorRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterSensor.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        sensorSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchSensor.class);
                intent.putExtra("userID", userID);
                MainActivity.this.startActivity(intent);
            }
        });

        sensorModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ModifySensor.class);
                intent.putExtra("userID", userID);
                MainActivity.this.startActivity(intent);
            }
        });

        sensorDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DeleteSensor.class);
                intent.putExtra("userID", userID);
                MainActivity.this.startActivity(intent);
            }
        });

        searchGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchGroup.class);
                intent.putExtra("userID", userID);
                MainActivity.this.startActivity(intent);
            }
        });
    }



    public void updateToken(String InputToken, String userID){
        OkHttpClient client = new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("Token", InputToken)
                .add("userID", userID)
                .build();
        Request request = new Request.Builder()
                .url("")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(updateTokenCallback);
    }

    private Callback updateTokenCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.d("Test", "ERROR Message : " + e.getMessage());
        }

        @Override
        public void onResponse(Call call, okhttp3.Response response) throws IOException {
            final String responseData = response.body().string();
            Log.d("TEST","responseData : " + responseData);
        }
    };
}
