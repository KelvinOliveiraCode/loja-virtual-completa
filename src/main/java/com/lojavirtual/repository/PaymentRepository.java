package com.lojavirtual.repository;

import com.lojavirtual.domain.Payment;
import java.util.List;
import java.util.Optional;

/** Reposit&oacute;rio de pagamentos. */
public interface PaymentRepository {

    Payment save(Payment payment);

    Optional<Payment> findById(Long id);

    Optional<Payment> findByPedido(Long pedidoId);

    List<Payment> findAll();
}
