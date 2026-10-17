package com.lojavirtual.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.lojavirtual.domain.Order;
import com.lojavirtual.domain.OrderItem;
import com.lojavirtual.domain.Payment;
import com.lojavirtual.domain.Product;
import com.lojavirtual.domain.enums.OrderStatus;
import com.lojavirtual.domain.enums.PaymentMethod;
import com.lojavirtual.domain.enums.PaymentStatus;
import com.lojavirtual.exception.BusinessException;
import com.lojavirtual.exception.ResourceNotFoundException;
import com.lojavirtual.repository.InMemoryCartRepository;
import com.lojavirtual.repository.InMemoryOrderRepository;
import com.lojavirtual.repository.InMemoryProductRepository;
import com.lojavirtual.repository.CartRepository;
import com.lojavirtual.repository.OrderRepository;
import com.lojavirtual.repository.ProductRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class PaymentServiceTest {

    private final ProductRepository productRepo = new InMemoryProductRepository();
    private final CartRepository cartRepo = new InMemoryCartRepository();
    private final OrderRepository orderRepo = new InMemoryOrderRepository();
    private final ProductService productService = new ProductService(productRepo);
    private final CartService cartService = new CartService(cartRepo);
    private final OrderService orderService = new OrderService(orderRepo, productService, cartService);
    private final PaymentService service = new PaymentService(
            new com.lojavirtual.repository.InMemoryPaymentRepository(), orderRepo);

    private Order pedidoComTotal(BigDecimal total) {
        productRepo.save(new Product(1L, "Camisa", "d", total, 10, true));
        cartService.adicionarItem(1L, 1L, 1);
        return orderService.checkout(1L);
    }

    @Test
    void shouldProcessarPagamento_whenValorCorreto() {
        Order pedido = pedidoComTotal(new BigDecimal("100.00"));

        Payment pagamento = service.processar(pedido.id(), PaymentMethod.PIX, pedido.total());

        assertThat(pagamento.status()).isEqualTo(PaymentStatus.APROVADO);
        assertThat(pagamento.valor()).isEqualTo(pedido.total());
        assertThat(orderService.buscarPorId(pedido.id()).status()).isEqualTo(OrderStatus.PAGO);
    }

    @Test
    void shouldThrowBusinessException_whenValorDiferenteDoTotal() {
        Order pedido = pedidoComTotal(new BigDecimal("100.00"));

        assertThatThrownBy(() -> service.processar(pedido.id(), PaymentMethod.PIX, new BigDecimal("999.00")))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void shouldThrowBusinessException_whenValorNegativo() {
        Order pedido = pedidoComTotal(new BigDecimal("100.00"));

        assertThatThrownBy(() -> service.processar(pedido.id(), PaymentMethod.PIX, new BigDecimal("-10")))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void shouldThrowResourceNotFound_whenPagamentoPedidoInexistente() {
        assertThatThrownBy(() -> service.processar(999L, PaymentMethod.PIX, new BigDecimal("10")))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldBuscarPagamentoPorPedido() {
        Order pedido = pedidoComTotal(new BigDecimal("100.00"));
        Payment pagamento = service.processar(pedido.id(), PaymentMethod.PIX, pedido.total());

        Payment busca = service.buscarPorPedido(pedido.id());

        assertThat(busca.id()).isEqualTo(pagamento.id());
    }
}
