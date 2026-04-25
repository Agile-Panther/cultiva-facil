package br.edu.cesar.cultivafacil.domain.cultivo.rotacao;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.NomeCultura;
import br.edu.cesar.cultivafacil.domain.terreno.zona.ZonaId;

import java.util.Optional;

public interface IntervalodeDescansoRepositorio {

    void salvar(IntervalodeDescanso intervalo);

    Optional<IntervalodeDescanso> buscarIntervalo(ZonaId zonaId, NomeCultura cultura);

    boolean existeCicloEncerrado(ZonaId zonaId, NomeCultura cultura);
}
