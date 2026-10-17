package com.lojavirtual.domain;

import java.math.BigDecimal;

/** Item de um pedido j&aacute; com preço e subtotal congelados no momento da compra. */
public record OrderItem(
        Long produtoId,
        String nome,
        BigDecimal precoUnitario,
        Integer quantidade,
        BigDecimal subtotal
) {
}
