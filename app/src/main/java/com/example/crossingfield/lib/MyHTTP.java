package com.example.crossingfield.lib;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyHTTP {

    private URL url;
    private HttpURLConnection httpURLConnection = null;

    public MyHTTP(String urlSt){
        try{
            url = new URL(urlSt);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void upload(ByteArrayOutputStream data) {

        try{
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setDoOutput(true);

            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(20000);

            httpURLConnection.connect();

            try{
                OutputStream outputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
                outputStream.write(data.toByteArray());
                outputStream.flush();
            }catch (IOException e){
                e.printStackTrace();
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (httpURLConnection != null){
                httpURLConnection.disconnect();
                httpURLConnection = null;
            }
        }
    }

    public Bitmap download(){

        try{
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(20000);

            httpURLConnection.connect();

            Bitmap bitmap = BitmapFactory.decodeStream(httpURLConnection.getInputStream());

            return bitmap;
        }catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }
}
