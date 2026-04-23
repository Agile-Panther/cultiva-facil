package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CicloAgricolaTest {

    private static final UUID ZONA_ID = UUID.randomUUID();
    private static final QuantidadePlantada QTD = new QuantidadePlantada(new BigDecimal("10.00"), UnidadeMedidaCiclo.KG);

    @Nested
    @DisplayName("US-12 — Criação do Ciclo Agrícola (RN-043, RN-044, RN-045)")
    class CriacaoCicloTest {

        @Test
        @DisplayName("RN-043: deve criar ciclo com status ATIVO ao vincular cultura")
        void deveCriarCicloComStatusAtivo() {
            CicloAgricola ciclo = new CicloAgricola(ZONA_ID, new NomeCultura("Tomate"), QTD);

            assertEquals(StatusCiclo.ATIVO, ciclo.getStatus());
            assertNotNull(ciclo.getId());
            assertEquals(ZONA_ID, ciclo.getZonaId());
            assertEquals("Tomate", ciclo.getNomeCultura().getValor());
            assertEquals(QTD, ciclo.getQuantidadePlantada());
            assertEquals(LocalDate.now(), ciclo.getDataInicio());
            assertNull(ciclo.getDataColheita());
        }

        @Test
        @DisplayName("RN-044: deve rejeitar quantidadePlantada nula")
        void deveRejeitarQuantidadePlantadaNula() {
            assertThrows(NullPointerException.class,
                    () -> new CicloAgricola(ZONA_ID, new NomeCultura("Tomate"), null));
        }

        @Test
        @DisplayName("deve rejeitar zonaId nulo")
        void deveRejeitarZonaIdNulo() {
            assertThrows(NullPointerException.class,
                    () -> new CicloAgricola(null, new NomeCultura("Tomate"), QTD));
        }

        @Test
        @DisplayName("deve rejeitar nomeCultura nula")
        void deveRejeitarNomeCulturaNula() {
            assertThrows(NullPointerException.class,
                    () -> new CicloAgricola(ZONA_ID, null, QTD));
        }
    }

    @Nested
    @DisplayName("Encerramento do Ciclo")
    class EncerramentoCicloTest {

        @Test
        @DisplayName("deve encerrar ciclo com data de colheita")
        void deveEncerrarCicloComDataColheita() {
            CicloAgricola ciclo = new CicloAgricola(ZONA_ID, new NomeCultura("Tomate"), QTD);
            LocalDate dataColheita = LocalDate.now();

            ciclo.encerrar(dataColheita);

            assertEquals(StatusCiclo.ENCERRADO, ciclo.getStatus());
            assertEquals(dataColheita, ciclo.getDataColheita());
        }

        @Test
        @DisplayName("deve rejeitar encerramento de ciclo ja encerrado")
        void deveRejeitarEncerramentoDeCicloJaEncerrado() {
            CicloAgricola ciclo = new CicloAgricola(ZONA_ID, new NomeCultura("Tomate"), QTD);
            ciclo.encerrar(LocalDate.now());

            assertThrows(IllegalStateException.class, () -> ciclo.encerrar(LocalDate.now()));
        }

        @Test
        @DisplayName("deve rejeitar data de colheita nula")
        void deveRejeitarDataColheitaNula() {
            CicloAgricola ciclo = new CicloAgricola(ZONA_ID, new NomeCultura("Tomate"), QTD);

            assertThrows(NullPointerException.class, () -> ciclo.encerrar(null));
        }
    }

    @Nested
    @DisplayName("RN-046 — Imutabilidade da quantidade plantada")
    class ImutabilidadeTest {

        @Test
        @DisplayName("RN-046: quantidade plantada nao pode ser alterada apos criacao")
        void quantidadePlantadaImutavelAposCriacao() {
            CicloAgricola ciclo = new CicloAgricola(ZONA_ID, new NomeCultura("Tomate"), QTD);

            // QuantidadePlantada é final no AR — não há setter
            // Verificamos que o valor retornado é o mesmo da criação
            assertEquals(QTD, ciclo.getQuantidadePlantada());
        }
    }

    @Nested
    @DisplayName("Reconstituição do Ciclo")
    class ReconstituicaoTest {

        @Test
        @DisplayName("deve reconstituir ciclo encerrado com todos os campos")
        void deveReconstituirCicloEncerrado() {
            UUID id = UUID.randomUUID();
            LocalDate dataInicio = LocalDate.of(2025, 1, 1);
            LocalDate dataColheita = LocalDate.of(2025, 6, 1);
            NomeCultura nome = new NomeCultura("Alface");

            CicloAgricola ciclo = new CicloAgricola(id, ZONA_ID, nome, QTD,
                    dataInicio, StatusCiclo.ENCERRADO, dataColheita);

            assertEquals(id, ciclo.getId());
            assertEquals(StatusCiclo.ENCERRADO, ciclo.getStatus());
            assertEquals(dataColheita, ciclo.getDataColheita());
        }
    }

    @Nested
    @DisplayName("Domain Events")
    class DomainEventsTest {

        @Test
        @DisplayName("CicloIniciado deve conter dados do ciclo e quantidade plantada")
        void cicloIniciadoDeveConterDados() {
            UUID cicloId = UUID.randomUUID();
            NomeCultura nome = new NomeCultura("Tomate");

            CicloAgricola.CicloIniciado evento = new CicloAgricola.CicloIniciado(cicloId, ZONA_ID, nome, QTD);

            assertEquals(cicloId, evento.getCicloAgricolaId());
            assertEquals(ZONA_ID, evento.getZonaId());
            assertEquals(nome, evento.getNomeCultura());
            assertEquals(QTD, evento.getQuantidadePlantada());
        }

        @Test
        @DisplayName("CicloEncerrado deve conter dados da colheita")
        void cicloEncerradoDeveConterDados() {
            UUID cicloId = UUID.randomUUID();
            LocalDate dataColheita = LocalDate.now();

            CicloAgricola.CicloEncerrado evento = new CicloAgricola.CicloEncerrado(cicloId, ZONA_ID, dataColheita);

            assertEquals(cicloId, evento.getCicloAgricolaId());
            assertEquals(ZONA_ID, evento.getZonaId());
            assertEquals(dataColheita, evento.getDataColheita());
        }
    }
}
