package br.edu.cesar.cultivafacil.domain.cultivo.cultura;

import br.edu.cesar.cultivafacil.domain.propriedade.propriedade.PropriedadeId;

import java.util.Objects;

public class CatalogoCulturaServico {

    private final CulturaRepositorio culturaRepositorio;

    public CatalogoCulturaServico(CulturaRepositorio culturaRepositorio) {
        this.culturaRepositorio = Objects.requireNonNull(culturaRepositorio, "culturaRepositorio obrigatorio");
    }

    public Cultura cadastrarCustomizada(PropriedadeId propriedadeId, String nomeComum,
                                        String variedade, String familiaBotanica) {
        Objects.requireNonNull(propriedadeId, "propriedadeId obrigatoria");
        NomeComumCultura nome = new NomeComumCultura(nomeComum);
        Variedade variedadeCultura = new Variedade(variedade);

        if (culturaRepositorio.existePorNomeEVariedade(propriedadeId, nome, variedadeCultura)) {
            throw new IllegalArgumentException(
                "NOME_INVALIDO: combinação de nome e variedade já existe no catálogo");
        }

        Cultura cultura = Cultura.customizada(propriedadeId, nome.getValor(), variedadeCultura.getValor(), familiaBotanica);
        culturaRepositorio.salvar(cultura);
        return cultura;
    }
}
