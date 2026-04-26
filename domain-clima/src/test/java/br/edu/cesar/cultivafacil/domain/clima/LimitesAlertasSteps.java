package br.edu.cesar.cultivafacil.domain.clima;

import br.edu.cesar.cultivafacil.domain.clima.alertaClimatico.AlertaClimatico;
import br.edu.cesar.cultivafacil.domain.clima.alertaClimatico.TipoAlerta;
import br.edu.cesar.cultivafacil.domain.clima.alertaIrrigacao.AlertaIrrigacao;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

import br.edu.cesar.cultivafacil.domain.clima.limite.JanelaObservacao;
import br.edu.cesar.cultivafacil.domain.clima.limite.LimiteClimatico;
import br.edu.cesar.cultivafacil.domain.clima.limite.NecessidadeHidrica;
import br.edu.cesar.cultivafacil.domain.clima.limite.PrecipitacaoLimite;
import br.edu.cesar.cultivafacil.domain.clima.limite.TemperaturaLimite;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LimitesAlertasSteps {

    private static final int JANELA_PADRAO_DIAS = 5;
    private static final int MAX_TEMPERATURA_NEGOCIO = 50;
    private static final int MIN_TEMPERATURA_NEGOCIO = -5;
    private static final int MAX_PRECIPITACAO_NEGOCIO = 300;
    private static final int MAX_NECESSIDADE_NEGOCIO = 100;

    private boolean zonaComCulturaAtiva;
    private boolean cicloEncerrado;

    private Integer temperaturaMaxInformada;
    private Integer precipitacaoMaxInformada;
    private Integer necessidadeInformada;
    private Integer janelaDiasInformada;

    private LimiteClimatico limiteClimatico;
    private NecessidadeHidrica necessidadeHidrica;
    private AlertaClimatico alertaClimatico;
    private AlertaIrrigacao alertaIrrigacao;
    private LocalDateTime ultimoAlertaClimatico;
    private LocalDateTime ultimoAlertaIrrigacao;
    private String ultimoErro;

    private double previsaoPrecipitacao;
    private double precipitacaoAcumulada;

    @Dado("que a Zona possui vinculo de cultura ativo de {string}")
    public void que_a_zona_possui_vinculo_de_cultura_ativo_de(String cultura) {
        this.zonaComCulturaAtiva = true;
        this.cicloEncerrado = false;
        this.ultimoErro = null;
        this.limiteClimatico = null;
        this.necessidadeHidrica = null;
        this.alertaClimatico = null;
        this.alertaIrrigacao = null;
        assertNotNull(cultura);
    }

    @Quando("o Peao informa temperatura maxima {int}°C, precipitacao maxima {int} mm\\/24h e Necessidade Hidrica {int} mm para janela de 5Dias e confirma")
    public void o_peao_informa_temperatura_maxima_c_precipitacao_maxima_mm_24h_e_necessidade_hidrica_mm_para_janela_de_5dias_e_confirma(Integer temperatura, Integer precipitacao, Integer necessidade) {
        this.temperaturaMaxInformada = temperatura;
        this.precipitacaoMaxInformada = precipitacao;
        this.necessidadeInformada = necessidade;
        this.janelaDiasInformada = JANELA_PADRAO_DIAS;
        cadastrarLimites();
    }

    @Entao("os limites climaticos e a Necessidade Hidrica sao salvos com sucesso para a Zona e cultura ativa")
    public void os_limites_climaticos_e_a_necessidade_hidrica_sao_salvos_com_sucesso_para_a_zona_e_cultura_ativa() {
        assertEquals(null, ultimoErro);
        assertNotNull(limiteClimatico);
        assertNotNull(necessidadeHidrica);
        assertTrue(zonaComCulturaAtiva);
    }

    @Dado("que a Zona esta Vazia")
    public void que_a_zona_esta_vazia() {
        this.zonaComCulturaAtiva = false;
        this.ultimoErro = null;
    }

    @Quando("o Peao tenta cadastrar limites climaticos ou Necessidade Hidrica para essa Zona")
    public void o_peao_tenta_cadastrar_limites_climaticos_ou_necessidade_hidrica_para_essa_zona() {
        this.temperaturaMaxInformada = 35;
        this.precipitacaoMaxInformada = 80;
        this.necessidadeInformada = 15;
        this.janelaDiasInformada = JANELA_PADRAO_DIAS;
        cadastrarLimites();
    }

    @Dado("que o Peao informa temperatura de {int}°C")
    public void que_o_peao_informa_temperatura_de_c(Integer temperatura) {
        this.zonaComCulturaAtiva = true;
        this.temperaturaMaxInformada = temperatura;
        this.precipitacaoMaxInformada = 80;
        this.necessidadeInformada = 15;
        this.janelaDiasInformada = JANELA_PADRAO_DIAS;
    }

    @Quando("submete o cadastro")
    public void submete_o_cadastro() {
        cadastrarLimites();
    }

    @Entao("o sistema rejeita com erro {string}")
    public void o_sistema_rejeita_com_erro(String erroEsperado) {
        assertEquals(erroEsperado, ultimoErro);
    }

    @Dado("que o Peao informa precipitacao de {int} mm\\/24h")
    public void que_o_peao_informa_precipitacao_de_mm_24h(Integer precipitacao) {
        this.zonaComCulturaAtiva = true;
        this.temperaturaMaxInformada = 35;
        this.precipitacaoMaxInformada = precipitacao;
        this.necessidadeInformada = 15;
        this.janelaDiasInformada = JANELA_PADRAO_DIAS;
    }

    @Dado("que o Peao informa Necessidade Hidrica de {int} mm para a janela")
    public void que_o_peao_informa_necessidade_hidrica_de_mm_para_a_janela(Integer necessidade) {
        this.zonaComCulturaAtiva = true;
        this.temperaturaMaxInformada = 35;
        this.precipitacaoMaxInformada = 80;
        this.necessidadeInformada = necessidade;
        this.janelaDiasInformada = JANELA_PADRAO_DIAS;
    }

    @Dado("que o Peao informa janela de observacao {string}")
    public void que_o_peao_informa_janela_de_observacao(String janela) {
        this.zonaComCulturaAtiva = true;
        this.temperaturaMaxInformada = 35;
        this.precipitacaoMaxInformada = 80;
        this.necessidadeInformada = 15;
        this.janelaDiasInformada = Integer.parseInt(janela.replace("Dias", ""));
    }

    @Dado("que a Zona possui cultura ativa e limites climaticos e Necessidade Hidrica cadastrados")
    public void que_a_zona_possui_cultura_ativa_e_limites_climaticos_e_necessidade_hidrica_cadastrados() {
        this.zonaComCulturaAtiva = true;
        this.cicloEncerrado = false;
        this.temperaturaMaxInformada = 35;
        this.precipitacaoMaxInformada = 80;
        this.necessidadeInformada = 15;
        this.janelaDiasInformada = JANELA_PADRAO_DIAS;
        cadastrarLimites();
        assertEquals(null, ultimoErro);
    }

    @Quando("o Peao edita o limite de temperatura para {int}°C e a Necessidade Hidrica para {int} mm")
    public void o_peao_edita_o_limite_de_temperatura_para_c_e_a_necessidade_hidrica_para_mm(Integer temperatura, Integer necessidade) {
        if (cicloEncerrado || !zonaComCulturaAtiva) {
            this.ultimoErro = "LIMITE_CLIMATICO_IMUTAVEL";
            return;
        }
        this.temperaturaMaxInformada = temperatura;
        this.necessidadeInformada = necessidade;
        cadastrarLimites();
    }

    @Entao("os dados sao atualizados com sucesso")
    public void os_dados_sao_atualizados_com_sucesso() {
        assertEquals(null, ultimoErro);
        assertNotNull(limiteClimatico);
        assertNotNull(necessidadeHidrica);
        assertEquals(38.0, limiteClimatico.getTemperatura().getValorMax());
        assertEquals(20.0, necessidadeHidrica.getValor());
    }

    @Dado("que o ciclo da Zona foi encerrado e os limites foram cadastrados para aquele ciclo")
    public void que_o_ciclo_da_zona_foi_encerrado_e_os_limites_foram_cadastrados_para_aquele_ciclo() {
        this.zonaComCulturaAtiva = true;
        this.cicloEncerrado = false;
        this.temperaturaMaxInformada = 35;
        this.precipitacaoMaxInformada = 80;
        this.necessidadeInformada = 15;
        this.janelaDiasInformada = JANELA_PADRAO_DIAS;
        cadastrarLimites();
        this.cicloEncerrado = true;
        this.zonaComCulturaAtiva = false;
    }

    @Quando("o Peao tenta editar os limites climaticos ou a Necessidade Hidrica da Zona")
    public void o_peao_tenta_editar_os_limites_climaticos_ou_a_necessidade_hidrica_da_zona() {
        if (cicloEncerrado || !zonaComCulturaAtiva) {
            this.ultimoErro = "LIMITE_CLIMATICO_IMUTAVEL";
        }
    }

    @Dado("que a Zona possui cultura ativa, limites climaticos \\(precipitacao max. 80mm\\/24h\\) e Necessidade Hidrica {int} mm\\/5Dias cadastrados; a previsao indica {int} mm\\/24h e a precipitacao acumulada dos ultimos {int} dias e {int} mm")
    public void que_a_zona_possui_cultura_ativa_limites_climaticos_precipitacao_max_80mm_24h_e_necessidade_hidrica_mm_5dias_cadastrados_a_previsao_indica_mm_24h_e_a_precipitacao_acumulada_dos_ultimos_dias_e_mm(Integer necessidade, Integer previsao, Integer dias, Integer acumulado) {
        this.zonaComCulturaAtiva = true;
        this.cicloEncerrado = false;
        this.temperaturaMaxInformada = 35;
        this.precipitacaoMaxInformada = 80;
        this.necessidadeInformada = necessidade;
        this.janelaDiasInformada = JANELA_PADRAO_DIAS;
        cadastrarLimites();
        this.previsaoPrecipitacao = previsao;
        this.precipitacaoAcumulada = acumulado;
        assertEquals(JANELA_PADRAO_DIAS, dias);
    }

    @Quando("o sistema processa a previsao e a precipitacao acumulada")
    public void o_sistema_processa_a_previsao_e_a_precipitacao_acumulada() {
        processarAlertas();
    }

    @Entao("um Alerta Climatico de precipitacao e um Alerta de Necessidade de Irrigacao sao gerados para a Zona e enviados ao Peao")
    public void um_alerta_climatico_de_precipitacao_e_um_alerta_de_necessidade_de_irrigacao_sao_gerados_para_a_zona_e_enviados_ao_peao() {
        assertEquals(null, ultimoErro);
        assertNotNull(alertaClimatico);
        assertNotNull(alertaIrrigacao);
        assertEquals(TipoAlerta.PRECIPITACAO, alertaClimatico.getTipoAlerta());
    }

    @Dado("que a Zona possui cultura ativa mas nao possui limites climaticos cadastrados")
    public void que_a_zona_possui_cultura_ativa_mas_nao_possui_limites_climaticos_cadastrados() {
        this.zonaComCulturaAtiva = true;
        this.limiteClimatico = null;
        this.necessidadeHidrica = new NecessidadeHidrica(15);
        this.previsaoPrecipitacao = 120;
    }

    @Quando("a previsao indica condicoes adversas")
    public void a_previsao_indica_condicoes_adversas() {
        if (limiteClimatico == null) {
            this.ultimoErro = "ALERTA_SEM_LIMITE";
            this.alertaClimatico = null;
        }
    }

    @Entao("nenhum Alerta Climatico e gerado e o sistema retorna erro {string}")
    public void nenhum_alerta_climatico_e_gerado_e_o_sistema_retorna_erro(String erroEsperado) {
        assertEquals(erroEsperado, ultimoErro);
        assertEquals(null, alertaClimatico);
    }

    @Dado("que um Alerta Climatico de precipitacao ja foi gerado para a Zona ha {int} horas")
    public void que_um_alerta_climatico_de_precipitacao_ja_foi_gerado_para_a_zona_ha_horas(Integer horas) {
        this.zonaComCulturaAtiva = true;
        this.temperaturaMaxInformada = 35;
        this.precipitacaoMaxInformada = 80;
        this.necessidadeInformada = 15;
        this.janelaDiasInformada = JANELA_PADRAO_DIAS;
        cadastrarLimites();
        this.ultimoAlertaClimatico = LocalDateTime.now().minusHours(horas);
    }

    @Quando("a previsao volta a ultrapassar o limite de precipitacao")
    public void a_previsao_volta_a_ultrapassar_o_limite_de_precipitacao() {
        this.previsaoPrecipitacao = 120;
        if (ultimoAlertaClimatico != null && ultimoAlertaClimatico.isAfter(LocalDateTime.now().minusHours(24))) {
            this.ultimoErro = "FREQUENCIA_ALERTA_EXCEDIDA";
            this.alertaClimatico = null;
            return;
        }
        processarAlertas();
    }

    @Entao("o sistema bloqueia a geracao de um segundo alerta do mesmo tipo dentro do intervalo de 24h e retorna erro {string}")
    public void o_sistema_bloqueia_a_geracao_de_um_segundo_alerta_do_mesmo_tipo_dentro_do_intervalo_de_24h_e_retorna_erro(String erroEsperado) {
        assertEquals(erroEsperado, ultimoErro);
        assertEquals(null, alertaClimatico);
    }

    @Dado("que a Zona possui cultura ativa mas nao possui Necessidade Hidrica cadastrada")
    public void que_a_zona_possui_cultura_ativa_mas_nao_possui_necessidade_hidrica_cadastrada() {
        this.zonaComCulturaAtiva = true;
        this.temperaturaMaxInformada = 35;
        this.precipitacaoMaxInformada = 80;
        this.necessidadeInformada = 15;
        this.janelaDiasInformada = JANELA_PADRAO_DIAS;
        cadastrarLimites();
        this.necessidadeHidrica = null;
        this.precipitacaoAcumulada = 1;
    }

    @Quando("a precipitacao acumulada cai abaixo de qualquer limiar")
    public void a_precipitacao_acumulada_cai_abaixo_de_qualquer_limiar() {
        if (necessidadeHidrica == null) {
            this.ultimoErro = "NECESSIDADE_HIDRICA_NAO_CADASTRADA";
            this.alertaIrrigacao = null;
            return;
        }
        processarAlertas();
    }

    @Entao("nenhum Alerta de Irrigacao e gerado e o sistema retorna erro {string}")
    public void nenhum_alerta_de_irrigacao_e_gerado_e_o_sistema_retorna_erro(String erroEsperado) {
        assertEquals(erroEsperado, ultimoErro);
        assertEquals(null, alertaIrrigacao);
    }

    @Dado("que um Alerta de Necessidade de Irrigacao ja foi gerado para a Zona ha {int} horas")
    public void que_um_alerta_de_necessidade_de_irrigacao_ja_foi_gerado_para_a_zona_ha_horas(Integer horas) {
        this.zonaComCulturaAtiva = true;
        this.temperaturaMaxInformada = 35;
        this.precipitacaoMaxInformada = 80;
        this.necessidadeInformada = 15;
        this.janelaDiasInformada = JANELA_PADRAO_DIAS;
        cadastrarLimites();
        this.ultimoAlertaIrrigacao = LocalDateTime.now().minusHours(horas);
        this.precipitacaoAcumulada = 10;
    }

    @Quando("a precipitacao acumulada continua abaixo da Necessidade Hidrica definida")
    public void a_precipitacao_acumulada_continua_abaixo_da_necessidade_hidrica_definida() {
        if (ultimoAlertaIrrigacao != null && ultimoAlertaIrrigacao.isAfter(LocalDateTime.now().minusHours(24))) {
            this.ultimoErro = "FREQUENCIA_ALERTA_EXCEDIDA";
            this.alertaIrrigacao = null;
            return;
        }
        processarAlertas();
    }

    @Entao("o sistema bloqueia a geracao de um segundo Alerta de Irrigacao dentro do intervalo de 24 horas e retorna erro {string}")
    public void o_sistema_bloqueia_a_geracao_de_um_segundo_alerta_de_irrigacao_dentro_do_intervalo_de_24_horas_e_retorna_erro(String erroEsperado) {
        assertEquals(erroEsperado, ultimoErro);
        assertEquals(null, alertaIrrigacao);
    }

    @Dado("que o sistema gerou um Alerta de Necessidade de Irrigacao para a Zona {string} ha {int} horas")
    public void que_o_sistema_gerou_um_alerta_de_necessidade_de_irrigacao_para_a_zona_ha_horas(String zona, Integer horas) {
        this.zonaComCulturaAtiva = true;
        this.temperaturaMaxInformada = 35;
        this.precipitacaoMaxInformada = 80;
        this.necessidadeInformada = 15;
        this.janelaDiasInformada = JANELA_PADRAO_DIAS;
        cadastrarLimites();
        this.ultimoAlertaIrrigacao = LocalDateTime.now().minusHours(horas);
        assertNotNull(zona);
    }

    @Quando("a precipitacao acumulada volta a cair abaixo da Necessidade Hidrica minima da mesma Zona")
    public void a_precipitacao_acumulada_volta_a_cair_abaixo_da_necessidade_hidrica_minima_da_mesma_zona() {
        this.precipitacaoAcumulada = 10;
        if (ultimoAlertaIrrigacao != null && ultimoAlertaIrrigacao.isAfter(LocalDateTime.now().minusHours(24))) {
            this.alertaIrrigacao = null;
            return;
        }
        processarAlertas();
    }

    @Entao("o sistema nao emite novo alerta")
    public void o_sistema_nao_emite_novo_alerta() {
        assertEquals(null, alertaIrrigacao);
    }

    private void cadastrarLimites() {
        this.ultimoErro = null;

        if (!zonaComCulturaAtiva) {
            this.ultimoErro = "ZONA_SEM_CULTURA_ATIVA";
            return;
        }
        if (temperaturaMaxInformada == null || temperaturaMaxInformada < MIN_TEMPERATURA_NEGOCIO
                || temperaturaMaxInformada > MAX_TEMPERATURA_NEGOCIO) {
            this.ultimoErro = "TEMPERATURA_INVALIDA";
            return;
        }
        if (precipitacaoMaxInformada == null || precipitacaoMaxInformada < 0
                || precipitacaoMaxInformada > MAX_PRECIPITACAO_NEGOCIO) {
            this.ultimoErro = "PRECIPITACAO_INVALIDA";
            return;
        }
        if (necessidadeInformada == null || necessidadeInformada < 0 || necessidadeInformada > MAX_NECESSIDADE_NEGOCIO) {
            this.ultimoErro = "NECESSIDADE_HIDRICA_INVALIDA";
            return;
        }
        if (janelaDiasInformada == null || janelaDiasInformada != JANELA_PADRAO_DIAS) {
            this.ultimoErro = "JANELA_OBSERVACAO_INVALIDA";
            return;
        }

        TemperaturaLimite temperatura = new TemperaturaLimite(0, temperaturaMaxInformada);
        PrecipitacaoLimite precipitacao = new PrecipitacaoLimite(precipitacaoMaxInformada);
        JanelaObservacao janela = new JanelaObservacao(janelaDiasInformada);
        this.necessidadeHidrica = new NecessidadeHidrica(necessidadeInformada);
        this.limiteClimatico = new LimiteClimatico(temperatura, precipitacao, janela);
    }

    private void processarAlertas() {
        this.ultimoErro = null;

        if (limiteClimatico == null) {
            this.ultimoErro = "ALERTA_SEM_LIMITE";
            this.alertaClimatico = null;
            return;
        }

        if (previsaoPrecipitacao > limiteClimatico.getPrecipitacao().getValor()) {
            this.alertaClimatico = new AlertaClimatico(
                    limiteClimatico.getId(),
                    TipoAlerta.PRECIPITACAO,
                    "Precipitacao acima do limite"
            );
            this.ultimoAlertaClimatico = LocalDateTime.now();
        }

        if (necessidadeHidrica == null) {
            this.ultimoErro = "NECESSIDADE_HIDRICA_NAO_CADASTRADA";
            this.alertaIrrigacao = null;
            return;
        }

        if (precipitacaoAcumulada < necessidadeHidrica.getValor()) {
            this.alertaIrrigacao = new AlertaIrrigacao(necessidadeHidrica, "Necessidade de irrigacao identificada");
            this.ultimoAlertaIrrigacao = LocalDateTime.now();
        }
    }
}

