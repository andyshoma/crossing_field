package com.example.crossingfield.lib;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MySocket {
    private final String IP_addr = "192.168.2.200";
    private final Integer port = 1919;

    private Context context;

    private String send_message = "";
    private String get_message = "";
    private Object get_object;

    public MySocket(Context context){
        this.context = context;
    }

    public void send() {
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... voids){
                Socket socket = null;
                PrintWriter pw = null;

                try {
                    // サーバへ接続
                    socket = new Socket(IP_addr, port);

                    pw = new PrintWriter(socket.getOutputStream(), true);

                    if(socket != null && socket.isConnected()) {

                        // サーバへメッセージを送信
                        pw.println(send_message);
                    }
                }catch (UnknownHostException e){
                    e.printStackTrace();
                }catch (IOException e) {
                    e.printStackTrace();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    try{
                        socket.close();
                        socket = null;
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }return null;
            }
        }.execute();

    }

    public void sendMessage(){
        Socket socket = null;
        PrintWriter pw = null;

        try {
            // サーバへ接続
            socket = new Socket(IP_addr, port);

            pw = new PrintWriter(socket.getOutputStream(), true);

            if(socket != null && socket.isConnected()) {

                // サーバへメッセージを送信
                pw.println(send_message);
            }
        }catch (UnknownHostException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                socket.close();
                socket = null;
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public String getMessage(){
        Socket socket = null;
        BufferedReader reader = null;

        try{
            // サーバへ接続
            socket = new Socket(IP_addr, port);

            if (socket != null && socket.isConnected()){
                // メッセージ取得オブジェクトのインスタンス化
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // サーバからのメッセージを受信
                get_message = reader.readLine();
            }

        }catch (UnknownHostException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try{
                // 接続終了処理
                reader.close();
                socket.close();
                socket = null;
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return get_message;
    }

    public String trySend(){
        Socket socket = null;
        PrintWriter pw = null;
        BufferedReader reader = null;

        try {
            // サーバへ接続
            socket = new Socket(IP_addr, port);

            pw = new PrintWriter(socket.getOutputStream(), true);

            if(socket != null && socket.isConnected()) {

                // サーバへメッセージを送信
                pw.println(send_message);

                System.out.println("send:" + send_message);

                // メッセージ取得オブジェクトのインスタンス化
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // サーバからのメッセージを受信
                get_message = reader.readLine();
                System.out.println("メッセージを受信しました");
                System.out.println("get:" + get_message);
            }
        }catch (UnknownHostException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                reader.close();
                socket.close();
                socket = null;
                System.out.println("socketを閉じました");
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        return get_message;
    }

    public void setMessage(String message){
        this.send_message = message;
    }
}
