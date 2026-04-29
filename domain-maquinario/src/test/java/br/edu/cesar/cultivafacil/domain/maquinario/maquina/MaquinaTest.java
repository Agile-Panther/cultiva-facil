package br.edu.cesar.cultivafacil.domain.maquinario.maquina;

import br.edu.cesar.cultivafacil.domain.evento.EventoBarramento;
import br.edu.cesar.cultivafacil.domain.propriedade.propriedade.PropriedadeId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MaquinaTest {

    private PropriedadeId propriedadeId;
    private EventoBarramento barramento;
    private LocalDate hoje;

    @BeforeEach
    void setUp() {
        propriedadeId = PropriedadeId.novo();
        barramento = new EventoBarramento();
        hoje = LocalDate.now();
    }

    private Maquina criarTrator(String placa, double horimetroInicial) {
        return new Maquina(
                propriedadeId,
                TipoMaquinario.TRATOR,
                new ModeloMaquina("John Deere", "5075E"),
                new IdentificadorFrota(placa),
                Horimetro.de(horimetroInicial),
                hoje,
                barramento
        );
    }

    @Test
    void deveCadastrarComStatusDisponivel() {
        Maquina maquina = criarTrator("PLC-001", 0);
        assertEquals(StatusIntegridade.DISPONIVEL, maquina.getStatus());
    }

    @Test
    void deveCadastrarComHorimetroInicial() {
        Maquina maquina = criarTrator("PLC-001", 100.5);
        assertEquals(new BigDecimal("100.5"), maquina.getHorimetro().getValor());
    }

    @Test
    void deveRejeitarHorimetroInicialNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> criarTrator("PLC-001", -1));
    }

    @Test
    void deveAceitarApontamentoComHorimetroMaior() {
        Maquina maquina = criarTrator("PLC-001", 100);
        assertDoesNotThrow(() -> maquina.apontarUso(Horimetro.de(200), hoje));
        assertEquals(0, new BigDecimal("200").compareTo(maquina.getHorimetro().getValor()));
    }

    @Test
    void deveRejeitarApontamentoComHorimetroIgual() {
        Maquina maquina = criarTrator("PLC-001", 100);
        assertThrows(IllegalArgumentException.class,
                () -> maquina.apontarUso(Horimetro.de(100), hoje));
    }

    @Test
    void deveRejeitarApontamentoComHorimetroMenor() {
        Maquina maquina = criarTrator("PLC-001", 200);
        assertThrows(IllegalArgumentException.class,
                () -> maquina.apontarUso(Horimetro.de(100), hoje));
    }

    @Test
    void deveRegistrarHistoricoDeApontamentos() {
        Maquina maquina = criarTrator("PLC-001", 0);
        maquina.apontarUso(Horimetro.de(100), hoje);
        maquina.apontarUso(Horimetro.de(200), hoje.plusDays(10));
        assertEquals(2, maquina.getHistorico().size());
    }

    @Test
    void deveAtualizarPlanoManutencao() {
        Maquina maquina = criarTrator("PLC-001", 0);
        DataEstimadaManutencao data = new DataEstimadaManutencao(hoje.plusDays(30));
        maquina.atualizarPlanoManutencao(data);
        assertNotNull(maquina.getPlano());
        assertEquals(data, maquina.getPlano().getDataEstimada());
    }
}
