package org.arjun.cataloguemicroservice.service.converter;

import java.math.BigDecimal;
import org.arjun.cataloguemicroservice.CreateCatalogueRequest;
import org.arjun.cataloguemicroservice.CreateItemRequest;
import org.arjun.cataloguemicroservice.CreateItemStreamRequest;
import org.arjun.cataloguemicroservice.CreateUserRequest;
import org.arjun.cataloguemicroservice.entity.Catalogue;
import org.arjun.cataloguemicroservice.entity.Item;
import org.arjun.cataloguemicroservice.entity.User;
import org.springframework.stereotype.Component;


@Component
public class Converter {
  public User toUser(CreateUserRequest request) {
    return new org.arjun.cataloguemicroservice.entity.User(
                    request.getUser().getUserId(),
                    request.getUser().getUsername());
  }

  public Catalogue toCatalogue(CreateCatalogueRequest request) {
    return new org.arjun.cataloguemicroservice.entity.Catalogue(
                    request.getCatalogue().getCatalogueId(),
                    request.getCatalogue().getUserId(),
                    request.getCatalogue().getCatalogueName(),
                    request.getCatalogue().getDescription());
  }

  public Item toItem(CreateItemRequest request) {
    return new org.arjun.cataloguemicroservice.entity.Item(
            request.getItem().getName(),
            BigDecimal.valueOf(request.getItem().getPrice()),
            request.getItem().getQuantity(),
            request.getItem().getType().toString(),
            request.getItem().getItemId(),
            request.getItem().getCatalogueId());
  }

  public CreateItemRequest convertToCreateItemRequest(CreateItemStreamRequest request) {
    return CreateItemRequest.newBuilder().setItem(request.getItem()).build();
  }
}
