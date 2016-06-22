package com.android.scoket_chatclient;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    public static final int server_msg = 0x00;
    public  static final int clientOne = 0x01;
    public  static final int clientTwo = 0x02;

    private Button clientSent;
    private Button clientRecive;
    private Button initClient;

    private Button startServer;
    private Button serverSent;
    private Button serverRecive;
    private TextView clientReciveMsg;
    private TextView serverReciveMsg;
//    private Thread server;
    private ChatServer server;
    private ChatClient client;

   // private requuestNet clientThread;
    private MyHandler myHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initClient = (Button)findViewById(R.id.init_client);
        clientSent = (Button)findViewById(R.id.client_sent);
        clientRecive = (Button)findViewById(R.id.client_recived);
        startServer = (Button)findViewById(R.id.start_server);
        serverRecive = (Button)findViewById(R.id.server_recived);
        serverSent = (Button)findViewById(R.id.server_send);
        clientReciveMsg = (TextView)findViewById(R.id.client_recive_message);
        serverReciveMsg = (TextView)findViewById(R.id.server_recive_message);

//        server = new Thread(new ChatServer());
        server = new ChatServer();
      //  clientThread =  new requuestNet();
        myHandler = new MyHandler();

        client = new ChatClient();

        client.setRecivedMsg(new ChatClient.showRecivedMsg() {
            @Override
            public void setMsg(String msg) {
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("message",msg);
                message.what = clientOne;
                message.setData(bundle);
                myHandler.sendMessage(message);
            }

        });
        server.setMessage(new ChatServer.serverRecied() {
            @Override
            public void setMsg(String msg) {
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("message",msg);
                message.what = server_msg;
                message.setData(bundle);
                myHandler.sendMessage(message);
            }
        });

        initClient.setOnClickListener(this);
        clientSent.setOnClickListener(this);
        clientRecive.setOnClickListener(this);
        startServer.setOnClickListener(this);
        serverSent.setOnClickListener(this);
        serverRecive.setOnClickListener(this);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.init_client:
                new Thread(new initClient()).start();
//                clientThread.start();
                break;
            case R.id.client_sent:
                client.sentMsg("客户端发送的数据");
                break;
            case R.id.client_recived:
                new Thread(new clientRecived()).start();
                break;

            case R.id.start_server:
                Log.i(TAG , "开启服务器");
                server.start();
                break;
            case R.id.server_send:
                server.sendMsg("服务器发送的数据");
                break;
            case R.id.server_recived:
                new Thread(new serverRecived()).start();
                break;




        }
    }

    public class initClient implements Runnable {

        @Override
        public void run() {
            Log.i(TAG,"初始化客户端");
            client.initClient();

        }

    }
    public class serverRecived implements Runnable{
        @Override
        public void run() {
            server.recivedMsg();
        }
    }
    public class clientRecived implements Runnable{
        @Override
        public void run() {
            client.reciveMsg();
        }
    }
    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == clientOne){
                clientReciveMsg.setText(msg.getData().getString("message","没有接受到数据"));
            }else if (msg.what == server_msg){
                serverReciveMsg.setText(msg.getData().getString("message","没有接受到数据"));
            }
        }
    }

}
