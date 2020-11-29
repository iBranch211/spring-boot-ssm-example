package com.spring.ssm.services;

import com.spring.ssm.domain.Payment;
import com.spring.ssm.domain.PaymentEvent;
import com.spring.ssm.domain.PaymentState;
import com.spring.ssm.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@SpringBootTest
class PaymentServiceImplTest {

    @Autowired
    PaymentService paymentService;

    @Autowired
    PaymentRepository paymentRepository;


    Payment payment;

    @BeforeEach
    void setUp() {
        payment = Payment.builder()
                .amout(new BigDecimal("12.99"))
                .build();
    }

    @Transactional
    @RepeatedTest(10)
    @Test
    void preAuth() {
         Payment savedPayment = paymentService.newPayment(payment);

         System.out.println("Should Be NEW");
         System.out.println(savedPayment.getState());

         StateMachine<PaymentState, PaymentEvent> sm = paymentService.preAuth(savedPayment.getId());

         Payment preAuthedPayment = paymentRepository.getOne(savedPayment.getId());

         System.out.println("Should Be PRE_AUTH or PRE_AUTH_ERROR");
         System.out.println(sm.getState().getId());

         System.out.println(preAuthedPayment);
    }
}