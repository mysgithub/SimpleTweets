package com.codepath.apps.simpletweets.models;

import android.text.format.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Shyam Rokde on 2/16/16.
 */
public class Tweet {
  private String body;
  private long uid;
  private User user;
  private String createdAt;


  public String getBody() {
    return body;
  }

  public long getUid() {
    return uid;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public User getUser() {
    return user;
  }

  private String getFormattedRelativeTime(String createdAt){
    String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
    SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
    sf.setLenient(true);

    String relativeDate = "";

    try {
      long dateMillis = sf.parse(createdAt).getTime();
      String relativeTime = DateUtils.getRelativeTimeSpanString(dateMillis).toString();
      String[] words = relativeTime.split("\\s+");
      relativeDate = String.format("%s%s", words[0], words[1].charAt(0));
    } catch (ParseException e) {
      e.printStackTrace();
    }

    return relativeDate;
  }

  public static Tweet fromJSON(JSONObject jsonObject){
    Tweet tweet = new Tweet();
    try{
      tweet.body = jsonObject.getString("text");
      tweet.uid = jsonObject.getLong("id");
      tweet.createdAt = tweet.getFormattedRelativeTime(jsonObject.getString("created_at"));
      tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
    }catch (JSONException e){
      e.printStackTrace();
    }

    return tweet;
  }

  public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray){
    ArrayList<Tweet> tweets = new ArrayList<>();

    for(int i=0; i < jsonArray.length(); i++){
      try {
        JSONObject jsonObject = jsonArray.getJSONObject(i);
        Tweet tweet = Tweet.fromJSON(jsonObject);
        if(tweet!=null){
          tweets.add(tweet);
        }
      } catch (JSONException e) {
        e.printStackTrace();
        continue;
      }
    }
    return tweets;
  }
}
