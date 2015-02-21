package com.nhscoding.safe2tell.API;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

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
 * Created by david_000 on 2/17/2015.
 */
public class GenreParser extends AsyncTask<InputStream, InputStream, InputStream> {

    InputStream is;

    List list;

    @Override
    protected InputStream doInBackground(InputStream... params) {
        try {
            URL url = new URL("http://24.8.58.134/Safe2Tell/API/GenreAPI");
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
        Log.i("Genre Parser", "Data Recieved");
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

    public GenreObject readSection(JsonReader reader) throws IOException {
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
                name.replaceAll("13579", " ");
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new GenreObject(id, name);
    }

    public List getList () {
        return list;
    }
}
