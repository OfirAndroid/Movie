package com.movie.movie.listeners;

import com.movie.movie.network.entities.MovieCastResponse;

/*
 * The MovieCastListener is used for passing the movie cast data received from the web to the class who implements it.
 * */
public interface MovieCastListener {

  void OnReceivedMovieCast(MovieCastResponse movieCastResponse);// this method is called when we get the movie cast data
  void OnFailedToReceiveMovieCast(String error);// this method is called when we failed to get the movie cast data
}
