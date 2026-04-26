package br.edu.cesar.cultivafacil.domain.cultivo.compatibilidade;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.NomeCultura;

import java.util.Objects;

public class RelacaoCompatibilidade {

    private final RelacaoCompatibilidadeId id;
    private final NomeCultura culturaBase;
    private final NomeCultura culturaRelacionada;
    private final ClassificacaoConsorcio classificacao;

    public RelacaoCompatibilidade(NomeCultura culturaBase, NomeCultura culturaRelacionada,
                                  ClassificacaoConsorcio classificacao) {
        Objects.requireNonNull(culturaBase, "culturaBase obrigatoria");
        Objects.requireNonNull(culturaRelacionada, "culturaRelacionada obrigatoria");
        Objects.requireNonNull(classificacao, "classificacao obrigatoria");
        this.id = RelacaoCompatibilidadeId.novo();
        this.culturaBase = culturaBase;
        this.culturaRelacionada = culturaRelacionada;
        this.classificacao = classificacao;
    }

    public RelacaoCompatibilidade(RelacaoCompatibilidadeId id, NomeCultura culturaBase,
                                  NomeCultura culturaRelacionada, ClassificacaoConsorcio classificacao) {
        Objects.requireNonNull(id, "id obrigatorio");
        Objects.requireNonNull(culturaBase, "culturaBase obrigatoria");
        Objects.requireNonNull(culturaRelacionada, "culturaRelacionada obrigatoria");
        Objects.requireNonNull(classificacao, "classificacao obrigatoria");
        this.id = id;
        this.culturaBase = culturaBase;
        this.culturaRelacionada = culturaRelacionada;
        this.classificacao = classificacao;
    }

    public RelacaoCompatibilidadeId getId() { return id; }
    public NomeCultura getCulturaBase() { return culturaBase; }
    public NomeCultura getCulturaRelacionada() { return culturaRelacionada; }
    public ClassificacaoConsorcio getClassificacao() { return classificacao; }
}
