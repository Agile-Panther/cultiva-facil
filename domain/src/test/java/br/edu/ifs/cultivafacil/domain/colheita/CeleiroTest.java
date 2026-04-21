package br.edu.ifs.cultivafacil.domain.colheita;


import br.edu.ifs.cultivafacil.shared.AgricultorId;
import br.edu.ifs.cultivafacil.shared.CicloAgricolaId;
import br.edu.ifs.cultivafacil.shared.ZonaId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class CeleiroTest {

    private ZonaId zonaId;
    private CicloAgricolaId cicloId;
    private Celeiro celeiro;

    @BeforeEach
    void setUp() {
        zonaId = new ZonaId(UUID.randomUUID());
        cicloId = new CicloAgricolaId(UUID.randomUUID());
        celeiro = new Celeiro(zonaId);
        celeiro.inicializarProjecao(cicloId, new BigDecimal("200.00"));
    }

    @Test
    void inicializarProjecao_comQuantidadeValida_deveSetarProjecaoCorretamente() {
        ItemCeleiro item = celeiro.getItens().get(0);
        assertThat(item.getQuantidadePlantada()).isEqualByComparingTo("200.00");
        assertThat(item.getPerdasAcumuladas()).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(item.getProjecaoAtual()).isEqualByComparingTo("200.00");
    }

    @Test
    void registrarPerda_deveDecrementarProjecao() {
        celeiro.registrarPerda(cicloId, new BigDecimal("30.00"));

        ItemCeleiro item = celeiro.getItens().get(0);
        assertThat(item.getProjecaoAtual()).isEqualByComparingTo("170.00");
        assertThat(item.getPerdasAcumuladas()).isEqualByComparingTo("30.00");
    }

    @Test
    void registrarPerda_quandoProjecaoAbaixoDaMeta_deveEmitirAlertaProjecaoEvent() {
        // meta=150, plantado=200, perda=60 → projeção=140 < 150
        celeiro.definirMeta(cicloId, new BigDecimal("150.00"), true);
        celeiro.registrarPerda(cicloId, new BigDecimal("60.00"));

        assertThat(celeiro.getEventos()).hasSize(1);
        assertThat(celeiro.getEventos().get(0)).isInstanceOf(Celeiro.AlertaProjecaoEvent.class);

        Celeiro.AlertaProjecaoEvent evento = (Celeiro.AlertaProjecaoEvent) celeiro.getEventos().get(0);
        assertThat(evento.getProjecaoAtual()).isEqualByComparingTo("140.00");
        assertThat(evento.getMetaComerciavel()).isEqualByComparingTo("150.00");
    }

    @Test
    void registrarPerda_comSegundoAlertaEm24h_deveRejeitarComFREQUENCIA_ALERTA_EXCEDIDA() {
        celeiro.definirMeta(cicloId, new BigDecimal("150.00"), true);
        celeiro.registrarPerda(cicloId, new BigDecimal("60.00")); // 1º alerta → projeção=140

        assertThatThrownBy(() -> celeiro.registrarPerda(cicloId, new BigDecimal("5.00")))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("FREQUENCIA_ALERTA_EXCEDIDA");
    }

    @Test
    void registrarSaida_comSaldoSuficiente_deveCriarSaidaCeleiro() {
        SaidaCeleiro saida = celeiro.registrarSaida(cicloId, new BigDecimal("50.00"), MotivoSaida.VENDA);

        assertThat(saida).isNotNull();
        assertThat(saida.getQuantidade()).isEqualByComparingTo("50.00");
        assertThat(saida.getMotivo()).isEqualTo(MotivoSaida.VENDA);
        assertThat(celeiro.getSaidas()).hasSize(1);
    }

    @Test
    void registrarSaida_comSaldoInsuficiente_deveRejeitarComSALDO_INSUFICIENTE() {
        assertThatThrownBy(() ->
                celeiro.registrarSaida(cicloId, new BigDecimal("250.00"), MotivoSaida.CONSUMO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("SALDO_INSUFICIENTE");
    }

    @Test
    void definirMeta_comValorValido_devePersistirMeta() {
        celeiro.definirMeta(cicloId, new BigDecimal("120.00"), true);

        assertThat(celeiro.getMetaComerciavel()).isNotNull();
        assertThat(celeiro.getMetaComerciavel().getValor()).isEqualByComparingTo("120.00");
    }

    @Test
    void definirMeta_comValorZero_deveRejeitarComMETA_COMERCIALIZAVEL_INVALIDA() {
        assertThatThrownBy(() -> celeiro.definirMeta(cicloId, BigDecimal.ZERO, true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("META_COMERCIALIZAVEL_INVALIDA");
    }

    @Test
    void definirMeta_comValorSuperiorAoPlantado_deveRejeitarComMETA_COMERCIALIZAVEL_INVALIDA() {
        assertThatThrownBy(() -> celeiro.definirMeta(cicloId, new BigDecimal("300.00"), true))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("META_COMERCIALIZAVEL_INVALIDA");
    }

    @Test
    void definirMeta_comCicloInativo_deveRejeitarComCICLO_INATIVO() {
        assertThatThrownBy(() -> celeiro.definirMeta(cicloId, new BigDecimal("100.00"), false))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("CICLO_INATIVO");
    }

    @Test
    void salvarConfiguracaoRelatorio_dentroDoLimite_devePersistir() {
        AgricultorId agricultorId = new AgricultorId(UUID.randomUUID());

        celeiro.salvarConfiguracaoRelatorio(agricultorId, "Relatório Trimestral", FiltroPeriodo.TRIMESTRE, 0L);

        assertThat(celeiro.getConfiguracoes()).hasSize(1);
    }

    @Test
    void salvarConfiguracaoRelatorio_comLimiteExcedido_deveRejeitarComLIMITE_CONFIGURACOES_EXCEDIDO() {
        AgricultorId agricultorId = new AgricultorId(UUID.randomUUID());

        assertThatThrownBy(() ->
                celeiro.salvarConfiguracaoRelatorio(agricultorId, "Nova Config", FiltroPeriodo.ANO, 5L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("LIMITE_CONFIGURACOES_EXCEDIDO");
    }
}
