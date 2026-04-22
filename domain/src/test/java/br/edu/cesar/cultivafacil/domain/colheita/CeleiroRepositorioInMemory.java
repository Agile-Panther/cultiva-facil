package br.edu.cesar.cultivafacil.domain.colheita;


import br.edu.cesar.cultivafacil.domain.colheita.repository.CeleiroRepositorio;
import br.edu.cesar.cultivafacil.shared.AgricultorId;
import br.edu.cesar.cultivafacil.shared.ZonaId;

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
