package com.lojavirtual.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.lojavirtual.domain.Order;
import com.lojavirtual.domain.OrderItem;
import com.lojavirtual.domain.Product;
import com.lojavirtual.domain.enums.OrderStatus;
import com.lojavirtual.exception.BusinessException;
import com.lojavirtual.repository.InMemoryCartRepository;
import com.lojavirtual.repository.InMemoryOrderRepository;
import com.lojavirtual.repository.InMemoryProductRepository;
import com.lojavirtual.repository.CartRepository;
import com.lojavirtual.repository.OrderRepository;
import com.lojavirtual.repository.ProductRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class OrderServiceTest {

    private final ProductRepository productRepo = new InMemoryProductRepository();
    private final CartRepository cartRepo = new InMemoryCartRepository();
    private final OrderRepository orderRepo = new InMemoryOrderRepository();
    private final ProductService productService = new ProductService(productRepo);
    private final CartService cartService = new CartService(cartRepo);
    private final OrderService service = new OrderService(orderRepo, productService, cartService);

    private void adicionaProdutoComEstoque(Long id, String nome, BigDecimal preco, Integer estoque) {
        productRepo.save(new Product(id, nome, "d", preco, estoque, true));
    }

    @Test
    void shouldCheckout_whenCarrinhoTemItens() {
        adicionaProdutoComEstoque(1L, "Camisa", new BigDecimal("50.00"), 10);
        cartService.adicionarItem(1L, 1L, 2);

        Order ordem = service.checkout(1L);

        assertThat(ordem.status()).isEqualTo(OrderStatus.RECEBIDO);
        assertThat(ordem.total()).isEqualTo(new BigDecimal("100.00"));
        assertThat(ordem.items()).hasSize(1);
        assertThat(cartService.obterOuCriar(1L).isEmpty()).isTrue();
    }

    @Test
    void shouldThrowBusinessException_whenCarrinhoVazio() {
        assertThatThrownBy(() -> service.checkout(1L))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void shouldThrowBusinessException_whenEstoqueInsuficiente() {
        adicionaProdutoComEstoque(1L, "Camisa", new BigDecimal("50"), 1);
        cartService.adicionarItem(1L, 1L, 5);

        assertThatThrownBy(() -> service.checkout(1L))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void shouldBaixarEstoqueAoFinalizarCheckout() {
        adicionaProdutoComEstoque(1L, "Camisa", new BigDecimal("50"), 10);
        cartService.adicionarItem(1L, 1L, 3);

        service.checkout(1L);

        assertThat(productRepo.findById(1L).get().estoque()).isEqualTo(7);
    }

    @Test
    void shouldBuscarPedidoPorId_whenExiste() {
        adicionaProdutoComEstoque(1L, "Camisa", new BigDecimal("50"), 10);
        cartService.adicionarItem(1L, 1L, 1);
        Order ordem = service.checkout(1L);

        Order busca = service.buscarPorId(ordem.id());

        assertThat(busca.id()).isEqualTo(ordem.id());
        assertThat(busca.total()).isEqualByComparingTo(new BigDecimal("50.00"));
    }

    @Test
    void shouldListarPedidos() {
        adicionaProdutoComEstoque(1L, "Camisa", new BigDecimal("50"), 10);
        cartService.adicionarItem(1L, 1L, 1);
        service.checkout(1L);

        assertThat(service.listarTodos()).hasSize(1);
    }
}
