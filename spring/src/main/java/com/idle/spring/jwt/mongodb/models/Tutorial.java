package com.idle.spring.jwt.mongodb.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tutorials")
public class Tutorial {
  @Id
  private String id;

  private String title;
  private String description;
  private String img;

  public Tutorial() {

  }

//  public Tutorial(String title, String description) {
//    this.title = title;
//    this.description = description;
//  }

  public Tutorial(String title, String description, String img) {
    this.title = title;
    this.description = description;
    this.img = img;
  }

  public String getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

}
