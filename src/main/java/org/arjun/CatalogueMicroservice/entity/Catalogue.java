package org.arjun.CatalogueMicroservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Catalogue {

  @Id
  private Integer catalogueId;
  private Integer userId;

  public Catalogue() {
  }

  public Catalogue(Integer catalogueId, Integer userId) {
    this.catalogueId = catalogueId;
    this.userId = userId;
  }

  public Integer getCatalogueId() {
    return catalogueId;
  }

  public void setCatalogueId(Integer catalogueId) {
    this.catalogueId = catalogueId;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  @Override
  public String toString() {
    return "Catalogue{" +
            "catalogueId=" + catalogueId +
            ", userId=" + userId +
            '}';
  }
}
