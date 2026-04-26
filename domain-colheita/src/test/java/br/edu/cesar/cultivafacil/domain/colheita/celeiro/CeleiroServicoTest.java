package br.edu.cesar.cultivafacil.domain.colheita.celeiro;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.CicloAgricolaId;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CeleiroServicoTest {

    @Mock
    private CeleiroRepositorio celeiroRepositorio;

    @Mock
    private ConfiguracaoRelatorioRepositorio configRepositorio;

    @InjectMocks
    private CeleiroServico servico;

    // RN-101: consulta sem configuracao persistida rejeitada
    @Test
    void deveRejeitarConsultaSemConfiguracaoPersistida() {
        var celeiroId = CeleiroId.novo();
        when(configRepositorio.buscarPorCeleiroEhNome(celeiroId, "Safra Verao 2026"))
                .thenReturn(Optional.empty());

        var ex = assertThrows(IllegalArgumentException.class,
                () -> servico.buscarConfiguracao(celeiroId, "Safra Verao 2026"));
        assertEquals("CONFIGURACAO_INEXISTENTE", ex.getMessage());

        verify(configRepositorio, times(1)).buscarPorCeleiroEhNome(celeiroId, "Safra Verao 2026");
    }

    // US-27: retorna configuracao quando existe
    @Test
    void deveRetornarConfiguracaoExistente() {
        var celeiroId = CeleiroId.novo();
        var config = new ConfiguracaoRelatorio("Safra Verao 2026", FiltroPeriodo.SEMESTRE);
        when(configRepositorio.buscarPorCeleiroEhNome(celeiroId, "Safra Verao 2026"))
                .thenReturn(Optional.of(config));

        var resultado = servico.buscarConfiguracao(celeiroId, "Safra Verao 2026");

        assertNotNull(resultado);
        assertEquals("Safra Verao 2026", resultado.getNome());
        assertEquals(FiltroPeriodo.SEMESTRE, resultado.getPeriodo());
    }

    // buscarCeleiro: lanca excecao quando nao encontrado
    @Test
    void deveRejeitarBuscaDeCeleiroInexistente() {
        var id = CeleiroId.novo();
        when(celeiroRepositorio.buscarPorId(id)).thenReturn(Optional.empty());

        var ex = assertThrows(IllegalArgumentException.class, () -> servico.buscarCeleiro(id));
        assertEquals("CELEIRO_NAO_ENCONTRADO", ex.getMessage());

        verify(celeiroRepositorio, times(1)).buscarPorId(id);
    }

    // salvarConfiguracao: delega ao Celeiro e persiste
    @Test
    void deveSalvarConfiguracaoEPersistirCeleiro() {
        var talhaoId = new TalhaoId(UUID.randomUUID());
        var cicloId = new CicloAgricolaId(UUID.randomUUID());
        var celeiro = new Celeiro(talhaoId, cicloId, new ItemCeleiro("Tomate", 100));

        servico.salvarConfiguracao(celeiro, "Safra 2026", FiltroPeriodo.ANO);

        assertEquals(1, celeiro.getConfiguracoes().size());
        verify(celeiroRepositorio, times(1)).salvar(celeiro);
    }
}
