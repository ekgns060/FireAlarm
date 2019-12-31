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

public class ModifySensor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifysensor);

        final EditText snameText = (EditText)findViewById(R.id.snameText);
        final EditText locationText = (EditText)findViewById(R.id.locationText);
        final Button Modify = (Button)findViewById(R.id.Modify);

        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
        final String code = "3";

        Modify.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String sensorName = snameText.getText().toString();
                final String sensorLocation = locationText.getText().toString();

                String UserID = userID;
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ModifySensor.this);
                                builder.setMessage("센서 수정에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                                String SensorName = sensorName;
                                finish();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ModifySensor.this);
                                builder.setMessage("센서 수정에 실패했습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                SensorRequest sensorRequest = new SensorRequest(userID, sensorName, sensorLocation, code, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ModifySensor.this);
                queue.add(sensorRequest);
            }
        });
    }
}
