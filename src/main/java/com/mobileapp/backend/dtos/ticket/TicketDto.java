package com.mobileapp.backend.dtos.ticket;

import com.mobileapp.backend.dtos.transaction.TransactionDto;
import com.mobileapp.backend.dtos.user.UserDto;
import com.mobileapp.backend.entities.NewsEntity;
import com.mobileapp.backend.entities.TicketEntity;
import lombok.Data;

import java.util.Date;

@Data
public class TicketDto {
    private Long id;
    private Long totalPrice;
    private Date expiry;
    private TransactionDto transactionId;
    private Date createdAt;
    private Date updatedAt;
    private String createdBy;
    private String updatedBy;

    public TicketDto(TicketEntity ticket) {
        this.id = ticket.getId();
        this.totalPrice = ticket.getTotalPrice();
        this.expiry = ticket.getExpiry();
        this.transactionId = new TransactionDto(ticket.getTransactionId());
        this.createdBy = ticket.getCreatedBy();
        this.createdAt = ticket.getCreatedAt();
        this.updatedAt = ticket.getUpdatedAt();
        this.updatedBy = ticket.getUpdatedBy();
    }
}
