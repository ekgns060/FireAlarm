package com.example.jeongdahun.management;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SMSActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendsms);

        Intent intent = getIntent();
        final String sensorLocation = intent.getStringExtra("sensorLocation");

        final EditText addrTxt = (EditText) SMSActivity.this.findViewById(R.id.addrEditText);
        final EditText msgTxt = (EditText) SMSActivity.this.findViewById(R.id.msgEditText);
        msgTxt.setText(sensorLocation);

        Button sendBtn = (Button) findViewById(R.id.sendSmsBtn);

        sendSMS(addrTxt.getText().toString(), msgTxt.getText().toString());
        sendBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                try {
                    sendSMS(addrTxt.getText().toString(), msgTxt.getText().toString());
                    // 1
                    //sendSmsMessage(addrTxt.getText().toString(), msgTxt.getText().toString());

                    // 2
                    //sendSMS(addrTxt.getText().toString(), msgTxt.getText().toString());

                    Toast.makeText(SMSActivity.this, "SMS 발송 완료", Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    Toast.makeText(SMSActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }

    protected void sendSmsMessage(String address, String message) throws Exception {
        // TODO Auto-generated method stub
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(address, null, message, null, null);

    }

    private void sendSMS(String phoneNumber, String message) {

        PendingIntent sentIntent = PendingIntent.getBroadcast(this, 0 , new Intent("SMS_SENT"), 0);
        PendingIntent deliveredIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED"), 0);

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch  (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "문자전송완료", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
            }, new IntentFilter("SMS_SENT"));
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentIntent, deliveredIntent);
    }
}