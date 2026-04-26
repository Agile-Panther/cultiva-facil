package br.edu.cesar.cultivafacil.domain.colheita.celeiro;

import java.util.Objects;
import java.util.UUID;

public class ConfiguracaoRelatorio {

    private final UUID id;
    private final String nome;
    private final FiltroPeriodo periodo;

    public ConfiguracaoRelatorio(String nome, FiltroPeriodo periodo) {
        Objects.requireNonNull(nome, "Nome não pode ser nulo");
        Objects.requireNonNull(periodo, "Período não pode ser nulo");
        if (nome.trim().length() < 2) throw new IllegalArgumentException("NOME_CONFIG_INVALIDO");
        this.id = UUID.randomUUID();
        this.nome = nome.trim();
        this.periodo = periodo;
    }

    public ConfiguracaoRelatorio(UUID id, String nome, FiltroPeriodo periodo) {
        this.id = id;
        this.nome = nome;
        this.periodo = periodo;
    }

    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public FiltroPeriodo getPeriodo() { return periodo; }
}
