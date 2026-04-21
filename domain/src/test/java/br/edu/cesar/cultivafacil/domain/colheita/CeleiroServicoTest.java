package br.edu.cesar.cultivafacil.domain.colheita;


import br.edu.cesar.cultivafacil.domain.colheita.repository.CeleiroRepositorio;
import br.edu.cesar.cultivafacil.domain.colheita.repository.ConfiguracaoRelatorioRepositorio;
import br.edu.ifs.cultivafacil.shared.AgricultorId;
import br.edu.ifs.cultivafacil.shared.CicloAgricolaId;
import br.edu.ifs.cultivafacil.shared.ZonaId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CeleiroServicoTest {

    @Mock
    private CeleiroRepositorio celeiroRepositorio;

    @Mock
    private ConfiguracaoRelatorioRepositorio configuracaoRepositorio;

    @InjectMocks
    private CeleiroServico servico;

    private ZonaId zonaId;
    private AgricultorId agricultorId;
    private Celeiro celeiro;

    @BeforeEach
    void setUp() {
        zonaId = new ZonaId(UUID.randomUUID());
        agricultorId = new AgricultorId(UUID.randomUUID());
        CicloAgricolaId cicloId = new CicloAgricolaId(UUID.randomUUID());
        celeiro = new Celeiro(zonaId);
        celeiro.inicializarProjecao(cicloId, new BigDecimal("200.00"));
    }

    @Test
    void salvarConfiguracaoRelatorio_comLimiteSemExceder_devePersistir() {
        when(configuracaoRepositorio.buscarPorNomeEAgricultorId("Relatório Trimestral", agricultorId))
                .thenReturn(Optional.empty());
        when(configuracaoRepositorio.contarPorAgricultorId(agricultorId)).thenReturn(0L);
        when(celeiroRepositorio.buscarPorZonaId(zonaId)).thenReturn(Optional.of(celeiro));

        servico.salvarConfiguracaoRelatorio(zonaId, agricultorId, "Relatório Trimestral", FiltroPeriodo.TRIMESTRE);

        verify(celeiroRepositorio).salvar(celeiro);
        assertThat(celeiro.getConfiguracoes()).hasSize(1);
    }

    @Test
    void salvarConfiguracaoRelatorio_comLimiteExcedido_deveRejeitarComLIMITE_CONFIGURACOES_EXCEDIDO() {
        when(configuracaoRepositorio.buscarPorNomeEAgricultorId("Nova Config", agricultorId))
                .thenReturn(Optional.empty());
        when(configuracaoRepositorio.contarPorAgricultorId(agricultorId)).thenReturn(5L);
        when(celeiroRepositorio.buscarPorZonaId(zonaId)).thenReturn(Optional.of(celeiro));

        assertThatThrownBy(() ->
                servico.salvarConfiguracaoRelatorio(zonaId, agricultorId, "Nova Config", FiltroPeriodo.ANO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("LIMITE_CONFIGURACOES_EXCEDIDO");
    }

    @Test
    void salvarConfiguracaoRelatorio_comNomeDuplicado_deveRejeitarComNOME_CONFIG_DUPLICADO() {
        ConfiguracaoRelatorio existente = new ConfiguracaoRelatorio(agricultorId, "Mensal", FiltroPeriodo.ULTIMO_MES);
        when(configuracaoRepositorio.buscarPorNomeEAgricultorId("Mensal", agricultorId))
                .thenReturn(Optional.of(existente));

        assertThatThrownBy(() ->
                servico.salvarConfiguracaoRelatorio(zonaId, agricultorId, "Mensal", FiltroPeriodo.ULTIMO_MES))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("NOME_CONFIG_DUPLICADO");
    }
}
