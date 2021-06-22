package org.arjun.cataloguemicroservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "user")
public class User {

  @Id
  private String userId;
  private String userName;


  public User() {
  }

  public User(String uid, String uname) {
    this.userId = uid;
    this.userName = uname;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
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
    return "User{"
            + "uid=" + userId
            + ", uname='" + userName + '\''
            + '}';
  }

  public org.arjun.cataloguemicroservice.User toProto() {
    return org.arjun.cataloguemicroservice.User.newBuilder()
            .setUserId(getUserId()).setUsername(getUserName()).build();
  }


}
