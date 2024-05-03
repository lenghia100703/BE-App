package com.mobileapp.backend.dtos.transaction;

import com.mobileapp.backend.dtos.user.UserDto;
import com.mobileapp.backend.entities.PostEntity;
import com.mobileapp.backend.entities.TransactionEntity;
import com.mobileapp.backend.enums.Currency;
import com.mobileapp.backend.enums.PaymentMethod;
import com.mobileapp.backend.enums.Status;
import lombok.Data;

import java.util.Date;

@Data
public class TransactionDto {
    private Long id;
    private PaymentMethod paymentMethod;
    private Long totalPrice;
    private Long quantity;
    private Status status;
    private String orderId;
    private Currency currency;
    private UserDto userId;
    private Date createdAt;
    private Date updatedAt;
    private String createdBy;
    private String updatedBy;

    public TransactionDto(TransactionEntity transaction) {
        this.id = transaction.getId();
        this.paymentMethod = transaction.getPaymentMethod();
        this.totalPrice = transaction.getTotalPrice();
        this.quantity = transaction.getQuantity();
        this.orderId = transaction.getOrderId();
        this.currency = transaction.getCurrency();
        this.status = transaction.getStatus();
        this.userId = new UserDto(transaction.getUserId());
        this.createdBy = transaction.getCreatedBy();
        this.createdAt = transaction.getCreatedAt();
        this.updatedAt = transaction.getUpdatedAt();
        this.updatedBy = transaction.getUpdatedBy();
    }
}
