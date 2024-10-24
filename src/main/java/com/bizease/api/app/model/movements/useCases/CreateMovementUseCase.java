package com.bizease.api.app.model.movements.useCases;

import com.bizease.api.app.exceptions.NotFoundException;
import com.bizease.api.app.model.inventories.entities.Inventories;
import com.bizease.api.app.model.inventories.repository.InventoriesRepository;
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

@Service
public class CreateMovementUseCase {

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private InventoriesRepository inventoriesRepository;

    public Movement execute(MovementDTO movementDTO) {
        var user = findUser(movementDTO.getUserId());
        var product = findProduct(movementDTO.getProductId());
        var inventory = findInventory(movementDTO.getInventoryId());

        TypeEnum type = TypeEnum.fromString(movementDTO.getType());

        Movement movement = new Movement();
        movement.setType(type);
        movement.setQuantity(movementDTO.getQuantity());
        movement.setFromLocation(movementDTO.getFromLocation());
        movement.setToLocation(movementDTO.getToLocation());
        movement.setMovementDate(movementDTO.getMovementDate());
        movement.setObservation(movementDTO.getObservation());
        movement.setUser(user);
        movement.setProduct(product);
        movement.setInventory(inventory);

        return this.movementRepository.save(movement);
    }

    private User findUser(Long id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Usuário");
        });
        return user;
    }

    private Products findProduct(Long id) {
        Products product = this.productsRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Produto");
        });
        return product;
    }

    private Inventories findInventory(Long id) {
        Inventories inventory = this.inventoriesRepository.findById(id).orElseThrow(() -> {
           throw new NotFoundException("Usuário");
        });
        return inventory;
    }
}
