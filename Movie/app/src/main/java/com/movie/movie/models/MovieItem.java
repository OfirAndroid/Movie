package com.movie.movie.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/*
 * The MovieItem is a model of a movie according to the MovieApi movie response
 * */
public class MovieItem extends BaseModel implements Serializable {

  @SerializedName("id")
  private long id;
  @SerializedName("vote_average")
  private double voteAverage;
  @SerializedName("title")
  private String title;
  @SerializedName("backdrop_path")
  private String backdropPath;
  @SerializedName("poster_path")
  private String posterPath;
  @SerializedName("release_date")
  private String releaseDate;
  @SerializedName("overview")
  private String overview;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public double getVoteAverage() {
    return voteAverage;
  }

  public void setVoteAverage(double vote_average) {
    this.voteAverage = voteAverage;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBackdropPath() {
    return backdropPath;
  }

  public void setBackdropPath(String backdropPath) {
    this.backdropPath = backdropPath;
  }

  public String getPosterPath() {
    return posterPath;
  }

  public void setPosterPath(String posterPath) {
    this.posterPath = posterPath;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }
}
