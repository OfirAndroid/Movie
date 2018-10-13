package com.movie.movie.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.movie.movie.R;

/*
* The MovieCastViewHolder class represent the view item of the movie cast in the recycler view
* */
public class MovieCastViewHolder extends RecyclerView.ViewHolder {

  public TextView name; // the actor's name
  public TextView characterName; // the character's name in the movie
  public ImageView img; // the image of the actor

  public MovieCastViewHolder(View itemView) {
    super(itemView);
    img = itemView.findViewById(R.id.img_cast);
    name = itemView.findViewById(R.id.tv_cast_name);
    characterName = itemView.findViewById(R.id.tv_character_name);
  }

}