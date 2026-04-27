package br.edu.cesar.cultivafacil.domain.agenda.tarefa;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.CicloAgricolaId;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;

import java.time.LocalDate;
import java.util.Objects;

public class Tarefa {

    private final TarefaId id;
    private NomeTarefa nome;
    private DataTarefa data;
    private final TalhaoId talhaoId;
    private final CicloAgricolaId cicloAgricolaId;

    private TarefaCriada eventoCriacao;

    /**
     * Construtor de criação: Tarefa manual (US-18).
     * Valida RN-056a (data não pode ser anterior à dataAtual)
     * e RN-056b (data não pode ser posterior ao dataCicloFim).
     */
    public Tarefa(NomeTarefa nome, DataTarefa data, TalhaoId talhaoId,
                  CicloAgricolaId cicloAgricolaId, LocalDate dataAtual, LocalDate dataCicloFim) {
        if (nome == null) throw new IllegalArgumentException("NOME_TAREFA_INVALIDO");
        Objects.requireNonNull(data, "DATA_TAREFA_INVALIDA");
        Objects.requireNonNull(talhaoId, "TalhaoId obrigatório");
        Objects.requireNonNull(cicloAgricolaId, "CicloAgricolaId obrigatório");
        Objects.requireNonNull(dataAtual, "Data atual obrigatória");
        Objects.requireNonNull(dataCicloFim, "Data fim do ciclo obrigatória");

        if (data.getValor().isBefore(dataAtual)) {
            throw new IllegalArgumentException("DATA_TAREFA_PASSADA");
        }
        if (data.getValor().isAfter(dataCicloFim)) {
            throw new IllegalArgumentException("DATA_TAREFA_FORA_DO_CICLO");
        }

        this.id = TarefaId.novo();
        this.nome = nome;
        this.data = data;
        this.talhaoId = talhaoId;
        this.cicloAgricolaId = cicloAgricolaId;

        this.eventoCriacao = new TarefaCriada(this.id, this.talhaoId, this.cicloAgricolaId);
    }

    /**
     * Construtor de reconstituição: restaura Tarefa já persistida (auto-gerada ou manual).
     */
    public Tarefa(TarefaId id, NomeTarefa nome, DataTarefa data,
                  TalhaoId talhaoId, CicloAgricolaId cicloAgricolaId) {
        Objects.requireNonNull(id, "TarefaId obrigatório");
        Objects.requireNonNull(nome, "NOME_TAREFA_INVALIDO");
        Objects.requireNonNull(data, "DATA_TAREFA_INVALIDA");
        Objects.requireNonNull(talhaoId, "TalhaoId obrigatório");
        Objects.requireNonNull(cicloAgricolaId, "CicloAgricolaId obrigatório");

        this.id = id;
        this.nome = nome;
        this.data = data;
        this.talhaoId = talhaoId;
        this.cicloAgricolaId = cicloAgricolaId;
    }

    /**
     * Edita nome e data da Tarefa dentro do intervalo do ciclo ativo (US-17).
     * Valida RN-054 (nome 2-100 chars via NomeTarefa VO)
     * e RN-055 (data deve estar entre dataCicloInicio e dataCicloFim).
     */
    public void editar(NomeTarefa novoNome, DataTarefa novaData,
                       LocalDate dataCicloInicio, LocalDate dataCicloFim) {
        Objects.requireNonNull(novoNome, "NOME_TAREFA_INVALIDO");
        Objects.requireNonNull(novaData, "DATA_TAREFA_INVALIDA");

        if (novaData.getValor().isBefore(dataCicloInicio) || novaData.getValor().isAfter(dataCicloFim)) {
            throw new IllegalArgumentException("DATA_TAREFA_FORA_DO_CICLO");
        }

        this.nome = novoNome;
        this.data = novaData;
    }

    /**
     * Valida se a Tarefa pode ser excluída (US-19).
     * Valida RN-058: somente Tarefas de ciclo ativo podem ser excluídas.
     */
    public void validarExclusao(boolean cicloAtivo) {
        if (!cicloAtivo) {
            throw new IllegalArgumentException("TAREFA_CICLO_ENCERRADO");
        }
    }

    public TarefaId getId() {
        return id;
    }

    public NomeTarefa getNome() {
        return nome;
    }

    public DataTarefa getData() {
        return data;
    }

    public TalhaoId getTalhaoId() {
        return talhaoId;
    }

    public CicloAgricolaId getCicloAgricolaId() {
        return cicloAgricolaId;
    }

    public TarefaCriada getEventoCriacao() {
        return eventoCriacao;
    }

    // ─── Evento de Domínio ────────────────────────────────────────────────────

    public record TarefaCriada(TarefaId tarefaId, TalhaoId talhaoId, CicloAgricolaId cicloAgricolaId) {
    }
}
