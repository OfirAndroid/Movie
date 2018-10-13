package com.movie.movie.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.movie.movie.R;

/*
 * The MovieViewHolder class represent the view item of the movie in the recycler view
 * */
public class MovieViewHolder extends RecyclerView.ViewHolder {

  public TextView title; // the name of the movie
  public TextView rate; // the rate of the movie
  public TextView year; // the year of the movie
  public TextView description; // the overview of the movie
  public ImageView img; // the image of the movie

  public MovieViewHolder(View itemView) {
    super(itemView);
    img = itemView.findViewById(R.id.bg_img);
    title = itemView.findViewById(R.id.title);
    year = itemView.findViewById(R.id.tv_year);
    description = itemView.findViewById(R.id.tv_overview);
    rate = itemView.findViewById(R.id.tv_rate);
  }

}