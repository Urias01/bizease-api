package com.bizease.api.app.model.user.entities;

import com.bizease.api.app.model.commerce.entities.Commerce;
import com.bizease.api.app.model.commons.enums.IsActiveEnum;
import com.bizease.api.app.model.user.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @UuidGenerator()
    @Column(unique = true, nullable = false)
    private UUID uuid;

    @Column(nullable = false)
    @Length(max = 100)
    private String name;

    @Column(nullable = false)
    @Length(max = 254)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, name = "is_active")
    private IsActiveEnum isActive;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "com_id")
    private Commerce commerce;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private RoleEnum role;
}
