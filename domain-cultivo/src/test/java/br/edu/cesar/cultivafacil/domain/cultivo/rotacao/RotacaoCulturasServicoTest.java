package br.edu.cesar.cultivafacil.domain.cultivo.rotacao;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.CicloAgricola;
import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.CicloAgricolaRepositorio;
import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.NomeCultura;
import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.StatusCiclo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RotacaoCulturasServicoTest {

    private CicloAgricolaRepositorio cicloRepositorio;
    private IntervaloDeDescansoRepositorio intervaloRepositorio;
    private RotacaoCulturasServico servico;

    private static final UUID ZONA_ID = UUID.randomUUID();
    private static final NomeCultura TOMATE = new NomeCultura("Tomate");
    private static final NomeCultura ALFACE = new NomeCultura("Alface");

    @BeforeEach
    void setUp() {
        cicloRepositorio = mock(CicloAgricolaRepositorio.class);
        intervaloRepositorio = mock(IntervaloDeDescansoRepositorio.class);
        servico = new RotacaoCulturasServico(cicloRepositorio, intervaloRepositorio);
    }

    @Nested
    @DisplayName("US-15 — Definir Intervalo de Descanso (RN-051)")
    class DefinirIntervaloDescansoTest {

        @Test
        @DisplayName("RN-051: deve permitir definir intervalo com historico de ciclo encerrado")
        void devePermitirDefinirIntervaloComHistorico() {
            CicloAgricola cicloEncerrado = cicloEncerrado(ZONA_ID, TOMATE, LocalDate.now().minusDays(60));
            when(cicloRepositorio.buscarEncerradosPorZonaIdENomeCultura(ZONA_ID, TOMATE))
                    .thenReturn(List.of(cicloEncerrado));

            DiasDescanso dias = new DiasDescanso(30);
            IntervaloDeDescanso resultado = servico.definirIntervaloDeDescanso(ZONA_ID, TOMATE, dias);

            assertNotNull(resultado);
            assertEquals(ZONA_ID, resultado.getZonaId());
            assertEquals(TOMATE, resultado.getNomeCultura());
            assertEquals(dias, resultado.getDiasDescanso());
            verify(intervaloRepositorio).salvar(resultado);
        }

        @Test
        @DisplayName("RN-051: deve rejeitar definir intervalo sem historico de ciclo encerrado")
        void deveRejeitarDefinirIntervaloSemHistorico() {
            when(cicloRepositorio.buscarEncerradosPorZonaIdENomeCultura(ZONA_ID, ALFACE))
                    .thenReturn(Collections.emptyList());

            assertThrows(IntervaloSemHistoricoException.class,
                    () -> servico.definirIntervaloDeDescanso(ZONA_ID, ALFACE, new DiasDescanso(30)));

            verify(intervaloRepositorio, never()).salvar(any());
        }
    }

    @Nested
    @DisplayName("US-16 — Bloqueio por Intervalo de Descanso (RN-053)")
    class BloqueioIntervaloDescansoTest {

        @Test
        @DisplayName("RN-053: deve rejeitar vinculo dentro do intervalo de descanso")
        void deveRejeitarVinculoDentroDoIntervalo() {
            IntervaloDeDescanso intervalo = new IntervaloDeDescanso(ZONA_ID, TOMATE, new DiasDescanso(30));
            when(intervaloRepositorio.buscarPorZonaIdENomeCultura(ZONA_ID, TOMATE))
                    .thenReturn(Optional.of(intervalo));

            CicloAgricola cicloEncerrado = cicloEncerrado(ZONA_ID, TOMATE, LocalDate.now().minusDays(10));
            when(cicloRepositorio.buscarEncerradosPorZonaIdENomeCultura(ZONA_ID, TOMATE))
                    .thenReturn(List.of(cicloEncerrado));

            assertThrows(IntervaloNaoCumpridoException.class,
                    () -> servico.validarIntervaloDeDescanso(ZONA_ID, TOMATE));
        }

        @Test
        @DisplayName("RN-053: deve permitir vinculo apos intervalo de descanso cumprido")
        void devePermitirVinculoAposIntervaloCumprido() {
            IntervaloDeDescanso intervalo = new IntervaloDeDescanso(ZONA_ID, TOMATE, new DiasDescanso(30));
            when(intervaloRepositorio.buscarPorZonaIdENomeCultura(ZONA_ID, TOMATE))
                    .thenReturn(Optional.of(intervalo));

            CicloAgricola cicloEncerrado = cicloEncerrado(ZONA_ID, TOMATE, LocalDate.now().minusDays(40));
            when(cicloRepositorio.buscarEncerradosPorZonaIdENomeCultura(ZONA_ID, TOMATE))
                    .thenReturn(List.of(cicloEncerrado));

            assertDoesNotThrow(() -> servico.validarIntervaloDeDescanso(ZONA_ID, TOMATE));
        }

        @Test
        @DisplayName("RN-053: deve permitir vinculo no dia exato de liberacao")
        void devePermitirVinculoNoDiaExatoDeLiberacao() {
            IntervaloDeDescanso intervalo = new IntervaloDeDescanso(ZONA_ID, TOMATE, new DiasDescanso(30));
            when(intervaloRepositorio.buscarPorZonaIdENomeCultura(ZONA_ID, TOMATE))
                    .thenReturn(Optional.of(intervalo));

            CicloAgricola cicloEncerrado = cicloEncerrado(ZONA_ID, TOMATE, LocalDate.now().minusDays(30));
            when(cicloRepositorio.buscarEncerradosPorZonaIdENomeCultura(ZONA_ID, TOMATE))
                    .thenReturn(List.of(cicloEncerrado));

            assertDoesNotThrow(() -> servico.validarIntervaloDeDescanso(ZONA_ID, TOMATE));
        }

        @Test
        @DisplayName("deve permitir vinculo quando nao ha intervalo definido")
        void devePermitirVinculoQuandoNaoHaIntervalo() {
            when(intervaloRepositorio.buscarPorZonaIdENomeCultura(ZONA_ID, TOMATE))
                    .thenReturn(Optional.empty());

            assertDoesNotThrow(() -> servico.validarIntervaloDeDescanso(ZONA_ID, TOMATE));
        }

        @Test
        @DisplayName("RN-053: intervalo de descanso nao deve bloquear cultura diferente")
        void intervaloNaoDeveBloquearCulturaDiferente() {
            when(intervaloRepositorio.buscarPorZonaIdENomeCultura(ZONA_ID, ALFACE))
                    .thenReturn(Optional.empty());

            assertDoesNotThrow(() -> servico.validarIntervaloDeDescanso(ZONA_ID, ALFACE));
        }

        @Test
        @DisplayName("RN-053: deve considerar ultima colheita quando ha multiplos ciclos")
        void deveConsiderarUltimaColheitaQuandoHaMultiplosCiclos() {
            IntervaloDeDescanso intervalo = new IntervaloDeDescanso(ZONA_ID, TOMATE, new DiasDescanso(20));
            when(intervaloRepositorio.buscarPorZonaIdENomeCultura(ZONA_ID, TOMATE))
                    .thenReturn(Optional.of(intervalo));

            CicloAgricola ciclo1 = cicloEncerrado(ZONA_ID, TOMATE, LocalDate.now().minusDays(60));
            CicloAgricola ciclo2 = cicloEncerrado(ZONA_ID, TOMATE, LocalDate.now().minusDays(10));
            when(cicloRepositorio.buscarEncerradosPorZonaIdENomeCultura(ZONA_ID, TOMATE))
                    .thenReturn(List.of(ciclo1, ciclo2));

            assertThrows(IntervaloNaoCumpridoException.class,
                    () -> servico.validarIntervaloDeDescanso(ZONA_ID, TOMATE));
        }
    }

    private CicloAgricola cicloEncerrado(UUID zonaId, NomeCultura nome, LocalDate dataColheita) {
        return new CicloAgricola(
                UUID.randomUUID(), zonaId, nome,
                dataColheita.minusDays(90), StatusCiclo.ENCERRADO, dataColheita
        );
    }
}
