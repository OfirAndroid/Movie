package com.movie.movie.network;

import com.movie.movie.models.ActorItem;
import com.movie.movie.network.entities.MovieCastResponse;
import com.movie.movie.network.entities.MovieDurationResponse;
import com.movie.movie.network.entities.MovieTopRatedResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/*
 * The MovieApi class is used for the networks API calls
 * */

public interface MovieApi {

  String BASE_URL = "https://api.themoviedb.org/3/";
  String BASE_IMAGE = "https://image.tmdb.org/t/p/w500";
  String API_KEY = "4e0be2c22f7268edffde97481d49064a";


  /*
   *  This method called when we need to get the top rated movies data from the server
   * */
  @Headers({"Content-Type: application/json"})
  @GET("movie/top_rated")
  Observable<MovieTopRatedResponse> getTopRatedMovie(@Query("api_key") String apiKey, //
                                                     @Query("language") String language,//
                                                     @Query("page") int page);//


  /*
   *  This method called when we need to get the movie cast data from the server
   * */
  @Headers({"Content-Type: application/json"})
  @GET("movie/{movie_id}/credits")
  Observable<MovieCastResponse> getCast(@Path("movie_id") long movieId, //
                                        @Query("api_key") String apiKey);


  /*
   *  This method called when we need to get the duration of a movie from the server
   * */
  @Headers({"Content-Type: application/json"})
  @GET("movie/{movie_id}")
  Observable<MovieDurationResponse> getMovieDuration(@Path("movie_id") long movieId, //
                                                     @Query("api_key") String apiKey);


  /*
   *  This method called when we need to get the actor details from the server
   * */
  @Headers({"Content-Type: application/json"})
  @GET("person/{person_id}")
  Observable<ActorItem> getActorDetails(@Path("person_id") long personId, //
                                        @Query("api_key") String apiKey);

  /*
   *  This method called for searching a movie in the server
   * */
  @Headers({"Content-Type: application/json"})
  @GET("search/movie")
  Observable<MovieTopRatedResponse> searchMovies(@Query("api_key") String apiKey, //
                                                 @Query("language") String language,//
                                                 @Query("query") String query,//
                                                 @Query("page") String page);//


}
