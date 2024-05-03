package com.mobileapp.backend.controllers;

import com.mobileapp.backend.dtos.CommonResponseDto;
import com.mobileapp.backend.dtos.PaginatedDataDto;
import com.mobileapp.backend.dtos.transaction.AddTransactionDto;
import com.mobileapp.backend.dtos.transaction.TransactionDto;
import com.mobileapp.backend.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @GetMapping("")
    public PaginatedDataDto<TransactionDto> getAllTransactions(@RequestParam("page") int page) {
        return transactionService.getAllTransactions(page);
    }

    @GetMapping("/{id}")
    public CommonResponseDto<TransactionDto> getTransactionById(@PathVariable("id") Long id) {
        return new CommonResponseDto<>(new TransactionDto(transactionService.getTransactionById(id)));
    }

    @PostMapping("")
    public CommonResponseDto<TransactionDto> createTransaction(@RequestBody AddTransactionDto addTransactionDto) {
        return new CommonResponseDto<>(new TransactionDto(transactionService.createTransaction(addTransactionDto)));
    }
}
