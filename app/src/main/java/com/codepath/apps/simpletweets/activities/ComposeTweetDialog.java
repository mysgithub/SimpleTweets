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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.simpletweets.OnTweetPostListener;
import com.codepath.apps.simpletweets.R;
import com.codepath.apps.simpletweets.TwitterApplication;
import com.codepath.apps.simpletweets.models.Profile;
import com.codepath.apps.simpletweets.models.Tweet;
import com.codepath.apps.simpletweets.network.TwitterClient;
import com.codepath.apps.simpletweets.utils.TwitterUtil;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

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
  @Bind(R.id.ivProfileImage) ImageView ivProfileImage;
  @Bind(R.id.tvName) TextView tvName;
  @Bind(R.id.tvScreenName) TextView tvScreenName;
  @Bind(R.id.btnTweet) Button btnTweet;

  private TwitterClient twitterClient;
  private OnTweetPostListener tweetPostListener;
  private Profile profile;


  public ComposeTweetDialog(){}


  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_compose, container);
    getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

    ButterKnife.bind(this, view);

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
    twitterClient.getUserInfo(mUserInfoResponseHandler);
  }

  public void setProfileData(){
    Picasso.with(getContext()).load(profile.getProfileImageUrl()).into(ivProfileImage);
    tvName.setText(profile.getName());
    tvScreenName.setText("@" + profile.getScreenName());
  }

  private final JsonHttpResponseHandler mPostTweetResponseHandler = new JsonHttpResponseHandler(){
    @Override
    public void onStart() {
      Log.d("DEBUG", "POST Request: " + super.getRequestURI().toString());
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
      Log.d("DEBUG", "POST Resposne: " + response.toString());
      Tweet tweet = new Tweet(response);
      tweet.save();
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

  private final JsonHttpResponseHandler mUserInfoResponseHandler = new JsonHttpResponseHandler(){
    @Override
    public void onStart() {
      Log.d("DEBUG", "Request: " + super.getRequestURI().toString());
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
      Log.d("DEBUG", "Resposne: " + response.toString());
      profile = new Profile(response);
      setProfileData();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
      Toast.makeText(getContext(), "Sorry!! Unable to connect to twitter", Toast.LENGTH_SHORT).show();
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
