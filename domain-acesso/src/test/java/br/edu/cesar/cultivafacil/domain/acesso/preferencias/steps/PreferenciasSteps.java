package br.edu.cesar.cultivafacil.domain.acesso.preferencias.steps;

import br.edu.cesar.cultivafacil.domain.acesso.conta.ContaId;
import br.edu.cesar.cultivafacil.domain.acesso.preferencias.*;
import br.edu.cesar.cultivafacil.domain.evento.EventoBarramento;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.EnumSet;

import static org.junit.jupiter.api.Assertions.*;

public class PreferenciasSteps {

    private PreferenciasNotificacao preferencias;
    private Exception excecaoCapturada;

    @Dado("que estou autenticado como Membro da Propriedade")
    public void autenticadoComoMembroDaPropriedade() {
        preferencias = new PreferenciasNotificacao(ContaId.novo(), new EventoBarramento());
        excecaoCapturada = null;
    }

    // =========================================================
    // US-08 - RN-027 e RN-028: Configuracao de notificacoes
    // =========================================================

    @Quando("configuro o tipo de notificacao {string} com {int} notificacoes hoje")
    public void configurarTipoNotificacao(String tipo, int notificacoesHoje) {
        try {
            TipoNotificacao tipoNotificacao = TipoNotificacao.de(tipo);
            preferencias.configurarNotificacoes(EnumSet.of(tipoNotificacao), notificacoesHoje);
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    @Entao("o sistema aceita a configuracao de notificacao")
    public void sistemaAceitaConfiguracaoNotificacao() {
        assertNull(excecaoCapturada,
            "Nao deveria ter excecao, mas foi lancada: " + excecaoCapturada);
    }

    @Entao("o sistema rejeita a configuracao de notificacao")
    public void sistemaRejeitaConfiguracaoNotificacao() {
        assertNotNull(excecaoCapturada, "Deveria ter excecao, mas nao foi lancada");
    }

    // =========================================================
    // US-07 - RN-025 e RN-026: Preferencias de exibicao
    // =========================================================

    @Quando("configuro o horario {string} e unidade de area {string}")
    public void configurarPreferenciasExibicao(String horarioStr, String unidadeStr) {
        try {
            HorarioResumoDiario horario = new HorarioResumoDiario(LocalTime.parse(horarioStr));
            UnidadeArea unidade = UnidadeArea.de(unidadeStr);
            preferencias.definirPreferencias(horario, unidade);
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    @Entao("o sistema aceita as preferencias de exibicao")
    public void sistemaAceitaPreferenciasExibicao() {
        assertNull(excecaoCapturada,
            "Nao deveria ter excecao, mas foi lancada: " + excecaoCapturada);
    }

    // =========================================================
    // US-07 - RN-026: Validacao de ValorArea
    // =========================================================

    @Quando("tento registrar um valor de area de {string}")
    public void registrarValorArea(String valorStr) {
        try {
            new ValorArea(new BigDecimal(valorStr));
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    @Entao("o sistema rejeita o valor de area")
    public void sistemaRejeitaValorArea() {
        assertNotNull(excecaoCapturada, "Deveria ter excecao, mas nao foi lancada");
    }

    // =========================================================
    // US-07 - RN-025: Validacao de HorarioResumoDiario
    // =========================================================

    @Quando("configuro o horario do resumo diario para {string}")
    public void configurarHorarioResumoDiario(String horarioStr) {
        try {
            new HorarioResumoDiario(LocalTime.parse(horarioStr));
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    @Entao("o sistema rejeita o horario de resumo")
    public void sistemaRejeitaHorarioResumo() {
        assertNotNull(excecaoCapturada, "Deveria ter excecao, mas nao foi lancada");
    }
}
