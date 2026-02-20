package com.wallet.controller;

import com.wallet.dto.*;
import com.wallet.service.WalletServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletServiceImpl walletService;

    // Get Balance
    @GetMapping("/{userId}/balance")
    public ResponseEntity<BalanceResponse> getBalance(
            @PathVariable UUID userId) {

        BalanceResponse response = walletService.getBalance(userId);
        return ResponseEntity.ok(response);
    }

    // Top-up (Purchase)
    @PostMapping("/topup")
    public ResponseEntity<TransactionResponse> topUp(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody TopUpRequest request) {

        TransactionResponse response =
                walletService.topUp(request.getUserId(), request.getAmount(), idempotencyKey);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Bonus
    @PostMapping("/bonus")
    public ResponseEntity<TransactionResponse> bonus(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody BonusRequest request) {

        TransactionResponse response =
                walletService.bonus(request.getUserId(), request.getAmount(), idempotencyKey);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Spend
    @PostMapping("/spend")
    public ResponseEntity<TransactionResponse> spend(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody SpendRequest request) {

        TransactionResponse response =
                walletService.spend(request.getUserId(), request.getAmount(), idempotencyKey);

        return ResponseEntity.ok(response);
    }

}