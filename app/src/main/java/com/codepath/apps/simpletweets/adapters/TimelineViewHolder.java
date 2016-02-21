package com.codepath.apps.simpletweets.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.simpletweets.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Shyam Rokde on 2/20/16.
 */
public class TimelineViewHolder extends RecyclerView.ViewHolder {
  @Bind(R.id.ivProfileImage) public ImageView ivProfileImage;
  @Bind(R.id.tvUserName) public TextView tvUserName;
  @Bind(R.id.tvBody) public TextView tvBody;
  @Bind(R.id.tvTime) public TextView tvTime;
  @Bind(R.id.tvScreenName) public TextView tvScreenName;

  public TimelineViewHolder(final View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }
}
