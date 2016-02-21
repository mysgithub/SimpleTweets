package com.codepath.apps.simpletweets.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.simpletweets.R;
import com.codepath.apps.simpletweets.TwitterApplication;
import com.codepath.apps.simpletweets.models.Tweet;
import com.codepath.apps.simpletweets.models.User;
import com.codepath.apps.simpletweets.models.gson.TweetPostResponse;
import com.codepath.apps.simpletweets.models.gson.singletweet.TweetResponse;
import com.codepath.apps.simpletweets.models.gson.singletweet.Variant;
import com.codepath.apps.simpletweets.network.TwitterClient;
import com.codepath.apps.simpletweets.utils.TwitterUtil;
import com.loopj.android.http.TextHttpResponseHandler;
import com.malmstein.fenster.controller.MediaFensterPlayerController;
import com.malmstein.fenster.view.FensterVideoView;

import org.apache.http.Header;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TweetDetailActivity extends AppCompatActivity {

  @Bind(R.id.ivProfileImage) public ImageView ivProfileImage;
  @Bind(R.id.tvUserName) public TextView tvUserName;
  @Bind(R.id.tvScreenName) public TextView tvScreenName;
  @Bind(R.id.tvBody) public TextView tvBody;
  @Bind(R.id.tvTime) public TextView tvTime;
  @Bind(R.id.etTweetReply) EditText etTweetReply;
  @Bind(R.id.tvCharCount) TextView tvCharCount;
  @Bind(R.id.btnTweet) Button btnTweet;
  @Bind(R.id.linearLayoutTweetButton) LinearLayout linearLayoutTweetButton;
  @Bind(R.id.ivMedia) ImageView ivMedia;
  @Bind(R.id.play_video_texture) FensterVideoView textureVideoView;
  @Bind(R.id.play_video_controller) MediaFensterPlayerController playerController;
  @Bind(R.id.fmLayout) FrameLayout fmLayout;

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

    // Set Background
    ivMedia.setBackgroundResource(android.R.color.transparent);
    ivProfileImage.setBackgroundResource(android.R.color.transparent);

    // Display selected tweet
    populateTweet();
  }

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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
    tweet = (Tweet) getIntent().getParcelableExtra("tweet");
    //tweet = (Tweet) getIntent().getSerializableExtra("tweet");

    // 2. Populate data from object
    String formattedScreenName = String.format("@%s", tweet.getUser().getScreenName());
    tvUserName.setText(tweet.getUser().getName());
    tvScreenName.setText(formattedScreenName);

    tvBody.setText(tweet.getBody());
    ivProfileImage.setImageResource(android.R.color.transparent); // clear out old image for recycled view
    Glide.with(getApplicationContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);
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
    twitterClient.getTweet(mTweetDetailResponseHandler, tweet.getUid());
    // TODO - Just Hard code TweetId for testing video
    /*long twid = 699676008585166848L;
    twitterClient.getTweet(mTweetDetailResponseHandler, twid);*/

    Log.d("DEBUG", "TweetId: " + tweet.getUid());
  }

  private final TextHttpResponseHandler mTweetDetailResponseHandler = new TextHttpResponseHandler() {
    @Override
    public void onStart() {
      Log.d("DEBUG", "Tweet Request: " + super.getRequestURI().toString());
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
      Log.d("DEBUG", "Tweet Resposne: " + responseString);

      TweetResponse tweetResponse = TweetResponse.parseJSON(responseString);

      String mediaUrl;
      // Image first
      if(tweetResponse.getEntities().getMedia() != null && tweetResponse.getEntities().getMedia().size() > 0){
        mediaUrl = tweetResponse.getEntities().getMedia().get(0).getMediaUrl();
        if(!mediaUrl.isEmpty()){
          ivMedia.setImageResource(android.R.color.transparent);
          Glide.with(getApplicationContext()).load(mediaUrl)
              .into(ivMedia);
        }else{
          ivMedia.setVisibility(View.GONE);
        }
      }

      // See if thr is any video
      if(tweetResponse.getExtendedEntities() != null
          && tweetResponse.getExtendedEntities().getMedia() != null
          && tweetResponse.getExtendedEntities().getMedia().size() > 0){
        if(!tweetResponse.getExtendedEntities().getMedia().get(0).getExpandedUrl().isEmpty()){
          mediaUrl = tweetResponse.getExtendedEntities().getMedia().get(0).getMediaUrl();
          ivMedia.setImageResource(android.R.color.transparent);
          ivMedia.setVisibility(View.VISIBLE);
          Glide.with(getApplicationContext()).load(mediaUrl).into(ivMedia);
          Log.d("DEBUG", "We got video - " + tweetResponse.getExtendedEntities().getMedia().get(0).getExpandedUrl());

          // We need Mp4 - check for it now
          if(tweetResponse.getExtendedEntities().getMedia().get(0).getVideoInfo() != null
                  && tweetResponse.getExtendedEntities().getMedia().get(0).getVideoInfo().getVariants() != null){
            List<Variant> variantList = tweetResponse.getExtendedEntities().getMedia().get(0).getVideoInfo().getVariants();
            for (Variant v: variantList) {
              if(v.getContentType().equalsIgnoreCase("video/mp4")){
                ivMedia.setVisibility(View.GONE);
                fmLayout.setVisibility(View.VISIBLE);
                // Finally we got this
                String videoUrl = v.getUrl();
                // Display Video
                textureVideoView.setMediaController(playerController);
                textureVideoView.setVideo(videoUrl, playerController.DEFAULT_VIDEO_START);
                textureVideoView.start();
              }
            }
          }
          //
        }
      }

    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
      Log.d("ERROR", "Unable to find tweet - " + responseString);
      Toast.makeText(TweetDetailActivity.this, "Unable to connect to twitter.com", Toast.LENGTH_SHORT).show();
    }
  };

  private View.OnClickListener mOnReplyTweetClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      linearLayoutTweetButton.setVisibility(View.VISIBLE);
      String formatted = String.format("@%s ", tweet.getUser().getScreenName());
      etTweetReply.setText(formatted);
      etTweetReply.setSelection(etTweetReply.length());
    }
  };

  private View.OnClickListener mOnReplyTweetButtonClickListener = new View.OnClickListener(){
    @Override
    public void onClick(View v) {
      // Post to Twitter and Display Toast
      if(TwitterUtil.isInternetAvailable()){
        twitterClient.postTweet(postResponseHandler, etTweetReply.getText().toString());
      }else{
        Toast.makeText(TweetDetailActivity.this, "Unable to connect to twitter.com", Toast.LENGTH_LONG).show();
      }

    }
  };

  private final TextWatcher mTextWatcher = new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

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
    public void afterTextChanged(Editable s) { }
  };

  private final TextHttpResponseHandler postResponseHandler = new TextHttpResponseHandler(){
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

      // Clean Edit Text and Hide Button
      etTweetReply.setText("");
      linearLayoutTweetButton.setVisibility(View.GONE);
      // Show Toast
      Toast.makeText(TweetDetailActivity.this, "Reply Tweet Posted Successfully!!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
      Log.d("ERROR", "Unable to Reply Post - " + responseString);
      Toast.makeText(TweetDetailActivity.this, "Sorry!! Unable to reply tweet at this time!", Toast.LENGTH_LONG).show();
    }

  };
}

