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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateMovementUseCase {

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private CommerceRepository commerceRepository;

    public Long execute(UUID uuid, MovementDTO movementDTO) {
        Movement updatedMovement = this.movementRepository.findByUuid(uuid).orElseThrow(() -> {
            throw new NotFoundException("Movimentação");
        });

        var user = findUser(movementDTO.getUserUuid());
        var product = findProduct(movementDTO.getProductUuid());
        var commerce = findCommerce(movementDTO.getCommerceUuid());

        TypeEnum type = TypeEnum.fromString(movementDTO.getType());

        updatedMovement.setType(type);
        updatedMovement.setQuantity(movementDTO.getQuantity());
        updatedMovement.setOrigin(movementDTO.getOrigin());
        updatedMovement.setDestination(movementDTO.getDestination());
        updatedMovement.setMovementDate(movementDTO.getMovementDate());
        updatedMovement.setObservation(movementDTO.getObservation());
        updatedMovement.setUser(user);
        updatedMovement.setProduct(product);
        updatedMovement.setCommerce(commerce);

        updatedMovement = this.movementRepository.save(updatedMovement);
        return updatedMovement.getId();
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
