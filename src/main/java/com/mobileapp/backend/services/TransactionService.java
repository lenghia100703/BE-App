package com.mobileapp.backend.services;

import com.mobileapp.backend.constants.PageableConstants;
import com.mobileapp.backend.dtos.PaginatedDataDto;
import com.mobileapp.backend.dtos.transaction.AddTransactionDto;
import com.mobileapp.backend.dtos.transaction.TransactionDto;
import com.mobileapp.backend.entities.TransactionEntity;
import com.mobileapp.backend.entities.UserEntity;
import com.mobileapp.backend.enums.ResponseCode;
import com.mobileapp.backend.exceptions.CommonException;
import com.mobileapp.backend.repositories.TransactionRepository;
import com.mobileapp.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    public PaginatedDataDto<TransactionDto> getAllTransactions(int page) {
        List<TransactionEntity> allTransactions = transactionRepository.findAllTransactionNotDeleted();
        if (page >= 1) {
            Pageable pageable = PageRequest.of(page - 1, PageableConstants.LIMIT);
            Page<TransactionEntity> transactionPage = transactionRepository.findAll(pageable);

            List<TransactionEntity> transaction = transactionPage.getContent();

            return new PaginatedDataDto<>(transaction.stream().map(TransactionDto::new).toList(), page, allTransactions.toArray().length);
        } else {
            return new PaginatedDataDto<>(allTransactions.stream().map(TransactionDto::new).toList(), 1, allTransactions.toArray().length);
        }
    }

    public TransactionEntity getTransactionById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    public List<TransactionEntity> getTransactionByUserId(Long id) {
        return transactionRepository.findTransactionByUserId(id);
    }

    public TransactionEntity createTransaction(AddTransactionDto addTransactionDto) {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setQuantity(addTransactionDto.getQuantity());
        transaction.setTotalPrice(addTransactionDto.getQuantity() * 2); // 2$/ticket
        transaction.setCreatedAt(new Date(System.currentTimeMillis()));
        transaction.setCreatedBy(userService.getCurrentUser().getEmail());
        transaction.setUserId(userService.getCurrentUser());

        return transactionRepository.save(transaction);
    }

    public TransactionEntity createTransactionByUserId(Long id, AddTransactionDto addTransactionDto) {
        UserEntity user = userRepository.getById(id);
        if (user == null) {
            throw new CommonException(ResponseCode.NOT_FOUND, "Không tìm thấy người dùng!");
        }
        TransactionEntity transaction = new TransactionEntity();
        transaction.setQuantity(addTransactionDto.getQuantity());
        transaction.setTotalPrice(addTransactionDto.getQuantity() * 2); // 2$/ticket
        transaction.setCreatedAt(new Date(System.currentTimeMillis()));
        transaction.setCreatedBy(user.getEmail());
        transaction.setUserId(user);

        return transactionRepository.save(transaction);
    }

    public String deleteTransaction(Long id) {
        TransactionEntity transaction = transactionRepository.getById(id);

        if (transaction == null) {
            throw new CommonException(ResponseCode.NOT_FOUND);
        }

        transaction.setDeleted(true);
        transactionRepository.save(transaction);
        return "Deleted successfully";
    }
}
