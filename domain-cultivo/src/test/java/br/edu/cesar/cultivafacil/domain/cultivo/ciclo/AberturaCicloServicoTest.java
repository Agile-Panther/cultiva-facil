package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AberturaCicloServicoTest {

    private final CicloAgricolaRepositorio repositorio = mock(CicloAgricolaRepositorio.class);
    private final AberturaCicloServico servico = new AberturaCicloServico(repositorio);

    // F-06 RN-051 — sucesso
    @Test
    void deveAbrirCicloQuandoTalhaoSemCicloAtivo() {
        var talhaoId = TalhaoId.novo();
        when(repositorio.buscarAtivoPorTalhao(talhaoId)).thenReturn(Optional.empty());

        var ciclo = servico.abrirCiclo(talhaoId, new NomeCultura("Tomate"),
                new QuantidadePlantada(200.0), UnidadeMedidaCiclo.KG);

        assertEquals(StatusCiclo.ATIVO, ciclo.getStatus());
        verify(repositorio).salvar(ciclo);
    }

    // F-06 RN-051 — falha
    @Test
    void deveRejeitarAberturaQuandoTalhaoJaPossuiCicloAtivo() {
        var talhaoId = TalhaoId.novo();
        var cicloExistente = new CicloAgricola(talhaoId, new NomeCultura("Tomate"),
                new QuantidadePlantada(100.0), UnidadeMedidaCiclo.KG);
        when(repositorio.buscarAtivoPorTalhao(talhaoId)).thenReturn(Optional.of(cicloExistente));

        var ex = assertThrows(IllegalArgumentException.class,
                () -> servico.abrirCiclo(talhaoId, new NomeCultura("Milho"),
                        new QuantidadePlantada(50.0), UnidadeMedidaCiclo.KG));
        assertTrue(ex.getMessage().contains("TALHAO_INVALIDO"));
    }
}
