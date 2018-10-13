package com.movie.movie.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.movie.movie.R;

/*
 * The ProgressViewHolder class represent the view item of the progress in the recycler view
 * */

public class ProgressViewHolder extends RecyclerView.ViewHolder {
  public ProgressBar progressBar; // the progress object

  public ProgressViewHolder(View view) {
    super(view);
    progressBar = view.findViewById(R.id.progressBar);
  }
}