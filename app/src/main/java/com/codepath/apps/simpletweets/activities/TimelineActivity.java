package com.codepath.apps.simpletweets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.codepath.apps.simpletweets.EndlessRecyclerViewScrollListener;
import com.codepath.apps.simpletweets.fragments.ComposeTweetDialog;
import com.codepath.apps.simpletweets.interfaces.OnTweetPostListener;
import com.codepath.apps.simpletweets.R;
import com.codepath.apps.simpletweets.TwitterApplication;
import com.codepath.apps.simpletweets.network.TwitterClient;
import com.codepath.apps.simpletweets.adapters.TweetsRecyclerViewAdapter;
import com.codepath.apps.simpletweets.models.Tweet;
import com.codepath.apps.simpletweets.utils.ItemClickSupport;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class TimelineActivity extends AppCompatActivity implements OnTweetPostListener {

  private TwitterClient client;
  private ArrayList<Tweet> tweets;
  private TweetsRecyclerViewAdapter tweetsRecyclerViewAdapter;


  @Bind(R.id.rvTweets) RecyclerView rvTweets;
  @Bind(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_timeline);

    ButterKnife.bind(this);

    // Change ActionBar Icon
    showTwitterIcon();

    // Setup RecyclerView
    setupRecyclerView();

    // Item click Listener
    ItemClickSupport.addTo(rvTweets).setOnItemClickListener(mItemClickListener);

    // Setup refresh listener which triggers new data loading
    swipeContainer.setOnRefreshListener(mRefreshListener);
    // Configure the refreshing colors
    swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
        android.R.color.white,
        android.R.color.holo_blue_light,
        android.R.color.holo_orange_light);

    // Get client
    client = TwitterApplication.getRestClient();

    // Populate TimeLine
    populateTimeline();

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_timeline, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    switch (id){
      case R.id.itemCompose:
        showComposeTweetDialog();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  /**
   * Timeline from Twitter or DB
   */
  private void populateTimeline(){
    if(isInternetAvailable()){
      client.getHomeTimeline(mJsonHttpResponseHandler, 0);
    }else {
      // No Internet - doosh...
      getStoredTweets(0);
    }
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
        if(isInternetAvailable()) {
          long maxId = tweets.get(tweets.size() - 1).getUid() - 1; // -1 so that duplicate will not appear..
          client.getHomeTimeline(mJsonHttpResponseHandler, maxId);
        }else{
          getStoredTweets(page);
        }
      }
    });
  }

  /**
   * Get Stored Tweets
   * @param page
   */
  private void getStoredTweets(int page){
    int curSize = tweetsRecyclerViewAdapter.getItemCount();
    List<Tweet> tweetList = Tweet.getAll(page);
    if(tweetList.size() > 0){
      tweets.addAll(tweetList);
      tweetsRecyclerViewAdapter.notifyItemRangeInserted(curSize, tweetList.size());
    }
  }


  public void showComposeTweetDialog(){
    FragmentManager fragmentManager = getSupportFragmentManager();
    ComposeTweetDialog dialog = ComposeTweetDialog.newInstance();

    dialog.show(fragmentManager, "compose");
  }

  public void showTwitterIcon(){
    ActionBar actionBar = getSupportActionBar();
    if(actionBar != null){
      actionBar.setDisplayShowHomeEnabled(true);
      actionBar.setLogo(R.drawable.ic_twitter);
      actionBar.setDisplayUseLogoEnabled(true);
    }
  }

  @Override
  public void onTweetPost(Tweet tweet) {
    // Add Tweet in the beginning of list
    tweets.add(0, tweet);
    tweetsRecyclerViewAdapter.notifyItemInserted(0);
  }


  public boolean isInternetAvailable() {
    Runtime runtime = Runtime.getRuntime();
    try {
      Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
      int     exitValue = ipProcess.waitFor();
      return (exitValue == 0);
    } catch (IOException e)          { e.printStackTrace(); }
    catch (InterruptedException e) { e.printStackTrace(); }
    return false;
  }


  private final SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
    @Override
    public void onRefresh() {
      if(isInternetAvailable()) {
        // Clear Old Items
        tweetsRecyclerViewAdapter.clear();
        // Get New
        client.getHomeTimeline(mJsonHttpResponseHandler, 0);
      }else{
        swipeContainer.setRefreshing(false);
      }
    }
  };

  private final JsonHttpResponseHandler mJsonHttpResponseHandler = new JsonHttpResponseHandler() {
    @Override
    public void onStart() {
      Log.d("DEBUG", "Request: " + super.getRequestURI().toString());
    }

    @Override
    public void onFinish() {
      super.onFinish();
      //Progress Bar
      // Swipe Refreshing
      swipeContainer.setRefreshing(false);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
      Log.d("DEBUG", "Resposne: " + jsonArray.toString());

      int curSize = tweetsRecyclerViewAdapter.getItemCount();
      ArrayList<Tweet> arrayList = Tweet.fromJSONArray(jsonArray);
      tweets.addAll(arrayList);

      // TODO: Remove later
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
  private final ItemClickSupport.OnItemClickListener mItemClickListener = new ItemClickSupport.OnItemClickListener() {
    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

      Tweet tweet = tweets.get(position);

      Intent i = new Intent(getApplicationContext(), TweetDetailActivity.class);
      i.putExtra("tweet", tweet);
      startActivity(i);
    }
  };

}

