package com.bizease.api.app.mappers;

import java.math.BigDecimal;

import com.bizease.api.app.models.Product;
import com.bizease.api.app.models.request.ProductRequest;
import com.bizease.api.app.models.response.ProductResponse;

public class ProductMapper {

  public static ProductResponse toResponse(Product product) {
    return new ProductResponse(
        product.getId().toString(),
        product.getName(),
        product.getDescription(),
        product.getBarcode(),
        product.getPrice().toString());
  }

  public static Product toEntity(String name, String description, String barcode, String price) {
    Product product = new Product();
    product.setName(name);
    product.setDescription(description);
    product.setBarcode(barcode);
    product.setPrice(new BigDecimal(price));
    return product;
  }

  public static Product toEntity(ProductRequest productRequest) {
    Product product = new Product();
    product.setName(productRequest.name());
    product.setDescription(productRequest.description());
    product.setBarcode(productRequest.barcode());
    product.setPrice(new BigDecimal(productRequest.price()));
    return product;
  }
}
