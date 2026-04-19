package br.edu.ifs.cultivafacil.domain.colheita;


import br.edu.ifs.cultivafacil.domain.colheita.repository.CeleiroRepositorio;

import java.util.*;

public class CeleiroRepositorioInMemory implements CeleiroRepositorio {

    private final Map<CeleiroId, Celeiro> store = new HashMap<>();

    @Override
    public void salvar(Celeiro celeiro) {
        store.put(celeiro.getId(), celeiro);
    }

    @Override
    public Optional<Celeiro> buscarPorZonaId(ZonaId zonaId) {
        return store.values().stream()
                .filter(c -> c.getZonaId().equals(zonaId))
                .findFirst();
    }

    @Override
    public List<Celeiro> buscarPorPropriedadeId(AgricultorId agricultorId) {
        return new ArrayList<>(store.values());
    }
}
