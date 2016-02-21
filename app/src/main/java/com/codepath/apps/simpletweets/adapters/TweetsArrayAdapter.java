package com.codepath.apps.simpletweets.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.simpletweets.R;
import com.codepath.apps.simpletweets.models.Tweet;

import java.util.List;

/**
 * Created by Shyam Rokde on 2/16/16.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {


  public TweetsArrayAdapter(Context context, List<Tweet> tweets){
    super(context, android.R.layout.simple_list_item_1, tweets);
  }

  // Must Use TimelineViewHolder

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    // 1. Get Tweet
    Tweet tweet = getItem(position);
    // 2. inflate
    if(convertView == null){
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
    }
    // 3. Find subview
    // Make this ButterKnife
    ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
    TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
    TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
    TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);

    // 4. Populate
    tvUserName.setText(tweet.getUser().getScreenName());
    tvBody.setText(tweet.getBody());
    ivProfileImage.setImageResource(android.R.color.transparent); // clear out old image for recycled view
    Glide.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);
    tvTime.setText(tweet.getCreatedAt().toString());

    return convertView;
  }
}
