package br.edu.cesar.cultivafacil.domain.cultivo.compatibilidade;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.NomeCultura;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;

import java.util.Objects;

public class ConsorcioCultura {

    private final ConsorcioCulturaId id;
    private final TalhaoId talhaoId;
    private final NomeCultura cultura;
    private final ClassificacaoConsorcio classificacao;
    private final boolean cienciaDoAgricultor;
    private boolean encerrado;

    public ConsorcioCultura(TalhaoId talhaoId, NomeCultura cultura,
                            ClassificacaoConsorcio classificacao, boolean cienciaDoAgricultor) {
        Objects.requireNonNull(talhaoId, "talhaoId obrigatorio");
        Objects.requireNonNull(cultura, "cultura obrigatoria");
        Objects.requireNonNull(classificacao, "classificacao obrigatoria");
        this.id = ConsorcioCulturaId.novo();
        this.talhaoId = talhaoId;
        this.cultura = cultura;
        this.classificacao = classificacao;
        this.cienciaDoAgricultor = cienciaDoAgricultor;
        this.encerrado = false;
    }

    public ConsorcioCultura(ConsorcioCulturaId id, TalhaoId talhaoId, NomeCultura cultura,
                            ClassificacaoConsorcio classificacao, boolean cienciaDoAgricultor, boolean encerrado) {
        Objects.requireNonNull(id, "id obrigatorio");
        Objects.requireNonNull(talhaoId, "talhaoId obrigatorio");
        Objects.requireNonNull(cultura, "cultura obrigatoria");
        Objects.requireNonNull(classificacao, "classificacao obrigatoria");
        this.id = id;
        this.talhaoId = talhaoId;
        this.cultura = cultura;
        this.classificacao = classificacao;
        this.cienciaDoAgricultor = cienciaDoAgricultor;
        this.encerrado = encerrado;
    }

    public void encerrar() {
        this.encerrado = true;
    }

    public ConsorcioCulturaId getId() { return id; }
    public TalhaoId getTalhaoId() { return talhaoId; }
    public NomeCultura getCultura() { return cultura; }
    public ClassificacaoConsorcio getClassificacao() { return classificacao; }
    public boolean isCienciaDoAgricultor() { return cienciaDoAgricultor; }
    public boolean isEncerrado() { return encerrado; }
}
