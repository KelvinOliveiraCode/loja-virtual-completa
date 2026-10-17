package com.lojavirtual.controller;

import com.lojavirtual.domain.Product;
import com.lojavirtual.dto.ProductRequest;
import com.lojavirtual.dto.ProductResponse;
import com.lojavirtual.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/** Controlador de produtos (cat&aacute;logo). */
@RestController
@RequestMapping("/produtos")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponse> listar() {
        return productService.listarAtivos().stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductResponse buscar(@PathVariable Long id) {
        return ProductResponse.from(productService.buscarPorId(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse criar(@RequestBody ProductRequest request) {
        Product produto = new Product(
                null,
                request.nome(),
                request.descricao(),
                request.preco(),
                request.estoque(),
                request.ativo());
        return ProductResponse.from(productService.criar(produto));
    }

    @PutMapping("/{id}/estoque")
    public ProductResponse atualizarEstoque(@PathVariable Long id,
                                            @RequestBody Integer novoEstoque) {
        return ProductResponse.from(productService.atualizarEstoque(id, novoEstoque));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        productService.deletar(id);
    }
}
