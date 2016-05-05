package com.sughoshkumar.learningnetwork;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    // member instances
    Toolbar toolbar;
    ProgressDialog mProgress;
    RetrieveJSONTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize the custom toolbar and add it to the activity
        toolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);

        // initialize the progress dialog for proper visual
        // description of the process
        mProgress = new ProgressDialog(this);

        // create fragment to start
        PostsView currentView = new PostsView();

        // initialize the task and start retrieving data
        // from the appropriate URL
        task = new RetrieveJSONTask(this, currentView);
        task.execute("https://rawgit.com/ksughosh/Data/master/prototype.json");

        // instantiate fragments and add the inital fragment
        // initial fragment is by default the news feeds
        // shown by the PostView fragment
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.mainLayout, currentView);
        transaction.commit();
    }


    /**
     * On button click listener
     * @param view button
     */
    public void setFragmentView(View view) {
        Fragment currentView;
        String backViewName;
        if (view == findViewById(R.id.postsButton)){
            currentView = new PostsView();
            backViewName = "post";
        }
        else if (view == findViewById(R.id.contactButton)){
            currentView = new MessageView();
            backViewName = "message";
        }
        else{
            currentView = new PostsView();
            backViewName = "request";
        }
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.mainLayout, currentView);
        transaction.addToBackStack(backViewName);
        transaction.commit();

    }


    /**
     * Method to access the progress diaglog
     * @param message string to be shown in progress
     */
    public void updateProgress(String message) {
        mProgress.setMessage(message);
        if (!mProgress.isShowing())
            mProgress.show();
    }


    /**
     * Static class so that there exits only weak reference to
     * avoid memory leak if MainActivity is destroyed before
     * the this RetrieveJSONTask has finished retrieving the JSON.
     */

    static class RetrieveJSONTask extends AsyncTask<String, String, JSONArray> {

        // member instance
        private URL mUrl;
        MainJsonResult delegate;
        WeakReference<MainActivity> weakActivity;
        MainActivity activity;

        /**
         * Constructor
         * @param activity main activity reference
         * @param delegate listener
         */
        RetrieveJSONTask(MainActivity activity, MainJsonResult delegate){
            weakActivity = new WeakReference<>(activity);
            this.activity = weakActivity.get();
            this.delegate = delegate;
        }

        /**
         * Set listener
         * @param object listener implementer
         */
        public void setDelegate(MainJsonResult object){
            delegate = object;
        }

        /**
         * Pre-execution
         */
        @Override
        protected void onPreExecute() {
            activity.updateProgress("Downloading data ...");
        }

        /**
         * Doing in background
         * @param params send URL from where the data is read
         * @return JSONArray of data read from URL
         */
        @Override
        protected JSONArray doInBackground(String... params) {
            Log.e("Async", "JSON Task Called");
            JSONArray parsedObject = null;
            if (isCancelled()){
                publishProgress("The download task thread is interrupted");
            }
            try {
                mUrl = new URL(params[0]);
                HttpURLConnection httpConnection = (HttpURLConnection) mUrl.openConnection();
                httpConnection.setConnectTimeout(5000);
                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    try {
                        parsedObject = convertStreamToJSON(httpConnection.getInputStream());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        httpConnection.disconnect();
                    }
                }
                else{
                    return null;
                }
            }
            catch (SocketTimeoutException e){
                activity.updateProgress("Timeout, host did not respond");
                e.printStackTrace();
            }
            catch (IOException e) {
                activity.updateProgress("Error connecting, please check internet connection");
                e.printStackTrace();
            }
            return parsedObject;
        }

        /**
         * Send the data through the delegate for loose
         * coupling using observer pattern
         * @param jsonArray array from the URL
         */
        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            if (jsonArray != null) {
                delegate.onResultReceived(jsonArray);
                activity.mProgress.hide();
            }
            else
                throw new NullPointerException();
        }

        /**
         * Convert HTTP inputstream to JSONArray
         * @param inputStream HTTP stream
         * @return JSONArray from the stream
         * @throws JSONException not JSON object or parsing error
         * @throws IOException File not accessible
         */
        private JSONArray convertStreamToJSON(InputStream inputStream) throws JSONException, IOException {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream));
            String object;
            StringBuilder JSONString = new StringBuilder();
            while ((object = streamReader.readLine()) != null) {
                JSONString.append(object);
            }
            inputStream.close();
            return new JSONArray(JSONString.toString());
        }
    }

    /**
     * Interface for observer pattern to update the JSONArray
     * that could be used by the class that inherits this interface.
     */
    interface MainJsonResult {
        void onResultReceived(JSONArray jsonList);
    }

}
