package com.nhscoding.safe2tell.API;

import android.os.AsyncTask;
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
import java.lang.reflect.Array;

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
public class PostParser extends AsyncTask<String, String, String>{

    @Override
    protected String doInBackground(String... uri) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpResponse response;
        String responseString = null;
        try {
            response = httpclient.execute(new HttpGet("http://24.8.58.134/Safe2Tell/api/PostsAPI"));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                responseString = out.toString();
                out.close();
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            //TODO Handle problems..
        } catch (IOException e) {
            //TODO Handle problems..
        }
        return responseString;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.i("HTTP Response", result);
        //Do anything with response..
    }
}
