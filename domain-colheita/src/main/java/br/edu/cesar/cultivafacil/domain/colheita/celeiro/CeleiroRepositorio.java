package br.edu.cesar.cultivafacil.domain.colheita.celeiro;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.CicloAgricolaId;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;

import java.util.Optional;

public interface CeleiroRepositorio {

    void salvar(Celeiro celeiro);

    Optional<Celeiro> buscarPorId(CeleiroId id);

    Optional<Celeiro> buscarPorCiclo(CicloAgricolaId cicloAgricolaId);

    Optional<Celeiro> buscarCicloAtivoPorTalhao(TalhaoId talhaoId);
}
