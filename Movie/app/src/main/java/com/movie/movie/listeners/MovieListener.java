package com.movie.movie.listeners;

import com.movie.movie.network.entities.MovieTopRatedResponse;

/*
	* The MovieListener is used for passing the movie data received from the web to the class who implements it.
	* */
public interface MovieListener {
	void OnReceivedMovies(MovieTopRatedResponse response); // this method is called when we get the movie data
	void OnFailedToReceiveMovies(String error);// this method is called when we failed to get the movie data
}
