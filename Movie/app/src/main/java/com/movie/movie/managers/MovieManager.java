package com.movie.movie.managers;

import android.annotation.SuppressLint;

import com.movie.movie.listeners.ActorListener;
import com.movie.movie.listeners.MovieCastListener;
import com.movie.movie.listeners.MovieDurationListener;
import com.movie.movie.listeners.MovieListener;
import com.movie.movie.network.MovieApi;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;



/*
 * The MovieManager is a Singleton class who provide all the data needed about the movies
 * */

public class MovieManager {
  private static MovieManager movieManager;

  private RetrofitManager retrofitManager;

  private MovieManager() {
    retrofitManager = new RetrofitManager();
    retrofitManager.setApi(MovieApi.class, MovieApi.BASE_URL);
  }

  public synchronized static MovieManager getInstance() {
    if (movieManager == null) {
      movieManager = new MovieManager();
    }
    return movieManager;
  }

  /*
   *  this method get the top rated movie from the MovieApi per page.
   *  call the MovieListener in the end with the response
   * */
  @SuppressLint("CheckResult")
  public void getTopRatedMovies(int page, MovieListener listener) {

    if (retrofitManager == null || listener == null) {
      return;
    }

    retrofitManager.with(MovieApi.class).create(MovieApi.class)
            .getTopRatedMovie(MovieApi.API_KEY, "en-US", page).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(listener::OnReceivedMovies, throwable -> listener.OnFailedToReceiveMovies(throwable.getMessage()));
  }


  /*
   *  this method search for movies according to the search param and page param.
   *  call the MovieListener in the end with the response
   * */
  @SuppressLint("CheckResult")
  public void searchMovies(String search, int page, MovieListener listener) {
    if (retrofitManager == null || listener == null) {
      return;
    }

    retrofitManager.with(MovieApi.class).create(MovieApi.class)
            .searchMovies(MovieApi.API_KEY, "en-US", search, String.valueOf(page)).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(listener::OnReceivedMovies, throwable -> listener.OnFailedToReceiveMovies(throwable.getMessage()));
  }

  /*
   *  this method get the movie's cast according to the movie id param.
   *  call the MovieCastListener in the end with the response
   * */
  @SuppressLint("CheckResult")
  public void getCast(long movieId, MovieCastListener listener) {
    if (retrofitManager == null || listener == null) {
      return;
    }

    retrofitManager.with(MovieApi.class).create(MovieApi.class)
            .getCast(movieId, MovieApi.API_KEY).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(listener::OnReceivedMovieCast, throwable -> listener.OnFailedToReceiveMovieCast(throwable.getMessage()));
  }

  /*
   *  this method get the duration of a movie  according to the movie id param.
   *  call the MovieDurationListener in the end with the response
   * */
  @SuppressLint("CheckResult")
  public void getDuration(long movieId, MovieDurationListener listener) {

    if (retrofitManager == null || listener == null) {
      return;
    }

    retrofitManager.with(MovieApi.class).create(MovieApi.class)
            .getMovieDuration(movieId, MovieApi.API_KEY).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(listener::OnReceivedMovieDuration, throwable -> listener.OnFailedToReceiveMovieDuration(throwable.getMessage()));

  }

  /*
   *  this method get the actor details according to the actor id param.
   *  call the ActorListener in the end with the response
   * */
  @SuppressLint("CheckResult")
  public void getActorDetails(long actorId, ActorListener listener) {

    if (retrofitManager == null || listener == null) {
      return;
    }

    retrofitManager.with(MovieApi.class).create(MovieApi.class)
            .getActorDetails(actorId, MovieApi.API_KEY).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe(listener::OnReceivedActorDetails, throwable -> listener.OnFailedToReceiveActorDetails(throwable.getMessage()));

  }
}
