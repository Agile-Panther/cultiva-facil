package br.edu.cesar.cultivafacil.domain.colheita.celeiro;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.CicloAgricolaId;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CeleiroRepositorioInMemory implements CeleiroRepositorio {

    private final Map<CeleiroId, Celeiro> store = new HashMap<>();

    @Override
    public void salvar(Celeiro celeiro) {
        store.put(celeiro.getId(), celeiro);
    }

    @Override
    public Optional<Celeiro> buscarPorId(CeleiroId id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Celeiro> buscarPorCiclo(CicloAgricolaId cicloAgricolaId) {
        return store.values().stream()
                .filter(c -> c.getCicloAgricolaId().equals(cicloAgricolaId))
                .findFirst();
    }

    @Override
    public Optional<Celeiro> buscarCicloAtivoPorTalhao(TalhaoId talhaoId) {
        return store.values().stream()
                .filter(c -> c.getTalhaoId().equals(talhaoId) && c.isCicloAtivo())
                .findFirst();
    }
}
