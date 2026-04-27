package br.edu.cesar.cultivafacil.domain.colheita.colheita;

import java.util.Optional;
import java.util.List;

public interface ColheitaRepositorio {
    void salvar(Colheita colheita);
    Optional<Colheita> buscarPorId(ColheitaId id);
    List<Colheita> listarPorZonaECiclo(String zonaId, String cicloAgricolaId);
}
