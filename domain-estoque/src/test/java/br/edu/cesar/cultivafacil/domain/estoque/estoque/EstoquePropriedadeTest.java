package br.edu.cesar.cultivafacil.domain.estoque.estoque;

import br.edu.cesar.cultivafacil.domain.propriedade.propriedade.PropriedadeId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EstoquePropriedadeTest {

    private EstoquePropriedade estoque;
    private ItemEstoqueId fertilizanteId;

    @BeforeEach
    void setUp() {
        estoque = new EstoquePropriedade(PropriedadeId.novo(), LocalDate.of(2026, 4, 28));
        fertilizanteId = estoque.cadastrarItem("Fertilizante NPK", TipoItemEstoque.INSUMO, new UnidadeMedidaEstoque("kg"));
    }

    @Test
    void deveRegistrarEntradaValida() {
        estoque.registrarEntrada(fertilizanteId, "100", "kg", "Compra Confirmada", "NF-1");

        assertEquals(0, estoque.saldoDe(fertilizanteId).valor().compareTo(new java.math.BigDecimal("100")));
        assertEquals(1, estoque.entradas().size());
        assertEquals(1, estoque.eventos().size());
    }

    @Test
    void deveRejeitarOrigemDeEntradaInvalida() {
        var ex = assertThrows(IllegalArgumentException.class,
                () -> estoque.registrarEntrada(fertilizanteId, "100", "kg", "Doacao", "NF-2"));

        assertEquals("ESTOQUE_INVALIDO", ex.getMessage());
    }

    @Test
    void deveRejeitarQuantidadeDeEntradaInvalida() {
        var ex = assertThrows(IllegalArgumentException.class,
                () -> estoque.registrarEntrada(fertilizanteId, "0", "kg", "Compra Confirmada", "NF-3"));

        assertEquals("QUANTIDADE_INVALIDA", ex.getMessage());
    }

    @Test
    void deveRejeitarUnidadeDivergenteNaEntrada() {
        var ex = assertThrows(IllegalArgumentException.class,
                () -> estoque.registrarEntrada(fertilizanteId, "100", "g", "Compra Confirmada", "NF-4"));

        assertEquals("UNIDADE_INVALIDA", ex.getMessage());
    }

    @Test
    void deveRejeitarEntradaDuplicada() {
        estoque.registrarEntrada(fertilizanteId, "100", "kg", "Compra Confirmada", "NF-5");

        var ex = assertThrows(IllegalArgumentException.class,
                () -> estoque.registrarEntrada(fertilizanteId, "100", "kg", "Compra Confirmada", "NF-5"));

        assertEquals("QUANTIDADE_INVALIDA: entrada duplicada", ex.getMessage());
    }

    @Test
    void deveRegistrarSaidaValida() {
        estoque.registrarEntrada(fertilizanteId, "100", "kg", "Compra Confirmada", "NF-6");

        estoque.registrarSaida(fertilizanteId, "50", "Venda", LocalDate.of(2026, 4, 28), null);

        assertEquals(0, estoque.saldoDe(fertilizanteId).valor().compareTo(new java.math.BigDecimal("50")));
        assertEquals(1, estoque.saidas().size());
    }

    @Test
    void deveRejeitarMotivoSaidaInvalido() {
        estoque.registrarEntrada(fertilizanteId, "100", "kg", "Compra Confirmada", "NF-7");

        var ex = assertThrows(IllegalArgumentException.class,
                () -> estoque.registrarSaida(fertilizanteId, "50", "Perda", LocalDate.of(2026, 4, 28), null));

        assertEquals("TALHAO_INVALIDO", ex.getMessage());
    }

    @Test
    void deveRejeitarDescarteSemJustificativaValida() {
        estoque.registrarEntrada(fertilizanteId, "100", "kg", "Compra Confirmada", "NF-8");

        var ex = assertThrows(IllegalArgumentException.class,
                () -> estoque.registrarSaida(fertilizanteId, "50", "Descarte", LocalDate.of(2026, 4, 28), "curta"));

        assertEquals("JUSTIFICATIVA_INVALIDA", ex.getMessage());
    }

    @Test
    void deveRejeitarDataFuturaNaSaida() {
        estoque.registrarEntrada(fertilizanteId, "100", "kg", "Compra Confirmada", "NF-9");

        var ex = assertThrows(IllegalArgumentException.class,
                () -> estoque.registrarSaida(fertilizanteId, "50", "Venda", LocalDate.of(2026, 4, 29), null));

        assertEquals("DATA_INVALIDA", ex.getMessage());
    }

    @Test
    void deveConfigurarLimiteMinimoValido() {
        estoque.configurarLimiteMinimo(fertilizanteId, "50", "kg");

        assertEquals(1, estoque.limitesMinimos().size());
        assertTrue(estoque.eventos().stream().anyMatch(LimiteMinimoConfigurado.class::isInstance));
    }

    @Test
    void deveRejeitarSegundoLimiteMinimoAtivoParaMesmoItem() {
        estoque.configurarLimiteMinimo(fertilizanteId, "50", "kg");

        var ex = assertThrows(IllegalArgumentException.class,
                () -> estoque.configurarLimiteMinimo(fertilizanteId, "30", "kg"));

        assertEquals("ESTOQUE_INVALIDO: ja existe configuracao ativa para o item", ex.getMessage());
    }

    @Test
    void devePublicarEventoQuandoSaldoFicaAbaixoDoMinimo() {
        estoque.registrarEntrada(fertilizanteId, "100", "kg", "Compra Confirmada", "NF-10");
        estoque.configurarLimiteMinimo(fertilizanteId, "50", "kg");

        estoque.registrarSaida(fertilizanteId, "60", "Venda", LocalDate.of(2026, 4, 28), null);

        assertTrue(estoque.eventos().stream().anyMatch(EstoqueAbaixoDoMinimo.class::isInstance));
    }
}
