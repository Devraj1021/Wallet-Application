package com.wallet.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {

    @Id
    @Column(updatable = false, nullable = false, unique = true)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String referenceId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    private String type;       // TOPUP, BONUS, SPEND
    private Long amount;
    private String direction;  // CREDIT, DEBIT
    private String status;     // SUCCESS, FAILED

    private LocalDateTime createdAt;
}