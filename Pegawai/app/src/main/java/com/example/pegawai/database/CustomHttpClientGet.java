package com.example.pegawai.database;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by subki on 11/30/2015.
 */
public class CustomHttpClientGet {
    HttpClient httpClient;
    HttpGet httpGet;
    HttpResponse httpResponse;

    public CustomHttpClientGet(){
        httpClient=new DefaultHttpClient();
    }

    public String get(String url) throws IOException {
        httpGet=new HttpGet(url);
        httpResponse=httpClient.execute(httpGet);

        BufferedReader rd = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));

        String line = "";
        StringBuilder builder=new StringBuilder();
        while ((line = rd.readLine()) != null) {
            builder.append(line);
        }

        return builder.toString();
    }
}

