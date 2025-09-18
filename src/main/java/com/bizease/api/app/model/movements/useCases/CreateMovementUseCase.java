package com.bizease.api.app.model.movements.useCases;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commerce.repository.CommerceRepository;
import com.bizease.api.app.model.movements.dto.MovementDTO;
import com.bizease.api.app.model.movements.entities.Movement;
import com.bizease.api.app.model.movements.enums.TypeEnum;
import com.bizease.api.app.model.movements.repositories.MovementRepository;
import com.bizease.api.app.model.products.entities.Products;
import com.bizease.api.app.model.products.repository.ProductsRepository;
import com.bizease.api.app.model.user.entities.User;
import com.bizease.api.app.model.user.repository.UserRepository;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateMovementUseCase {

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private CommerceRepository commerceRepository;

    public Long execute(MovementDTO movementDTO) {
        var user = findUser(movementDTO.getUserUuid());
        var product = findProduct(movementDTO.getProductUuid());
        var commerce = findCommerce(movementDTO.getCommerceUuid());

        TypeEnum type = TypeEnum.fromString(movementDTO.getType());

        Movement movement = new Movement();
        movement.setType(type);
        movement.setQuantity(movementDTO.getQuantity());
        movement.setOrigin(movementDTO.getOrigin());
        movement.setDestination(movementDTO.getDestination());
        movement.setMovementDate(movementDTO.getMovementDate());
        movement.setObservation(movementDTO.getObservation());
        movement.setUser(user);
        movement.setProduct(product);
        movement.setCommerce(commerce);

        movement = this.movementRepository.save(movement);

        return movement.getId();
    }

    private User findUser(String uuid) {
        User user = this.userRepository.findByUuid(UUID.fromString(uuid)).orElseThrow(() -> {
            throw new NotFoundException("Usuário");
        });
        return user;
    }

    private Products findProduct(String uuid) {
        Products product = this.productsRepository.findByUuid(uuid).orElseThrow(() -> {
            throw new NotFoundException("Produto");
        });
        return product;
    }

    private Commerce findCommerce(String uuid) {
        Commerce commerce = this.commerceRepository.findByUuid(uuid).orElseThrow(() -> {
            throw new NotFoundException("Comércio");
        });
        return commerce;
    }
}
