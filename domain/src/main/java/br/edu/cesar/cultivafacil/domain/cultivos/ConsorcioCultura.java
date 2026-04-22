package br.edu.cesar.cultivafacil.domain.cultivos;

import br.edu.cesar.cultivafacil.shared.ZonaId;
import org.apache.commons.lang3.Validate;

public class ConsorcioCultura {

    private ConsorcioCulturaId id;
    private ZonaId zonaId;
    private NomeCultura cultura;
    private ClassificacaoConsorcio classificacao;
    private boolean cienciaDoAgricultor;
    private boolean encerrado;

    public ConsorcioCultura(ZonaId zonaId, NomeCultura cultura,
                            ClassificacaoConsorcio classificacao, boolean cienciaDoAgricultor) {
        Validate.notNull(zonaId, "zonaId obrigatorio");
        Validate.notNull(cultura, "cultura obrigatoria");
        Validate.notNull(classificacao, "classificacao obrigatoria");
        this.id = ConsorcioCulturaId.novo();
        this.zonaId = zonaId;
        this.cultura = cultura;
        this.classificacao = classificacao;
        this.cienciaDoAgricultor = cienciaDoAgricultor;
        this.encerrado = false;
    }

    public ConsorcioCultura(ConsorcioCulturaId id, ZonaId zonaId, NomeCultura cultura,
                            ClassificacaoConsorcio classificacao, boolean cienciaDoAgricultor, boolean encerrado) {
        Validate.notNull(id, "id obrigatorio");
        Validate.notNull(zonaId, "zonaId obrigatorio");
        Validate.notNull(cultura, "cultura obrigatoria");
        Validate.notNull(classificacao, "classificacao obrigatoria");
        this.id = id;
        this.zonaId = zonaId;
        this.cultura = cultura;
        this.classificacao = classificacao;
        this.cienciaDoAgricultor = cienciaDoAgricultor;
        this.encerrado = encerrado;
    }

    public void encerrar() {
        this.encerrado = true;
    }

    public ConsorcioCulturaId getId() { return id; }
    public ZonaId getZonaId() { return zonaId; }
    public NomeCultura getCultura() { return cultura; }
    public ClassificacaoConsorcio getClassificacao() { return classificacao; }
    public boolean isCienciaDoAgricultor() { return cienciaDoAgricultor; }
    public boolean isEncerrado() { return encerrado; }

    private void setId(ConsorcioCulturaId id) { this.id = id; }
    private void setZonaId(ZonaId zonaId) { this.zonaId = zonaId; }
    private void setCultura(NomeCultura cultura) { this.cultura = cultura; }
    private void setClassificacao(ClassificacaoConsorcio classificacao) { this.classificacao = classificacao; }
    private void setCienciaDoAgricultor(boolean cienciaDoAgricultor) { this.cienciaDoAgricultor = cienciaDoAgricultor; }
}
