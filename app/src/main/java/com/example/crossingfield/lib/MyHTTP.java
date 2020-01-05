package com.example.crossingfield.lib;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

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

    public void upload(String data) {

        try{
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");

            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setRequestProperty("Accept-Language", "jp");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(20000);

            httpURLConnection.connect();

            try{
                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(data.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
                outputStream.close();
            }catch (IOException e){
                e.printStackTrace();
            }

            if (httpURLConnection.getResponseCode() != 200){
                throw new RuntimeException("Failed : HTTP error code : " + httpURLConnection.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            String output;
            while ((output = br.readLine()) != null){
                System.out.println(output);
            }

        }catch (IOException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
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
