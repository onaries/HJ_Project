package com.imagelab.smartpowerman.Network;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ksw89 on 2016-11-21.
 */
// Thread를 이용하여 Mysql의 결과값을 가져옴
public class MysqlThread extends AsyncTask<String, Integer, String> {
    @Override
    protected String doInBackground(String... urls) {
        StringBuilder jsonHtml = new StringBuilder();
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (conn != null) {
                conn.setConnectTimeout(10000);
                conn.setUseCaches(false);

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    while (true){
                        String line = br.readLine();
                        if (line == null) break;
                        jsonHtml.append(line + "\n");
                    }

                    br.close();
                }
                conn.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonHtml.toString();
    }
}
