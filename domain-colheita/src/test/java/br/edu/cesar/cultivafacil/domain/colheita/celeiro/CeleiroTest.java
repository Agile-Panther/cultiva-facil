package br.edu.cesar.cultivafacil.domain.colheita.celeiro;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.CicloAgricolaId;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CeleiroTest {

    private TalhaoId talhaoId;
    private CicloAgricolaId cicloId;
    private ItemCeleiro item;
    private Celeiro celeiro;

    @BeforeEach
    void setUp() {
        talhaoId = new TalhaoId(UUID.randomUUID());
        cicloId = new CicloAgricolaId(UUID.randomUUID());
        item = new ItemCeleiro("Tomate", BigDecimal.valueOf(200));
        celeiro = new Celeiro(talhaoId, cicloId, item);
    }

    // US-24: projecao correta
    @Test
    void deveCalcularProjecaoCorretamente() {
        celeiro.registrarPerda(BigDecimal.valueOf(30));
        assertEquals(0, BigDecimal.valueOf(170).compareTo(celeiro.calcularProjecao()));
    }

    // US-24: saldo inicial igual a quantidade plantada
    @Test
    void deveTerSaldoInicialIgualAQuantidadePlantada() {
        assertEquals(0, BigDecimal.valueOf(200).compareTo(celeiro.getItem().getSaldoDisponivel()));
    }

    // RN-090a: meta zero rejeitada
    @Test
    void deveRejeitarMetaComerciavelZero() {
        var ex = assertThrows(IllegalArgumentException.class, () -> celeiro.definirMeta(BigDecimal.ZERO));
        assertEquals("META_COMERCIALIZAVEL_INVALIDA", ex.getMessage());
    }

    // RN-090b: meta maior que plantado rejeitada
    @Test
    void deveRejeitarMetaComerciavelSuperiorAoPlantado() {
        var ex = assertThrows(IllegalArgumentException.class, () -> celeiro.definirMeta(BigDecimal.valueOf(250)));
        assertEquals("META_COMERCIALIZAVEL_INVALIDA", ex.getMessage());
    }

    // RN-091: meta com ciclo encerrado rejeitada
    @Test
    void deveRejeitarDefinicaoDeMetaComCicloEncerrado() {
        celeiro.encerrarCiclo();
        var ex = assertThrows(IllegalArgumentException.class, () -> celeiro.definirMeta(BigDecimal.valueOf(150)));
        assertEquals("CICLO_ENCERRADO", ex.getMessage());
    }

    // US-36: alerta emitido quando projecao cai abaixo da meta
    @Test
    void deveEmitirAlertaQuandoProjecaoCaiAbaixoDaMeta() {
        celeiro.definirMeta(BigDecimal.valueOf(150));
        celeiro.registrarPerda(BigDecimal.valueOf(60));
        assertEquals(1, celeiro.getEventos().size());
        assertInstanceOf(Celeiro.AlertaProjecao.class, celeiro.getEventos().get(0));
    }

    // RN-092: segundo alerta em menos de 24h bloqueado
    @Test
    void deveBloquearSegundoAlertaEm24Horas() {
        var celeiroComAlerta = new Celeiro(
                CeleiroId.novo(), talhaoId, cicloId, true,
                new ItemCeleiro(UUID.randomUUID(), "Milho",
                        BigDecimal.valueOf(200), BigDecimal.valueOf(50), BigDecimal.valueOf(150)),
                new MetaComerciavel(BigDecimal.valueOf(150)),
                new ArrayList<>(),
                new ArrayList<>(),
                LocalDateTime.now().minusHours(6)
        );
        var ex = assertThrows(IllegalArgumentException.class, () -> celeiroComAlerta.registrarPerda(BigDecimal.TEN));
        assertEquals("FREQUENCIA_ALERTA_EXCEDIDA", ex.getMessage());
    }

    // US-25: saida registrada com sucesso
    @Test
    void deveRegistrarSaidaComSucesso() {
        celeiro.registrarSaida(BigDecimal.valueOf(100), MotivoSaida.VENDA);
        assertEquals(0, BigDecimal.valueOf(100).compareTo(celeiro.getItem().getSaldoDisponivel()));
        assertEquals(1, celeiro.getSaidas().size());
    }

    // RN-094: saida maior que saldo rejeitada
    @Test
    void deveRejeitarSaidaMaiorQueSaldo() {
        var ex = assertThrows(IllegalArgumentException.class,
                () -> celeiro.registrarSaida(BigDecimal.valueOf(250), MotivoSaida.VENDA));
        assertEquals("SALDO_INSUFICIENTE", ex.getMessage());
    }

    // RN-095: motivo PERDA manual rejeitado
    @Test
    void deveRejeitarSaidaManualComMotivoPerda() {
        var ex = assertThrows(IllegalArgumentException.class,
                () -> celeiro.registrarSaida(BigDecimal.valueOf(50), MotivoSaida.PERDA));
        assertEquals("MOTIVO_SAIDA_INVALIDO", ex.getMessage());
    }

    // RN-096: relatorio em ciclo ativo rejeitado
    @Test
    void deveRejeitarRelatorioDePerdaEmCicloAtivo() {
        var ex = assertThrows(IllegalArgumentException.class, () -> celeiro.gerarRelatorioPerda());
        assertEquals("RELATORIO_CICLO_ATIVO", ex.getMessage());
    }

    // US-37: relatorio com dados consolidados
    @Test
    void deveGerarRelatorioComDadosConsolidados() {
        celeiro.registrarPerda(BigDecimal.valueOf(15));
        celeiro.encerrarCiclo();
        RelatorioPerda relatorio = celeiro.gerarRelatorioPerda();
        assertEquals(0, BigDecimal.valueOf(200).compareTo(relatorio.getQuantidadePlantada()));
        assertEquals(0, BigDecimal.valueOf(15).compareTo(relatorio.getPerdasAcumuladas()));
    }

    // RN-098a: nome de configuracao invalido rejeitado
    @Test
    void deveRejeitarNomeDeConfiguracaoInvalido() {
        var ex = assertThrows(IllegalArgumentException.class,
                () -> celeiro.adicionarConfiguracao("A", FiltroPeriodo.SEMESTRE));
        assertEquals("NOME_CONFIG_INVALIDO", ex.getMessage());
    }

    // RN-098b: nome de configuracao duplicado rejeitado
    @Test
    void deveRejeitarNomeDeConfiguracaoDuplicado() {
        celeiro.adicionarConfiguracao("Safra Verao 2026", FiltroPeriodo.SEMESTRE);
        var ex = assertThrows(IllegalArgumentException.class,
                () -> celeiro.adicionarConfiguracao("Safra Verao 2026", FiltroPeriodo.ANO));
        assertEquals("NOME_CONFIG_DUPLICADO", ex.getMessage());
    }

    // RN-100: sexta configuracao rejeitada
    @Test
    void deveRejeitarSextaConfiguracao() {
        celeiro.adicionarConfiguracao("Config 1", FiltroPeriodo.ANO);
        celeiro.adicionarConfiguracao("Config 2", FiltroPeriodo.SEMESTRE);
        celeiro.adicionarConfiguracao("Config 3", FiltroPeriodo.TRIMESTRE);
        celeiro.adicionarConfiguracao("Config 4", FiltroPeriodo.ULTIMO_MES);
        celeiro.adicionarConfiguracao("Config 5", FiltroPeriodo.ANO);
        var ex = assertThrows(IllegalArgumentException.class,
                () -> celeiro.adicionarConfiguracao("Config 6", FiltroPeriodo.SEMESTRE));
        assertEquals("LIMITE_CONFIGURACOES_EXCEDIDO", ex.getMessage());
    }

    // US-26: configuracao salva com sucesso
    @Test
    void deveSalvarConfiguracaoComSucesso() {
        celeiro.adicionarConfiguracao("Safra Verao 2026", FiltroPeriodo.SEMESTRE);
        assertEquals(1, celeiro.getConfiguracoes().size());
        assertEquals("Safra Verao 2026", celeiro.getConfiguracoes().get(0).getNome());
    }

    // RN-101: consulta sem configuracao persistida rejeitada
    @Test
    void deveRejeitarConsultaSemConfiguracaoPersistida() {
        var ex = assertThrows(IllegalArgumentException.class,
                () -> celeiro.buscarConfiguracao("Safra Verao 2026"));
        assertEquals("CONFIGURACAO_INEXISTENTE", ex.getMessage());
    }

    // US-27: retorna configuracao quando existe
    @Test
    void deveRetornarConfiguracaoExistente() {
        celeiro.adicionarConfiguracao("Safra Verao 2026", FiltroPeriodo.SEMESTRE);
        var resultado = celeiro.buscarConfiguracao("Safra Verao 2026");
        assertNotNull(resultado);
        assertEquals("Safra Verao 2026", resultado.getNome());
        assertEquals(FiltroPeriodo.SEMESTRE, resultado.getPeriodo());
    }
}
