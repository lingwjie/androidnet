package com.android.urlconnection;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private final String Url = "http:// zh.wikipedia.org/w/api.php?action=opensearch&search=Android";
    private final int TYPE= 0x00;
    private final int TYPE_GET = 0x01;
    private final int TYPE_POST = 0x02;

    private TextView show;
    private Button search;
    private Button getButton;
    private Button postButton;

    private MyHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        show = (TextView) findViewById(R.id.show);
        search = (Button) findViewById(R.id.search);
        show = (TextView) findViewById(R.id.show);
        getButton = (Button) findViewById(R.id.get);
        postButton = (Button) findViewById(R.id.post);
        handler = new MyHandler();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new request(TYPE)).start();

            }
        });

        getButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new Thread(new request(TYPE_GET)).start();
            }
        });
        postButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new Thread(new request(TYPE_POST)).start();
            }
        });
    }

    private class request implements Runnable {

        int type;

        public request(int type) {
            this.type = type;
        }

        @Override
        public void run() {
            if (type == TYPE) {
                type();
            } else if (type == TYPE_GET) {
                typeGet();
            } else if (type == TYPE_POST) {
                typePdst();
            }

        }

        private void type(){
            try {
                URL url = new URL(Url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    //可以定义全局变量，可以防止提示不符
                    Toast.makeText(MainActivity.this, "链接成功", Toast.LENGTH_SHORT);
                    InputStreamReader reader = new InputStreamReader(connection.getInputStream(), "UTF-8");
                    int i;
                    String content = "";
                    while ((i = reader.read()) != -1) {

                        content = content + i;
                    }
                    reader.close();
                    Message message = new Message();
                    message.what = TYPE;
                    message.obj = content;
                    handler.sendMessage(message);
                }
                connection.disconnect();
            } catch (MalformedURLException e) {
                Log.e(TAG, e.toString());
                // Toast.makeText(MainActivity.this , "链接失败" , Toast.LENGTH_SHORT);
            } catch (IOException e) {
                Log.e(TAG, e.toString());
                // Toast.makeText(MainActivity.this , "链接失败" , Toast.LENGTH_SHORT);
//
            }

        }

        private void typePdst(){

            String respone = GetAndPost.sendPost("https://www.baidu.com", null);
            Message msg = new Message();
            msg.what = TYPE_POST;
            msg.obj = respone;
            handler.sendMessage(msg);


        }

        private void typeGet(){

            String respone = GetAndPost.sendGet("https://www.baidu.com", null);
            Message msg = new Message();
            msg.what = TYPE_GET;
            msg.obj = respone;
            handler.sendMessage(msg);

        }
    }

    private class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            show.setText(msg.obj.toString());

        }
    }

}
