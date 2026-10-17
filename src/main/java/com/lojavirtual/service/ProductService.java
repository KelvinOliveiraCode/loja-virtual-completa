package com.lojavirtual.service;

import com.lojavirtual.domain.Product;
import com.lojavirtual.exception.BusinessException;
import com.lojavirtual.exception.ResourceNotFoundException;
import com.lojavirtual.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/** Servi&ccedil;o de cat&aacute;logo de produtos. */
@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    /** Lista todos os produtos ativos. */
    public List<Product> listarAtivos() {
        return repository.findByAtivo(true);
    }

    /** Busca um produto pelo id. */
    public Product buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto n&atilde;o encontrado: " + id));
    }

    /** Cadastra um novo produto. */
    public Product criar(Product product) {
        if (product.preco() == null || product.preco().signum() <= 0) {
            throw new BusinessException("Pre&ccedil;o do produto deve ser maior que zero.");
        }
        if (product.estoque() == null || product.estoque() < 0) {
            throw new BusinessException("Estoque n&atilde;o pode ser negativo.");
        }
        return repository.save(product);
    }

    /** Atualiza o estoque de um produto. */
    public Product atualizarEstoque(Long id, Integer novoEstoque) {
        Product produto = buscarPorId(id);
        if (novoEstoque == null || novoEstoque < 0) {
            throw new BusinessException("Estoque n&atilde;o pode ser negativo.");
        }
        Product atualizado = new Product(
                produto.id(),
                produto.nome(),
                produto.descricao(),
                produto.preco(),
                novoEstoque,
                produto.ativo());
        return repository.save(atualizado);
    }

    /** Baixa a quantidade informada do estoque do produto. */
    public void baixarEstoque(Long produtoId, Integer quantidade) {
        Product produto = buscarPorId(produtoId);
        if (produto.estoque() == null || produto.estoque() < quantidade) {
            throw new BusinessException("Estoque insuficiente para o produto: " + produto.nome());
        }
        atualizarEstoque(produtoId, produto.estoque() - quantidade);
    }

    /** Remove um produto pelo id. */
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Produto n&atilde;o encontrado: " + id);
        }
        repository.deleteById(id);
    }
}
