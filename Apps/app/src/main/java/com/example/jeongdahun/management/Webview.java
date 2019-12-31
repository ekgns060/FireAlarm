package com.example.jeongdahun.management;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class Webview extends AppCompatActivity {

    private WebView browser;
    private Handler handler;
    private String t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        init_webView();
        // 핸들러를 통한 JavaScript 이벤트 반응
        handler = new Handler();



    }
    public void init_webView() {
        // WebView 설정
        browser = (WebView) findViewById(R.id.webView);
        // JavaScript 허용
        browser.getSettings().setJavaScriptEnabled(true);
        // JavaScript의 window.open 허용
        browser.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        browser.getSettings().setSupportMultipleWindows(true);

        // JavaScript이벤트에 대응할 함수를 정의 한 클래스를 붙여줌
        // 두 번째 파라미터는 사용될 php에도 동일하게 사용해야함
        browser.addJavascriptInterface(new AndroidBridge(), "Android");
        // web client 를 chrome 으로 설정
        browser.setWebViewClient(new WebViewClient());
        browser.setWebChromeClient(new WebChromeClient(){
            @Override public boolean onCreateWindow(WebView view, boolean dialog1, boolean userGesture, Message resultMsg)
            {
                WebView newWebView = new WebView(Webview.this);
                WebSettings webSettings = newWebView.getSettings();
                webSettings.setJavaScriptEnabled(true);

                Dialog dialog = new Dialog(Webview.this);
                dialog.setContentView(newWebView);
                ViewGroup.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                params.height = ViewGroup.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
                dialog.show();

                newWebView.setWebChromeClient(new WebChromeClient(){
                    @Override public void onCloseWindow(WebView window){

                    }
                });

                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(newWebView);
                resultMsg.sendToTarget();
                return true;
            }
        });
        // webview url load
        browser.loadUrl("");

    }
    private class AndroidBridge {
        @JavascriptInterface
        public void setAddress(final String arg1, final String arg2, final String arg3) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    t1 = String.format("(%s) %s %s", arg1, arg2, arg3);

                    Intent i = getIntent();
                    final String userID = i.getStringExtra("userID");

                    Intent intent = new Intent(getApplicationContext(), RegisterSensor.class);
                    intent.putExtra("address",t1);
                    intent.putExtra("userID",userID);
                    startActivity(intent);

                    // WebView를 초기화 하지않으면 재사용할 수 없음
                    init_webView();
                    finish();

                }
            });
        }
    }
} 