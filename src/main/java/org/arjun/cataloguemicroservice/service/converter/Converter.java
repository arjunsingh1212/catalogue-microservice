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

  /**
   * Convert the proto to User obj.
   * @param request proto
   * @return User obj
   */
  public User toUser(final CreateUserRequest request) {
    return new User(
                    request.getUser().getUserId(),
                    request.getUser().getUsername());
  }

  /**
   * Convert the proto to Catalogue obj.
   * @param request proto
   * @return Catalogye obj
   */
  public Catalogue toCatalogue(final CreateCatalogueRequest request) {
    return new Catalogue(
                    request.getCatalogue().getCatalogueId(),
                    request.getCatalogue().getUserId(),
                    request.getCatalogue().getCatalogueName(),
                    request.getCatalogue().getDescription());
  }

  /**
   * Convert the proto to Item obj.
   * @param request proto
   * @return Item obj
   */
  public Item toItem(final CreateItemRequest request) {
    return new Item(
            request.getItem().getName(),
            BigDecimal.valueOf(request.getItem().getPrice()),
            request.getItem().getQuantity(),
            request.getItem().getType().toString(),
            request.getItem().getItemId(),
            request.getItem().getCatalogueId());
  }

  public CreateItemRequest convertToCreateItemRequest(final CreateItemStreamRequest request) {
    return CreateItemRequest.newBuilder().setItem(request.getItem()).build();
  }
}
