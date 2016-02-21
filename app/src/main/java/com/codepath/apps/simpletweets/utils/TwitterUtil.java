package com.codepath.apps.simpletweets.utils;

import android.graphics.Color;
import android.text.format.DateUtils;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Transformation;

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

  public static Transformation roundedCornerTransformation = new RoundedTransformationBuilder()
      .borderColor(Color.TRANSPARENT)
      .borderWidthDp(0)
      .cornerRadiusDp(3)
      .oval(false)
      .build();
}
