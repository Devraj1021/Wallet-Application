package com.wallet.service;

import com.wallet.dto.BalanceResponse;
import com.wallet.dto.TransactionResponse;
import com.wallet.entity.Transaction;
import com.wallet.entity.Wallet;
import com.wallet.exception.DuplicateTransactionException;
import com.wallet.exception.InsufficientBalanceException;
import com.wallet.exception.WalletNotFoundException;
import com.wallet.repository.TransactionRepository;
import com.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    // Get Balance
    @Override
    public BalanceResponse getBalance(UUID userId) {

        Wallet wallet = walletRepository
                .findByUserId(userId)
                .orElseThrow(WalletNotFoundException::new);

        return new BalanceResponse(userId, wallet.getBalance());
    }

    // TopUp
    @Override
    @Transactional
    public TransactionResponse topUp(UUID userId, Long amount, String referenceId) {
        return credit(userId, amount, referenceId, "TOPUP");
    }

    // Bonus
    @Override
    @Transactional
    public TransactionResponse bonus(UUID userId, Long amount, String referenceId) {
        return credit(userId, amount, referenceId, "BONUS");
    }

    // Spend
    @Override
    @Transactional
    public TransactionResponse spend(UUID userId, Long amount, String referenceId) {

        // Idempotency check
        if (transactionRepository.existsByReferenceId(referenceId)) {
            throw new DuplicateTransactionException();
        }

        // Lock wallet row
        Wallet wallet = walletRepository
                .findByUserIdForUpdate(userId)
                .orElseThrow(WalletNotFoundException::new);

        // Check balance
        if (wallet.getBalance() < amount) {
            throw new InsufficientBalanceException();
        }

        // Deduct balance
        wallet.setBalance(wallet.getBalance() - amount);

        // Create transaction
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setReferenceId(referenceId);
        transaction.setWallet(wallet);
        transaction.setType("SPEND");
        transaction.setAmount(amount);
        transaction.setDirection("DEBIT");
        transaction.setStatus("SUCCESS");
        transaction.setCreatedAt(LocalDateTime.now());

        transactionRepository.save(transaction);
        walletRepository.save(wallet);

        return new TransactionResponse(
                transaction.getId(),
                userId,
                "SPEND",
                amount,
                wallet.getBalance(),
                "SUCCESS"
        );
    }

    // Internal Credit Method (Used by TopUp & Bonus)
    private TransactionResponse credit(
            UUID userId,
            Long amount,
            String referenceId,
            String transactionType) {

        // Idempotency check
        if (transactionRepository.existsByReferenceId(referenceId)) {
            throw new DuplicateTransactionException();
        }

        // Lock wallet row
        Wallet wallet = walletRepository
                .findByUserIdForUpdate(userId)
                .orElseThrow(WalletNotFoundException::new);

        // Add balance
        wallet.setBalance(wallet.getBalance() + amount);

        // Create transaction
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID());
        transaction.setReferenceId(referenceId);
        transaction.setWallet(wallet);
        transaction.setType(transactionType);
        transaction.setAmount(amount);
        transaction.setDirection("CREDIT");
        transaction.setStatus("SUCCESS");

        transactionRepository.save(transaction);
        walletRepository.save(wallet);

        return new TransactionResponse(
                transaction.getId(),
                userId,
                transactionType,
                amount,
                wallet.getBalance(),
                "SUCCESS"
        );
    }
}