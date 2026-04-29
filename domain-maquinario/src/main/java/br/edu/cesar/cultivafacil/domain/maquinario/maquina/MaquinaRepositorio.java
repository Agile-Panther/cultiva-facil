package br.edu.cesar.cultivafacil.domain.maquinario.maquina;

import br.edu.cesar.cultivafacil.domain.propriedade.propriedade.PropriedadeId;

import java.util.Optional;

public interface MaquinaRepositorio {

    void salvar(Maquina maquina);

    Optional<Maquina> buscarPorId(MaquinaId id);

    boolean existeIdentificador(IdentificadorFrota identificador, PropriedadeId propriedadeId);
}
