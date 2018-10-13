package com.movie.movie.managers;

import java.util.HashMap;

import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/*
 * The RetrofitManager manage and build the retrofit's by the API class and URL
 * */
public class RetrofitManager {

  private HashMap<Class, Retrofit> retrofits = new HashMap<>(); //  hold the API class with the according retrofit object

  /*
   * this method returns the retrofit object according to the API class
   * */
  public Retrofit with(Class api) {
    return retrofits.get(api);
  }


  /*
   * this method set and build if needed a retrofit object with the API class and the URL and put it in a HashMap
   * */
  public Retrofit setApi(Class api, String url) {
    Retrofit retrofit = retrofits.get(api);
    if (retrofit != null)
      return retrofit;

    retrofits.put(api, new Builder().baseUrl(url).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create())
            .build());
    return retrofits.get(api);
  }
}
