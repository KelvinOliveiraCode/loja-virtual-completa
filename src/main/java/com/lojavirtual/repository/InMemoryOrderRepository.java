package com.lojavirtual.repository;

import com.lojavirtual.domain.Order;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/** Implementa&ccedil;&atilde;o em mem&oacute;ria do {@link OrderRepository}. */
@Repository
public class InMemoryOrderRepository implements OrderRepository {

    private final Map<Long, Order> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Order save(Order order) {
        Long id = order.id() != null ? order.id() : sequence.incrementAndGet();
        Order stored = new Order(
                id,
                order.clienteId(),
                order.items(),
                order.status(),
                order.total(),
                order.createdAt());
        store.put(id, stored);
        return stored;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Order> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }

    @Override
    public boolean existsById(Long id) {
        return store.containsKey(id);
    }
}
