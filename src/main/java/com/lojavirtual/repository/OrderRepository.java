package com.lojavirtual.repository;

import com.lojavirtual.domain.Order;
import java.util.List;
import java.util.Optional;

/** Reposit&oacute;rio de pedidos. */
public interface OrderRepository {

    Order save(Order order);

    Optional<Order> findById(Long id);

    List<Order> findAll();

    void deleteById(Long id);

    boolean existsById(Long id);
}
