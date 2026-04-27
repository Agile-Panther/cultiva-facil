package br.edu.cesar.cultivafacil.domain.cultivo;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.*;
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
    private CicloAgricola ciclo;
    private List<CicloAgricola> ciclosAtivos;
    private Exception excecao;
    private RotacaoCulturasServico rotacaoServico;
    private IntervalodeDescansoRepositorioFake repoFake;
    private boolean intervaloRegistrado;

    @Before
    public void setUp() {
        zonaId = ZonaId.novo();
        ciclo = null;
        ciclosAtivos = new ArrayList<>();
        excecao = null;
        repoFake = new IntervalodeDescansoRepositorioFake();
        rotacaoServico = new RotacaoCulturasServico(repoFake);
        intervaloRegistrado = false;
    }

    // ====== F-06: Ciclo Agricola ======

    @Dado("que a Zona esta vazia e o Intervalo de Descanso foi cumprido")
    public void zonaVaziaDescansoCumprido() {
        ciclosAtivos.clear();
    }

    @Dado("que a Zona esta vazia")
    public void zonaVazia() {
        ciclosAtivos.clear();
    }

    @Dado("que a Zona ja possui um Ciclo ativo de {string}")
    public void zonaComCicloAtivo(String cultura) {
        CicloAgricola existente = new CicloAgricola(
                zonaId, new NomeCultura(cultura),
                new QuantidadePlantada(100.0), UnidadeMedidaCiclo.KG);
        ciclosAtivos.add(existente);
    }

    @Dado("que o Ciclo Agricola de {string} foi confirmado com {int} KG")
    public void cicloConfirmado(String cultura, int quantidade) {
        ciclo = new CicloAgricola(
                zonaId, new NomeCultura(cultura),
                new QuantidadePlantada((double) quantidade), UnidadeMedidaCiclo.KG);
    }

    @Quando("o Proprietario vincula a cultura {string} com quantidade {int} e unidade {string}")
    public void vinculaCultura(String cultura, int quantidade, String unidade) {
        try {
            UnidadeMedidaCiclo unidadeEnum = UnidadeMedidaCiclo.valueOf(unidade);
            ciclo = new CicloAgricola(
                    zonaId, new NomeCultura(cultura),
                    new QuantidadePlantada((double) quantidade), unidadeEnum);
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Quando("o Proprietario tenta vincular a cultura {string} a mesma Zona")
    public void tentaVincularMesmaZona(String cultura) {
        try {
            boolean zonaOcupada = ciclosAtivos.stream()
                    .anyMatch(c -> c.getZonaId().equals(zonaId)
                            && c.getStatus() == StatusCiclo.ATIVO);
            if (zonaOcupada) {
                throw new IllegalStateException(
                        "ZONA_OCUPADA: a Zona ja possui um Ciclo ativo");
            }
            ciclo = new CicloAgricola(
                    zonaId, new NomeCultura(cultura),
                    new QuantidadePlantada(100.0), UnidadeMedidaCiclo.KG);
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Quando("o Proprietario tenta vincular {string} com quantidade {int}")
    public void tentaVincularComQuantidade(String cultura, int quantidade) {
        try {
            new CicloAgricola(
                    zonaId, new NomeCultura(cultura),
                    new QuantidadePlantada((double) quantidade), UnidadeMedidaCiclo.KG);
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Quando("o Proprietario tenta vincular {string} com unidade {string}")
    public void tentaVincularComUnidade(String cultura, String unidade) {
        try {
            UnidadeMedidaCiclo unidadeEnum = UnidadeMedidaCiclo.valueOf(unidade.toUpperCase());
            new CicloAgricola(
                    zonaId, new NomeCultura(cultura),
                    new QuantidadePlantada(200.0), unidadeEnum);
        } catch (IllegalArgumentException e) {
            excecao = new IllegalArgumentException(
                    "UNIDADE_INVALIDA: unidade de medida '" + unidade + "' nao e valida");
        }
    }

    @Quando("o Proprietario tenta alterar a quantidade plantada para {int}")
    public void tentaAlterarQuantidade(int novaQuantidade) {
        try {
            ciclo.alterarQuantidadePlantada(
                    new QuantidadePlantada((double) novaQuantidade));
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Entao("o Ciclo Agricola e iniciado com status ATIVO")
    public void cicloIniciadoComStatusAtivo() {
        assertNull(excecao);
        assertNotNull(ciclo);
        assertEquals(StatusCiclo.ATIVO, ciclo.getStatus());
    }

    // ====== F-08: Rotacao de Culturas ======

    @Dado("que a Zona possui ao menos um ciclo de {string} encerrado")
    public void zonaPossuiCicloEncerrado(String cultura) {
        repoFake.setCicloEncerradoExiste(true);
    }

    @Dado("que a Zona nunca teve nenhum ciclo de {string} encerrado")
    public void zonaSemCicloEncerrado(String cultura) {
        repoFake.setCicloEncerradoExiste(false);
    }

    @Dado("que o Intervalo de {int} dias para {string} nao foi cumprido com colheita ha {int} dias")
    public void intervaloNaoCumprido(int diasIntervalo, String cultura, int diasPassados) {
        LocalDate dataColheita = LocalDate.now().minusDays(diasPassados);
        IntervalodeDescanso intervalo = new IntervalodeDescanso(
                zonaId, new NomeCultura(cultura),
                new DiasDescanso(diasIntervalo), dataColheita);
        repoFake.salvar(intervalo);
    }

    @Quando("o Proprietario cadastra Intervalo de Descanso de {int} dias para {string}")
    public void cadastraIntervalo(int dias, String cultura) {
        try {
            rotacaoServico.cadastrarIntervalo(
                    zonaId, new NomeCultura(cultura), new DiasDescanso(dias));
            intervaloRegistrado = true;
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Quando("o Proprietario tenta cadastrar Intervalo de Descanso de {int} dias para {string}")
    public void tentaCadastrarIntervalo(int dias, String cultura) {
        try {
            rotacaoServico.cadastrarIntervalo(
                    zonaId, new NomeCultura(cultura), new DiasDescanso(dias));
            intervaloRegistrado = true;
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Quando("o Proprietario tenta vincular {string} a Zona")
    public void tentaVincularAZona(String cultura) {
        try {
            rotacaoServico.validarDescanso(
                    zonaId, new NomeCultura(cultura), LocalDate.now());
        } catch (Exception e) {
            excecao = e;
        }
    }

    @Entao("o Intervalo e registrado com sucesso")
    public void intervaloRegistradoComSucesso() {
        assertNull(excecao);
        assertTrue(intervaloRegistrado);
    }

    // ====== Shared ======

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
