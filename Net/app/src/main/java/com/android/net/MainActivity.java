package com.android.net;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button openUrl = (Button)findViewById(R.id.open_url);
        WebView webView = (WebView)findViewById(R.id.webview);
        openUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://www.baidu.com");
                Intent intent = new Intent(Intent.ACTION_VIEW , uri);
                startActivity(intent);
            }
        });
        webView.loadUrl("http://www.baidu.com");
        webView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });

    }
}
