package br.edu.cesar.cultivafacil.domain.colheita.repository;


import br.edu.cesar.cultivafacil.domain.colheita.ConfiguracaoRelatorio;
import br.edu.ifs.cultivafacil.shared.AgricultorId;

import java.util.List;
import java.util.Optional;

public interface ConfiguracaoRelatorioRepositorio {

    void salvar(ConfiguracaoRelatorio configuracao);

    List<ConfiguracaoRelatorio> buscarPorAgricultorId(AgricultorId agricultorId);

    long contarPorAgricultorId(AgricultorId agricultorId);

    Optional<ConfiguracaoRelatorio> buscarPorNomeEAgricultorId(String nome, AgricultorId agricultorId);
}
