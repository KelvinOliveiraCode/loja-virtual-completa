package com.lojavirtual.service;

import com.lojavirtual.domain.Order;
import com.lojavirtual.domain.Payment;
import com.lojavirtual.domain.enums.OrderStatus;
import com.lojavirtual.domain.enums.PaymentMethod;
import com.lojavirtual.domain.enums.PaymentStatus;
import com.lojavirtual.exception.BusinessException;
import com.lojavirtual.exception.ResourceNotFoundException;
import com.lojavirtual.repository.OrderRepository;
import com.lojavirtual.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Servi&ccedil;o de pagamento (modo simula&ccedil;&atilde;o: aprova&ccedil;&atilde;o imediata).
 */
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    /**
     * Processa o pagamento de um pedido. Valida que o valor coincide com o total do pedido.
     */
    public Payment processar(Long pedidoId, PaymentMethod metodo, BigDecimal valor) {
        Order pedido = orderRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido n&atilde;o encontrado: " + pedidoId));
        if (valor == null || valor.signum() <= 0) {
            throw new BusinessException("Valor do pagamento deve ser maior que zero.");
        }
        if (valor.compareTo(pedido.total()) != 0) {
            throw new BusinessException("Valor do pagamento difere do total do pedido.");
        }
        Payment pagamento = new Payment(
                null,
                pedidoId,
                valor,
                metodo,
                PaymentStatus.APROVADO,
                LocalDateTime.now());
        Payment salvo = paymentRepository.save(pagamento);
        Order pago = new Order(
                pedido.id(),
                pedido.clienteId(),
                pedido.items(),
                OrderStatus.PAGO,
                pedido.total(),
                pedido.createdAt());
        orderRepository.save(pago);
        return salvo;
    }

    /** Busca um pagamento pelo id. */
    public Payment buscarPorId(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pagamento n&atilde;o encontrado: " + id));
    }

    /** Busca o pagamento de um pedido. */
    public Payment buscarPorPedido(Long pedidoId) {
        return paymentRepository.findByPedido(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Pagamento n&atilde;o encontrado para o pedido: " + pedidoId));
    }
}
