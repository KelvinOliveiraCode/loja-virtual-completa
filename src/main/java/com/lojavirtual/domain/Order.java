package com.lojavirtual.domain;

import com.lojavirtual.domain.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/** Pedido finalizado a partir de um carrinho. */
public record Order(
        Long id,
        Long clienteId,
        List<OrderItem> items,
        OrderStatus status,
        BigDecimal total,
        LocalDateTime createdAt
) {
}
