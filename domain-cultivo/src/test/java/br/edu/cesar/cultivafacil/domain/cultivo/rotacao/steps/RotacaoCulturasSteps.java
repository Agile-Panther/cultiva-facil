package br.edu.cesar.cultivafacil.domain.cultivo.rotacao.steps;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.NomeCultura;
import br.edu.cesar.cultivafacil.domain.cultivo.rotacao.*;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class RotacaoCulturasSteps {

    private TalhaoId talhaoId;
    private Exception excecao;
    private RotacaoCulturasServico rotacaoServico;
    private PoliticaDescansoSoloRepositorioFake repoFake;
    private boolean intervaloRegistrado;

    @Before
    public void setUp() {
        talhaoId = TalhaoId.novo();
        excecao = null;
        repoFake = new PoliticaDescansoSoloRepositorioFake();
        rotacaoServico = new RotacaoCulturasServico(repoFake);
        intervaloRegistrado = false;
    }

    // ====== Dado ======

    @Dado("que o Talhão possui ao menos um ciclo de {string} encerrado")
    public void talhaoPossuiCicloEncerrado(String cultura) {
        repoFake.setCicloEncerradoExiste(true);
    }

    @Dado("que o Talhão nunca teve nenhum ciclo de {string} encerrado")
    public void talhaoSemCicloEncerrado(String cultura) {
        repoFake.setCicloEncerradoExiste(false);
    }

    @E("que o Intervalo de {int} dias para {string} já foi cumprido")
    public void intervaloJaFoiCumprido(int diasIntervalo, String cultura) {
        LocalDate dataReferencia = LocalDate.now().minusDays(diasIntervalo + 10);
        PoliticaDescansoSolo politica = new PoliticaDescansoSolo(
                talhaoId, new NomeCultura(cultura),
                new DiasDescanso(diasIntervalo), dataReferencia);
        repoFake.salvar(politica);
    }

    @E("que o Intervalo de {int} dias para {string} ainda não foi cumprido")
    public void intervaloNaoCumprido(int diasIntervalo, String cultura) {
        LocalDate dataReferencia = LocalDate.now().minusDays(diasIntervalo - 10);
        PoliticaDescansoSolo politica = new PoliticaDescansoSolo(
                talhaoId, new NomeCultura(cultura),
                new DiasDescanso(diasIntervalo), dataReferencia);
        repoFake.salvar(politica);
    }

    // ====== Quando ======

    @Quando("o Proprietário cadastra Intervalo de Descanso de {int} dias para {string}")
    public void cadastraIntervalo(int dias, String cultura) {
        try {
            rotacaoServico.cadastrarIntervalo(
                    talhaoId, new NomeCultura(cultura), new DiasDescanso(dias));
            intervaloRegistrado = true;
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Quando("o Proprietário tenta cadastrar Intervalo de Descanso de {int} dias para {string}")
    public void tentaCadastrarIntervalo(int dias, String cultura) {
        try {
            rotacaoServico.cadastrarIntervalo(
                    talhaoId, new NomeCultura(cultura), new DiasDescanso(dias));
            intervaloRegistrado = true;
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Quando("o Proprietário tenta conceder dispensa para {string} com justificativa válida")
    public void tentaConcederDispensaComJustificativaValida(String cultura) {
        try {
            var justificativa = new JustificativaDispensa(
                    "Esta eh uma justificativa valida com mais de vinte caracteres para dispensar o descanso");
            rotacaoServico.concederDispensa(
                    talhaoId, new NomeCultura(cultura), justificativa, LocalDate.now());
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Quando("o Proprietário tenta conceder dispensa para {string} com justificativa {string}")
    public void tentaConcederDispensaComJustificativa(String cultura, String justificativa) {
        try {
            var j = new JustificativaDispensa(justificativa);
            rotacaoServico.concederDispensa(
                    talhaoId, new NomeCultura(cultura), j, LocalDate.now());
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Quando("o Proprietário concede dispensa para {string} com justificativa válida")
    public void concedeDispensaComJustificativaValida(String cultura) {
        try {
            var justificativa = new JustificativaDispensa(
                    "Esta eh uma justificativa valida com mais de vinte caracteres para dispensar o descanso");
            rotacaoServico.concederDispensa(
                    talhaoId, new NomeCultura(cultura), justificativa, LocalDate.now());
        } catch (Exception e) {
            excecao = e;
        }
    }

    // ====== Então ======

    @Entao("o Intervalo é registrado com sucesso")
    public void intervaloRegistradoComSucesso() {
        assertNull(excecao, "Esperava cadastro bem-sucedido mas obteve: "
                + (excecao != null ? excecao.getMessage() : ""));
        assertTrue(intervaloRegistrado, "O Intervalo deveria ter sido registrado");
    }

    @Entao("a Dispensa é concedida com sucesso")
    public void dispensaConcedidaComSucesso() {
        assertNull(excecao, "Esperava dispensa bem-sucedida mas obteve: "
                + (excecao != null ? excecao.getMessage() : ""));
    }

    @Entao("o sistema rejeita o cadastro com erro {string}")
    public void sistemaRejeitaCadastroComErro(String codigoErro) {
        assertNotNull(excecao, "Esperava rejeicao do cadastro mas nenhuma excecao foi lancada");
        assertTrue(excecao.getMessage().contains(codigoErro),
                "Esperava erro contendo '" + codigoErro
                        + "' mas obteve: " + excecao.getMessage());
    }

    @Entao("o sistema rejeita a dispensa com erro {string}")
    public void sistemaRejeitaDispensaComErro(String codigoErro) {
        assertNotNull(excecao, "Esperava rejeicao da dispensa mas nenhuma excecao foi lancada");
        assertTrue(excecao.getMessage().contains(codigoErro),
                "Esperava erro contendo '" + codigoErro
                        + "' mas obteve: " + excecao.getMessage());
    }

    // ====== Fake Repository ======

    private static class PoliticaDescansoSoloRepositorioFake
            implements PoliticaDescansoSoloRepositorio {

        private final List<PoliticaDescansoSolo> politicas = new ArrayList<>();
        private boolean cicloEncerradoExiste = false;

        void setCicloEncerradoExiste(boolean existe) {
            this.cicloEncerradoExiste = existe;
        }

        @Override
        public void salvar(PoliticaDescansoSolo politica) {
            politicas.add(politica);
        }

        @Override
        public Optional<PoliticaDescansoSolo> buscarPorTalhaoECultura(TalhaoId talhaoId, NomeCultura cultura) {
            return politicas.stream()
                    .filter(p -> p.getTalhaoId().equals(talhaoId)
                            && p.getCultura().equals(cultura))
                    .findFirst();
        }

        @Override
        public boolean existeCicloEncerrado(TalhaoId talhaoId, NomeCultura cultura) {
            return cicloEncerradoExiste;
        }
    }
}
