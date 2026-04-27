package br.edu.cesar.cultivafacil.domain.insumo.plano;

import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

class PlanoInsumosRepositorioEmMemoria implements PlanoInsumosRepositorio {

    private final Map<PlanoInsumosId, PlanoInsumos> storage = new HashMap<>();

    @Override
    public void salvar(PlanoInsumos plano) {
        storage.put(plano.getId(), plano);
    }

    @Override
    public Optional<PlanoInsumos> buscarPorId(PlanoInsumosId id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<PlanoInsumos> listarPorTalhao(TalhaoId talhaoId) {
        return storage.values().stream()
                .filter(p -> p.getTalhaoId().equals(talhaoId))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existePorTalhaoEMes(TalhaoId talhaoId, MesReferencia mes) {
        return storage.values().stream()
                .anyMatch(p -> p.getTalhaoId().equals(talhaoId) && p.getMesReferencia().equals(mes));
    }

    @Override
    public List<PlanoInsumos> listarHistoricoPorTalhao(TalhaoId talhaoId) {
        return storage.values().stream()
                .filter(p -> p.getTalhaoId().equals(talhaoId) && p.isEncerrado())
                .collect(Collectors.toList());
    }
}