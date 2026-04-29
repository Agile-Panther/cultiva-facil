package br.edu.cesar.cultivafacil.domain.cultivo.compatibilidade;

public class ResultadoCompatibilidade {

    private final ClassificacaoConsorcio classificacao;
    private final String beneficioAgronomico;

    public ResultadoCompatibilidade(ClassificacaoConsorcio classificacao, String beneficioAgronomico) {
        this.classificacao = classificacao;
        this.beneficioAgronomico = beneficioAgronomico;
    }

    public ClassificacaoConsorcio getClassificacao() {
        return classificacao;
    }

    public String getBeneficioAgronomico() {
        return beneficioAgronomico;
    }
}
