package br.edu.cesar.cultivafacil.domain.cultivo.compatibilidade;

import br.edu.cesar.cultivafacil.domain.cultivo.cultura.CulturaId;

import java.util.Objects;

public class RelacaoCompatibilidade {

    private final RelacaoCompatibilidadeId id;
    private final CulturaId primeiraCulturaId;
    private final CulturaId segundaCulturaId;
    private final ClassificacaoConsorcio classificacao;
    private final String beneficioAgronomico;

    public RelacaoCompatibilidade(CulturaId primeiraCulturaId, CulturaId segundaCulturaId,
                                  ClassificacaoConsorcio classificacao, String beneficioAgronomico) {
        this(RelacaoCompatibilidadeId.novo(), primeiraCulturaId, segundaCulturaId,
            classificacao, beneficioAgronomico);
    }

    public RelacaoCompatibilidade(RelacaoCompatibilidadeId id, CulturaId primeiraCulturaId,
                                  CulturaId segundaCulturaId, ClassificacaoConsorcio classificacao,
                                  String beneficioAgronomico) {
        this.id = Objects.requireNonNull(id, "id obrigatorio");
        this.primeiraCulturaId = Objects.requireNonNull(primeiraCulturaId, "primeiraCulturaId obrigatoria");
        this.segundaCulturaId = Objects.requireNonNull(segundaCulturaId, "segundaCulturaId obrigatoria");
        this.classificacao = Objects.requireNonNull(classificacao, "classificacao obrigatoria");
        this.beneficioAgronomico = Objects.requireNonNullElse(beneficioAgronomico, "").trim();
        if (primeiraCulturaId.equals(segundaCulturaId)) {
            throw new IllegalArgumentException("CULTURA_INVALIDO");
        }
    }

    public boolean envolve(CulturaId primeira, CulturaId segunda) {
        return (primeiraCulturaId.equals(primeira) && segundaCulturaId.equals(segunda))
            || (primeiraCulturaId.equals(segunda) && segundaCulturaId.equals(primeira));
    }

    public RelacaoCompatibilidadeId getId() {
        return id;
    }

    public CulturaId getPrimeiraCulturaId() {
        return primeiraCulturaId;
    }

    public CulturaId getSegundaCulturaId() {
        return segundaCulturaId;
    }

    public ClassificacaoConsorcio getClassificacao() {
        return classificacao;
    }

    public String getBeneficioAgronomico() {
        return beneficioAgronomico;
    }
}
