package br.edu.cesar.cultivafacil.domain.terreno.terreno;

import java.util.List;
import java.util.Optional;

/**
 * Pure domain repository interface for Terreno.
 * Implementation lives in the infrastructure layer.
 */
public interface TerrenoRepositorio {

    /**
     * Persists a new or updated Terreno.
     */
    void salvar(Terreno terreno);

    /**
     * Retrieves a Terreno by its ID.
     */
    Optional<Terreno> buscarPorId(TerrenoId id);

    /**
     * Lists all land plots belonging to a given farmer (by AgricultorId as String UUID).
     */
    List<Terreno> listarPorAgricultor(String agricultorId);

    /**
     * Removes a Terreno from persistence.
     * The Application Service must call {@link Terreno#validarPermissaoExclusao()}
     * before invoking this method (RN-037).
     */
    void excluir(TerrenoId id);
}