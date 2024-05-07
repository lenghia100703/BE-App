package com.mobileapp.backend.entities;

import com.mobileapp.backend.enums.Currency;
import com.mobileapp.backend.enums.PaymentMethod;
import com.mobileapp.backend.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod = PaymentMethod.PAYPAL;

    private Long totalPrice;

    private Long quantity;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PROCESSING;

    @Enumerated(EnumType.STRING)
    private Currency currency = Currency.USD;

    private String orderId;

    private boolean isDeleted = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity userId;
}
