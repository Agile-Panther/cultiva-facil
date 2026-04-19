package br.com.cultivafacil.domain.cultivos;

import br.com.cultivafacil.domain.cultivos.exception.IntervaloNaoCumpridoException;
import br.com.cultivafacil.domain.cultivos.exception.IntervaloSemHistoricoException;
import br.com.cultivafacil.domain.cultivos.exception.ZonaComCultivoAtivoException;
import br.com.cultivafacil.domain.cultivos.model.CicloAgricola;
import br.com.cultivafacil.domain.cultivos.model.Zona;
import br.com.cultivafacil.domain.cultivos.vo.DiasDescanso;
import br.com.cultivafacil.domain.cultivos.vo.StatusZona;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ZonaTest {


    @Nested
    @DisplayName("US-12 — Vínculo de Cultura")
    class VinculoCulturaTest {

        @Test
        @DisplayName("deve mudar situação da Zona para ATIVA ao vincular cultura")
        void deveAtivarZonaAoVincularCultura() {
            Zona zona = new Zona();

            zona.vincularCultura("Milho");

            assertEquals(StatusZona.ATIVA, zona.getSituacao());
        }

        @Test
        @DisplayName("deve registrar ciclo agrícola ao vincular cultura")
        void deveRegistrarCicloAoVincularCultura() {
            Zona zona = new Zona();

            zona.vincularCultura("Milho");

            assertEquals(1, zona.getCiclos().size());
            assertEquals("Milho", zona.getCiclos().get(0).getNomeCultura());
        }

        @Test
        @DisplayName("RN-01: deve rejeitar vínculo de cultura em Zona com cultivo ativo")
        void deveRejeitarSegundoCultivoAtivo() {
            Zona zona = new Zona();
            zona.vincularCultura("Milho");

            assertThrows(
                    ZonaComCultivoAtivoException.class,
                    () -> zona.vincularCultura("Feijão")
            );
        }

        @Test
        @DisplayName("RN-01: exceção de zona ocupada deve carregar código ZONA_OCUPADA")
        void excecaoZonaOcupadaDeveCarregarCodigo() {
            Zona zona = new Zona();
            zona.vincularCultura("Milho");

            ZonaComCultivoAtivoException ex = assertThrows(
                    ZonaComCultivoAtivoException.class,
                    () -> zona.vincularCultura("Feijão")
            );

            assertEquals("ZONA_OCUPADA", ex.getCodigo());
        }
    }

    @Nested
    @DisplayName("US-15 — Intervalo de Descanso")
    class IntervaloDescansoTest {

        @Test
        @DisplayName("deve permitir definir intervalo de descanso com histórico encerrado")
        void devePermitirDefinirIntervaloComHistorico() {
            Zona zona = new Zona();
            zona.vincularCultura("Tomate");
            zona.encerrarCiclo("Tomate", LocalDate.now().minusDays(40));

            assertDoesNotThrow(
                    () -> zona.definirIntervaloDescanso("Tomate", new DiasDescanso(30))
            );
        }

        @Test
        @DisplayName("RN-01: deve rejeitar intervalo de descanso sem histórico encerrado")
        void deveRejeitarIntervaloSemHistoricoEncerrado() {
            Zona zona = new Zona();
            zona.vincularCultura("Milho");

            assertThrows(
                    IntervaloSemHistoricoException.class,
                    () -> zona.definirIntervaloDescanso("Milho", new DiasDescanso(30))
            );
        }

        @Test
        @DisplayName("RN-01: deve rejeitar intervalo de descanso para cultura sem nenhum ciclo")
        void deveRejeitarIntervaloParaCulturaSemCiclo() {
            Zona zona = new Zona();

            assertThrows(
                    IntervaloSemHistoricoException.class,
                    () -> zona.definirIntervaloDescanso("Milho", new DiasDescanso(30))
            );
        }

        @Test
        @DisplayName("RN-01: exceção de intervalo sem histórico deve carregar código INTERVALO_SEM_HISTORICO")
        void excecaoIntervaloSemHistoricoDeveCarregarCodigo() {
            Zona zona = new Zona();
            zona.vincularCultura("Milho");

            IntervaloSemHistoricoException ex = assertThrows(
                    IntervaloSemHistoricoException.class,
                    () -> zona.definirIntervaloDescanso("Milho", new DiasDescanso(30))
            );

            assertEquals("INTERVALO_SEM_HISTORICO", ex.getCodigo());
        }
    }

    @Nested
    @DisplayName("US-16 — Bloqueio por Intervalo de Descanso")
    class BloqueioIntervaloDescansoTest {

        @Test
        @DisplayName("deve permitir vínculo após cumprimento do intervalo de descanso")
        void devePermitirVinculoAposIntervaloDeDescanso() {
            Zona zona = new Zona();
            zona.vincularCultura("Tomate");
            zona.encerrarCiclo("Tomate", LocalDate.now().minusDays(35));
            zona.definirIntervaloDescanso("Tomate", new DiasDescanso(30));

            assertDoesNotThrow(() -> zona.vincularCultura("Tomate"));
        }

        @Test
        @DisplayName("deve retornar ao estado ATIVA após novo vínculo com intervalo cumprido")
        void deveAtivarZonaAposNovoVinculo() {
            Zona zona = new Zona();
            zona.vincularCultura("Tomate");
            zona.encerrarCiclo("Tomate", LocalDate.now().minusDays(35));
            zona.definirIntervaloDescanso("Tomate", new DiasDescanso(30));

            zona.vincularCultura("Tomate");

            assertEquals(StatusZona.ATIVA, zona.getSituacao());
        }

        @Test
        @DisplayName("deve registrar dois ciclos após replantar com intervalo cumprido")
        void deveAcumularDoisCiclosAposReplantar() {
            Zona zona = new Zona();
            zona.vincularCultura("Tomate");
            zona.encerrarCiclo("Tomate", LocalDate.now().minusDays(35));
            zona.definirIntervaloDescanso("Tomate", new DiasDescanso(30));

            zona.vincularCultura("Tomate");

            assertEquals(2, zona.getCiclos().size());
        }

        @Test
        @DisplayName("RN-01: deve rejeitar vínculo dentro do intervalo de descanso")
        void deveRejeitarVinculoDentroDoIntervaloDeDescanso() {
            Zona zona = new Zona();
            zona.vincularCultura("Milho");
            zona.encerrarCiclo("Milho", LocalDate.now().minusDays(10));
            zona.definirIntervaloDescanso("Milho", new DiasDescanso(30));

            assertThrows(
                    IntervaloNaoCumpridoException.class,
                    () -> zona.vincularCultura("Milho")
            );
        }

        @Test
        @DisplayName("RN-01: exceção de intervalo não cumprido deve carregar código INTERVALO_NAO_CUMPRIDO")
        void excecaoIntervaloNaoCumpridoDeveCarregarCodigo() {
            Zona zona = new Zona();
            zona.vincularCultura("Milho");
            zona.encerrarCiclo("Milho", LocalDate.now().minusDays(10));
            zona.definirIntervaloDescanso("Milho", new DiasDescanso(30));

            IntervaloNaoCumpridoException ex = assertThrows(
                    IntervaloNaoCumpridoException.class,
                    () -> zona.vincularCultura("Milho")
            );

            assertEquals("INTERVALO_NAO_CUMPRIDO", ex.getCodigo());
        }

        @Test
        @DisplayName("RN-01: deve permitir vínculo exatamente no dia de liberação do intervalo")
        void devePermitirVinculoNoDiaDeLiberacao() {
            Zona zona = new Zona();
            zona.vincularCultura("Milho");
            zona.encerrarCiclo("Milho", LocalDate.now().minusDays(30));
            zona.definirIntervaloDescanso("Milho", new DiasDescanso(30));

            assertDoesNotThrow(() -> zona.vincularCultura("Milho"));
        }

        @Test
        @DisplayName("deve permitir replantar mesma cultura quando nenhum intervalo foi definido")
        void devePermitirReplantioCulturaSemIntervaloCadastrado() {
            Zona zona = new Zona();
            zona.vincularCultura("Milho");
            zona.encerrarCiclo("Milho", LocalDate.now().minusDays(1));

            assertDoesNotThrow(() -> zona.vincularCultura("Milho"));
        }

        @Test
        @DisplayName("RN-01: intervalo de descanso de uma cultura não deve bloquear outra cultura")
        void intervaloDeDescansoNaoDeveBloquearCulturaDistinta() {
            Zona zona = new Zona();
            zona.vincularCultura("Milho");
            zona.encerrarCiclo("Milho", LocalDate.now().minusDays(5));
            zona.definirIntervaloDescanso("Milho", new DiasDescanso(30));

            // Plantar Feijão (cultura diferente) não deve ser bloqueado pelo intervalo do Milho
            assertDoesNotThrow(() -> zona.vincularCultura("Feijão"));
        }
    }

    @Nested
    @DisplayName("Encerramento de Ciclo")
    class EncerramentoCicloTest {

        @Test
        @DisplayName("deve retornar Zona ao estado VAZIA ao encerrar ciclo")
        void deveRetornarZonaVaziaAoEncerrarCiclo() {
            Zona zona = new Zona();
            zona.vincularCultura("Milho");

            zona.encerrarCiclo("Milho", LocalDate.now());

            assertEquals(StatusZona.VAZIA, zona.getSituacao());
        }

        @Test
        @DisplayName("deve registrar data de colheita ao encerrar ciclo")
        void deveRegistrarDataColheitaAoEncerrarCiclo() {
            Zona zona = new Zona();
            zona.vincularCultura("Milho");
            LocalDate dataColheita = LocalDate.now();

            zona.encerrarCiclo("Milho", dataColheita);

            CicloAgricola ciclo = zona.getCiclos().get(0);
            assertEquals(CicloAgricola.StatusCiclo.ENCERRADO, ciclo.getStatus());
            assertEquals(dataColheita, ciclo.getDataColheita());
        }
    }
}