package br.edu.cesar.cultivafacil.domain.maquinario.maquina;

import br.edu.cesar.cultivafacil.domain.evento.EventoBarramento;
import br.edu.cesar.cultivafacil.domain.maquinario.maquina.events.AlertaManutencaoIminente;
import br.edu.cesar.cultivafacil.domain.propriedade.propriedade.PropriedadeId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PrevisaoManutencaoServicoTest {

    @Mock
    private MatrizDesgasteRepositorio matrizRepositorio;

    @InjectMocks
    private PrevisaoManutencaoServico servico;

    private LocalDate hoje;
    private PropriedadeId propriedadeId;
    private EventoBarramento barramento;

    @BeforeEach
    void setUp() {
        hoje = LocalDate.of(2026, 4, 29);
        propriedadeId = PropriedadeId.novo();
        barramento = new EventoBarramento();
    }

    private Maquina maquinaComHorimetro(double horimetro) {
        Maquina m = new Maquina(
                propriedadeId,
                TipoMaquinario.TRATOR,
                new ModeloMaquina("John Deere", "5075E"),
                new IdentificadorFrota("PLC-001"),
                Horimetro.de(0),
                hoje.minusDays(100),
                barramento
        );
        m.apontarUso(Horimetro.de(horimetro), hoje);
        return m;
    }

    private MatrizDesgasteFabricante matrizLimite500h() {
        return new MatrizDesgasteFabricante("John Deere", "5075E", LimiteHorasManutencao.de(500));
    }

    private MatrizDesgasteFabricante matrizLimite250h() {
        return new MatrizDesgasteFabricante("John Deere", "5075E", LimiteHorasManutencao.de(250));
    }

    @Test
    void deveGerarAlertaQuandoFaltarMenosDe50Horas() {
        when(matrizRepositorio.buscarPorModelo(any())).thenReturn(Optional.of(matrizLimite500h()));
        Maquina maquina = maquinaComHorimetro(455);

        Optional<AlertaManutencaoIminente> alerta = servico.avaliar(maquina, hoje);

        assertTrue(alerta.isPresent());
        verify(matrizRepositorio, times(1)).buscarPorModelo(any());
    }

    @Test
    void naoDeveGerarAlertaQuandoFaltaremExatamente50Horas() {
        when(matrizRepositorio.buscarPorModelo(any())).thenReturn(Optional.of(matrizLimite500h()));
        // horimetro=450 em 200 dias -> media=2.25h/dia -> DataEstimada=hoje+23 dias (>15) -> sem alerta de data
        // horasRestantes=50 -> nao < 50 -> sem alerta de horas
        Maquina maquina = new Maquina(
                propriedadeId,
                TipoMaquinario.TRATOR,
                new ModeloMaquina("John Deere", "5075E"),
                new IdentificadorFrota("PLC-005"),
                Horimetro.de(0),
                hoje.minusDays(200),
                barramento
        );
        maquina.apontarUso(Horimetro.de(450), hoje);

        Optional<AlertaManutencaoIminente> alerta = servico.avaliar(maquina, hoje);

        assertFalse(alerta.isPresent());
    }

    @Test
    void deveGerarAlertaQuandoDataEstimadaNosProximos15Dias() {
        when(matrizRepositorio.buscarPorModelo(any())).thenReturn(Optional.of(matrizLimite250h()));
        // Media = 100h/100dias = 1h/dia
        // Horimetro = 237h, restam 13h -> 13 dias < 15 -> alerta
        Maquina maquina = new Maquina(
                propriedadeId,
                TipoMaquinario.TRATOR,
                new ModeloMaquina("John Deere", "5075E"),
                new IdentificadorFrota("PLC-002"),
                Horimetro.de(0),
                hoje.minusDays(100),
                barramento
        );
        maquina.apontarUso(Horimetro.de(100), hoje.minusDays(50));
        maquina.apontarUso(Horimetro.de(237), hoje);

        Optional<AlertaManutencaoIminente> alerta = servico.avaliar(maquina, hoje);

        assertTrue(alerta.isPresent());
    }

    @Test
    void deveRetornarVazioSemMatriz() {
        when(matrizRepositorio.buscarPorModelo(any())).thenReturn(Optional.empty());
        Maquina maquina = maquinaComHorimetro(455);

        Optional<AlertaManutencaoIminente> alerta = servico.avaliar(maquina, hoje);

        assertFalse(alerta.isPresent());
    }

    @Test
    void deveCalcularDataEstimadaCorretamente() {
        when(matrizRepositorio.buscarPorModelo(any())).thenReturn(Optional.of(matrizLimite500h()));
        // 100h usadas em 100 dias -> media = 1h/dia
        // Horimetro atual = 100h, restam 400h -> 400 dias
        Maquina maquina = new Maquina(
                propriedadeId,
                TipoMaquinario.TRATOR,
                new ModeloMaquina("John Deere", "5075E"),
                new IdentificadorFrota("PLC-003"),
                Horimetro.de(0),
                hoje.minusDays(100),
                barramento
        );
        maquina.apontarUso(Horimetro.de(100), hoje);

        Optional<DataEstimadaManutencao> data = servico.calcularDataEstimada(maquina, hoje);

        assertTrue(data.isPresent());
        assertEquals(hoje.plusDays(400), data.get().getValor());
    }

    @Test
    void deveRetornarVazioQuandoSemHistorico() {
        when(matrizRepositorio.buscarPorModelo(any())).thenReturn(Optional.of(matrizLimite500h()));
        Maquina maquina = new Maquina(
                propriedadeId,
                TipoMaquinario.TRATOR,
                new ModeloMaquina("John Deere", "5075E"),
                new IdentificadorFrota("PLC-004"),
                Horimetro.de(0),
                hoje,
                barramento
        );

        Optional<DataEstimadaManutencao> data = servico.calcularDataEstimada(maquina, hoje);

        assertFalse(data.isPresent());
    }
}
