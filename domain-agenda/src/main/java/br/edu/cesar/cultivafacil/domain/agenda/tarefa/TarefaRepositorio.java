package br.edu.cesar.cultivafacil.domain.agenda.tarefa;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.CicloAgricolaId;

import java.util.List;
import java.util.Optional;

public interface TarefaRepositorio {

    void salvar(Tarefa tarefa);

    Optional<Tarefa> buscarPorId(TarefaId id);

    List<Tarefa> listarPorCiclo(CicloAgricolaId cicloAgricolaId);

    void remover(TarefaId id);
}
