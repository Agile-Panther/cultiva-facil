package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Integracao - CicloAgricola + Repositorio em memoria")
class CicloAgricolaIntegracaoTest {

    @Test
    @DisplayName("deve salvar ciclo ativo e recuperar por zona")
    void deveSalvarECarregarCicloAtivoPorZona() {
        InMemoryCicloAgricolaRepositorio repositorio = new InMemoryCicloAgricolaRepositorio();
        UUID zonaId = UUID.randomUUID();

        CicloAgricola ciclo = new CicloAgricola(
                zonaId,
                new NomeCultura("Tomate"),
                new QuantidadePlantada(new BigDecimal("200.00"), UnidadeMedidaCiclo.KG)
        );

        repositorio.salvar(ciclo);

        Optional<CicloAgricola> cicloAtivo = repositorio.buscarCicloAtivoPorZonaId(zonaId);
        assertTrue(cicloAtivo.isPresent());
        assertEquals(StatusCiclo.ATIVO, cicloAtivo.get().getStatus());
        assertEquals("Tomate", cicloAtivo.get().getNomeCultura().getValor());
    }

    @Test
    @DisplayName("deve encerrar ciclo e deixar de retornar ciclo ativo")
    void deveEncerrarCicloERemoverDaBuscaDeAtivo() {
        InMemoryCicloAgricolaRepositorio repositorio = new InMemoryCicloAgricolaRepositorio();
        UUID zonaId = UUID.randomUUID();

        CicloAgricola ciclo = new CicloAgricola(
                zonaId,
                new NomeCultura("Milho"),
                new QuantidadePlantada(new BigDecimal("100.00"), UnidadeMedidaCiclo.KG)
        );

        repositorio.salvar(ciclo);
        ciclo.encerrar(LocalDate.now());
        repositorio.salvar(ciclo);

        assertTrue(repositorio.buscarCicloAtivoPorZonaId(zonaId).isEmpty());

        List<CicloAgricola> historico = repositorio.buscarPorZonaId(zonaId);
        assertEquals(1, historico.size());
        assertEquals(StatusCiclo.ENCERRADO, historico.get(0).getStatus());
        assertNotNull(historico.get(0).getDataColheita());
    }

    private static final class InMemoryCicloAgricolaRepositorio implements CicloAgricolaRepositorio {

        private final Map<UUID, CicloAgricola> porId = new LinkedHashMap<>();

        @Override
        public void salvar(CicloAgricola ciclo) {
            porId.put(ciclo.getId(), ciclo);
        }

        @Override
        public Optional<CicloAgricola> buscarPorId(UUID id) {
            return Optional.ofNullable(porId.get(id));
        }

        @Override
        public List<CicloAgricola> buscarPorZonaId(UUID zonaId) {
            List<CicloAgricola> ciclos = new ArrayList<>();
            for (CicloAgricola ciclo : porId.values()) {
                if (ciclo.getZonaId().equals(zonaId)) {
                    ciclos.add(ciclo);
                }
            }
            return ciclos;
        }

        @Override
        public Optional<CicloAgricola> buscarCicloAtivoPorZonaId(UUID zonaId) {
            for (CicloAgricola ciclo : porId.values()) {
                if (ciclo.getZonaId().equals(zonaId) && ciclo.getStatus() == StatusCiclo.ATIVO) {
                    return Optional.of(ciclo);
                }
            }
            return Optional.empty();
        }
    }
}
