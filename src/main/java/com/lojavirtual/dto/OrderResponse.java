package com.lojavirtual.dto;

import com.lojavirtual.domain.Order;
import com.lojavirtual.domain.enums.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/** Pedido exposto pela API. */
public record OrderResponse(
        Long id,
        Long clienteId,
        List<OrderItemResponse> items,
        OrderStatus status,
        BigDecimal total,
        LocalDateTime createdAt
) {
    public static OrderResponse from(Order order) {
        List<OrderItemResponse> itens = order.items().stream()
                .map(item -> new OrderItemResponse(
                        item.produtoId(),
                        item.nome(),
                        item.precoUnitario(),
                        item.quantidade(),
                        item.subtotal()))
                .collect(Collectors.toList());
        return new OrderResponse(
                order.id(),
                order.clienteId(),
                itens,
                order.status(),
                order.total(),
                order.createdAt());
    }
}
