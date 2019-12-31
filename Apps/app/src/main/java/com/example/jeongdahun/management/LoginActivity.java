package com.example.jeongdahun.management;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {

    OAuthLogin mOAuthLoginModule;
    OAuthLoginButton mOauthLoginButton;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = LoginActivity.this;

        final EditText idText = (EditText) findViewById(R.id.idText);
        final EditText passwordText = (EditText) findViewById(R.id.passwordText);
        final Button loginButton = (Button) findViewById(R.id.loginButton);
        final TextView registerButton = (TextView) findViewById(R.id.registerButton);


        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(
                mContext
                , ""
                , ""
                , "네아로"
        );
        mOauthLoginButton = (OAuthLoginButton) findViewById(R.id.buttonOAuthLoginImg);
        mOauthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);
        //mOAuthLoginModule.logoutAndDeleteToken(LoginActivity.this);



            if (mOAuthLoginModule.getAccessToken(LoginActivity.this) == null) {

                mOauthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);

            } else {

                Log.d("태그", "토큰값" + mOAuthLoginModule.getAccessToken(LoginActivity.this).toString());

                mOauthLoginButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String userID = "";
                        final String userPassword = "";

                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if (success) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                        builder.setMessage("로그인에 성공했습니다.")
                                                .setPositiveButton("확인", null)
                                                .create()
                                                .show();
                                        String userID = jsonResponse.getString("userID");
                                        String userPassword = jsonResponse.getString("userPassword");
                                        String sensorCount = jsonResponse.getString("sensorCount");
                                        int count = Integer.parseInt(sensorCount);

                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        intent.putExtra("userID", userID);
                                        intent.putExtra("userPassword", userPassword);
                                        intent.putExtra("sensorCount", sensorCount);

                                        for (int i = 0; i < count; i++) {
                                            String sensorName = jsonResponse.getString("sensorName" + Integer.toString(i));
                                            intent.putExtra("sensorName" + Integer.toString(i), sensorName);
                                        }

                                        startActivity(intent);
                                        finish();
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                        builder.setMessage("로그인에 실패하였습니다.")
                                                .setNegativeButton("다시 시도", null)
                                                .create()
                                                .show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                        queue.add(loginRequest);
                    }
                });

            }
            /*
            else {
                mOauthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);
                mOAuthLoginModule = OAuthLogin.getInstance();
                mOAuthLoginModule.init(
                        mContext
                        , ""
                        , ""
                        , "네아로"
                );

                if (mOAuthLoginModule.getAccessToken(LoginActivity.this) != null) {
                    Log.d("태그", "토큰값" + mOAuthLoginModule.getAccessToken(LoginActivity.this).toString());
                }
                //finish();
                //Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                //startActivity(intent);

            }






        //mOAuthLoginModule.logoutAndDeleteToken(LoginActivity.this);


        //mOAuthLoginModule.logout(LoginActivity.this);
        //Log.d("태그", "값" + mOAuthLoginModule.getAccessToken(LoginActivity.this).toString());
        //mOauthLoginButton = (OAuthLoginButton) findViewById(R.id.buttonOAuthLoginImg);
/*

        mOauthLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userID = "";
                final String userPassword = "";


                //mOauthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);

/*
                //Log.d("태그", "값" + mOAuthLoginModule.getAccessToken(LoginActivity.this).toString());
                if(mOAuthLoginModule.getAccessToken(LoginActivity.this) != null) {
                    mOauthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage("로그인에 성공했습니다.")
                                            .setPositiveButton("확인", null)
                                            .create()
                                            .show();
                                    String userID = jsonResponse.getString("userID");
                                    String userPassword = jsonResponse.getString("userPassword");
                                    String sensorCount = jsonResponse.getString("sensorCount");
                                    int count = Integer.parseInt(sensorCount);

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("userID", userID);
                                    intent.putExtra("userPassword", userPassword);
                                    intent.putExtra("sensorCount", sensorCount);

                                    for (int i = 0; i < count; i++) {
                                        String sensorName = jsonResponse.getString("sensorName" + Integer.toString(i));
                                        intent.putExtra("sensorName" + Integer.toString(i), sensorName);
                                    }

                                    startActivity(intent);
                                    finish();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage("로그인에 실패하였습니다.")
                                            .setNegativeButton("다시 시도", null)
                                            .create()
                                            .show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    queue.add(loginRequest);
                } else {
                    mOauthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);
                }
            }
        });*/


            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                    LoginActivity.this.startActivity(registerIntent);
                }
            });


            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String userID = idText.getText().toString();
                    final String userPassword = passwordText.getText().toString();

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage("로그인에 성공했습니다.")
                                            .setPositiveButton("확인", null)
                                            .create()
                                            .show();
                                    String userID = jsonResponse.getString("userID");
                                    String userPassword = jsonResponse.getString("userPassword");
                                    String sensorCount = jsonResponse.getString("sensorCount");
                                    int count = Integer.parseInt(sensorCount);

                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putExtra("userID", userID);
                                    intent.putExtra("userPassword", userPassword);
                                    intent.putExtra("sensorCount", sensorCount);

                                    for (int i = 0; i < count; i++) {
                                        String sensorName = jsonResponse.getString("sensorName" + Integer.toString(i));
                                        intent.putExtra("sensorName" + Integer.toString(i), sensorName);
                                    }

                                    startActivity(intent);
                                    finish();
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                    builder.setMessage("로그인에 실패하였습니다.")
                                            .setNegativeButton("다시 시도", null)
                                            .create()
                                            .show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    queue.add(loginRequest);
                }
            });
        }

        private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
            @Override
            public void run(boolean success) {
                if (success) {
                    final String accessToken = mOAuthLoginModule.getAccessToken(mContext);
                    String refreshToken = mOAuthLoginModule.getRefreshToken(mContext);
                    long expiresAt = mOAuthLoginModule.getExpiresAt(mContext);
                    String tokenType = mOAuthLoginModule.getTokenType(mContext);


                } else {
                    String errorCode = mOAuthLoginModule.getLastErrorCode(mContext).getCode();
                    String errorDesc = mOAuthLoginModule.getLastErrorDesc(mContext);
                    Toast.makeText(mContext, "errorCode:" + errorCode
                            + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
                }
            }
        };
    }