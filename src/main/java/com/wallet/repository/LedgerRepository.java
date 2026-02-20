package com.wallet.repository;

import com.wallet.entity.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface LedgerRepository extends JpaRepository<LedgerEntry, UUID> {

    @Query("""
        SELECT COALESCE(SUM(
            CASE
                WHEN le.entryType = 'CREDIT' THEN le.amount
                WHEN le.entryType = 'DEBIT' THEN -le.amount
            END
        ), 0)
        FROM LedgerEntry le
        WHERE le.wallet.id = :walletId
    """)
    Long calculateBalance(UUID walletId);
}