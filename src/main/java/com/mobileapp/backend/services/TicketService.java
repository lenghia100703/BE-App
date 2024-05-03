package com.mobileapp.backend.services;

import com.mobileapp.backend.constants.PageableConstants;
import com.mobileapp.backend.constants.TicketConstant;
import com.mobileapp.backend.dtos.PaginatedDataDto;
import com.mobileapp.backend.dtos.ticket.TicketDto;
import com.mobileapp.backend.entities.TicketEntity;
import com.mobileapp.backend.entities.TransactionEntity;
import com.mobileapp.backend.enums.ResponseCode;
import com.mobileapp.backend.exceptions.CommonException;
import com.mobileapp.backend.repositories.TicketRepository;
import com.mobileapp.backend.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TicketService {
    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public PaginatedDataDto<TicketDto> getAllTicket(int page) {
        List<TicketEntity> allTicket = ticketRepository.findAll();
        if (page >= 1) {
            Pageable pageable = PageRequest.of(page - 1, PageableConstants.LIMIT);
            Page<TicketEntity> newsPage = ticketRepository.findAll(pageable);

            List<TicketEntity> news = newsPage.getContent();
            return new PaginatedDataDto<>(news.stream().map(TicketDto::new).toList(), page, allTicket.toArray().length);

        } else {
            return new PaginatedDataDto<>(allTicket.stream().map(TicketDto::new).toList(), 1, allTicket.toArray().length);
        }
    }

    public TicketEntity createTicket(Long transactionId) {
        TicketEntity ticket = new TicketEntity();
        TransactionEntity transaction = transactionRepository.getById(transactionId);

        if (transaction == null) {
            throw new CommonException(ResponseCode.NOT_FOUND);
        }

        ticket.setTotalPrice(transaction.getTotalPrice());
        ticket.setTransactionId(transaction);
        ticket.setExpiry(new Date(System.currentTimeMillis() + TicketConstant.TICKET_EXPIRATION_DATE));

        return ticketRepository.save(ticket);
    }
}
