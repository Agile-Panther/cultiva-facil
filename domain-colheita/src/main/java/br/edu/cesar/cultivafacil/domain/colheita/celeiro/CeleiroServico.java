package br.edu.cesar.cultivafacil.domain.colheita.celeiro;

import java.util.Objects;

public class CeleiroServico {

    private final CeleiroRepositorio celeiroRepositorio;
    private final ConfiguracaoRelatorioRepositorio configRepositorio;

    public CeleiroServico(CeleiroRepositorio celeiroRepositorio,
                          ConfiguracaoRelatorioRepositorio configRepositorio) {
        Objects.requireNonNull(celeiroRepositorio, "CeleiroRepositorio não pode ser nulo");
        Objects.requireNonNull(configRepositorio, "ConfiguracaoRelatorioRepositorio não pode ser nulo");
        this.celeiroRepositorio = celeiroRepositorio;
        this.configRepositorio = configRepositorio;
    }

    public ConfiguracaoRelatorio buscarConfiguracao(CeleiroId celeiroId, String nome) {
        Objects.requireNonNull(celeiroId, "CeleiroId não pode ser nulo");
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("CONFIGURACAO_INEXISTENTE");
        return configRepositorio.buscarPorCeleiroEhNome(celeiroId, nome)
                .orElseThrow(() -> new IllegalArgumentException("CONFIGURACAO_INEXISTENTE"));
    }

    public Celeiro buscarCeleiro(CeleiroId id) {
        Objects.requireNonNull(id, "CeleiroId não pode ser nulo");
        return celeiroRepositorio.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("CELEIRO_NAO_ENCONTRADO"));
    }

    public void salvarConfiguracao(Celeiro celeiro, String nome, FiltroPeriodo periodo) {
        celeiro.adicionarConfiguracao(nome, periodo);
        celeiroRepositorio.salvar(celeiro);
    }
}
