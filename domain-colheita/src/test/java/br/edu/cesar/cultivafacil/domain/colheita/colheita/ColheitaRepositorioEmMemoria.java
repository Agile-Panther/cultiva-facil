package br.edu.cesar.cultivafacil.domain.colheita.colheita;

import java.util.*;

public class ColheitaRepositorioEmMemoria implements ColheitaRepositorio {
    private final Map<ColheitaId, Colheita> banco = new HashMap<>();

    @Override
    public void salvar(Colheita colheita) {
        banco.put(colheita.getId(), colheita);
    }

    @Override
    public Optional<Colheita> buscarPorId(ColheitaId id) {
        return Optional.ofNullable(banco.get(id));
    }

    @Override
    public List<Colheita> listarPorZonaECiclo(String zonaId, String cicloAgricolaId) {
        return new ArrayList<>(banco.values());
    }
}
