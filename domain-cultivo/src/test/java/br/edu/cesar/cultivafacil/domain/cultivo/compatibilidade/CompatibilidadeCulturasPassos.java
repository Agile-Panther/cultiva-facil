package br.edu.cesar.cultivafacil.domain.cultivo.compatibilidade;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.NomeCultura;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CompatibilidadeCulturasPassos {

    private CompatibilidadeCulturasServico servico;
    private RelacaoCompatibilidadeRepositorioEmMemoria repositorio;

    private TalhaoId talhaoId;
    private NomeCultura culturaAtiva;
    private NomeCultura culturaNova;
    private boolean consentimento;

    private ConsorcioCultura resultado;
    private Exception excecaoCapturada;

    @Before
    public void inicializar() {
        repositorio = new RelacaoCompatibilidadeRepositorioEmMemoria();
        servico = new CompatibilidadeCulturasServico(repositorio);
        talhaoId = TalhaoId.novo();
        consentimento = false;
        resultado = null;
        excecaoCapturada = null;
    }

    @Dado("que a Zona possui Cultivo ativo de {string}")
    public void queAZonaPossuiCultivoAtivoDe(String nome) {
        culturaAtiva = new NomeCultura(nome);
    }

    @E("{string} é classificado como Companheiro de {string}")
    public void eClassificadoComoCompanheiroDe(String novaCultura, String culturaBase) {
        NomeCultura base = new NomeCultura(culturaBase);
        culturaNova = new NomeCultura(novaCultura);
        repositorio.salvar(new RelacaoCompatibilidade(base, culturaNova, ClassificacaoConsorcio.COMPANHEIRA));
    }

    @Quando("o Agricultor vincula {string} à Zona")
    public void oAgricultorVinculaAZona(String nome) {
        culturaNova = new NomeCultura(nome);
        try {
            resultado = servico.registrarConsorcio(talhaoId, culturaAtiva, culturaNova, consentimento);
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    @Entao("o vínculo é registrado com classificação COMPANHEIRA")
    public void oVinculoERegistradoComClassificacaoCompanheira() {
        assertNull(excecaoCapturada);
        assertNotNull(resultado);
        assertEquals(ClassificacaoConsorcio.COMPANHEIRA, resultado.getClassificacao());
    }

    @Dado("que {string} é classificado como Inimigo de {string}")
    public void eClassificadoComoInimigoDe(String novaCultura, String culturaBase) {
        culturaAtiva = new NomeCultura(culturaBase);
        culturaNova = new NomeCultura(novaCultura);
        repositorio.salvar(new RelacaoCompatibilidade(culturaAtiva, culturaNova, ClassificacaoConsorcio.INIMIGA));
    }

    @E("o Agricultor não confirma ciência do risco")
    public void oAgricultorNaoConfirmaCienciaDoRisco() {
        consentimento = false;
    }

    @Quando("tenta vincular {string} à Zona com {string} ativo")
    public void tentaVincularAZonaComAtivo(String novaCultura, String culturaBase) {
        culturaAtiva = new NomeCultura(culturaBase);
        culturaNova = new NomeCultura(novaCultura);
        try {
            resultado = servico.registrarConsorcio(talhaoId, culturaAtiva, culturaNova, consentimento);
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    @Entao("o sistema rejeita com erro {string}")
    public void oSistemaRejeitaComErro(String codigoErro) {
        assertNotNull(excecaoCapturada);
        assertTrue(excecaoCapturada.getMessage().contains(codigoErro),
            "Esperava mensagem contendo '" + codigoErro + "' mas foi: " + excecaoCapturada.getMessage());
    }

    @E("o Agricultor confirma explicitamente ciência do risco")
    public void oAgricultorConfirmaExplicitamenteCienciaDoRisco() {
        consentimento = true;
    }

    @Quando("submete o consórcio com consentimento registrado")
    public void submeteOConsorcioComConsentimentoRegistrado() {
        try {
            resultado = servico.registrarConsorcio(talhaoId, culturaAtiva, culturaNova, consentimento);
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    @Entao("o vínculo é aceito com indicador cienciaDoAgricultor verdadeiro")
    public void oVinculoEAceitoComIndicadorCienciaDoAgricultorVerdadeiro() {
        assertNull(excecaoCapturada);
        assertNotNull(resultado);
        assertTrue(resultado.isCienciaDoAgricultor());
        assertEquals(ClassificacaoConsorcio.INIMIGA, resultado.getClassificacao());
    }

    @Dado("que a Zona possui {string} ativo")
    public void queAZonaPossui_Ativo(String nome) {
        culturaAtiva = new NomeCultura(nome);
    }

    @E("{string} não possui relação de consórcio definida com {string}")
    public void naoPossuiRelacaoDeConsorcioDefinidaCom(String novaCultura, String culturaBase) {
        culturaNova = new NomeCultura(novaCultura);
    }

    @Quando("o Agricultor registra o consórcio")
    public void oAgricultorRegistraOConsorcio() {
        try {
            resultado = servico.registrarConsorcio(talhaoId, culturaAtiva, culturaNova, consentimento);
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    @Entao("o vínculo é aceito com classificação NEUTRA")
    public void oVinculoEAceitoComClassificacaoNeutra() {
        assertNull(excecaoCapturada);
        assertNotNull(resultado);
        assertEquals(ClassificacaoConsorcio.NEUTRA, resultado.getClassificacao());
        assertFalse(resultado.isCienciaDoAgricultor());
    }

    @Dado("que a Zona possui ao menos um vínculo de cultura registrado")
    public void queAZonaPossuiAoMenosUmVinculoDeCulturaRegistrado() {
        NomeCultura cultura = new NomeCultura("Alface");
        ConsorcioCultura consorcio = new ConsorcioCultura(talhaoId, cultura, ClassificacaoConsorcio.NEUTRA, false);
        repositorio.salvarConsorcio(consorcio);
    }

    @Quando("o Agricultor consulta o histórico de consórcios")
    public void oAgricultorConsultaOHistoricoDeConsorcios() {
        try {
            resultado = servico.consultarHistorico(talhaoId).get(0);
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    @Entao("todos os consórcios da Zona são retornados")
    public void todosOsConsorciosDaZonaSaoRetornados() {
        assertNull(excecaoCapturada);
        assertNotNull(resultado);
        assertEquals(talhaoId, resultado.getTalhaoId());
    }

    @Dado("que a Zona nunca recebeu nenhum vínculo de cultura")
    public void queAZonaNuncaRecebeuNenhumVinculoDeCultura() {
        // talhaoId sem consórcios — repositório em memória vazio por padrão
    }

    @Quando("o Agricultor tenta consultar o histórico de consórcios")
    public void oAgricultorTentaConsultarOHistoricoDeConsorcios() {
        try {
            servico.consultarHistorico(talhaoId);
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    // Implementação em memória do repositório para os testes Cucumber
    static class RelacaoCompatibilidadeRepositorioEmMemoria implements RelacaoCompatibilidadeRepositorio {

        private final Map<String, RelacaoCompatibilidade> catalogo = new HashMap<>();
        private final List<ConsorcioCultura> consorcios = new ArrayList<>();

        @Override
        public void salvar(RelacaoCompatibilidade relacao) {
            String chave = chave(relacao.getCulturaBase(), relacao.getCulturaRelacionada());
            catalogo.put(chave, relacao);
        }

        @Override
        public Optional<RelacaoCompatibilidade> buscarPorCulturas(NomeCultura culturaBase,
                                                                  NomeCultura culturaRelacionada) {
            String chave = chave(culturaBase, culturaRelacionada);
            RelacaoCompatibilidade relacao = catalogo.get(chave);
            if (relacao == null) {
                relacao = catalogo.get(chave(culturaRelacionada, culturaBase));
            }
            return Optional.ofNullable(relacao);
        }

        @Override
        public void salvarConsorcio(ConsorcioCultura consorcio) {
            consorcios.add(consorcio);
        }

        @Override
        public List<ConsorcioCultura> listarConsorcioPorTalhao(TalhaoId talhaoId) {
            return consorcios.stream()
                .filter(c -> c.getTalhaoId().equals(talhaoId))
                .toList();
        }

        private String chave(NomeCultura a, NomeCultura b) {
            return a.getValor() + "|" + b.getValor();
        }
    }
}
