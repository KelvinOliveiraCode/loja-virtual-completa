package com.lojavirtual.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.lojavirtual.domain.Product;
import com.lojavirtual.exception.BusinessException;
import com.lojavirtual.exception.ResourceNotFoundException;
import com.lojavirtual.repository.InMemoryProductRepository;
import com.lojavirtual.repository.ProductRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class ProductServiceTest {

    private final ProductRepository repo = new InMemoryProductRepository();
    private final ProductService service = new ProductService(repo);

    @Test
    void shouldListarApenasProdutosAtivos_whenExistemInativos() {
        repo.save(new Product(null, "Camisa", "desc", new BigDecimal("50.00"), 10, true));
        repo.save(new Product(null, "Calça", "desc", new BigDecimal("120.00"), 5, false));

        assertThat(service.listarAtivos()).hasSize(1);
        assertThat(service.listarAtivos().get(0).nome()).isEqualTo("Camisa");
    }

    @Test
    void shouldBuscarProdutoPorId_whenExiste() {
        Product produto = repo.save(new Product(null, "Camisa", "d", new BigDecimal("50"), 10, true));

        assertThat(service.buscarPorId(produto.id()).nome()).isEqualTo("Camisa");
    }

    @Test
    void shouldThrowResourceNotFound_whenBuscarProdutoInexistente() {
        assertThatThrownBy(() -> service.buscarPorId(999L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldCriarProduto_whenDadosValidos() {
        Product produto = service.criar(new Product(null, "X", "d", new BigDecimal("10"), 3, true));

        assertThat(produto.id()).isNotNull();
        assertThat(repo.existsById(produto.id())).isTrue();
    }

    @Test
    void shouldThrowBusinessException_whenCriarProdutoPrecoInvalido() {
        assertThatThrownBy(() -> service.criar(new Product(null, "X", "d", new BigDecimal("0"), 3, true)))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void shouldAtualizarEstoque_whenProdutoExiste() {
        Product produto = repo.save(new Product(null, "X", "d", new BigDecimal("10"), 10, true));

        Product atualizado = service.atualizarEstoque(produto.id(), 5);

        assertThat(atualizado.estoque()).isEqualTo(5);
    }

    @Test
    void shouldThrowBusinessException_whenEstoqueNegativo() {
        Product produto = repo.save(new Product(null, "X", "d", new BigDecimal("10"), 10, true));

        assertThatThrownBy(() -> service.atualizarEstoque(produto.id(), -1))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void shouldThrowBusinessException_whenEstoqueInsuficienteNaBaixa() {
        Product produto = repo.save(new Product(null, "X", "d", new BigDecimal("10"), 2, true));

        assertThatThrownBy(() -> service.baixarEstoque(produto.id(), 5))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    void shouldDeletarProduto_whenExiste() {
        Product produto = repo.save(new Product(null, "X", "d", new BigDecimal("10"), 3, true));

        service.deletar(produto.id());

        assertThat(repo.existsById(produto.id())).isFalse();
    }
}
