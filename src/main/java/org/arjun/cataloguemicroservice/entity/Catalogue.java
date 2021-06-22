package org.arjun.cataloguemicroservice.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "catalogue")
public class Catalogue {

  @Id
  private String catalogueId;
  private String userId;
  private String catalogueName;
  private String description;

  public Catalogue() {
  }

  public Catalogue(String catalogueId, String userId, String catalogueName, String description) {
    this.catalogueId = catalogueId;
    this.userId = userId;
    this.catalogueName = catalogueName;
    this.description = description;
  }

  public String getCatalogueId() {
    return catalogueId;
  }

  public void setCatalogueId(String catalogueId) {
    this.catalogueId = catalogueId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getCatalogueName() {
    return catalogueName;
  }

  public void setCatalogueName(String catalogueName) {
    this.catalogueName = catalogueName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "Catalogue{"
            + "catalogueId='" + catalogueId + '\''
            + ", userId='" + userId + '\''
            + ", catalogueName='" + catalogueName + '\''
            + ", description='" + description + '\''
            + '}';
  }

  public org.arjun.cataloguemicroservice.Catalogue toProto() {
    return org.arjun.cataloguemicroservice.Catalogue.newBuilder()
            .setUserId(getUserId()).setCatalogueName(getCatalogueName())
            .setDescription(getDescription()).setCatalogueId(getCatalogueId()).build();
  }
}