package com.lojavirtual.dto;

import com.lojavirtual.domain.Payment;
import com.lojavirtual.domain.enums.PaymentMethod;
import com.lojavirtual.domain.enums.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/** Pagamento exposto pela API. */
public record PaymentResponse(
        Long id,
        Long pedidoId,
        BigDecimal valor,
        PaymentMethod metodo,
        PaymentStatus status,
        LocalDateTime pagoEm
) {
    public static PaymentResponse from(Payment payment) {
        return new PaymentResponse(
                payment.id(),
                payment.pedidoId(),
                payment.valor(),
                payment.metodo(),
                payment.status(),
                payment.pagoEm());
    }
}
