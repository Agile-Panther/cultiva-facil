package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CicloAgricolaTest {

    private static final UUID ZONA_ID = UUID.randomUUID();

    @Nested
    @DisplayName("Criação do Ciclo Agrícola")
    class CriacaoCicloTest {

        @Test
        @DisplayName("deve criar ciclo com status ATIVO")
        void deveCriarCicloComStatusAtivo() {
            CicloAgricola ciclo = new CicloAgricola(ZONA_ID, new NomeCultura("Tomate"));

            assertEquals(StatusCiclo.ATIVO, ciclo.getStatus());
            assertNotNull(ciclo.getId());
            assertEquals(ZONA_ID, ciclo.getZonaId());
            assertEquals("Tomate", ciclo.getNomeCultura().getValor());
            assertEquals(LocalDate.now(), ciclo.getDataInicio());
            assertNull(ciclo.getDataColheita());
        }

        @Test
        @DisplayName("deve rejeitar zonaId nulo")
        void deveRejeitarZonaIdNulo() {
            assertThrows(NullPointerException.class,
                    () -> new CicloAgricola(null, new NomeCultura("Tomate")));
        }

        @Test
        @DisplayName("deve rejeitar nomeCultura nula")
        void deveRejeitarNomeCulturaNula() {
            assertThrows(NullPointerException.class,
                    () -> new CicloAgricola(ZONA_ID, null));
        }
    }

    @Nested
    @DisplayName("Encerramento do Ciclo")
    class EncerramentoCicloTest {

        @Test
        @DisplayName("deve encerrar ciclo com data de colheita")
        void deveEncerrarCicloComDataColheita() {
            CicloAgricola ciclo = new CicloAgricola(ZONA_ID, new NomeCultura("Tomate"));
            LocalDate dataColheita = LocalDate.now();

            ciclo.encerrar(dataColheita);

            assertEquals(StatusCiclo.ENCERRADO, ciclo.getStatus());
            assertEquals(dataColheita, ciclo.getDataColheita());
        }

        @Test
        @DisplayName("deve rejeitar encerramento de ciclo ja encerrado")
        void deveRejeitarEncerramentoDeCicloJaEncerrado() {
            CicloAgricola ciclo = new CicloAgricola(ZONA_ID, new NomeCultura("Tomate"));
            ciclo.encerrar(LocalDate.now());

            assertThrows(IllegalStateException.class,
                    () -> ciclo.encerrar(LocalDate.now()));
        }

        @Test
        @DisplayName("deve rejeitar data de colheita nula")
        void deveRejeitarDataColheitaNula() {
            CicloAgricola ciclo = new CicloAgricola(ZONA_ID, new NomeCultura("Tomate"));

            assertThrows(NullPointerException.class,
                    () -> ciclo.encerrar(null));
        }
    }

    @Nested
    @DisplayName("Reconstituição do Ciclo")
    class ReconstituicaoCicloTest {

        @Test
        @DisplayName("deve reconstituir ciclo encerrado com todos os campos")
        void deveReconstituirCicloEncerradoComTodosOsCampos() {
            UUID id = UUID.randomUUID();
            LocalDate dataInicio = LocalDate.of(2025, 1, 1);
            LocalDate dataColheita = LocalDate.of(2025, 6, 1);
            NomeCultura nome = new NomeCultura("Alface");

            CicloAgricola ciclo = new CicloAgricola(id, ZONA_ID, nome,
                    dataInicio, StatusCiclo.ENCERRADO, dataColheita);

            assertEquals(id, ciclo.getId());
            assertEquals(ZONA_ID, ciclo.getZonaId());
            assertEquals(nome, ciclo.getNomeCultura());
            assertEquals(dataInicio, ciclo.getDataInicio());
            assertEquals(StatusCiclo.ENCERRADO, ciclo.getStatus());
            assertEquals(dataColheita, ciclo.getDataColheita());
        }
    }

    @Nested
    @DisplayName("Domain Events")
    class DomainEventsTest {

        @Test
        @DisplayName("CicloIniciado deve conter dados do ciclo")
        void cicloIniciadoDeveConterDadosDoCiclo() {
            UUID cicloId = UUID.randomUUID();
            NomeCultura nome = new NomeCultura("Tomate");

            CicloAgricola.CicloIniciado evento = new CicloAgricola.CicloIniciado(cicloId, ZONA_ID, nome);

            assertEquals(cicloId, evento.getCicloAgricolaId());
            assertEquals(ZONA_ID, evento.getZonaId());
            assertEquals(nome, evento.getNomeCultura());
        }

        @Test
        @DisplayName("CicloEncerrado deve conter dados da colheita")
        void cicloEncerradoDeveConterDadosDaColheita() {
            UUID cicloId = UUID.randomUUID();
            LocalDate dataColheita = LocalDate.now();

            CicloAgricola.CicloEncerrado evento = new CicloAgricola.CicloEncerrado(cicloId, ZONA_ID, dataColheita);

            assertEquals(cicloId, evento.getCicloAgricolaId());
            assertEquals(ZONA_ID, evento.getZonaId());
            assertEquals(dataColheita, evento.getDataColheita());
        }
    }
}
