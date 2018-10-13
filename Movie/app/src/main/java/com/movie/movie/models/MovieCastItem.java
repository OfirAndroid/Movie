package com.movie.movie.models;

import com.google.gson.annotations.SerializedName;

/*
* The MovieCastItem is a model of a movie cast according to the MovieApi cast response
* */

public class MovieCastItem extends BaseModel {

  @SerializedName("id")
  private long id;
  @SerializedName("character")
  private String characterName;
  @SerializedName("name")
  private String name;
  @SerializedName("profile_path")
  private String profilePath;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getCharacterName() {
    return characterName;
  }

  public void setCharacterName(String characterName) {
    this.characterName = characterName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getProfilePath() {
    return profilePath;
  }

  public void setProfilePath(String profilePath) {
    this.profilePath = profilePath;
  }
}
