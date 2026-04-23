package br.edu.cesar.cultivafacil.domain.cultivo.bdd;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.CicloAgricola;
import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.CicloAgricolaRepositorio;
import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.NomeCultura;
import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.StatusCiclo;
import br.edu.cesar.cultivafacil.domain.cultivo.rotacao.*;

import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.E;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class RotacaoCulturasSteps {

    private UUID zonaId;
    private RotacaoCulturasServico servico;
    private CicloAgricolaRepositorioFake cicloRepositorio;
    private IntervaloDeDescansoRepositorioFake intervaloRepositorio;
    private Exception excecaoCapturada;
    private IntervaloDeDescanso intervaloRegistrado;

    @Before
    public void setUp() {
        zonaId = UUID.randomUUID();
        cicloRepositorio = new CicloAgricolaRepositorioFake();
        intervaloRepositorio = new IntervaloDeDescansoRepositorioFake();
        servico = new RotacaoCulturasServico(cicloRepositorio, intervaloRepositorio);
        excecaoCapturada = null;
        intervaloRegistrado = null;
    }

    // ── Dado ──────────────────────────────────────────────────────────

    @Dado("que existe uma Zona de plantio cadastrada")
    public void queExisteUmaZonaDePlantioCadastrada() {
        // zonaId já inicializado no setUp
    }

    @Dado("que a Zona possui um ciclo encerrado da cultura {string} colhido há {int} dias")
    public void queAZonaPossuiUmCicloEncerradoDaCulturaColhidoHaDias(String cultura, int diasAtras) {
        NomeCultura nome = new NomeCultura(cultura);
        LocalDate dataColheita = LocalDate.now().minusDays(diasAtras);
        CicloAgricola ciclo = new CicloAgricola(
                UUID.randomUUID(), zonaId, nome,
                dataColheita.minusDays(90), StatusCiclo.ENCERRADO, dataColheita
        );
        cicloRepositorio.salvar(ciclo);
    }

    @Dado("que a Zona não possui histórico de ciclo encerrado para {string}")
    public void queAZonaNaoPossuiHistoricoDeCicloEncerradoPara(String cultura) {
        // nenhum ciclo adicionado
    }

    @E("que existe um intervalo de descanso de {int} dias definido para {string}")
    public void queExisteUmIntervaloDeDescansoDeDiasDefinidoPara(int dias, String cultura) {
        NomeCultura nome = new NomeCultura(cultura);
        IntervaloDeDescanso intervalo = new IntervaloDeDescanso(zonaId, nome, new DiasDescanso(dias));
        intervaloRepositorio.salvar(intervalo);
    }

    // ── Quando ────────────────────────────────────────────────────────

    @Quando("o Agricultor define um intervalo de descanso de {int} dias para {string}")
    public void oAgricultorDefineUmIntervaloDeDescansoDeDiasPara(int dias, String cultura) {
        try {
            intervaloRegistrado = servico.definirIntervaloDeDescanso(
                    zonaId, new NomeCultura(cultura), new DiasDescanso(dias));
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    @Quando("o Agricultor tenta definir um intervalo de descanso de {int} dias para {string}")
    public void oAgricultorTentaDefinirUmIntervaloDeDescansoDeDiasPara(int dias, String cultura) {
        try {
            servico.definirIntervaloDeDescanso(zonaId, new NomeCultura(cultura), new DiasDescanso(dias));
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    @Quando("o Agricultor tenta criar um intervalo de descanso de {int} dias")
    public void oAgricultorTentaCriarUmIntervaloDeDescansoDeDias(int dias) {
        try {
            new DiasDescanso(dias);
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    @Quando("o Agricultor tenta vincular {string} à Zona")
    public void oAgricultorTentaVincularACulturaAZona(String cultura) {
        try {
            servico.validarIntervaloDeDescanso(zonaId, new NomeCultura(cultura));
        } catch (Exception e) {
            excecaoCapturada = e;
        }
    }

    // ── Então ─────────────────────────────────────────────────────────

    @Então("o intervalo de descanso é registrado com sucesso")
    public void oIntervaloDeDescansoERegistradoComSucesso() {
        assertNull(excecaoCapturada, "Nenhuma exceção deveria ter sido lançada");
        assertNotNull(intervaloRegistrado);
    }

    @Então("o sistema rejeita com erro {string}")
    public void oSistemaRejeitaComErro(String codigoErro) {
        assertNotNull(excecaoCapturada, "Uma exceção deveria ter sido lançada");
        switch (codigoErro) {
            case "INTERVALO_SEM_HISTORICO" ->
                    assertInstanceOf(IntervaloSemHistoricoException.class, excecaoCapturada);
            case "INTERVALO_NAO_CUMPRIDO" ->
                    assertInstanceOf(IntervaloNaoCumpridoException.class, excecaoCapturada);
            default -> fail("Código de erro desconhecido: " + codigoErro);
        }
    }

    @Então("o sistema rejeita a criação do intervalo")
    public void oSistemaRejeitaACriacaoDoIntervalo() {
        assertNotNull(excecaoCapturada, "Uma exceção deveria ter sido lançada");
        assertInstanceOf(IllegalArgumentException.class, excecaoCapturada);
    }

    @Então("o vínculo é permitido com sucesso")
    public void oVinculoEPermitidoComSucesso() {
        assertNull(excecaoCapturada, "Nenhuma exceção deveria ter sido lançada");
    }

    // ── Fakes (in-memory repositories) ────────────────────────────────

    private static class CicloAgricolaRepositorioFake implements CicloAgricolaRepositorio {
        private final List<CicloAgricola> ciclos = new ArrayList<>();

        @Override
        public void salvar(CicloAgricola ciclo) { ciclos.add(ciclo); }

        @Override
        public Optional<CicloAgricola> buscarPorId(UUID id) {
            return ciclos.stream().filter(c -> c.getId().equals(id)).findFirst();
        }

        @Override
        public List<CicloAgricola> buscarPorZonaId(UUID zonaId) {
            return ciclos.stream().filter(c -> c.getZonaId().equals(zonaId)).toList();
        }

        @Override
        public Optional<CicloAgricola> buscarCicloAtivoPorZonaId(UUID zonaId) {
            return ciclos.stream()
                    .filter(c -> c.getZonaId().equals(zonaId) && c.getStatus() == StatusCiclo.ATIVO)
                    .findFirst();
        }

        @Override
        public List<CicloAgricola> buscarEncerradosPorZonaIdENomeCultura(UUID zonaId, NomeCultura nome) {
            return ciclos.stream()
                    .filter(c -> c.getZonaId().equals(zonaId)
                            && c.getNomeCultura().equals(nome)
                            && c.getStatus() == StatusCiclo.ENCERRADO)
                    .toList();
        }
    }

    private static class IntervaloDeDescansoRepositorioFake implements IntervaloDeDescansoRepositorio {
        private final List<IntervaloDeDescanso> intervalos = new ArrayList<>();

        @Override
        public void salvar(IntervaloDeDescanso intervalo) { intervalos.add(intervalo); }

        @Override
        public Optional<IntervaloDeDescanso> buscarPorZonaIdENomeCultura(UUID zonaId, NomeCultura nome) {
            return intervalos.stream()
                    .filter(i -> i.getZonaId().equals(zonaId) && i.getNomeCultura().equals(nome))
                    .findFirst();
        }
    }
}
