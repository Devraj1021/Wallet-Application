package com.wallet.service;

import com.wallet.dto.BalanceResponse;
import com.wallet.dto.TransactionResponse;

import java.util.UUID;

public interface WalletService {

    BalanceResponse getBalance(UUID userId);

    TransactionResponse topUp(UUID userId, Long amount, String referenceId);

    TransactionResponse bonus(UUID userId, Long amount, String referenceId);

    TransactionResponse spend(UUID userId, Long amount, String referenceId);
}
