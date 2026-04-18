package br.com.cultivafacil.domain.manejo.vo;

import br.com.cultivafacil.domain.manejo.exception.DescricaoFocoInvalidaException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DescricaoFocoTest {

    // Rastreabilidade: F-10 / RN-01 / cenário de sucesso
    @Test
    void deveCriarDescricaoFocoComSucesso() {
        // Arrange
        String descricaoValida = "Descrição válida do foco fitossanitário.";

        // Act
        DescricaoFoco descricaoFoco = new DescricaoFoco(descricaoValida);

        // Assert
        assertNotNull(descricaoFoco);
        assertEquals(descricaoValida, descricaoFoco.getValor());
    }

    // Rastreabilidade: F-10 / RN-01 / cenário de falha (descrição nula)
    @Test
    void naoDeveCriarDescricaoFocoComDescricaoNula() {
        // Arrange
        String descricaoNula = null;

        // Act & Assert
        assertThrows(DescricaoFocoInvalidaException.class, () -> new DescricaoFoco(descricaoNula));
    }

    // Rastreabilidade: F-10 / RN-01 / cenário de falha (descrição vazia)
    @Test
    void naoDeveCriarDescricaoFocoComDescricaoVazia() {
        // Arrange
        String descricaoVazia = "";

        // Act & Assert
        assertThrows(DescricaoFocoInvalidaException.class, () -> new DescricaoFoco(descricaoVazia));
    }

    // Rastreabilidade: F-10 / RN-01 / cenário de falha (descrição curta)
    @Test
    void naoDeveCriarDescricaoFocoComDescricaoCurta() {
        // Arrange
        String descricaoCurta = "Curta.";

        // Act & Assert
        assertThrows(DescricaoFocoInvalidaException.class, () -> new DescricaoFoco(descricaoCurta));
    }

    // Rastreabilidade: F-10 / RN-01 / cenário de falha (descrição longa)
    @Test
    void naoDeveCriarDescricaoFocoComDescricaoLonga() {
        // Arrange
        String descricaoLonga = "a".repeat(501);

        // Act & Assert
        assertThrows(DescricaoFocoInvalidaException.class, () -> new DescricaoFoco(descricaoLonga));
    }
}

