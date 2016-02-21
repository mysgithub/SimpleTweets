package com.codepath.apps.simpletweets.fragments;

import android.app.Activity;
import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.simpletweets.R;
import com.codepath.apps.simpletweets.TwitterApplication;
import com.codepath.apps.simpletweets.interfaces.OnTweetPostListener;
import com.codepath.apps.simpletweets.models.Tweet;
import com.codepath.apps.simpletweets.models.User;
import com.codepath.apps.simpletweets.models.gson.TweetPostResponse;
import com.codepath.apps.simpletweets.models.gson.TwitterProfileResponse;
import com.codepath.apps.simpletweets.network.TwitterClient;
import com.codepath.apps.simpletweets.utils.TwitterUtil;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Shyam Rokde on 2/18/16.
 */
public class ComposeTweetDialog extends DialogFragment {

  @Bind(R.id.etTweet) EditText etTweet;
  @Bind(R.id.tvCharCount) TextView tvCharCount;
  @Bind(R.id.ivProfileImage) ImageView ivProfileImage;
  @Bind(R.id.tvName) TextView tvName;
  @Bind(R.id.tvScreenName) TextView tvScreenName;
  @Bind(R.id.btnTweet) Button btnTweet;

  private TwitterClient twitterClient;
  private OnTweetPostListener tweetPostListener;
  private TwitterProfileResponse twitterProfileResponse;
  private Context mContext;


  public ComposeTweetDialog(){}


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_compose, container);
    getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

    mContext = getContext();

    ButterKnife.bind(this, view);

    // Set Background
    ivProfileImage.setBackgroundResource(android.R.color.transparent);

    // Get client
    twitterClient = TwitterApplication.getRestClient();

    // Populate
    populateDailog();


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

  @OnClick(R.id.ibCancel)
  public void onCancelButtonClick(){
    dismiss();
  }

  @OnClick(R.id.btnTweet)
  public void onTweetButtonClick(){
    // Post Tweet
    twitterClient.postTweet(mPostTweetResponseHandler, etTweet.getText().toString());
  }

  public void populateDailog(){
    // Add TextWatcher
    etTweet.addTextChangedListener(mTextWatcher);
    tvCharCount.setText(String.valueOf(TwitterUtil.TWEET_MAX_ALLOWED_CHAR));

    // Get User Info
    twitterClient.getProfileInfo(mProfileInfoResponseHandler);
  }

  public void setProfileData(){
    Glide.with(getContext()).load(twitterProfileResponse.getProfileImageUrl()).fitCenter().into(ivProfileImage);
    tvName.setText(twitterProfileResponse.getName());
    String formatted = String.format("@%s", twitterProfileResponse.getScreenName());
    tvScreenName.setText(formatted);
  }

  private final TextHttpResponseHandler mPostTweetResponseHandler = new TextHttpResponseHandler(){
    @Override
    public void onStart() {
      Log.d("DEBUG", "POST Request: " + super.getRequestURI().toString());
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
      Log.d("DEBUG", "POST Resposne: " + responseString);

      TweetPostResponse tweetPostResponse = TweetPostResponse.parseJSON(responseString);
      User user = new User();
      user.setUid(tweetPostResponse.getUser().getId());
      user.setName(tweetPostResponse.getUser().getName());
      user.setScreenName(tweetPostResponse.getUser().getScreenName());
      user.setProfileImageUrl(tweetPostResponse.getUser().getProfileImageUrl());
      user.save(); // save in db
      Tweet tweet = new Tweet();
      tweet.setBody(tweetPostResponse.getText());
      tweet.setUid(tweetPostResponse.getId());
      tweet.setCreatedAt(TwitterUtil.getDateFromString(tweetPostResponse.getCreatedAt()));
      tweet.setUser(user);
      tweet.save(); // save in db

      tweetPostListener.onTweetPost(tweet);
      dismiss();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
      Log.d("ERROR", "Unable to Reply Post - " + responseString);
      Toast.makeText(getContext(), "Unable to connect to twitter.com", Toast.LENGTH_SHORT).show();
    }
  };

  private final TextHttpResponseHandler mProfileInfoResponseHandler = new TextHttpResponseHandler(){
    @Override
    public void onStart() {
      Log.d("DEBUG", "Request: " + super.getRequestURI().toString());
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
      Log.d("DEBUG", "Resposne: " + responseString);
      // Using Gson
      twitterProfileResponse = TwitterProfileResponse.parseJSON(responseString);
      setProfileData();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
      Toast.makeText(mContext, "Sorry!! Unable to connect to twitter.com", Toast.LENGTH_SHORT).show();
    }
  };

  private final TextWatcher mTextWatcher = new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
      int remainingCharCount = TwitterUtil.TWEET_MAX_ALLOWED_CHAR - s.length();
      tvCharCount.setText(String.valueOf(remainingCharCount));
      btnTweet.setEnabled(true);
      if(remainingCharCount < 0){
        btnTweet.setEnabled(false);
      }
    }

    @Override
    public void afterTextChanged(Editable s) {}
  };
}
