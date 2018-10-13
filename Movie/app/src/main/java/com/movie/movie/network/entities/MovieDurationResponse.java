package com.movie.movie.network.entities;

import com.google.gson.annotations.SerializedName;

/*
 * The MovieDurationResponse class is used for the duration of a movie response we get From the MovieApi
 * */
public class MovieDurationResponse {

  @SerializedName("runtime")
  public int runtime;
}
