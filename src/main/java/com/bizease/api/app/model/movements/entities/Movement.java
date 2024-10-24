package com.bizease.api.app.model.movements.entities;

import com.bizease.api.app.model.inventories.entities.Inventories;
import com.bizease.api.app.model.movements.enums.TypeEnum;
import com.bizease.api.app.model.products.entities.Products;
import com.bizease.api.app.model.user.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "movements")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UuidGenerator
    @Column(unique = true, nullable = false)
    private UUID uuid;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeEnum type;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "from_location")
    private String fromLocation;

    @Column(name = "to_location")
    private String toLocation;

    @Column(name = "movement_date")
    private LocalDate movementDate;

    private String observation;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "prod_id")
    private Products product;

    @OneToOne
    @JoinColumn(name = "inv_id")
    private Inventories inventory;
}
