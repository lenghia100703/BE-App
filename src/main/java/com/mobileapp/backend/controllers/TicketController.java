package com.mobileapp.backend.controllers;

import com.mobileapp.backend.dtos.CommonResponseDto;
import com.mobileapp.backend.dtos.PaginatedDataDto;
import com.mobileapp.backend.dtos.ticket.TicketDto;
import com.mobileapp.backend.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {
    @Autowired
    TicketService ticketService;

    @GetMapping("")
    public PaginatedDataDto<TicketDto> getAllTickets(@RequestParam("page") int page) {
        return ticketService.getAllTicket(page);
    }

    @GetMapping("/me/{id}")
    public CommonResponseDto<List<TicketDto>> getTicketByUserId(@PathVariable("id") Long id) {
        return new CommonResponseDto<>(ticketService.getTicketByUserId(id).stream().map(TicketDto::new).toList());
    }

    @PostMapping("/{id}")
    public CommonResponseDto<TicketDto> createTicket(@PathVariable("id") Long transactionId) {
        return new CommonResponseDto<>(new TicketDto(ticketService.createTicket(transactionId)));
    }
}
