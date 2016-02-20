package com.codepath.apps.simpletweets.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Shyam Rokde on 2/16/16.
 */
@Table(name = "users")
public class User extends Model implements Serializable{
  @Column(name = "name")
  private String name;
  @Column(name = "uid")
  private long uid;
  @Column(name = "screenName")
  private String screenName;
  @Column(name = "profileImageUrl")
  private String profileImageUrl;

  public User(){
    super();
  }

  public User(JSONObject jsonObject){
    super();
    try {
      this.uid = jsonObject.getLong("id");
      this.name = jsonObject.getString("name");
      this.screenName = jsonObject.getString("screen_name");
      this.profileImageUrl = jsonObject.getString("profile_image_url");
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

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

  public List<User> users(){
    return getMany(User.class, "user");
  }

}
