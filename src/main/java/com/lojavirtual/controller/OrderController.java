package com.lojavirtual.controller;

import com.lojavirtual.domain.Order;
import com.lojavirtual.dto.OrderResponse;
import com.lojavirtual.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/** Controlador de pedidos e checkout. */
@RestController
@RequestMapping("/pedidos")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /** Converte o carrinho do cliente em pedido (checkout). */
    @PostMapping("/checkout")
    public OrderResponse checkout(@RequestParam Long clienteId) {
        return OrderResponse.from(orderService.checkout(clienteId));
    }

    @GetMapping
    public List<OrderResponse> listar() {
        return orderService.listarTodos().stream()
                .map(OrderResponse::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OrderResponse buscar(@PathVariable Long id) {
        return OrderResponse.from(orderService.buscarPorId(id));
    }
}
