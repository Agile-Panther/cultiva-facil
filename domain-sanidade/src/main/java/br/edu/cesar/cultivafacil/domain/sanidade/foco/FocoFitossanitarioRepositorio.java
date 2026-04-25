package br.edu.cesar.cultivafacil.domain.sanidade.foco;

import br.com.cultivafacil.domain.manejo.model.FocoFitossanitario;
import br.com.cultivafacil.domain.manejo.vo.FocoFitossanitarioId;

import java.util.Optional;

public interface FocoFitossanitarioRepositorio {

    void salvar(FocoFitossanitario foco);

    Optional<FocoFitossanitario> buscarPorId(FocoFitossanitarioId id);
}

