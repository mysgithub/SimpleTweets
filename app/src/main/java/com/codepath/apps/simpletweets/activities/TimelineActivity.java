package com.codepath.apps.simpletweets.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.codepath.apps.simpletweets.R;
import com.codepath.apps.simpletweets.TwitterApplication;
import com.codepath.apps.simpletweets.TwitterClient;
import com.codepath.apps.simpletweets.adapters.TweetsArrayAdapter;
import com.codepath.apps.simpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class TimelineActivity extends AppCompatActivity {

  private TwitterClient client;
  private ArrayList<Tweet> tweets;
  private TweetsArrayAdapter aTweets;
  private ListView lvTweets;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_timeline);

    lvTweets = (ListView) findViewById(R.id.lvTweets);
    tweets = new ArrayList<>();
    aTweets = new TweetsArrayAdapter(this, tweets);
    lvTweets.setAdapter(aTweets);
    // Get client
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
      public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
        Log.d("DEBUG", jsonArray.toString());
        aTweets.addAll(Tweet.fromJSONArray(jsonArray));
      }

      @Override
      public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        Log.d("Failed2: ", "" + statusCode);
        Log.d("Error : ", "" + throwable);
        Log.d("Exception:", errorResponse.toString());
      }

    });
  }
}
