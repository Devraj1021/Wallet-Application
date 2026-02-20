package com.wallet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ledger_entries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LedgerEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EntryType entryType;

    @Column(nullable = false)
    private Long amount;

    private LocalDateTime createdAt;

    public LedgerEntry(Wallet wallet, Transaction transaction, EntryType entryType, Long amount, LocalDateTime now) {
        this.wallet = wallet;
        this.transaction = transaction;
        this.entryType = entryType;
        this.amount = amount;
        this.createdAt = now;
    }
}