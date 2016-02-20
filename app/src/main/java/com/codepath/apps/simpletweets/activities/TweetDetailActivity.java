package com.codepath.apps.simpletweets.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.simpletweets.R;
import com.codepath.apps.simpletweets.TwitterApplication;
import com.codepath.apps.simpletweets.TwitterClient;
import com.codepath.apps.simpletweets.models.Tweet;
import com.codepath.apps.simpletweets.utils.TwitterUtil;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TweetDetailActivity extends AppCompatActivity {

  @Bind(R.id.ivProfileImage) public ImageView ivProfileImage;
  @Bind(R.id.tvUserName) public TextView tvUserName;
  @Bind(R.id.tvBody) public TextView tvBody;
  @Bind(R.id.tvTime) public TextView tvTime;
  @Bind(R.id.etTweetReply) EditText etTweetReply;
  @Bind(R.id.tvCharCount) TextView tvCharCount;
  @Bind(R.id.btnTweet) Button btnTweet;
  @Bind(R.id.linearLayoutTweetButton) LinearLayout linearLayoutTweetButton;

  private TwitterClient twitterClient;
  private Tweet tweet;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tweet_detail);
    ButterKnife.bind(this);

    // Enable up icon
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    // Show Icon
    showTwitterIcon();

    // Twitter Client
    twitterClient = TwitterApplication.getRestClient();

    // Display selected tweet
    populateTweet();
  }

  public void showTwitterIcon(){
    ActionBar actionBar = getSupportActionBar();
    if(actionBar != null){
      actionBar.setDisplayShowHomeEnabled(true);
      actionBar.setLogo(R.drawable.ic_twitter);
      actionBar.setDisplayUseLogoEnabled(true);
    }
  }

  private void populateTweet(){
    // 1. Get Tweet
    tweet = (Tweet) getIntent().getSerializableExtra("tweet");

    // 2. Populate data from object
    String formattedUsername = String.format("<b>%s</b> @%s", tweet.getUser().getName(), tweet.getUser().getScreenName());
    tvUserName.setText(Html.fromHtml(formattedUsername));
    tvBody.setText(tweet.getBody());
    ivProfileImage.setImageResource(android.R.color.transparent); // clear out old image for recycled view
    Picasso.with(getApplicationContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);
    tvTime.setText(TwitterUtil.getFormattedRelativeTime(tweet.getCreatedAt()));
    etTweetReply.setHint("Reply to " + tweet.getUser().getName());

    // Add Edit Text click
    etTweetReply.setOnClickListener(mOnReplyTweetClickListener);
    // Add TextWatcher on Edit Text
    etTweetReply.addTextChangedListener(mTextWatcher);
    tvCharCount.setText(String.valueOf(TwitterUtil.TWEET_MAX_ALLOWED_CHAR));
    // Add Button Click
    btnTweet.setOnClickListener(mOnReplyTweetButtonClickListener);

    // 3. Call Twitter for more details
    //twitterClient.getTweet(mJsonHttpResponseHandler, tweet.getUid());

  }

  private final JsonHttpResponseHandler mJsonHttpResponseHandler = new JsonHttpResponseHandler() {
    @Override
    public void onStart() {
      Log.d("DEBUG", "Tweet Request: " + super.getRequestURI().toString());
    }

    @Override
    public void onFinish() {
      super.onFinish();
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
      Log.d("DEBUG", "Tweet Resposne: " + jsonObject);

    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
      Log.d("Failed2: ", "" + statusCode);
      Log.d("Error : ", "" + throwable);
      Log.d("Exception:", errorResponse.toString());
    }
  };

  private View.OnClickListener mOnReplyTweetClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      linearLayoutTweetButton.setVisibility(View.VISIBLE);
      etTweetReply.setText("@" + tweet.getUser().getScreenName());
      etTweetReply.setSelection(etTweetReply.length());
    }
  };

  private View.OnClickListener mOnReplyTweetButtonClickListener = new View.OnClickListener(){
    @Override
    public void onClick(View v) {
      // Post to Twitter and Display Toast
      // Post Tweet
      twitterClient.postTweet(postResponseHandler, etTweetReply.getText().toString());
    }
  };

  private final TextWatcher mTextWatcher = new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
      int remainingCharCount = TwitterUtil.TWEET_MAX_ALLOWED_CHAR - s.length();
      tvCharCount.setText(String.valueOf(remainingCharCount));
      btnTweet.setEnabled(true);
      // Deactivate Button if it reaches maximum
      if(remainingCharCount < 0){
        btnTweet.setEnabled(false);
      }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
  };

  private final JsonHttpResponseHandler postResponseHandler = new JsonHttpResponseHandler(){
    @Override
    public void onStart() {
      Log.d("DEBUG", "POST Request: " + super.getRequestURI().toString());
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
      Log.d("DEBUG", "POST Resposne: " + response.toString());
      Tweet tweet = new Tweet(response);
      tweet.save();
      // Clean Edit Text and Hide Button
      etTweetReply.setText("");
      linearLayoutTweetButton.setVisibility(View.GONE);
      // Show Toast
      Toast.makeText(TweetDetailActivity.this, "Reply Tweet Posted Successfully!!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
      Log.d("ERROR", "Unable to Reply Post - " + errorResponse.toString());
      // Show Toast
      Toast.makeText(TweetDetailActivity.this, "Sorry!! Unable to reply tweet at this time!", Toast.LENGTH_LONG).show();
    }
  };
}

