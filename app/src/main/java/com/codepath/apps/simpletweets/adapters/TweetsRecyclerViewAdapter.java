package com.codepath.apps.simpletweets.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.codepath.apps.simpletweets.R;
import com.codepath.apps.simpletweets.models.Tweet;
import com.codepath.apps.simpletweets.utils.TwitterUtil;

import java.util.ArrayList;

/**
 * Created by Shyam Rokde on 2/17/16.
 */
public class TweetsRecyclerViewAdapter extends RecyclerView.Adapter<TimelineViewHolder>{

  public ArrayList<Tweet> mTweets;
  Context mContext;

  public TweetsRecyclerViewAdapter(ArrayList<Tweet> tweets, Context context){
    mTweets = tweets;
    mContext = context;
  }

  @Override
  public TimelineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    LayoutInflater inflater = LayoutInflater.from(context);

    // Inflate custom layout
    View tweetResultView = inflater.inflate(R.layout.item_tweet, parent, false);
    // Return new holder instance
    return new TimelineViewHolder(tweetResultView);
  }

  @Override
  public void onBindViewHolder(TimelineViewHolder viewHolder, int position) {
    // 1. Get Tweet
    Tweet tweet = mTweets.get(position);

    // 2. Populate
    String formattedScreenName = String.format("@%s", tweet.getUser().getScreenName());
    viewHolder.tvUserName.setText(tweet.getUser().getName());
    viewHolder.tvScreenName.setText(formattedScreenName);
    viewHolder.tvBody.setText(tweet.getBody());
    viewHolder.ivProfileImage.setImageResource(android.R.color.transparent); // clear out old image for recycled view
    Glide.with(mContext).load(tweet.getUser().getProfileImageUrl()).fitCenter().into(viewHolder.ivProfileImage);
    viewHolder.tvTime.setText(TwitterUtil.getFormattedRelativeTime(tweet.getCreatedAt()));
  }



  @Override
  public int getItemCount() {
    return mTweets.size();
  }


  public void clear() {
    final int size = getItemCount();
    mTweets.clear();
    notifyItemRangeRemoved(0, size);
  }
}
