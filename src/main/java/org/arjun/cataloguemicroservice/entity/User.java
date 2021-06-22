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

  public User(final String uid, final String uname) {
    this.userId = uid;
    this.userName = uname;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(final String userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(final String userName) {
    this.userName = userName;
  }

  @Override
  public String toString() {
    return "User{"
            + "uid=" + userId
            + ", uname='" + userName + '\''
            + '}';
  }


}
