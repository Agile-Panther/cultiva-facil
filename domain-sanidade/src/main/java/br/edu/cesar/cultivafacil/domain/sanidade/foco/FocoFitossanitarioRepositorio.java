package br.edu.cesar.cultivafacil.domain.sanidade.foco;

import br.edu.cesar.cultivafacil.domain.sanidade.foco.FocoFitossanitario;
import br.edu.cesar.cultivafacil.domain.sanidade.foco.FocoFitossanitarioId;

import java.util.Optional;

public interface FocoFitossanitarioRepositorio {

    void salvar(FocoFitossanitario foco);

    Optional<FocoFitossanitario> buscarPorId(FocoFitossanitarioId id);
}

