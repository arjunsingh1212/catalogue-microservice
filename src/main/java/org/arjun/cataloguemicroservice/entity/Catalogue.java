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
    //default Empty constructor req. by JPA.
  }

  public Catalogue(final String catalogueId, final String userId,
                   final String catalogueName, final String description) {
    this.catalogueId = catalogueId;
    this.userId = userId;
    this.catalogueName = catalogueName;
    this.description = description;
  }

  public String getCatalogueId() {
    return catalogueId;
  }

  public void setCatalogueId(final String catalogueId) {
    this.catalogueId = catalogueId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(final String userId) {
    this.userId = userId;
  }

  public String getCatalogueName() {
    return catalogueName;
  }

  public void setCatalogueName(final String catalogueName) {
    this.catalogueName = catalogueName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
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
}
