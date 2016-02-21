package com.codepath.apps.simpletweets.models;

import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.List;

/**
 * Created by Shyam Rokde on 2/16/16.
 */
@Parcel
@Table(name = "users")
public class User extends Model implements Parcelable {
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

  // TODO: Not used because of GSON - Remove later
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

  public void setName(String name) {
    this.name = name;
  }

  public void setUid(long uid) {
    this.uid = uid;
  }

  public void setScreenName(String screenName) {
    this.screenName = screenName;
  }

  public void setProfileImageUrl(String profileImageUrl) {
    this.profileImageUrl = profileImageUrl;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(android.os.Parcel dest, int flags) {
    dest.writeString(this.name);
    dest.writeLong(this.uid);
    dest.writeString(this.screenName);
    dest.writeString(this.profileImageUrl);
  }

  protected User(android.os.Parcel in) {
    this.name = in.readString();
    this.uid = in.readLong();
    this.screenName = in.readString();
    this.profileImageUrl = in.readString();
  }

  public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
    public User createFromParcel(android.os.Parcel source) {
      return new User(source);
    }

    public User[] newArray(int size) {
      return new User[size];
    }
  };
}
