package com.codepath.apps.simpletweets.models;

import android.os.Parcelable;
import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.codepath.apps.simpletweets.utils.TwitterUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Shyam Rokde on 2/16/16.
 */
@Parcel
@Table(name = "tweets")
public class Tweet extends Model implements Parcelable {

  @Column(name = "body")
  private String body;
  @Column(name = "uid", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
  private long uid;
  @Column(name = "user", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
  private User user;
  @Column(name = "createdAt", index = true)
  private Date createdAt;

  public Tweet(){
    super();
  }

  public String getBody() {
    return body;
  }

  public long getUid() {
    return uid;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public User getUser() {
    return user;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public void setUid(long uid) {
    this.uid = uid;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  // TODO: Not used because of GSON - Remove later
  public Tweet(JSONObject jsonObject){
    super();
    try{
      this.body = jsonObject.getString("text");
      this.uid = jsonObject.getLong("id");
      this.createdAt = TwitterUtil.getDateFromString(jsonObject.getString("created_at"));
      this.user = new User(jsonObject.getJSONObject("user"));
    }catch (JSONException e){
      e.printStackTrace();
    }
  }


  public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray){
    ArrayList<Tweet> tweets = new ArrayList<>();

    for(int i=0; i < jsonArray.length(); i++){
      try {
        JSONObject jsonObject = jsonArray.getJSONObject(i);
        Tweet tweet = new Tweet(jsonObject);
        // TODO: Do this in AsyncTask; we should also delete some tweets
        User user = tweet.user;
        user.save();
        tweet.user = user;
        tweet.save();

        tweets.add(tweet);
      } catch (JSONException e) {
        e.printStackTrace();
        continue;
      }
    }
    return tweets;
  }

  public static List<Tweet> getAll(int page){
    final int limit = 25;
    Log.d("DEBUG", "Loading from database - offset " + limit * page); // TODO: Remove later
    List<Tweet> tweets = new Select().from(Tweet.class)
        .orderBy("createdAt DESC")
        .offset(limit * page).limit(limit).execute();

    //TODO: new Delete().from(Tweet.class).execute();
    return tweets;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(android.os.Parcel dest, int flags) {
    dest.writeString(this.body);
    dest.writeLong(this.uid);
    dest.writeParcelable(this.user, 0);
    dest.writeLong(createdAt != null ? createdAt.getTime() : -1);
  }

  protected Tweet(android.os.Parcel in) {
    this.body = in.readString();
    this.uid = in.readLong();
    this.user = in.readParcelable(User.class.getClassLoader());
    long tmpCreatedAt = in.readLong();
    this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
  }

  public static final Parcelable.Creator<Tweet> CREATOR = new Parcelable.Creator<Tweet>() {
    public Tweet createFromParcel(android.os.Parcel source) {
      return new Tweet(source);
    }

    public Tweet[] newArray(int size) {
      return new Tweet[size];
    }
  };
}
