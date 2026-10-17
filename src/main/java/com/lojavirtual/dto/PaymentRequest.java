package com.lojavirtual.dto;

import com.lojavirtual.domain.enums.PaymentMethod;
import java.math.BigDecimal;

/** Requisi&ccedil;&atilde;o de processamento de pagamento. */
public record PaymentRequest(
        Long pedidoId,
        PaymentMethod metodo,
        BigDecimal valor
) {
}
