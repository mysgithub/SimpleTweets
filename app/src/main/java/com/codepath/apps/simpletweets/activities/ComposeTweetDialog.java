package com.codepath.apps.simpletweets.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import com.codepath.apps.simpletweets.OnTweetPostListener;
import com.codepath.apps.simpletweets.R;
import com.codepath.apps.simpletweets.TwitterApplication;
import com.codepath.apps.simpletweets.TwitterClient;
import com.codepath.apps.simpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Shyam Rokde on 2/18/16.
 */
public class ComposeTweetDialog extends DialogFragment {

  @Bind(R.id.etTweet) EditText etTweet;

  private TwitterClient client;
  private OnTweetPostListener tweetPostListener;

  public ComposeTweetDialog(){}

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_compose, container);
    getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

    ButterKnife.bind(this, view);

    return view;
  }

  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    if(activity instanceof OnTweetPostListener){
      tweetPostListener = (OnTweetPostListener) activity;
    }else {
      throw new ClassCastException(activity.toString() + " must implement OnTweetPostListener");
    }
  }

  // Singleton
  public static ComposeTweetDialog newInstance(){
    ComposeTweetDialog dialog = new ComposeTweetDialog();
    return dialog;
  }

  @OnClick(R.id.btnCancel)
  public void onCancelButtonClick(){
    dismiss();
  }
  @OnClick(R.id.btnTweet)
  public void onTweetButtonClick(){
    // Get client
    client = TwitterApplication.getRestClient();
    // Post Tweet
    client.postTweet(getResponseHandler(), etTweet.getText().toString());
  }

  private JsonHttpResponseHandler getResponseHandler(){
    return new JsonHttpResponseHandler(){
      @Override
      public void onStart() {
        Log.d("DEBUG", "POST Request: " + super.getRequestURI().toString());
      }

      @Override
      public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        Log.d("DEBUG", "POST Resposne: " + response.toString());
        Tweet tweet = Tweet.fromJSON(response);
        tweetPostListener.onTweetPost(tweet);
        dismiss();
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
