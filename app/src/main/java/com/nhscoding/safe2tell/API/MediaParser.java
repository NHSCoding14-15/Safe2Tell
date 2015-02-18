package com.nhscoding.safe2tell.API;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by david_000 on 2/17/2015.
 */
public class MediaParser extends AsyncTask<String, String, String> {

    InputStream is;

    MediaObject[] array;

    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL("http://24.8.58.134/Safe2Tell/API/MediaAPI");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            conn.connect();

            int response = conn.getResponseCode();
            Log.d("Response Code:", String.valueOf(response));

            is = conn.getInputStream();
            Reader reader = new InputStreamReader(is, "UTF-8");
            int length = conn.getContentLength();
            char[] buffer = new char[length];
            reader.read(buffer);
            return new String(buffer);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("Malformed URL", e.getLocalizedMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("IO Exception", e.getLocalizedMessage());
        }
        return "Error Occurred During Request";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.i("HTTP Response:", s);
    }

    public MediaObject[] getArray () {
        return array;
    }
}
