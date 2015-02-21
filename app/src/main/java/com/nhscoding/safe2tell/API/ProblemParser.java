package com.nhscoding.safe2tell.API;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.nhscoding.safe2tell.STORIES;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidkopala on 2/13/15.
 */
public class ProblemParser extends AsyncTask<InputStream, InputStream, InputStream> {

    InputStream is;

    List list;

    public boolean finished = false;

    @Override
    protected InputStream doInBackground(InputStream... params) {
        try {
            URL url = new URL("http://24.8.58.134/Safe2Tell/API/ProblemAPI");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            conn.connect();

            int response = conn.getResponseCode();
            Log.d("Response Code:", String.valueOf(response));

            is = conn.getInputStream();
            return is;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("Malformed URL", e.getLocalizedMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("IO Exception", e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(InputStream is) {
        super.onPostExecute(is);
        finished = true;
        Log.i("Problem Parser", "Data Recieved");
        try {
            list = readJSONStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List readJSONStream(InputStream is) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
        try{
            return readProblemArray(reader);
        } finally {
            reader.close();
        }
    }

    public List readProblemArray(JsonReader reader) throws IOException {
        List problems = new ArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
            problems.add(readSection(reader));
        }
        reader.endArray();
        return problems;
    }

    public ProblemObject readSection(JsonReader reader) throws IOException {
        int id = -1;
        String name = "";
        reader.beginObject();
        while (reader.hasNext()) {
            String title = reader.nextName();
            if (title.equals("ID")) {
                id = reader.nextInt();
            } else if(title.equals("Name")) {
                name = reader.nextString();
                name = name.replaceAll(" ", "");
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new ProblemObject(id, name);
    }

    public List getList () {
        return list;
    }
}