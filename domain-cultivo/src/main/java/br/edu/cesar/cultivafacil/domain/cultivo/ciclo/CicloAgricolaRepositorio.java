package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;

import java.util.List;
import java.util.Optional;

public interface CicloAgricolaRepositorio {

    void salvar(CicloAgricola ciclo);

    Optional<CicloAgricola> buscarPorId(CicloAgricolaId id);

    List<CicloAgricola> listarPorTalhao(TalhaoId talhaoId);
}
