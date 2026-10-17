package com.lojavirtual.repository;

import com.lojavirtual.domain.Payment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/** Implementa&ccedil;&atilde;o em mem&oacute;ria do {@link PaymentRepository}. */
@Repository
public class InMemoryPaymentRepository implements PaymentRepository {

    private final Map<Long, Payment> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Payment save(Payment payment) {
        Long id = payment.id() != null ? payment.id() : sequence.incrementAndGet();
        Payment stored = new Payment(
                id,
                payment.pedidoId(),
                payment.valor(),
                payment.metodo(),
                payment.status(),
                payment.pagoEm());
        store.put(id, stored);
        return stored;
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Payment> findByPedido(Long pedidoId) {
        return store.values().stream()
                .filter(p -> p.pedidoId().equals(pedidoId))
                .findFirst();
    }

    @Override
    public List<Payment> findAll() {
        return List.copyOf(store.values());
    }
}
