package com.lojavirtual.repository;

import com.lojavirtual.domain.Product;
import java.util.List;
import java.util.Optional;

/** Reposit&oacute;rio de produtos. */
public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(Long id);

    List<Product> findAll();

    List<Product> findByAtivo(Boolean ativo);

    void deleteById(Long id);

    boolean existsById(Long id);
}
