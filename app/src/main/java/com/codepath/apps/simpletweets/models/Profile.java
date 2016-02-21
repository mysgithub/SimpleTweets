package com.codepath.apps.simpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shyam Rokde on 2/20/16.
 */
public class Profile {


  public Profile(JSONObject jsonObject){
    try {
      this.id = jsonObject.getLong("id");
      this.name = jsonObject.getString("name");
      this.screenName = jsonObject.getString("screen_name");
      this.profileImageUrl = jsonObject.getString("profile_image_url");
      this.followingCount = jsonObject.getInt("friends_count");
      this.followersCount = jsonObject.getInt("followers_count");
      this.tweetsCount = jsonObject.getInt("statuses_count");
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getScreenName() {
    return screenName;
  }

  public String getProfileImageUrl() {
    return profileImageUrl;
  }

  public int getFollowingCount() {
    return followingCount;
  }

  public int getFollowersCount() {
    return followersCount;
  }

  public int getTweetsCount() {
    return tweetsCount;
  }

  private long id;
  private String name;
  private String screenName;
  private String profileImageUrl;
  private int followingCount;
  private int followersCount;
  private int tweetsCount;
}
