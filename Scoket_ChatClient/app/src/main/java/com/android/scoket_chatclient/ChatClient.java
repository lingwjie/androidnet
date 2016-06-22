package com.android.scoket_chatclient;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by gwj on 16-6-21.
 */
public class ChatClient {
    private static final String TAG = "SocketClient";
    private Socket client;
    private BufferedReader reader;
    private  BufferedWriter writer;
    public ChatClient(){


    }
    public void initClient(){
        try {
            //192.168.1.180  这个是我查询的平板上的ip
            client = new Socket("192.168.1.180",5000);
//            ReciveMsg();
        }catch (IOException e){
            Log.e(TAG , e.toString());
        }



    }

    public void sentMsg( String msg) {
        try {

            writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            writer.write(msg.replace("\n","")+"\n");
            writer.flush();
            writer.close();
            Log.i(TAG,"客户端发送数据完成");
        }catch (IOException e){
            Log.e(TAG, e.toString());
        }
    }
    public String reciveMsg(){
        String recivedMsg = null;
        try {
            reader = new BufferedReader( new InputStreamReader(client.getInputStream()));
            recivedMsg = reader.readLine();

            Log.i(TAG,"客户端获取数据");
//            Log.i(TAG,recivedMsg);
            showRecivedMsg.setMsg(recivedMsg);
        }catch (IOException e){
            Log.i(TAG ,e.toString());
        }
        return recivedMsg;
    }
     public void closeClient(){
         try {

             reader.close();
             writer.close();
             client.close();
         }catch (IOException e){
             Log.e(TAG , e.toString());
         }

     }

    /**
     * 回调的接口，当客户端接受到数据之后，显示出来
     */
    public interface showRecivedMsg{
        public void setMsg(String msg);
    }

    private showRecivedMsg showRecivedMsg;

    /**
     * 提供一个外部调用函数
     * @param showRecivedMsg
     */
    public void setRecivedMsg(showRecivedMsg showRecivedMsg){
        this.showRecivedMsg = showRecivedMsg;
    }
}

