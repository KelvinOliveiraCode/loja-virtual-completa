package com.lojavirtual.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.lojavirtual.domain.Cart;
import com.lojavirtual.repository.InMemoryCartRepository;
import com.lojavirtual.repository.CartRepository;
import org.junit.jupiter.api.Test;

class CartServiceTest {

    private final CartRepository repo = new InMemoryCartRepository();
    private final CartService service = new CartService(repo);

    @Test
    void shouldObterOuCriar_whenClienteNovo() {
        Cart carrinho = service.obterOuCriar(1L);

        assertThat(carrinho.getClienteId()).isEqualTo(1L);
        assertThat(carrinho.isEmpty()).isTrue();
    }

    @Test
    void shouldAdicionarItem_whenCarrinhoVazio() {
        service.adicionarItem(1L, 10L, 2);

        assertThat(service.obterOuCriar(1L).quantidade(10L)).isEqualTo(2);
    }

    @Test
    void shouldSomarQuantidade_whenAdicionarMesmoProdutoDuasVezes() {
        service.adicionarItem(1L, 10L, 2);
        service.adicionarItem(1L, 10L, 3);

        assertThat(service.obterOuCriar(1L).quantidade(10L)).isEqualTo(5);
    }

    @Test
    void shouldRemoverItem_whenExiste() {
        service.adicionarItem(1L, 10L, 2);
        service.removerItem(1L, 10L);

        assertThat(service.obterOuCriar(1L).quantidade(10L)).isEqualTo(0);
    }

    @Test
    void shouldLimparCarrinho_whenTemItens() {
        service.adicionarItem(1L, 10L, 2);
        service.limpar(1L);

        assertThat(service.obterOuCriar(1L).isEmpty()).isTrue();
    }

    @Test
    void shouldThrowIllegalArgumentException_whenAdicionarItemQuantidadeInvalida() {
        assertThatThrownBy(() -> service.adicionarItem(1L, 10L, 0))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
