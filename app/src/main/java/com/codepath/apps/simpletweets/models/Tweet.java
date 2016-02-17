package com.codepath.apps.simpletweets.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

  public static Tweet fromJSON(JSONObject jsonObject){
    Tweet tweet = new Tweet();
    try{
      tweet.body = jsonObject.getString("text");
      tweet.uid = jsonObject.getLong("id");
      tweet.createdAt = jsonObject.getString("created_at");
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
