package com.nhscoding.safe2tell.API;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by david_000 on 2/11/2015.
 */
/*public class PostParser extends AsyncTask {

    public Boolean finished = false;
    PostObject[] results;

    @Override
    protected String doInBackground(Object[] params) {
        return "test";
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        PostObject post;
        post = null;
        if (post == null) {
            post = new PostObject();
        }
        int length = 0;
        for (int i = 0; i < 0; i++) {
            length++;
        }
        results = new PostObject[length];
        for (int i = 0; i < length; i++) {

        }
        finished = true;
    }

    public Boolean getFinished() {
        return finished;
    }

    public PostObject[] getResults() {
        return results;
    }
}*/
public class PostParser extends AsyncTask<InputStream, InputStream, InputStream> {

    InputStream is;

    List array;

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
        Log.i("Post Parser", "Data Recieved");
        try {
            array = readJSONStream(is);
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
        int id = -1;
        int Level = -1;
        int Genre = -1;
        int Logo = -1;
        int ProblemID = -1;
        String Text = "";
        String Title = "";
        reader.beginObject();
        while (reader.hasNext()) {
            String title = reader.nextName();
            switch (title) {
                case "ID":
                    id = reader.nextInt();
                    break;
                case "Level":
                    Level = reader.nextInt();
                    break;
                case "Logo":
                    Logo = reader.nextInt();
                    break;
                case "ProblemID":
                    ProblemID = reader.nextInt();
                    break;
                case "Text":
                    Text = reader.nextString();
                    break;
                case "Title":
                    Title = reader.nextString();
                    break;
                case "Genre":
                    Genre = reader.nextInt();
                    break;
                default:
                    reader.skipValue();
                    break;
            }
        }
        reader.endObject();
        return new PostObject(id, Level, Genre, Logo, ProblemID, Text, Title);
    }

    public List getArray () {
        return array;
    }
}