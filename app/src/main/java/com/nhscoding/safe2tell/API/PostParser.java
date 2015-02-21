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
public class PostParser extends AsyncTask<InputStream, InputStream, InputStream> {

    InputStream is;

    List list;

    public boolean finished = false;

    @Override
    protected InputStream doInBackground(InputStream... params) {
        try {
            URL url = new URL("http://24.8.58.134/Safe2Tell/API/PostAPI");
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

    public PostObject readSection(JsonReader reader) throws IOException {
        int Genre  = -1;
        int id = -1;
        int Level = -1;
        int Logo = -1;
        int ProblemID = -1;
        String Text = "";
        String Title = "";
        reader.beginObject();
        while (reader.hasNext()) {
            String title = reader.nextName();
            if (title.equals("ID")) {
                id = reader.nextInt();
            } else if (title.equals("Genre")) {
                Genre = reader.nextInt();
            } else if(title.equals("Level")) {
                Level = reader.nextInt();
            } else if (title.equals("Logo")) {
                Logo = reader.nextInt();
            } else  if (title.equals("ProblemID")) {
                ProblemID = reader.nextInt();
            } else if (title.equals("Text")) {
                Text = reader.nextString();
                Text = Text.replaceAll(" ", "");
                Text = Text.replaceAll("13579", " ");
            } else if (title.equals("Title")) {
                Title = reader.nextString();
                Title = Title.replaceAll(" ", "");
                Title = Title.replaceAll("13579", " ");
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new PostObject(id, Level, Genre, Logo, ProblemID, Text, Title);
    }

    public List getList () {
        return list;
    }
}