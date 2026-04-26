package br.edu.cesar.cultivafacil.domain.sanidade;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.CicloAgricolaId;
import br.edu.cesar.cultivafacil.domain.sanidade.foco.DescricaoFoco;
import br.edu.cesar.cultivafacil.domain.sanidade.foco.FocoFitossanitario;
import br.edu.cesar.cultivafacil.domain.sanidade.foco.NivelInfestacao;
import br.edu.cesar.cultivafacil.domain.sanidade.foco.SeveridadeFoco;
import br.edu.cesar.cultivafacil.domain.sanidade.foco.TipoAgronomicoFoco;
import br.edu.cesar.cultivafacil.domain.sanidade.foco.exception.DescricaoFocoInvalidaException;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

import java.text.Normalizer;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FocoFitossanitarioSteps {

    private TalhaoId zonaId;
    private CicloAgricolaId cicloId;
    private FocoFitossanitario foco;

    private String tipoInformado;
    private String infestacaoInformada;
    private String severidadeInformada;
    private String descricaoInformada;

    private boolean cicloAtivo;
    private boolean jaExisteFocoNaData;
    private String tipoJaRegistrado;
    private boolean focoTemPerdaVinculada;

    private String ultimoErro;
    private boolean operacaoConcluida;

    @Dado("que a Zona possui ciclo ativo e ainda nao ha Foco do tipo {string} registrado nela na data de hoje")
    public void que_a_zona_possui_ciclo_ativo_e_ainda_nao_ha_foco_do_tipo_registrado_nela_na_data_de_hoje(String tipo) {
        inicializarContextoBase();
        this.cicloAtivo = true;
        this.jaExisteFocoNaData = false;
        this.tipoJaRegistrado = normalizar(tipo);
    }

    @Quando("o Peao informa tipo {string}, infestacao {string}, severidade {string} e descricao com {int} caracteres")
    public void o_peao_informa_tipo_infestacao_severidade_e_descricao_com_caracteres(String tipo, String infestacao, String severidade, Integer tamanhoDescricao) {
        this.tipoInformado = tipo;
        this.infestacaoInformada = infestacao;
        this.severidadeInformada = severidade;
        this.descricaoInformada = gerarDescricao(tamanhoDescricao);
        registrarFoco();
    }

    @Entao("o Foco Fitossanitario e salvo com sucesso na Zona")
    public void o_foco_fitossanitario_e_salvo_com_sucesso_na_zona() {
        assertNull(this.ultimoErro);
        assertTrue(this.operacaoConcluida);
        assertNotNull(this.foco);
        assertEquals(this.zonaId, this.foco.getZonaId());
    }

    @Dado("que a Zona esta Vazia \\(sem ciclo ativo)")
    public void que_a_zona_esta_vazia_sem_ciclo_ativo() {
        inicializarContextoBase();
        this.cicloAtivo = false;
    }

    @Quando("o Peao tenta registrar um Foco Fitossanitario")
    public void o_peao_tenta_registrar_um_foco_fitossanitario() {
        this.tipoInformado = "Praga";
        this.infestacaoInformada = "Alta";
        this.severidadeInformada = "Alta";
        this.descricaoInformada = gerarDescricao(80);
        registrarFoco();
    }

    @Entao("o sistema rejeita com erro {string}")
    public void o_sistema_rejeita_com_erro(String codigoErro) {
        assertEquals(codigoErro, this.ultimoErro);
        assertTrue(!this.operacaoConcluida);
    }

    @Dado("que o Peao informa o tipo agronomico {string}")
    public void que_o_peao_informa_o_tipo_agronomico(String tipo) {
        inicializarContextoBase();
        this.tipoInformado = tipo;
        this.infestacaoInformada = "Alta";
        this.severidadeInformada = "Alta";
        this.descricaoInformada = gerarDescricao(80);
    }

    @Dado("que o Peao informa nivel de infestacao {string}")
    public void que_o_peao_informa_nivel_de_infestacao(String nivel) {
        inicializarContextoBase();
        this.tipoInformado = "Praga";
        this.infestacaoInformada = nivel;
        this.severidadeInformada = "Alta";
        this.descricaoInformada = gerarDescricao(80);
    }

    @Dado("que o Peao informa severidade {string}")
    public void que_o_peao_informa_severidade(String severidade) {
        inicializarContextoBase();
        this.tipoInformado = "Praga";
        this.infestacaoInformada = "Alta";
        this.severidadeInformada = severidade;
        this.descricaoInformada = gerarDescricao(80);
    }

    @Dado("que o Peao informa descricao com apenas {int} caracteres")
    public void que_o_peao_informa_descricao_com_apenas_caracteres(Integer tamanho) {
        inicializarContextoBase();
        this.tipoInformado = "Praga";
        this.infestacaoInformada = "Alta";
        this.severidadeInformada = "Alta";
        this.descricaoInformada = gerarDescricao(tamanho);
    }

    @Dado("que o Peao informa descricao com {int} caracteres")
    public void que_o_peao_informa_descricao_com_caracteres(Integer tamanho) {
        inicializarContextoBase();
        this.tipoInformado = "Praga";
        this.infestacaoInformada = "Alta";
        this.severidadeInformada = "Alta";
        this.descricaoInformada = gerarDescricao(tamanho);
    }

    @Quando("submete o registro do Foco")
    public void submete_o_registro_do_foco() {
        registrarFoco();
    }

    @Dado("que a Zona ja possui um Foco do tipo {string} registrado na data de hoje")
    public void que_a_zona_ja_possui_um_foco_do_tipo_registrado_na_data_de_hoje(String tipo) {
        inicializarContextoBase();
        this.jaExisteFocoNaData = true;
        this.tipoJaRegistrado = normalizar(tipo);
    }

    @Quando("o Peao tenta registrar outro Foco do tipo {string} na mesma Zona na mesma data")
    public void o_peao_tenta_registrar_outro_foco_do_tipo_na_mesma_zona_na_mesma_data(String tipo) {
        this.tipoInformado = tipo;
        this.infestacaoInformada = "Alta";
        this.severidadeInformada = "Alta";
        this.descricaoInformada = gerarDescricao(80);
        registrarFoco();
    }

    @Dado("que o Foco pertence ao ciclo ativo da Zona e o ciclo ainda esta em andamento")
    public void que_o_foco_pertence_ao_ciclo_ativo_da_zona_e_o_ciclo_ainda_esta_em_andamento() {
        inicializarContextoBase();
        this.foco = criarFocoValido("Praga", "Alta", "Alta", 80);
        this.operacaoConcluida = false;
    }

    @Quando("o Peao atualiza o nvel de infestacao para {string}")
    public void o_peao_atualiza_o_nvel_de_infestacao_para(String nivel) {
        if (!this.cicloAtivo) {
            registrarErro("FOCO_IMUTAVEL");
            return;
        }

        NivelInfestacao novoNivel = converterNivel(nivel);
        if (novoNivel == null) {
            registrarErro("NIVEL_INFESTACAO_INVALIDO");
            return;
        }

        this.foco = new FocoFitossanitario(
                this.foco.getZonaId(),
                this.foco.getCicloAgricolaId(),
                this.foco.getTipo(),
                novoNivel,
                this.foco.getSeveridade(),
                this.foco.getDescricao()
        );
        this.ultimoErro = null;
        this.operacaoConcluida = true;
    }

    @Quando("o Peao atualiza o nível de infestacao para {string}")
    public void o_peao_atualiza_o_nivel_de_infestacao_para(String nivel) {
        o_peao_atualiza_o_nvel_de_infestacao_para(nivel);
    }

    @Entao("o Foco e atualizado com sucesso")
    public void o_foco_e_atualizado_com_sucesso() {
        assertNull(this.ultimoErro);
        assertTrue(this.operacaoConcluida);
        assertEquals(NivelInfestacao.MEDIO, this.foco.getNivel());
    }

    @Dado("que o ciclo da Zona foi encerrado e o Foco Fitossanitario pertence a esse ciclo")
    public void que_o_ciclo_da_zona_foi_encerrado_e_o_foco_fitossanitario_pertence_a_esse_ciclo() {
        inicializarContextoBase();
        this.cicloAtivo = false;
        this.foco = criarFocoValido("Praga", "Alta", "Alta", 80);
        this.operacaoConcluida = false;
    }

    @Quando("o Peao tenta atualizar o Foco")
    public void o_peao_tenta_atualizar_o_foco() {
        if (!this.cicloAtivo) {
            registrarErro("FOCO_IMUTAVEL");
            return;
        }
        this.operacaoConcluida = true;
    }

    @Dado("que o Peao tenta atualizar o tipo agronomico do Foco para {string}")
    public void que_o_peao_tenta_atualizar_o_tipo_agronomico_do_foco_para(String tipo) {
        inicializarContextoBase();
        this.foco = criarFocoValido("Praga", "Alta", "Alta", 80);
        this.tipoInformado = tipo;
    }

    @Quando("submete a atualizacao")
    public void submete_a_atualizacao() {
        TipoAgronomicoFoco tipoConvertido = converterTipo(this.tipoInformado);
        if (tipoConvertido == null) {
            registrarErro("TIPO_AGRONOMICO_INVALIDO");
            return;
        }

        this.foco = new FocoFitossanitario(
                this.foco.getZonaId(),
                this.foco.getCicloAgricolaId(),
                tipoConvertido,
                this.foco.getNivel(),
                this.foco.getSeveridade(),
                this.foco.getDescricao()
        );
        this.operacaoConcluida = true;
    }

    @Entao("o sistema rejeita aplicando as mesmas validacoes do registro original")
    public void o_sistema_rejeita_aplicando_as_mesmas_validacoes_do_registro_original() {
        assertEquals("TIPO_AGRONOMICO_INVALIDO", this.ultimoErro);
        assertTrue(!this.operacaoConcluida);
    }

    @Dado("que o Foco Fitossanitario possui ao menos um Registro de Perda vinculado")
    public void que_o_foco_fitossanitario_possui_ao_menos_um_registro_de_perda_vinculado() {
        inicializarContextoBase();
        this.foco = criarFocoValido("Doenca", "Media", "Media", 80);
        this.focoTemPerdaVinculada = true;
        this.operacaoConcluida = false;
    }

    @Quando("o Peao tenta remover o Foco")
    public void o_peao_tenta_remover_o_foco() {
        if (this.focoTemPerdaVinculada) {
            registrarErro("FOCO_COM_PERDAS_VINCULADAS");
            return;
        }

        this.foco = null;
        this.ultimoErro = null;
        this.operacaoConcluida = true;
    }

    private void registrarFoco() {
        if (!this.cicloAtivo) {
            registrarErro("ZONA_SEM_CICLO_ATIVO");
            return;
        }

        TipoAgronomicoFoco tipo = converterTipo(this.tipoInformado);
        if (tipo == null) {
            registrarErro("TIPO_AGRONOMICO_INVALIDO");
            return;
        }

        if (this.jaExisteFocoNaData && normalizar(this.tipoInformado).equals(this.tipoJaRegistrado)) {
            registrarErro("FOCO_DUPLICADO");
            return;
        }

        NivelInfestacao nivel = converterNivel(this.infestacaoInformada);
        if (nivel == null) {
            registrarErro("NIVEL_INFESTACAO_INVALIDO");
            return;
        }

        SeveridadeFoco severidade = converterSeveridade(this.severidadeInformada);
        if (severidade == null) {
            registrarErro("SEVERIDADE_INVALIDA");
            return;
        }

        try {
            DescricaoFoco descricao = new DescricaoFoco(this.descricaoInformada);
            this.foco = new FocoFitossanitario(this.zonaId, this.cicloId, tipo, nivel, severidade, descricao);
            this.ultimoErro = null;
            this.operacaoConcluida = true;
        } catch (DescricaoFocoInvalidaException ex) {
            registrarErro("DESCRICAO_FOCO_INVALIDA");
        }
    }

    private void inicializarContextoBase() {
        this.zonaId = TalhaoId.novo();
        this.cicloId = CicloAgricolaId.novo();
        this.foco = null;

        this.tipoInformado = null;
        this.infestacaoInformada = null;
        this.severidadeInformada = null;
        this.descricaoInformada = null;

        this.cicloAtivo = true;
        this.jaExisteFocoNaData = false;
        this.tipoJaRegistrado = null;
        this.focoTemPerdaVinculada = false;

        this.ultimoErro = null;
        this.operacaoConcluida = false;
    }

    private void registrarErro(String codigo) {
        this.ultimoErro = codigo;
        this.operacaoConcluida = false;
    }

    private FocoFitossanitario criarFocoValido(String tipo, String nivel, String severidade, int tamanhoDescricao) {
        return new FocoFitossanitario(
                this.zonaId,
                this.cicloId,
                converterTipo(tipo),
                converterNivel(nivel),
                converterSeveridade(severidade),
                new DescricaoFoco(gerarDescricao(tamanhoDescricao))
        );
    }

    private String gerarDescricao(Integer tamanho) {
        if (tamanho == null || tamanho < 0) {
            return "";
        }
        return "a".repeat(tamanho);
    }

    private TipoAgronomicoFoco converterTipo(String valor) {
        return switch (normalizar(valor)) {
            case "PRAGA" -> TipoAgronomicoFoco.PRAGA;
            case "DOENCA" -> TipoAgronomicoFoco.DOENCA;
            case "DANO FISICO", "DANO_FISICO" -> TipoAgronomicoFoco.DANO_FISICO;
            default -> null;
        };
    }

    private NivelInfestacao converterNivel(String valor) {
        return switch (normalizar(valor)) {
            case "BAIXA", "BAIXO" -> NivelInfestacao.BAIXO;
            case "MEDIA", "MEDIO" -> NivelInfestacao.MEDIO;
            case "ALTA", "ALTO" -> NivelInfestacao.ALTO;
            default -> null;
        };
    }

    private SeveridadeFoco converterSeveridade(String valor) {
        return switch (normalizar(valor)) {
            case "BAIXA", "BAIXO" -> SeveridadeFoco.BAIXA;
            case "MEDIA", "MEDIO" -> SeveridadeFoco.MEDIA;
            case "ALTA", "ALTO" -> SeveridadeFoco.ALTA;
            default -> null;
        };
    }

    private String normalizar(String valor) {
        if (valor == null) {
            return "";
        }

        String semAcento = Normalizer.normalize(valor, Normalizer.Form.NFD)
                .replaceAll("\\p{M}+", "");

        return semAcento.trim().toUpperCase(Locale.ROOT);
    }
}


