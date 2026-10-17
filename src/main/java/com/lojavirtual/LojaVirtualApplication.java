package com.lojavirtual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Ponto de entrada da aplica&ccedil;&atilde;o Loja Virtual Completa.
 *
 * <p>Escopo de MVP do e-commerce: cat&aacute;logo de produtos, carrinho de
 * compras, checkout (convers~ao de carrinho em pedido) e pagamento.
 *
 * @author Kelvin Oliveira
 */
@SpringBootApplication
public class LojaVirtualApplication {

    public static void main(String[] args) {
        SpringApplication.run(LojaVirtualApplication.class, args);
    }
}
