package com.android.scoket_chatclient;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by gwj on 16-6-21.
 */
//class ChatServer  implements Runnable {
    class ChatServer  extends Thread {

    private static final int PROT = 5000 ;
    private Socket client;
    private String answer;
    private static String TAG = "ChatServer";
    private ServerSocket serverSocket ;
    private BufferedReader reader;
    private BufferedWriter writer;
    public ChatServer(){
        initServer();
    }
    private void initServer(){
        try {
            serverSocket = new ServerSocket(PROT);

        }catch (IOException e){
            Log.e(TAG,e.toString());
        }
    }

    public void closeServer(){
        try {

        serverSocket.close();
        }catch (IOException e){
            Log.e(TAG,e.toString());
        }
    }
    @Override
    public void run() {
                Log.i(TAG,"服务器已打开");
        while (true){
            try {
                client = serverSocket.accept();
                Log.i(TAG,"获取到客户端的socket");
                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                //sendMsg("服务器发送数据");
            }catch (IOException e){
                Log.e(TAG,e.toString());
            }
        }

    }

    public void sendMsg(String Msg){
        try {
            writer.write(Msg);
            writer.flush();
            writer.close();
            Log.i(TAG,"服务器发送数据");
        }catch (IOException e){

        }

    }
    public void recivedMsg(){
        try {
            Log.i(TAG,"服务器接受数据");
            String msg = reader.readLine();
            Log.i(TAG,"服务器接受数据1");
//            sendMsg(msg);
            serverRecied.setMsg(msg);
        }catch (IOException e){

        }
    }

    public void close(){
        try {
        writer.close();
        reader.close();
        client.close();
        serverSocket.close();

        }catch (IOException e){
            Log.e(TAG,e.toString());
        }
    }

    public interface serverRecied{
        public void setMsg(String msg);
    }

    private serverRecied serverRecied;
    public void setMessage(serverRecied serverRecied){
        this.serverRecied = serverRecied;
    }
}
