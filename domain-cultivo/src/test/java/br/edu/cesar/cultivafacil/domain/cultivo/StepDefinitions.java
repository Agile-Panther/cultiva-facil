package br.edu.cesar.cultivafacil.domain.cultivo;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.NomeCultura;
import br.edu.cesar.cultivafacil.domain.cultivo.rotacao.*;
import br.edu.cesar.cultivafacil.domain.terreno.zona.ZonaId;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinitions {

    private ZonaId zonaId;
    private Exception excecao;
    private RotacaoCulturasServico rotacaoServico;
    private IntervalodeDescansoRepositorioFake repoFake;
    private boolean intervaloRegistrado;

    @Before
    public void setUp() {
        zonaId = ZonaId.novo();
        excecao = null;
        repoFake = new IntervalodeDescansoRepositorioFake();
        rotacaoServico = new RotacaoCulturasServico(repoFake);
        intervaloRegistrado = false;
    }

    // ====== Dado ======

    @Dado("que a Zona possui ao menos um ciclo de {string} encerrado")
    public void zonaPossuiCicloEncerrado(String cultura) {
        repoFake.setCicloEncerradoExiste(true);
    }

    @Dado("que a Zona nunca teve nenhum ciclo de {string} encerrado")
    public void zonaSemCicloEncerrado(String cultura) {
        repoFake.setCicloEncerradoExiste(false);
    }

    @Dado("que o Intervalo de {int} dias para {string} não foi cumprido com colheita há {int} dias")
    public void intervaloNaoCumprido(int diasIntervalo, String cultura, int diasPassados) {
        LocalDate dataColheita = LocalDate.now().minusDays(diasPassados);
        IntervalodeDescanso intervalo = new IntervalodeDescanso(
                zonaId, new NomeCultura(cultura),
                new DiasDescanso(diasIntervalo), dataColheita);
        repoFake.salvar(intervalo);
    }

    // ====== Quando ======

    @Quando("o Proprietário cadastra Intervalo de Descanso de {int} dias para {string}")
    public void cadastraIntervalo(int dias, String cultura) {
        try {
            rotacaoServico.cadastrarIntervalo(
                    zonaId, new NomeCultura(cultura), new DiasDescanso(dias));
            intervaloRegistrado = true;
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Quando("o Proprietário tenta cadastrar Intervalo de Descanso de {int} dias para {string}")
    public void tentaCadastrarIntervalo(int dias, String cultura) {
        try {
            rotacaoServico.cadastrarIntervalo(
                    zonaId, new NomeCultura(cultura), new DiasDescanso(dias));
            intervaloRegistrado = true;
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Quando("o Proprietário tenta vincular {string} à Zona")
    public void tentaVincularAZona(String cultura) {
        try {
            rotacaoServico.validarDescanso(
                    zonaId, new NomeCultura(cultura), LocalDate.now());
        } catch (Exception e) {
            excecao = e;
        }
    }

    // ====== Então ======

    @Entao("o Intervalo é registrado com sucesso")
    public void intervaloRegistradoComSucesso() {
        assertNull(excecao);
        assertTrue(intervaloRegistrado);
    }

    @Entao("o sistema rejeita com erro {string}")
    public void sistemaRejeitaComErro(String codigoErro) {
        assertNotNull(excecao, "Esperava uma excecao mas nenhuma foi lancada");
        assertTrue(excecao.getMessage().contains(codigoErro),
                "Esperava erro contendo '" + codigoErro
                        + "' mas obteve: " + excecao.getMessage());
    }

    // ====== Fake Repository ======

    private static class IntervalodeDescansoRepositorioFake
            implements IntervalodeDescansoRepositorio {

        private final List<IntervalodeDescanso> intervalos = new ArrayList<>();
        private boolean cicloEncerradoExiste = false;

        void setCicloEncerradoExiste(boolean existe) {
            this.cicloEncerradoExiste = existe;
        }

        @Override
        public void salvar(IntervalodeDescanso intervalo) {
            intervalos.add(intervalo);
        }

        @Override
        public Optional<IntervalodeDescanso> buscarIntervalo(ZonaId zonaId, NomeCultura cultura) {
            return intervalos.stream()
                    .filter(i -> i.getZonaId().equals(zonaId)
                            && i.getCultura().equals(cultura))
                    .findFirst();
        }

        @Override
        public boolean existeCicloEncerrado(ZonaId zonaId, NomeCultura cultura) {
            return cicloEncerradoExiste;
        }
    }
}
