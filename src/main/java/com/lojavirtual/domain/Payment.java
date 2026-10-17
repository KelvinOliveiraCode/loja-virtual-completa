package com.lojavirtual.domain;

import com.lojavirtual.domain.enums.PaymentMethod;
import com.lojavirtual.domain.enums.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/** Pagamento de um pedido (modo simula&ccedil;&atilde;o: aprova&ccedil;&atilde;o imediata). */
public record Payment(
        Long id,
        Long pedidoId,
        BigDecimal valor,
        PaymentMethod metodo,
        PaymentStatus status,
        LocalDateTime pagoEm
) {
}
