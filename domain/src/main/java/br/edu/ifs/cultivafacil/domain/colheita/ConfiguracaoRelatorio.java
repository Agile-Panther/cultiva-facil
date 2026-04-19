package br.edu.ifs.cultivafacil.domain.colheita;


import org.apache.commons.lang3.Validate;
import java.util.UUID;

public class ConfiguracaoRelatorio {

    private UUID id;
    private AgricultorId agricultorId;
    private String nome;
    private FiltroPeriodo filtroPeriodo;

    public ConfiguracaoRelatorio(AgricultorId agricultorId, String nome, FiltroPeriodo filtroPeriodo) {
        Validate.notNull(agricultorId, "AgricultorId não pode ser nulo");
        Validate.notNull(filtroPeriodo, "FiltroPeriodo não pode ser nulo");
        // RN-07: nome entre 2 e 100 chars
        Validate.notBlank(nome, "Nome da configuração não pode ser vazio");
        Validate.isTrue(nome.trim().length() >= 2 && nome.trim().length() <= 100, "NOME_CONFIG_INVALIDO");
        this.id = UUID.randomUUID();
        this.agricultorId = agricultorId;
        this.nome = nome.trim();
        this.filtroPeriodo = filtroPeriodo;
    }

    public ConfiguracaoRelatorio(UUID id, AgricultorId agricultorId, String nome, FiltroPeriodo filtroPeriodo) {
        Validate.notNull(id, "ID não pode ser nulo");
        Validate.notNull(agricultorId, "AgricultorId não pode ser nulo");
        Validate.notNull(filtroPeriodo, "FiltroPeriodo não pode ser nulo");
        Validate.notBlank(nome, "Nome não pode ser vazio");
        this.id = id;
        this.agricultorId = agricultorId;
        this.nome = nome.trim();
        this.filtroPeriodo = filtroPeriodo;
    }

    public UUID getId() { return id; }
    public AgricultorId getAgricultorId() { return agricultorId; }
    public String getNome() { return nome; }
    public FiltroPeriodo getFiltroPeriodo() { return filtroPeriodo; }
}
