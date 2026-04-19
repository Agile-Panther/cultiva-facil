package br.edu.ifs.cultivafacil.domain.colheita.repository;


import br.edu.ifs.cultivafacil.domain.colheita.ConfiguracaoRelatorio;

import java.util.List;
import java.util.Optional;

public interface ConfiguracaoRelatorioRepositorio {

    void salvar(ConfiguracaoRelatorio configuracao);

    List<ConfiguracaoRelatorio> buscarPorAgricultorId(AgricultorId agricultorId);

    long contarPorAgricultorId(AgricultorId agricultorId);

    Optional<ConfiguracaoRelatorio> buscarPorNomeEAgricultorId(String nome, AgricultorId agricultorId);
}
