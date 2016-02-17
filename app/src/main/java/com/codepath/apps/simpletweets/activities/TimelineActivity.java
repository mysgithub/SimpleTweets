package com.codepath.apps.simpletweets.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.codepath.apps.simpletweets.R;
import com.codepath.apps.simpletweets.TwitterApplication;
import com.codepath.apps.simpletweets.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;


public class TimelineActivity extends AppCompatActivity {

  private TwitterClient client;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_timeline);

    client = TwitterApplication.getRestClient();
    populateTimeline();
  }

  private void populateTimeline(){
    client.getHomeTimeline(new JsonHttpResponseHandler(){

      @Override
      public void onStart() {
        Log.d("DEBUG", "Request: " + super.getRequestURI().toString());
      }

      @Override
      public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        Log.d("Success: ", "" + statusCode);
      }

      @Override
      public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        Log.d("Failed1: ", "" + statusCode);
      }

      @Override
      public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        Log.d("Failed2: ", "" + statusCode);
        Log.d("Error : ", "" + throwable);
        Log.d("Exception:", errorResponse.toString());
      }

      @Override
      public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        super.onFailure(statusCode, headers, responseString, throwable);
        Log.d("Failed3: ", "" + statusCode);
        Log.d("Error : ", "" + throwable);
      }
    });
  }
}
