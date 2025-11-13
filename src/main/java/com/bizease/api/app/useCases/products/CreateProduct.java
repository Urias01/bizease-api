package com.bizease.api.app.useCases.products;

import org.springframework.stereotype.Service;

import com.bizease.api.app.exceptions.AlreadyExistException;
import com.bizease.api.app.mappers.ProductMapper;
import com.bizease.api.app.models.Product;
import com.bizease.api.app.models.request.ProductRequest;
import com.bizease.api.app.models.response.CreateResponse;
import com.bizease.api.app.repositories.ProductRepository;
import com.bizease.api.app.security.jwt.IJwtAuthContext;

@Service
public class CreateProduct {
  private final ProductRepository productRepository;
  private final IJwtAuthContext jwtAuthContext;

  public CreateProduct(ProductRepository productRepository, IJwtAuthContext jwtAuthContext) {
    this.productRepository = productRepository;
    this.jwtAuthContext = jwtAuthContext;
  }

  public CreateResponse execute(ProductRequest request) {
    String name = request.name().trim().toLowerCase();

    Product existsProduct = productRepository.findByNameAndTenantId(name, jwtAuthContext.getTenantId());

    if (existsProduct != null) {
      throw new AlreadyExistException("Product '" + name + "'");
    }

    Product product = ProductMapper.toEntity(request);

    Product savedProduct = productRepository.save(product);

    return new CreateResponse(savedProduct.getId());
  }
}
