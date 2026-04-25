package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;

import java.util.Optional;

public interface IntervalodeDescansoRepositorio {

    void salvar(IntervalodeDescanso intervalo);

    Optional<IntervalodeDescanso> buscarIntervalo(TalhaoId talhaoId, NomeCultura cultura);

    boolean existeCicloEncerrado(TalhaoId talhaoId, NomeCultura cultura);
}
