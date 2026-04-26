package br.edu.cesar.cultivafacil.domain.colheita.celeiro;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.CicloAgricolaId;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Quando;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CeleiroSteps {

    private Celeiro celeiro;
    private Exception excecaoCapturada;

    @Dado("que a Zona possui ciclo ativo com {double} kg de {string} plantados")
    public void zonaComCicloAtivo(double quantidade, String cultura) {
        var talhaoId = new TalhaoId(UUID.randomUUID());
        var cicloId = new CicloAgricolaId(UUID.randomUUID());
        celeiro = new Celeiro(talhaoId, cicloId, new ItemCeleiro(cultura, BigDecimal.valueOf(quantidade)));
    }

    @Dado("que o ciclo da Zona foi encerrado com {double} kg plantados de {string}")
    public void cicloEncerrado(double quantidade, String cultura) {
        var talhaoId = new TalhaoId(UUID.randomUUID());
        var cicloId = new CicloAgricolaId(UUID.randomUUID());
        celeiro = new Celeiro(talhaoId, cicloId, new ItemCeleiro(cultura, BigDecimal.valueOf(quantidade)));
        celeiro.encerrarCiclo();
    }

    @Dado("foram registradas {double} kg de perdas")
    public void registrarPerdas(double quantidade) {
        celeiro.registrarPerda(BigDecimal.valueOf(quantidade));
    }

    @Dado("foram registradas {double} kg de perdas no ciclo")
    public void registrarPerdasNoCiclo(double quantidade) {
        var talhaoId = celeiro.getTalhaoId();
        var cicloId = celeiro.getCicloAgricolaId();
        var cultura = celeiro.getItem().getCultura();
        var qtd = celeiro.getItem().getQuantidadePlantada();
        celeiro = new Celeiro(talhaoId, cicloId, new ItemCeleiro(cultura, qtd));
        celeiro.registrarPerda(BigDecimal.valueOf(quantidade));
        celeiro.encerrarCiclo();
    }

    @Dado("o Agricultor define Meta Comercializavel de {double} kg")
    public void definirMeta(double valor) {
        celeiro.definirMeta(BigDecimal.valueOf(valor));
    }

    @Dado("um Alerta de Projecao ja foi emitido ha menos de 24 horas")
    public void alertaEmitidoRecentement() {
        var talhaoId = celeiro.getTalhaoId();
        var cicloId = celeiro.getCicloAgricolaId();
        var item = celeiro.getItem();
        var meta = celeiro.getMeta();
        BigDecimal perdasParaAbaixarMeta = item.getQuantidadePlantada().subtract(meta.getValor()).add(BigDecimal.TEN);
        celeiro = new Celeiro(
                CeleiroId.novo(), talhaoId, cicloId, true,
                new ItemCeleiro(item.getId(), item.getCultura(), item.getQuantidadePlantada(),
                        perdasParaAbaixarMeta, item.getSaldoDisponivel().subtract(perdasParaAbaixarMeta)),
                meta,
                new ArrayList<>(),
                new ArrayList<>(),
                LocalDateTime.now().minusHours(6)
        );
    }

    @Dado("que o Celeiro possui saldo de {double} kg de {string}")
    public void celeiroComSaldo(double saldo, String cultura) {
        var talhaoId = new TalhaoId(UUID.randomUUID());
        var cicloId = new CicloAgricolaId(UUID.randomUUID());
        celeiro = new Celeiro(talhaoId, cicloId, new ItemCeleiro(cultura, BigDecimal.valueOf(saldo)));
    }

    @Dado("que o Agricultor possui menos de 5 configuracoes salvas no Celeiro")
    public void celeiroComPoucasConfiguracoes() {
        var talhaoId = new TalhaoId(UUID.randomUUID());
        var cicloId = new CicloAgricolaId(UUID.randomUUID());
        celeiro = new Celeiro(talhaoId, cicloId, new ItemCeleiro("Tomate", BigDecimal.valueOf(100)));
    }

    @Dado("que o Agricultor ja possui a configuracao {string} salva")
    public void celeiroComConfiguracaoExistente(String nome) {
        var talhaoId = new TalhaoId(UUID.randomUUID());
        var cicloId = new CicloAgricolaId(UUID.randomUUID());
        celeiro = new Celeiro(talhaoId, cicloId, new ItemCeleiro("Tomate", BigDecimal.valueOf(100)));
        celeiro.adicionarConfiguracao(nome, FiltroPeriodo.SEMESTRE);
    }

    @Dado("que o Agricultor ja possui 5 configuracoes de relatorio salvas")
    public void celeiroComCincoConfiguracoes() {
        var talhaoId = new TalhaoId(UUID.randomUUID());
        var cicloId = new CicloAgricolaId(UUID.randomUUID());
        celeiro = new Celeiro(talhaoId, cicloId, new ItemCeleiro("Tomate", BigDecimal.valueOf(100)));
        celeiro.adicionarConfiguracao("Config 1", FiltroPeriodo.ANO);
        celeiro.adicionarConfiguracao("Config 2", FiltroPeriodo.SEMESTRE);
        celeiro.adicionarConfiguracao("Config 3", FiltroPeriodo.TRIMESTRE);
        celeiro.adicionarConfiguracao("Config 4", FiltroPeriodo.ULTIMO_MES);
        celeiro.adicionarConfiguracao("Config 5", FiltroPeriodo.ANO);
    }

    @Quando("o Agricultor acessa a visualização do Celeiro da Zona")
    public void acessarVisualizacaoCeleiro() {
        // acesso de leitura — sem side-effects
    }

    @Quando("sao registradas {double} kg de perdas tornando a projecao {double} kg")
    public void registrarPerdasComProjecao(double perdas, double projecaoEsperada) {
        celeiro.registrarPerda(BigDecimal.valueOf(perdas));
        assertEquals(0, BigDecimal.valueOf(projecaoEsperada).compareTo(celeiro.calcularProjecao()));
    }

    @Quando("o Agricultor tenta definir Meta Comercializavel de {double} kg")
    public void tentarDefinirMeta(double valor) {
        try {
            celeiro.definirMeta(BigDecimal.valueOf(valor));
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    @Quando("as perdas acumuladas continuam abaixo da Meta e o sistema tenta emitir novo alerta")
    public void sistemaVerificaEmitirAlerta() {
        try {
            celeiro.registrarPerda(BigDecimal.ONE);
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    @Quando("o Agricultor registra saida de {double} kg com motivo {string}")
    public void registrarSaida(double quantidade, String motivo) {
        celeiro.registrarSaida(BigDecimal.valueOf(quantidade), MotivoSaida.valueOf(motivo));
    }

    @Quando("o Agricultor tenta registrar saida de {double} kg com motivo {string}")
    public void tentarRegistrarSaida(double quantidade, String motivo) {
        try {
            celeiro.registrarSaida(BigDecimal.valueOf(quantidade), MotivoSaida.valueOf(motivo));
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    @Quando("o Agricultor tenta registrar uma saida com motivo {string}")
    public void tentarRegistrarSaidaComMotivo(String motivo) {
        try {
            celeiro.registrarSaida(BigDecimal.valueOf(50), MotivoSaida.valueOf(motivo));
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    @Quando("o Agricultor tenta consultar o Relatorio de Perdas")
    public void tentarConsultarRelatorioPerda() {
        try {
            celeiro.gerarRelatorioPerda();
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    @Quando("o Agricultor acessa o Relatorio de Perdas do ciclo")
    public void acessarRelatorioPerda() {
        // acesso normal — sem captura de excecao
    }

    @Quando("informa nome {string} e periodo {string}")
    public void informarNomeEPeriodo(String nome, String periodo) {
        try {
            celeiro.adicionarConfiguracao(nome, FiltroPeriodo.valueOf(periodo));
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    @Quando("tenta criar outra configuracao com o nome {string} e periodo {string}")
    public void tentarCriarConfiguracaoDuplicada(String nome, String periodo) {
        try {
            celeiro.adicionarConfiguracao(nome, FiltroPeriodo.valueOf(periodo));
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    @Quando("tenta salvar uma sexta configuracao com nome {string} e periodo {string}")
    public void tentarSalvarSextaConfiguracao(String nome, String periodo) {
        try {
            celeiro.adicionarConfiguracao(nome, FiltroPeriodo.valueOf(periodo));
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    @Entao("o sistema exibe quantidade plantada de {double} kg perdas acumuladas de {double} kg e projecao de {double} kg")
    public void verificarEstadoCeleiro(double plantada, double perdas, double projecao) {
        assertEquals(0, BigDecimal.valueOf(plantada).compareTo(celeiro.getItem().getQuantidadePlantada()));
        assertEquals(0, BigDecimal.valueOf(perdas).compareTo(celeiro.getItem().getPerdasAcumuladas()));
        assertEquals(0, BigDecimal.valueOf(projecao).compareTo(celeiro.calcularProjecao()));
    }

    @Entao("um Alerta de Projecao Abaixo do Esperado e emitido para a Zona")
    public void verificarAlertaEmitido() {
        assertTrue(celeiro.getEventos().stream()
                .anyMatch(e -> e instanceof Celeiro.AlertaProjecao));
    }

    @Entao("o sistema rejeita com erro {string}")
    public void verificarErro(String codigoErro) {
        assertNotNull(excecaoCapturada, "Esperava-se uma excecao com codigo: " + codigoErro);
        assertEquals(codigoErro, excecaoCapturada.getMessage());
    }

    @Entao("o saldo do Celeiro e reduzido para {double} kg")
    public void verificarSaldo(double saldoEsperado) {
        assertEquals(0, BigDecimal.valueOf(saldoEsperado).compareTo(celeiro.getItem().getSaldoDisponivel()));
    }

    @Entao("o sistema exibe quantidade plantada de {double} kg e perdas totais de {double} kg")
    public void verificarRelatorioPerda(double plantada, double perdas) {
        var relatorio = celeiro.gerarRelatorioPerda();
        assertEquals(0, BigDecimal.valueOf(plantada).compareTo(relatorio.getQuantidadePlantada()));
        assertEquals(0, BigDecimal.valueOf(perdas).compareTo(relatorio.getPerdasAcumuladas()));
    }

    @Entao("a configuracao de relatorio e persistida com sucesso")
    public void verificarConfiguracaoSalva() {
        assertFalse(celeiro.getConfiguracoes().isEmpty());
        assertNull(excecaoCapturada);
    }
}
