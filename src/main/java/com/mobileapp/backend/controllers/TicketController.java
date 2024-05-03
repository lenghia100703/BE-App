package com.mobileapp.backend.controllers;

import com.mobileapp.backend.dtos.CommonResponseDto;
import com.mobileapp.backend.dtos.PaginatedDataDto;
import com.mobileapp.backend.dtos.ticket.TicketDto;
import com.mobileapp.backend.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {
    @Autowired
    TicketService ticketService;

    @GetMapping("")
    public PaginatedDataDto<TicketDto> getAllTickets(@RequestParam("page") int page) {
        return ticketService.getAllTicket(page);
    }

    @PostMapping("/{id}")
    public CommonResponseDto<TicketDto> createTicket(@PathVariable("id") Long transactionId) {
        return new CommonResponseDto<>(new TicketDto(ticketService.createTicket(transactionId)));
    }
}
