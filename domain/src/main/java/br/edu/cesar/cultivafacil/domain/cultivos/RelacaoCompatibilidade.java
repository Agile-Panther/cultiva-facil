package br.edu.cesar.cultivafacil.domain.cultivos;

import org.apache.commons.lang3.Validate;

public class RelacaoCompatibilidade {

    private RelacaoCompatibilidadeId id;
    private NomeCultura culturaBase;
    private NomeCultura culturaRelacionada;
    private ClassificacaoConsorcio classificacao;

    public RelacaoCompatibilidade(NomeCultura culturaBase, NomeCultura culturaRelacionada,
                                  ClassificacaoConsorcio classificacao) {
        Validate.notNull(culturaBase, "culturaBase obrigatoria");
        Validate.notNull(culturaRelacionada, "culturaRelacionada obrigatoria");
        Validate.notNull(classificacao, "classificacao obrigatoria");
        this.id = RelacaoCompatibilidadeId.novo();
        this.culturaBase = culturaBase;
        this.culturaRelacionada = culturaRelacionada;
        this.classificacao = classificacao;
    }

    public RelacaoCompatibilidade(RelacaoCompatibilidadeId id, NomeCultura culturaBase,
                                  NomeCultura culturaRelacionada, ClassificacaoConsorcio classificacao) {
        Validate.notNull(id, "id obrigatorio");
        Validate.notNull(culturaBase, "culturaBase obrigatoria");
        Validate.notNull(culturaRelacionada, "culturaRelacionada obrigatoria");
        Validate.notNull(classificacao, "classificacao obrigatoria");
        this.id = id;
        this.culturaBase = culturaBase;
        this.culturaRelacionada = culturaRelacionada;
        this.classificacao = classificacao;
    }

    public RelacaoCompatibilidadeId getId() { return id; }
    public NomeCultura getCulturaBase() { return culturaBase; }
    public NomeCultura getCulturaRelacionada() { return culturaRelacionada; }
    public ClassificacaoConsorcio getClassificacao() { return classificacao; }

    private void setId(RelacaoCompatibilidadeId id) { this.id = id; }
    private void setCulturaBase(NomeCultura culturaBase) { this.culturaBase = culturaBase; }
    private void setCulturaRelacionada(NomeCultura culturaRelacionada) { this.culturaRelacionada = culturaRelacionada; }
    private void setClassificacao(ClassificacaoConsorcio classificacao) { this.classificacao = classificacao; }
}
