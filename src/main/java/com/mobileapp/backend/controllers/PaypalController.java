package com.mobileapp.backend.controllers;

import com.mobileapp.backend.dtos.CommonResponseDto;
import com.mobileapp.backend.services.PaypalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/paypal")
public class PaypalController {
    @Autowired
    PaypalService paypalService;
    @PostMapping("/create-order/{transactionId}")
    public CommonResponseDto<String> createOrder(@PathVariable("transactionId") Long transactionId) throws IOException {
        String approvalLink = paypalService.createOrder(transactionId);
        return new CommonResponseDto<>(approvalLink);
    }

    @GetMapping("/capture-order/{transactionId}")
    public CommonResponseDto<String> captureOrder(@PathVariable("transactionId") Long transactionId) throws IOException {
        return new CommonResponseDto<>(paypalService.captureOrder(transactionId));
    }
}
