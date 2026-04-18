package br.com.cultivafacil.domain.manejo.repository;

import br.com.cultivafacil.domain.manejo.model.FocoFitossanitario;
import br.com.cultivafacil.domain.manejo.vo.FocoFitossanitarioId;

import java.util.Optional;

public interface FocoFitossanitarioRepositorio {

    void salvar(FocoFitossanitario foco);

    Optional<FocoFitossanitario> buscarPorId(FocoFitossanitarioId id);
}

