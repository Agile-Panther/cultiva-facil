package br.edu.cesar.cultivafacil.domain.colheita.celeiro;

import java.util.Optional;

public interface ConfiguracaoRelatorioRepositorio {

    void salvar(ConfiguracaoRelatorio configuracao, CeleiroId celeiroId);

    Optional<ConfiguracaoRelatorio> buscarPorCeleiroEhNome(CeleiroId celeiroId, String nome);

    int contarPorCeleiro(CeleiroId celeiroId);
}
