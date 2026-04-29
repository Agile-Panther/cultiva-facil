package br.edu.cesar.cultivafacil.domain.maquinario.maquina;

import java.util.Optional;

public interface MatrizDesgasteRepositorio {

    Optional<MatrizDesgasteFabricante> buscarPorModelo(ModeloMaquina modelo);
}
