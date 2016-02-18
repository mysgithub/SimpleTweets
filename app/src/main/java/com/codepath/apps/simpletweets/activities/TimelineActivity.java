package com.codepath.apps.simpletweets.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;

import com.codepath.apps.simpletweets.EndlessRecyclerViewScrollListener;
import com.codepath.apps.simpletweets.R;
import com.codepath.apps.simpletweets.TwitterApplication;
import com.codepath.apps.simpletweets.TwitterClient;
import com.codepath.apps.simpletweets.adapters.TweetsRecyclerViewAdapter;
import com.codepath.apps.simpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class TimelineActivity extends AppCompatActivity {

  private TwitterClient client;
  private ArrayList<Tweet> tweets;
  private TweetsRecyclerViewAdapter tweetsRecyclerViewAdapter;
  private long maxId;

  @Bind(R.id.lvTweets) ListView lvTweets;
  @Bind(R.id.rvTweets) RecyclerView rvTweets;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_timeline);

    ButterKnife.bind(this);

    // Setup RecyclerView
    setupRecyclerView();

    // Get client
    client = TwitterApplication.getRestClient();
    populateTimeline();
  }

  private void populateTimeline(){

    client.getHomeTimeline(getResponseHandler(), 0);
  }

  private void setupRecyclerView(){
    tweets = new ArrayList<>();
    tweetsRecyclerViewAdapter = new TweetsRecyclerViewAdapter(tweets, this);
    //lvTweets.setAdapter(tweetsArrayAdapter);
    rvTweets.setAdapter(tweetsRecyclerViewAdapter);

    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    rvTweets.setLayoutManager(layoutManager);

    rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
      @Override
      public void onLoadMore(int page, int totalItemsCount) {
        long maxId = tweets.get(tweets.size() - 1).getUid() - 1; // -1 so that duplicate will not appear..
        client.getHomeTimeline(getResponseHandler(), maxId);
      }
    });
  }

  private JsonHttpResponseHandler getResponseHandler(){
    return new JsonHttpResponseHandler(){
      @Override
      public void onStart() {
        Log.d("DEBUG", "Request: " + super.getRequestURI().toString());
      }

      @Override
      public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
        Log.d("DEBUG", "Resposne: " + jsonArray.toString());
        int curSize = tweetsRecyclerViewAdapter.getItemCount();
        ArrayList<Tweet> arrayList = Tweet.fromJSONArray(jsonArray);
        tweets.addAll(arrayList);

        Log.d("DEBUG", "curSize: " + curSize);
        Log.d("DEBUG", "tweets.size: " + tweets.size());
        Log.d("DEBUG", "arrayList.size: " + arrayList.size());

        tweetsRecyclerViewAdapter.notifyItemRangeInserted(curSize, arrayList.size());
      }

      @Override
      public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        Log.d("Failed2: ", "" + statusCode);
        Log.d("Error : ", "" + throwable);
        Log.d("Exception:", errorResponse.toString());
      }
    };
  }

}
