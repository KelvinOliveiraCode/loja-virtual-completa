package com.lojavirtual.service;

import com.lojavirtual.domain.Cart;
import com.lojavirtual.domain.Order;
import com.lojavirtual.domain.OrderItem;
import com.lojavirtual.domain.Product;
import com.lojavirtual.domain.enums.OrderStatus;
import com.lojavirtual.exception.BusinessException;
import com.lojavirtual.exception.ResourceNotFoundException;
import com.lojavirtual.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Servi&ccedil;o de pedidos. Converte o carrinho do cliente em um pedido,
 * baixando o estoque dos produtos.
 */
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final CartService cartService;

    public OrderService(OrderRepository orderRepository,
                        ProductService productService,
                        CartService cartService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.cartService = cartService;
    }

    /**
     * Converte o carrinho do cliente em um pedido, baixando o estoque dos produtos.
     *
     * @throws BusinessException se o carrinho estiver vazio ou algum produto estiver sem estoque.
     */
    public Order checkout(Long clienteId) {
        Cart carrinho = cartService.obterOuCriar(clienteId);
        if (carrinho.isEmpty()) {
            throw new BusinessException("Carrinho vazio. Adicione itens antes de finalizar a compra.");
        }

        List<OrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Long, Integer> entry : carrinho.getItems().entrySet()) {
            Long produtoId = entry.getKey();
            Integer quantidade = entry.getValue();
            Product produto = productService.buscarPorId(produtoId);
            productService.baixarEstoque(produtoId, quantidade);
            BigDecimal subtotal = produto.preco().multiply(BigDecimal.valueOf(quantidade));
            items.add(new OrderItem(produtoId, produto.nome(), produto.preco(), quantidade, subtotal));
            total = total.add(subtotal);
        }

        Order ordem = new Order(
                null,
                clienteId,
                List.copyOf(items),
                OrderStatus.RECEBIDO,
                total,
                LocalDateTime.now());
        Order salva = orderRepository.save(ordem);
        cartService.limpar(clienteId);
        return salva;
    }

    /** Busca um pedido pelo id. */
    public Order buscarPorId(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido n&atilde;o encontrado: " + id));
    }

    /** Lista todos os pedidos. */
    public List<Order> listarTodos() {
        return orderRepository.findAll();
    }
}
