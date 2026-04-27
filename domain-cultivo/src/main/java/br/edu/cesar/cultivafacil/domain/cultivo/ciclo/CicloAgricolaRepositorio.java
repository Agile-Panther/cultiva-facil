package br.edu.cesar.cultivafacil.domain.cultivo.ciclo;

import br.edu.cesar.cultivafacil.domain.terreno.zona.ZonaId;

import java.util.List;
import java.util.Optional;

public interface CicloAgricolaRepositorio {

    void salvar(CicloAgricola ciclo);

    Optional<CicloAgricola> buscarPorId(CicloAgricolaId id);

    Optional<CicloAgricola> buscarAtivoPorZona(ZonaId zonaId);

    List<CicloAgricola> listarPorZona(ZonaId zonaId);
}
