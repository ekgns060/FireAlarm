package com.example.jeongdahun.management;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterSensor extends AppCompatActivity {

    private EditText locationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registersensor);

        final EditText snameText = (EditText)findViewById(R.id.snameText);
        final EditText locationText = (EditText)findViewById(R.id.locationText);
        final EditText detailText = (EditText)findViewById(R.id.detailText);
        final Button Register = (Button)findViewById(R.id.Register);

        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
        final String code = "1";

        locationText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    Intent i = new Intent(RegisterSensor.this, Webview.class);
                    i.putExtra("userID",userID);
                    startActivity(i);
                    finish();
                }
                return false;
            }
        });

        String name = intent.getExtras().getString("address"); /*String형*/
        locationText.setText(name);


        Register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String sensorName = snameText.getText().toString();
                final String sensorLocation = locationText.getText().toString();
                final String detailLocation = detailText.getText().toString();
                //Intent intent = getIntent();
                //final String userID = intent.getStringExtra("userID");
                String UserID = userID;
                Response.Listener<String> responseListener = new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterSensor.this);
                                builder.setMessage("센서 등록에 성공했습니다.")
                                        .setPositiveButton("확인", null)
                                        .create()
                                        .show();
                                String SensorName = sensorName;
                                finish();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterSensor.this);
                                builder.setMessage("센서 등록에 실패했습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };
                SensorRequest sensorRequest = new SensorRequest(userID, sensorName, sensorLocation +" " + detailLocation, code, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterSensor.this);
                queue.add(sensorRequest);
            }
        });

    }

}
