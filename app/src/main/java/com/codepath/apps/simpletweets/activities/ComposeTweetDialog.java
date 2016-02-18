package com.codepath.apps.simpletweets.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.codepath.apps.simpletweets.R;

/**
 * Created by Shyam Rokde on 2/18/16.
 */
public class ComposeTweetDialog extends DialogFragment {

  public ComposeTweetDialog(){}

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_compose, container);
    getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

    return view;
  }

  // Singleton
  public static ComposeTweetDialog newInstance(){
    ComposeTweetDialog dialog = new ComposeTweetDialog();
    return dialog;
  }
}
