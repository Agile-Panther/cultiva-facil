package br.edu.cesar.cultivafacil.domain.agenda.tarefa;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.CicloAgricolaId;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TarefaTest {

    private TalhaoId talhaoId;
    private CicloAgricolaId cicloId;

    private final LocalDate HOJE = LocalDate.of(2024, 4, 1);
    private final LocalDate INICIO_CICLO = LocalDate.of(2024, 4, 1);
    private final LocalDate FIM_CICLO = LocalDate.of(2024, 6, 30);

    @BeforeEach
    void setUp() {
        talhaoId = TalhaoId.novo();
        cicloId = CicloAgricolaId.novo();
    }

    // ─── Criação manual ────────────────────────────────────────────────────────

    @Test
    void deveCriarTarefaManualComDadosValidos() {
        var nome = new NomeTarefa("Adubação Extra");
        var data = new DataTarefa(LocalDate.of(2024, 4, 20));

        var tarefa = new Tarefa(nome, data, talhaoId, cicloId, HOJE, FIM_CICLO);

        assertNotNull(tarefa.getId());
        assertEquals("Adubação Extra", tarefa.getNome().getValor());
        assertEquals(LocalDate.of(2024, 4, 20), tarefa.getData().getValor());
    }

    @Test
    void deveCriarTarefaManualComDataIgualHoje() {
        var nome = new NomeTarefa("Adubação Extra");
        var data = new DataTarefa(HOJE);

        assertDoesNotThrow(() -> new Tarefa(nome, data, talhaoId, cicloId, HOJE, FIM_CICLO));
    }

    @Test
    void deveCriarTarefaManualComDataIgualFimCiclo() {
        var nome = new NomeTarefa("Tarefa Final");
        var data = new DataTarefa(FIM_CICLO);

        assertDoesNotThrow(() -> new Tarefa(nome, data, talhaoId, cicloId, HOJE, FIM_CICLO));
    }

    // F-09 US-18 RN-056a: data anterior à data atual rejeitada
    @Test
    void deveRejeitarTarefaManualComDataPassada() {
        var nome = new NomeTarefa("Adubação");
        var dataPassada = new DataTarefa(LocalDate.of(2024, 3, 15));

        var ex = assertThrows(IllegalArgumentException.class,
                () -> new Tarefa(nome, dataPassada, talhaoId, cicloId, HOJE, FIM_CICLO));
        assertEquals("DATA_TAREFA_PASSADA", ex.getMessage());
    }

    // F-09 US-18 RN-056b: data posterior ao fim do ciclo rejeitada
    @Test
    void deveRejeitarTarefaManualComDataAposEncerramentoDoCiclo() {
        var nome = new NomeTarefa("Adubação");
        var dataFutura = new DataTarefa(LocalDate.of(2024, 7, 15));

        var ex = assertThrows(IllegalArgumentException.class,
                () -> new Tarefa(nome, dataFutura, talhaoId, cicloId, HOJE, FIM_CICLO));
        assertEquals("DATA_TAREFA_FORA_DO_CICLO", ex.getMessage());
    }

    // F-09 US-18 RN-057: nome inválido para tarefa manual
    @Test
    void deveRejeitarTarefaManualComNomeInvalido() {
        var data = new DataTarefa(LocalDate.of(2024, 4, 20));

        assertThrows(IllegalArgumentException.class,
                () -> new Tarefa(new NomeTarefa(""), data, talhaoId, cicloId, HOJE, FIM_CICLO));
    }

    @Test
    void deveRejeitarTarefaManualComNomeNulo() {
        var data = new DataTarefa(LocalDate.of(2024, 4, 20));

        assertThrows(IllegalArgumentException.class,
                () -> new Tarefa(null, data, talhaoId, cicloId, HOJE, FIM_CICLO));
    }

    // ─── Edição ────────────────────────────────────────────────────────────────

    @Test
    void deveEditarTarefaComDadosValidos() {
        var tarefa = criarTarefaValida();
        var novoNome = new NomeTarefa("Irrigação Matinal");
        var novaData = new DataTarefa(LocalDate.of(2024, 4, 15));

        tarefa.editar(novoNome, novaData, INICIO_CICLO, FIM_CICLO);

        assertEquals("Irrigação Matinal", tarefa.getNome().getValor());
        assertEquals(LocalDate.of(2024, 4, 15), tarefa.getData().getValor());
    }

    @Test
    void deveEditarTarefaComDataNoInicioDoCiclo() {
        var tarefa = criarTarefaValida();
        var novaData = new DataTarefa(INICIO_CICLO);

        assertDoesNotThrow(() -> tarefa.editar(new NomeTarefa("Tarefa Início"), novaData, INICIO_CICLO, FIM_CICLO));
    }

    @Test
    void deveEditarTarefaComDataNoFimDoCiclo() {
        var tarefa = criarTarefaValida();
        var novaData = new DataTarefa(FIM_CICLO);

        assertDoesNotThrow(() -> tarefa.editar(new NomeTarefa("Tarefa Fim"), novaData, INICIO_CICLO, FIM_CICLO));
    }

    // F-09 US-17 RN-054: nome inválido ao editar
    @Test
    void deveRejeitarEdicaoComNomeMuitoCurto() {
        var tarefa = criarTarefaValida();

        var ex = assertThrows(IllegalArgumentException.class,
                () -> tarefa.editar(new NomeTarefa("X"), new DataTarefa(LocalDate.of(2024, 4, 15)), INICIO_CICLO, FIM_CICLO));
        assertEquals("NOME_TAREFA_INVALIDO", ex.getMessage());
    }

    // F-09 US-17 RN-054: nome com mais de 100 chars ao editar
    @Test
    void deveRejeitarEdicaoComNomeMuitoLongo() {
        var tarefa = criarTarefaValida();
        String nome101 = "A".repeat(101);

        var ex = assertThrows(IllegalArgumentException.class,
                () -> tarefa.editar(new NomeTarefa(nome101), new DataTarefa(LocalDate.of(2024, 4, 15)), INICIO_CICLO, FIM_CICLO));
        assertEquals("NOME_TAREFA_INVALIDO", ex.getMessage());
    }

    // F-09 US-17 RN-055: data anterior ao início do ciclo ao editar
    @Test
    void deveRejeitarEdicaoComDataAntesDoInicioDoCiclo() {
        var tarefa = criarTarefaValida();
        var dataAntes = new DataTarefa(LocalDate.of(2024, 3, 31));

        var ex = assertThrows(IllegalArgumentException.class,
                () -> tarefa.editar(new NomeTarefa("Irrigação"), dataAntes, INICIO_CICLO, FIM_CICLO));
        assertEquals("DATA_TAREFA_FORA_DO_CICLO", ex.getMessage());
    }

    // F-09 US-17 RN-055: data posterior ao encerramento do ciclo ao editar
    @Test
    void deveRejeitarEdicaoComDataAposEncerramentoDoCiclo() {
        var tarefa = criarTarefaValida();
        var dataDepois = new DataTarefa(LocalDate.of(2024, 7, 1));

        var ex = assertThrows(IllegalArgumentException.class,
                () -> tarefa.editar(new NomeTarefa("Irrigação"), dataDepois, INICIO_CICLO, FIM_CICLO));
        assertEquals("DATA_TAREFA_FORA_DO_CICLO", ex.getMessage());
    }

    // ─── Exclusão ──────────────────────────────────────────────────────────────

    @Test
    void devePermitirExclusaoQuandoCicloAtivo() {
        var tarefa = criarTarefaValida();
        assertDoesNotThrow(() -> tarefa.validarExclusao(true));
    }

    // F-09 US-19 RN-058: exclusão de tarefa de ciclo encerrado rejeitada
    @Test
    void deveRejeitarExclusaoQuandoCicloEncerrado() {
        var tarefa = criarTarefaValida();

        var ex = assertThrows(IllegalArgumentException.class,
                () -> tarefa.validarExclusao(false));
        assertEquals("TAREFA_CICLO_ENCERRADO", ex.getMessage());
    }

    // ─── Evento de domínio ──────────────────────────────────────────────────────

    @Test
    void devePubiciarEventoTarefaCriadaAoCriarTarefaManual() {
        var nome = new NomeTarefa("Coleta de Solo");
        var data = new DataTarefa(LocalDate.of(2024, 4, 10));
        var tarefa = new Tarefa(nome, data, talhaoId, cicloId, HOJE, FIM_CICLO);

        var evento = tarefa.getEventoCriacao();
        assertNotNull(evento);
        assertEquals(tarefa.getId(), evento.tarefaId());
        assertEquals(cicloId, evento.cicloAgricolaId());
    }

    // ─── Reconstituição ─────────────────────────────────────────────────────────

    @Test
    void deveReconstituirTarefaComTodosOsCampos() {
        var id = new TarefaId(UUID.randomUUID());
        var nome = new NomeTarefa("Irrigação");
        var data = new DataTarefa(LocalDate.of(2024, 5, 1));

        var tarefa = new Tarefa(id, nome, data, talhaoId, cicloId);

        assertEquals(id, tarefa.getId());
        assertEquals("Irrigação", tarefa.getNome().getValor());
        assertEquals(LocalDate.of(2024, 5, 1), tarefa.getData().getValor());
    }

    // ─── helpers ────────────────────────────────────────────────────────────────

    private Tarefa criarTarefaValida() {
        return new Tarefa(
                new NomeTarefa("Irrigação"),
                new DataTarefa(LocalDate.of(2024, 4, 10)),
                talhaoId,
                cicloId,
                HOJE,
                FIM_CICLO
        );
    }
}
