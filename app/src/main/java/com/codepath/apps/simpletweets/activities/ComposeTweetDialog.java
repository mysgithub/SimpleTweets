package com.codepath.apps.simpletweets.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

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
  @Bind(R.id.tvCharCount) TextView tvCharCount;

  private TwitterClient client;
  private OnTweetPostListener tweetPostListener;

  final static int TWEET_MAX_ALLOWED_CHAR = 140;

  public ComposeTweetDialog(){}

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_compose, container);
    getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

    ButterKnife.bind(this, view);

    // Add TextWatcher
    etTweet.addTextChangedListener(mTextWatcher);
    tvCharCount.setText(String.valueOf(TWEET_MAX_ALLOWED_CHAR));

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

  public static ComposeTweetDialog newInstance(){
    return new ComposeTweetDialog();
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
    client.postTweet(jsonHttpResponseHandler, etTweet.getText().toString());
  }

  private final JsonHttpResponseHandler jsonHttpResponseHandler = new JsonHttpResponseHandler(){
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



  private final TextWatcher mTextWatcher = new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
      int remainingCharCount = TWEET_MAX_ALLOWED_CHAR - s.length();
      tvCharCount.setText(String.valueOf(remainingCharCount));
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
  };
}
