package com.codepath.apps.simpletweets.models;

import android.text.format.DateUtils;
import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Shyam Rokde on 2/16/16.
 */
@Table(name = "tweets")
public class Tweet extends Model{

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

  public Tweet(JSONObject jsonObject){
    super();
    try{
      this.body = jsonObject.getString("text");
      this.uid = jsonObject.getLong("id");
      this.createdAt = getDateFromString(jsonObject.getString("created_at"));
      this.user = new User(jsonObject.getJSONObject("user"));
    }catch (JSONException e){
      e.printStackTrace();
    }
  }


  public Date getDateFromString(String date) {
    SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy", Locale.ENGLISH);
    sf.setLenient(true);

    try {
      return sf.parse(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }

  // TODO: Remove later
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
}
