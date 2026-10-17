package com.lojavirtual.dto;

import java.math.BigDecimal;

/** Item de pedido exposto pela API. */
public record OrderItemResponse(
        Long produtoId,
        String nome,
        BigDecimal precoUnitario,
        Integer quantidade,
        BigDecimal subtotal
) {
}
