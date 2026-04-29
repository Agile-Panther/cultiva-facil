package br.edu.cesar.cultivafacil.domain.propriedade.propriedade;

import br.edu.cesar.cultivafacil.shared.ContaId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryPropriedadeRepositorio implements PropriedadeRepositorio {

    private final Map<PropriedadeId, Propriedade> propriedades = new HashMap<>();

    @Override
    public void salvar(Propriedade propriedade) {
        propriedades.put(propriedade.getId(), propriedade);
    }

    @Override
    public Optional<Propriedade> buscarPorId(PropriedadeId id) {
        return Optional.ofNullable(propriedades.get(id));
    }

    @Override
    public Optional<Propriedade> buscarPorContaId(ContaId contaId) {
        return propriedades.values().stream()
                .filter(propriedade -> propriedade.getContaId().equals(contaId))
                .findFirst();
    }

    @Override
    public boolean existePorContaId(ContaId contaId) {
        return buscarPorContaId(contaId).isPresent();
    }
}
