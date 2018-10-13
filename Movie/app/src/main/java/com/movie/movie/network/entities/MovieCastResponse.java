package com.movie.movie.network.entities;

import com.google.gson.annotations.SerializedName;
import com.movie.movie.models.MovieCastItem;

import java.util.ArrayList;

/*
* The MovieCastResponse class is used for the cast response list we get From the MovieApi
* */
public class MovieCastResponse {

	@SerializedName("cast")
	public ArrayList<MovieCastItem> cast;
}
