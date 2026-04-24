package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BDD - F-06 Vinculo e Ciclo de Cultivo")
class CicloAgricolaBddTest {

    @Test
    @DisplayName("Dado que a zona esta vazia e os dados sao validos, quando vincular cultura, entao o ciclo deve iniciar ATIVO")
    void deveIniciarCicloQuandoDadosSaoValidos() {
        UUID zonaId = UUID.randomUUID();
        NomeCultura cultura = new NomeCultura("Tomate");
        QuantidadePlantada quantidade = new QuantidadePlantada(new BigDecimal("200.00"), UnidadeMedidaCiclo.KG);

        CicloAgricola ciclo = new CicloAgricola(zonaId, cultura, quantidade);

        assertNotNull(ciclo.getId());
        assertEquals(zonaId, ciclo.getZonaId());
        assertEquals(StatusCiclo.ATIVO, ciclo.getStatus());
        assertEquals(LocalDate.now(), ciclo.getDataInicio());
        assertEquals(quantidade, ciclo.getQuantidadePlantada());
        assertNull(ciclo.getDataColheita());
    }

    @Test
    @DisplayName("Dado que a quantidade plantada nao foi informada, quando tentar vincular cultura, entao deve rejeitar")
    void deveRejeitarQuandoQuantidadePlantadaNaoForInformada() {
        UUID zonaId = UUID.randomUUID();

        assertThrows(NullPointerException.class,
                () -> new CicloAgricola(zonaId, new NomeCultura("Tomate"), null));
    }

    @Test
    @DisplayName("Dado que a unidade de medida nao foi informada, quando criar quantidade plantada, entao deve rejeitar")
    void deveRejeitarQuandoUnidadeNaoForInformada() {
        assertThrows(NullPointerException.class,
                () -> new QuantidadePlantada(new BigDecimal("10.00"), null));
    }

    @Test
    @DisplayName("Dado que o ciclo foi criado, quando tentar alterar quantidade plantada, entao nao deve haver operacao de mutacao")
    void quantidadePlantadaDeveSerImutavel() {
        CicloAgricola ciclo = new CicloAgricola(
                UUID.randomUUID(),
                new NomeCultura("Milho"),
                new QuantidadePlantada(new BigDecimal("150.00"), UnidadeMedidaCiclo.KG)
        );

        assertEquals(new BigDecimal("150.00"), ciclo.getQuantidadePlantada().getValor());
        assertEquals(UnidadeMedidaCiclo.KG, ciclo.getQuantidadePlantada().getUnidade());
    }
}
