package com.lojavirtual.repository;

import com.lojavirtual.domain.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/** Implementa&ccedil;&atilde;o em mem&oacute;ria do {@link ProductRepository}. */
@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final Map<Long, Product> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(0);

    @Override
    public Product save(Product product) {
        Long id = product.id() != null ? product.id() : sequence.incrementAndGet();
        Product stored = new Product(
                id,
                product.nome(),
                product.descricao(),
                product.preco(),
                product.estoque(),
                product.ativo());
        store.put(id, stored);
        return stored;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Product> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public List<Product> findByAtivo(Boolean ativo) {
        return store.values().stream()
                .filter(p -> Boolean.TRUE.equals(p.ativo()) && Boolean.TRUE.equals(ativo))
                .toList();
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
