package com.mobileapp.backend.repositories;

import com.mobileapp.backend.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    @Query("SELECT t FROM TransactionEntity t WHERE t.isDeleted = false")
    List<TransactionEntity> findAllTransactionNotDeleted();

    @Query("SELECT t FROM TransactionEntity t WHERE t.userId.id = :id")
    List<TransactionEntity> findTransactionByUserId(@Param("id") Long id);
}
