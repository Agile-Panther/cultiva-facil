package br.edu.cesar.cultivafacil.domain.colheita;


import br.edu.cesar.cultivafacil.domain.colheita.repository.CeleiroRepositorio;
import br.edu.cesar.cultivafacil.domain.colheita.repository.ConfiguracaoRelatorioRepositorio;
import br.edu.ifs.cultivafacil.shared.AgricultorId;
import br.edu.ifs.cultivafacil.shared.ZonaId;
import org.apache.commons.lang3.Validate;

public class CeleiroServico {

    private final CeleiroRepositorio celeiroRepositorio;
    private final ConfiguracaoRelatorioRepositorio configuracaoRepositorio;

    public CeleiroServico(CeleiroRepositorio celeiroRepositorio,
                          ConfiguracaoRelatorioRepositorio configuracaoRepositorio) {
        Validate.notNull(celeiroRepositorio, "CeleiroRepositorio não pode ser nulo");
        Validate.notNull(configuracaoRepositorio, "ConfiguracaoRelatorioRepositorio não pode ser nulo");
        this.celeiroRepositorio = celeiroRepositorio;
        this.configuracaoRepositorio = configuracaoRepositorio;
    }

    // RN-07: nome único por Agricultor; RN-09: máximo 5 configurações
    public void salvarConfiguracaoRelatorio(ZonaId zonaId, AgricultorId agricultorId,
                                            String nome, FiltroPeriodo filtroPeriodo) {
        Validate.notNull(zonaId, "ZonaId não pode ser nulo");
        Validate.notNull(agricultorId, "AgricultorId não pode ser nulo");

        configuracaoRepositorio.buscarPorNomeEAgricultorId(nome, agricultorId)
                .ifPresent(c -> { throw new IllegalArgumentException("NOME_CONFIG_DUPLICADO"); });

        long total = configuracaoRepositorio.contarPorAgricultorId(agricultorId);

        Celeiro celeiro = celeiroRepositorio.buscarPorZonaId(zonaId)
                .orElseThrow(() -> new IllegalArgumentException("Celeiro não encontrado para a Zona"));

        celeiro.salvarConfiguracaoRelatorio(agricultorId, nome, filtroPeriodo, total);
        celeiroRepositorio.salvar(celeiro);
    }
}
