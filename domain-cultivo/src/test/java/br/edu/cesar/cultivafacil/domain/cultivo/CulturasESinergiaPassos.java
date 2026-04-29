package br.edu.cesar.cultivafacil.domain.cultivo;

import br.edu.cesar.cultivafacil.domain.cultivo.compatibilidade.CompatibilidadeCulturasServico;
import br.edu.cesar.cultivafacil.domain.cultivo.compatibilidade.RelacaoCompatibilidade;
import br.edu.cesar.cultivafacil.domain.cultivo.compatibilidade.RelacaoCompatibilidadeRepositorio;
import br.edu.cesar.cultivafacil.domain.cultivo.compatibilidade.ResultadoCompatibilidade;
import br.edu.cesar.cultivafacil.domain.cultivo.cultura.CatalogoCulturaServico;
import br.edu.cesar.cultivafacil.domain.cultivo.cultura.Cultura;
import br.edu.cesar.cultivafacil.domain.cultivo.cultura.CulturaId;
import br.edu.cesar.cultivafacil.domain.cultivo.cultura.CulturaRepositorio;
import br.edu.cesar.cultivafacil.domain.cultivo.cultura.NomeComumCultura;
import br.edu.cesar.cultivafacil.domain.cultivo.cultura.Variedade;
import br.edu.cesar.cultivafacil.domain.propriedade.propriedade.PropriedadeId;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

public class CulturasESinergiaPassos {

    private PropriedadeId propriedadeId;
    private CulturaRepositorioEmMemoria culturaRepositorio;
    private RelacaoCompatibilidadeRepositorioEmMemoria relacaoRepositorio;
    private CatalogoCulturaServico catalogoServico;
    private CompatibilidadeCulturasServico compatibilidadeServico;
    private Exception excecaoCapturada;
    private Cultura culturaCadastrada;
    private ResultadoCompatibilidade resultadoCompatibilidade;

    @Before
    public void inicializar() {
        propriedadeId = PropriedadeId.novo();
        culturaRepositorio = new CulturaRepositorioEmMemoria();
        relacaoRepositorio = new RelacaoCompatibilidadeRepositorioEmMemoria();
        catalogoServico = new CatalogoCulturaServico(culturaRepositorio);
        compatibilidadeServico = new CompatibilidadeCulturasServico(culturaRepositorio, relacaoRepositorio);
        excecaoCapturada = null;
        culturaCadastrada = null;
        resultadoCompatibilidade = null;
    }

    @Dado("que estou autenticado como Gestor {string}")
    public void queEstouAutenticadoComoGestor(String nome) {
        assertNotNull(nome);
    }

    @E("que existe a Propriedade {string}")
    public void queExisteAPropriedade(String nome) {
        assertNotNull(nome);
    }

    @Quando("cadastro uma Cultura com nome comum {string} e variedade {string}")
    public void cadastroUmaCulturaComNomeComumEVariedade(String nome, String variedade) {
        executarCadastro(nome, variedade, "Poaceae");
    }

    @Quando("cadastro uma Cultura com nome {string}, variedade {string} e família botânica {string}")
    public void cadastroUmaCulturaComNomeVariedadeEFamiliaBotanica(String nome, String variedade, String familia) {
        executarCadastro(nome, variedade, familia);
    }

    @Dado("que já existe uma Cultura customizada {string} no catálogo")
    public void queJaExisteUmaCulturaCustomizadaNoCatalogo(String descricao) {
        String[] partes = descricao.split(" - ");
        culturaRepositorio.salvar(Cultura.customizada(propriedadeId, partes[0], partes[1], "Poaceae"));
    }

    @Quando("cadastro uma nova Cultura customizada com nome {string} e variedade {string}")
    public void cadastroUmaNovaCulturaCustomizadaComNomeEVariedade(String nome, String variedade) {
        executarCadastro(nome, variedade, "Poaceae");
    }

    @Dado("que a Cultura {string} está {string} no catálogo")
    public void queACulturaEstaNoCatalogo(String nome, String status) {
        configurarCultura(nome, status);
    }

    @Dado("que a Cultura {string} está {string}")
    public void queACulturaEsta(String nome, String status) {
        configurarCultura(nome, status);
    }

    @Quando("verifico a compatibilidade entre {string} e {string}")
    public void verificoACompatibilidadeEntreE(String primeira, String segunda) {
        executarVerificacao(List.of(primeira, segunda));
    }

    @Quando("verifico a compatibilidade com os cultivos {string}")
    public void verificoACompatibilidadeComOsCultivos(String cultivos) {
        if (cultivos == null || cultivos.isBlank()) {
            executarVerificacao(List.of());
            return;
        }

        List<String> nomes = Arrays.stream(cultivos.split(","))
            .map(String::trim)
            .filter(nome -> !nome.isEmpty())
            .toList();

        nomes.stream().distinct().forEach(nome -> {
            if (culturaRepositorio.buscarPorNome(propriedadeId, new NomeComumCultura(nome)).isEmpty()) {
                culturaRepositorio.salvar(Cultura.nativa(propriedadeId, nome, "Comum", "Poaceae"));
            }
        });
        executarVerificacao(nomes);
    }

