package org.arjun.CatalogueMicroservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

  @Id
  private Integer userId;
  private String userName;

  public User() {
  }

  public User(Integer uid, String uname) {
    this.userId = uid;
    this.userName = uname;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  @Override
  public String toString() {
    return "User{" +
            "uid=" + userId +
            ", uname='" + userName + '\'' +
            '}';
  }
}
