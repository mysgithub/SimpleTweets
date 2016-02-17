package com.codepath.apps.simpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shyam Rokde on 2/16/16.
 */
public class User {
  private String name;
  private long uid;
  private String screenName;
  private String profileImageUrl;

  public String getName() {
    return name;
  }

  public long getUid() {
    return uid;
  }

  public String getScreenName() {
    return screenName;
  }

  public String getProfileImageUrl() {
    return profileImageUrl;
  }

  public static User fromJSON(JSONObject jsonObject){
    User user = new User();
    try {
      user.uid = jsonObject.getLong("id");
      user.name = jsonObject.getString("name");
      user.screenName = jsonObject.getString("screen_name");
      user.profileImageUrl = jsonObject.getString("profile_image_url");
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return user;
  }
}
