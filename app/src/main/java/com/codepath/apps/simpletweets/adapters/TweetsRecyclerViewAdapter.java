package com.codepath.apps.simpletweets.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpletweets.R;
import com.codepath.apps.simpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Shyam Rokde on 2/17/16.
 */
public class TweetsRecyclerViewAdapter extends RecyclerView.Adapter<TweetsRecyclerViewAdapter.ViewHolder>{

  public ArrayList<Tweet> mTweets;
  Context mContext;

  public TweetsRecyclerViewAdapter(ArrayList<Tweet> tweets, Context context){
    mTweets = tweets;
    mContext = context;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    LayoutInflater inflater = LayoutInflater.from(context);

    // Inflate custom layout
    View tweetResultView = inflater.inflate(R.layout.item_tweet, parent, false);
    // Return new holder instance
    return new ViewHolder(tweetResultView);
  }

  @Override
  public void onBindViewHolder(ViewHolder viewHolder, int position) {
    // 1. Get Tweet
    Tweet tweet = mTweets.get(position);

    // 2. Populate
    viewHolder.tvUserName.setText(tweet.getUser().getScreenName());
    viewHolder.tvBody.setText(tweet.getBody());
    viewHolder.ivProfileImage.setImageResource(android.R.color.transparent); // clear out old image for recycled view
    Picasso.with(mContext).load(tweet.getUser().getProfileImageUrl()).into(viewHolder.ivProfileImage);
    viewHolder.tvTime.setText(getFormattedRelativeTime(tweet.getCreatedAt()));
  }

  private String getFormattedRelativeTime(Date timestamp){
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

  @Override
  public int getItemCount() {
    return mTweets.size();
  }

  /**
   * ViewHolder
   */
  public static class ViewHolder extends RecyclerView.ViewHolder  {

    @Bind(R.id.ivProfileImage) ImageView ivProfileImage;
    @Bind(R.id.tvUserName) TextView tvUserName;
    @Bind(R.id.tvBody) TextView tvBody;
    @Bind(R.id.tvTime) TextView tvTime;

    public ViewHolder(final View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  public void clear() {
    final int size = getItemCount();
    mTweets.clear();
    notifyItemRangeRemoved(0, size);
  }
}
