package br.edu.cesar.cultivafacil.domain.cultivo.cultura;

import br.edu.cesar.cultivafacil.domain.propriedade.propriedade.PropriedadeId;

import java.util.Objects;
import java.util.Optional;

public class Cultura {

    private final CulturaId id;
    private final PropriedadeId propriedadeId;
    private final NomeComumCultura nomeComum;
    private final Variedade variedade;
    private final FamiliaBotanica familiaBotanica;
    private final OrigemCultura origem;
    private StatusCultura status;

    public static Cultura customizada(PropriedadeId propriedadeId, String nomeComum,
                                      String variedade, String familiaBotanica) {
        return new Cultura(CulturaId.novo(), propriedadeId, new NomeComumCultura(nomeComum),
            new Variedade(variedade), new FamiliaBotanica(familiaBotanica),
            OrigemCultura.CUSTOMIZADA, StatusCultura.ATIVA);
    }

    public static Cultura customizadaSemFamilia(PropriedadeId propriedadeId, String nomeComum, String variedade) {
        return new Cultura(CulturaId.novo(), propriedadeId, new NomeComumCultura(nomeComum),
            new Variedade(variedade), null, OrigemCultura.CUSTOMIZADA, StatusCultura.ATIVA);
    }

    public static Cultura nativa(PropriedadeId propriedadeId, String nomeComum,
                                 String variedade, String familiaBotanica) {
        return new Cultura(CulturaId.novo(), propriedadeId, new NomeComumCultura(nomeComum),
            new Variedade(variedade), new FamiliaBotanica(familiaBotanica),
            OrigemCultura.NATIVA, StatusCultura.ATIVA);
    }

    public Cultura(CulturaId id, PropriedadeId propriedadeId, NomeComumCultura nomeComum,
                   Variedade variedade, FamiliaBotanica familiaBotanica,
                   OrigemCultura origem, StatusCultura status) {
        this.id = Objects.requireNonNull(id, "id obrigatorio");
        this.propriedadeId = Objects.requireNonNull(propriedadeId, "propriedadeId obrigatoria");
        this.nomeComum = Objects.requireNonNull(nomeComum, "nomeComum obrigatorio");
        this.variedade = Objects.requireNonNull(variedade, "variedade obrigatoria");
        this.familiaBotanica = familiaBotanica;
        this.origem = Objects.requireNonNull(origem, "origem obrigatoria");
        this.status = Objects.requireNonNull(status, "status obrigatorio");
    }

    public void ativar() {
        status = StatusCultura.ATIVA;
    }

    public void inativar() {
        status = StatusCultura.INATIVA;
    }

    public boolean ativa() {
        return StatusCultura.ATIVA.equals(status);
    }

    public boolean customizadaSemFamiliaBotanica() {
        return OrigemCultura.CUSTOMIZADA.equals(origem) && familiaBotanica == null;
    }

    public CulturaId getId() {
        return id;
    }

    public PropriedadeId getPropriedadeId() {
        return propriedadeId;
    }

    public NomeComumCultura getNomeComum() {
        return nomeComum;
    }

    public Variedade getVariedade() {
        return variedade;
    }

    public Optional<FamiliaBotanica> getFamiliaBotanica() {
        return Optional.ofNullable(familiaBotanica);
    }

    public OrigemCultura getOrigem() {
        return origem;
    }

    public StatusCultura getStatus() {
        return status;
    }
}
