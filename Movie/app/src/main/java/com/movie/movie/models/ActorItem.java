package com.movie.movie.models;

import com.google.gson.annotations.SerializedName;

/*
 * The ActorItem is a model of an Actor according to the json we get from the MovieApi actor response
 * */
public class ActorItem {
  @SerializedName("birthday")
  private String birthday;
  @SerializedName("name")
  private String name;
  @SerializedName("biography")
  private String biography;
  @SerializedName("place_of_birth")
  private String placeOfBirth;
  @SerializedName("profile_path")
  private String profilePath;


  public String getBirthday() {
    return birthday;
  }

  public String getProfilePath() {
    return profilePath;
  }

  public void setProfilePath(String profilePath) {
    this.profilePath = profilePath;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBiography() {
    return biography;
  }

  public void setBiography(String biography) {
    this.biography = biography;
  }

  public String getPlaceOfBirth() {
    return placeOfBirth;
  }

  public void setPlaceOfBirth(String placeOfBirth) {
    this.placeOfBirth = placeOfBirth;
  }
}
