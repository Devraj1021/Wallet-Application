package com.wallet.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "wallets")
@Data
public class Wallet {

    @Id
    @Column(updatable = false, nullable = false, unique = true)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private Long balance;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "wallet",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true)
    private java.util.List<Transaction> transactions = new java.util.ArrayList<>();
}