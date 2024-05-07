package com.mobileapp.backend.services;

import com.mobileapp.backend.constants.TicketConstant;
import com.mobileapp.backend.entities.TicketEntity;
import com.mobileapp.backend.entities.TransactionEntity;
import com.mobileapp.backend.enums.Currency;
import com.mobileapp.backend.enums.Status;
import com.mobileapp.backend.repositories.TicketRepository;
import com.mobileapp.backend.repositories.TransactionRepository;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PaypalService {
    private final PayPalHttpClient payPalClient;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    TicketService ticketService;

    @Autowired
    TicketRepository ticketRepository;

    public PaypalService(PayPalHttpClient payPalClient) {
        this.payPalClient = payPalClient;
    }

    public String createOrder(Long transactionId) throws IOException {
        TransactionEntity transaction = transactionRepository.getById(transactionId);

        OrdersCreateRequest request = new OrdersCreateRequest();
        request.prefer("return=representation");
        request.requestBody(buildRequestBody(transaction.getTotalPrice()));

        HttpResponse<Order> response = this.payPalClient.execute(request);

        if (response.statusCode() == 201 || response.statusCode() == 200) {
            Order order = response.result();
            System.out.println(order.id());
            transaction.setOrderId(order.id());
            transactionRepository.save(transaction);
            for (LinkDescription link : order.links()) {
                if (link.rel().equalsIgnoreCase("approve")) {
                    System.out.println(link.href());
                    return link.href();
                }
            }
        }

        return null;
    }

    public String captureOrder(Long transactionId) throws IOException {
        TransactionEntity transaction = transactionRepository.getById(transactionId);
        HttpResponse<Order> response = this.payPalClient.execute(new OrdersCaptureRequest(transaction.getOrderId()));

        if (response.statusCode() == 201 || response.statusCode() == 200) {
            if (transaction != null) {
                transaction.setStatus(Status.SUCCESS);
                transactionRepository.save(transaction);
                ticketService.createTicket(transactionId);
                return "Payment successfully";
            }
        } else {
            transaction.setStatus(Status.FAIL);
            transactionRepository.save(transaction);
            return "Payment failure";
        }

        return null;
    }

    private OrderRequest buildRequestBody(Long amount) {
        OrderRequest orderRequest = new OrderRequest();
        List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<>();
        orderRequest.checkoutPaymentIntent("CAPTURE");

        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
                .amountWithBreakdown(new AmountWithBreakdown().currencyCode(String.valueOf(Currency.USD)).value(String.valueOf(amount)));


        purchaseUnitRequests.add(purchaseUnitRequest);
        orderRequest.purchaseUnits(purchaseUnitRequests);
        return orderRequest;
    }
}
