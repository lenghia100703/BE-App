package com.mobileapp.backend.controllers;

import com.mobileapp.backend.dtos.CommonResponseDto;
import com.mobileapp.backend.dtos.PaginatedDataDto;
import com.mobileapp.backend.dtos.transaction.AddTransactionDto;
import com.mobileapp.backend.dtos.transaction.TransactionDto;
import com.mobileapp.backend.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @GetMapping("")
    public PaginatedDataDto<TransactionDto> getAllTransactions(@RequestParam("page") int page) {
        return transactionService.getAllTransactions(page);
    }

    @GetMapping("/me/{id}")
    public CommonResponseDto<List<TransactionDto>> getTransactionByUserId(@PathVariable Long id) {
        return new CommonResponseDto<>(transactionService.getTransactionByUserId(id).stream().map(TransactionDto::new).toList());
    }

    @GetMapping("/{id}")
    public CommonResponseDto<TransactionDto> getTransactionById(@PathVariable("id") Long id) {
        return new CommonResponseDto<>(new TransactionDto(transactionService.getTransactionById(id)));
    }

    @PostMapping("")
    public CommonResponseDto<TransactionDto> createTransaction(@RequestBody AddTransactionDto addTransactionDto) {
        return new CommonResponseDto<>(new TransactionDto(transactionService.createTransaction(addTransactionDto)));
    }

    @PostMapping("/create-by-user/{id}")
    public CommonResponseDto<TransactionDto> createTransactionByUserId(@PathVariable("id") Long id, @RequestBody AddTransactionDto addTransactionDto) {
        return new CommonResponseDto<>(new TransactionDto(transactionService.createTransactionByUserId(id, addTransactionDto)));
    }

    @DeleteMapping("/{id}")
    public CommonResponseDto<String> deleteTransaction(@PathVariable("id") Long id) {
        return new CommonResponseDto<>(transactionService.deleteTransaction(id));
    }
}
