package com.movie.movie.listeners;

import com.movie.movie.network.entities.MovieDurationResponse;

/*
	* The MovieDurationListener is used for passing the movie duration received from the web to the class who implements it.
	* */
public interface MovieDurationListener {
	void OnReceivedMovieDuration(MovieDurationResponse duration); // this method is called when we get the movie's duration
	void OnFailedToReceiveMovieDuration(String error); // this method is called when we failed to get the movie's duration
}