    @Dado("que a Cultura customizada {string} possui família botânica {string}")
    public void queACulturaCustomizadaPossuiFamiliaBotanica(String nome, String familia) {
        Cultura cultura = familia == null || familia.isBlank()
            ? Cultura.customizadaSemFamilia(propriedadeId, nome, "Local")
            : Cultura.customizada(propriedadeId, nome, "Local", familia);
        culturaRepositorio.salvar(cultura);
    }

    @E("que a Cultura {string} é nativa do catálogo")
    public void queACulturaENativaDoCatalogo(String nome) {
        culturaRepositorio.salvar(Cultura.nativa(propriedadeId, nome, "Comum", "Fabaceae"));
    }

    @Entao("o sistema {string} o cadastro")
    public void oSistemaOCadastro(String resultadoEsperado) {
        assertResultado(resultadoEsperado, culturaCadastrada);
    }

    @Entao("o sistema rejeita o cadastro")
    public void oSistemaRejeitaOCadastro() {
        assertNotNull(excecaoCapturada);
    }

    @Entao("a mensagem de erro contém {string}")
    public void aMensagemDeErroContem(String trecho) {
        assertNotNull(excecaoCapturada);
        assertTrue(excecaoCapturada.getMessage().contains(trecho),
            "Esperava mensagem contendo '" + trecho + "', mas foi: " + excecaoCapturada.getMessage());
    }

    @Entao("o sistema {string} a verificação")
    public void oSistemaAVerificacao(String resultadoEsperado) {
        assertResultado(resultadoEsperado, resultadoCompatibilidade);
    }

    private void executarCadastro(String nome, String variedade, String familia) {
        try {
            culturaCadastrada = catalogoServico.cadastrarCustomizada(propriedadeId, nome, variedade, familia);
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    private void executarVerificacao(List<String> nomes) {
        try {
            resultadoCompatibilidade = compatibilidadeServico.verificar(propriedadeId, nomes);
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    private void configurarCultura(String nome, String status) {
        if ("ausente".equals(status)) {
            return;
        }

        Cultura cultura = Cultura.nativa(propriedadeId, nome, "Comum", "Poaceae");
        if ("inativa".equals(status)) {
            cultura.inativar();
        }
        culturaRepositorio.salvar(cultura);
    }

    private void assertResultado(String resultadoEsperado, Object resultado) {
        if ("aceita".equals(resultadoEsperado)) {
            assertNull(excecaoCapturada);
            assertNotNull(resultado);
        } else {
            assertNotNull(excecaoCapturada);
        }
    }

    static class CulturaRepositorioEmMemoria implements CulturaRepositorio {

        private final Map<String, Cultura> culturas = new ConcurrentHashMap<>();

        @Override
        public void salvar(Cultura cultura) {
            culturas.put(chave(cultura.getPropriedadeId(), cultura.getNomeComum()), cultura);
        }

        @Override
        public boolean existePorNomeEVariedade(PropriedadeId propriedadeId, NomeComumCultura nomeComum,
                                               Variedade variedade) {
            return culturas.values().stream()
                .anyMatch(cultura -> cultura.getPropriedadeId().equals(propriedadeId)
                    && cultura.getNomeComum().chaveNormalizada().equals(nomeComum.chaveNormalizada())
                    && cultura.getVariedade().chaveNormalizada().equals(variedade.chaveNormalizada()));
        }

        @Override
        public Optional<Cultura> buscarPorNome(PropriedadeId propriedadeId, NomeComumCultura nomeComum) {
            return Optional.ofNullable(culturas.get(chave(propriedadeId, nomeComum)));
        }

        private String chave(PropriedadeId propriedadeId, NomeComumCultura nomeComum) {
            return propriedadeId + "|" + nomeComum.chaveNormalizada();
        }
    }

    static class RelacaoCompatibilidadeRepositorioEmMemoria implements RelacaoCompatibilidadeRepositorio {

        private final List<RelacaoCompatibilidade> relacoes = new ArrayList<>();

        @Override
        public void salvar(RelacaoCompatibilidade relacao) {
            relacoes.add(relacao);
        }

        @Override
        public Optional<RelacaoCompatibilidade> buscarPorCulturas(CulturaId primeiraCulturaId,
                                                                  CulturaId segundaCulturaId) {
            return relacoes.stream()
                .filter(relacao -> relacao.envolve(primeiraCulturaId, segundaCulturaId))
                .findFirst();
        }
    }
}
