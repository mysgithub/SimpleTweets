package com.codepath.apps.simpletweets.utils;

import android.text.format.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Shyam Rokde on 2/20/16.
 */
public class TwitterUtil {

  public final static int TWEET_MAX_ALLOWED_CHAR = 140;

  public static String getFormattedRelativeTime(Date timestamp){
    String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
    SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
    sf.setLenient(true);

    String relativeDate = "";

    try {
      //long dateMillis = sf.parse(createdAt).getTime();
      String relativeTime = DateUtils.getRelativeTimeSpanString(timestamp.getTime()).toString();
      String[] words = relativeTime.split("\\s+");
      relativeDate = String.format("%s%s", words[0], words[1].charAt(0));
    } catch (Exception e) {
      e.printStackTrace();
    }

    return relativeDate;
  }
}
