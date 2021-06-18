package org.arjun.CatalogueMicroservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Catalogue {

  @Id
  private Integer catalogueId;
  private Integer userId;
  private String catalogueName;

  public Catalogue() {
  }

  public Catalogue(Integer catalogueId, Integer userId, String catalogueName) {
    this.catalogueId = catalogueId;
    this.userId = userId;
    this.catalogueName = catalogueName;
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

  public String getCatalogueName() {
    return catalogueName;
  }

  public void setCatalogueName(String catalogueName) {
    this.catalogueName = catalogueName;
  }

  @Override
  public String toString() {
    return "Catalogue{" +
            "catalogueId=" + catalogueId +
            ", userId=" + userId +
            '}';
  }
}
