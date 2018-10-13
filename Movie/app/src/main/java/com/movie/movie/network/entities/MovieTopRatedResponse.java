package com.movie.movie.network.entities;

import com.google.gson.annotations.SerializedName;
import com.movie.movie.models.MovieItem;

import java.util.ArrayList;

/*
 * The MovieTopRatedResponse class is used for the movie response list we get From the MovieApi
 * */
public class MovieTopRatedResponse {

  @SerializedName("results")
  public ArrayList<MovieItem> results;
}
