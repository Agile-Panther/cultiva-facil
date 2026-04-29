package br.edu.cesar.cultivafacil.domain.cultivo.compatibilidade;

import br.edu.cesar.cultivafacil.domain.cultivo.cultura.Cultura;
import br.edu.cesar.cultivafacil.domain.cultivo.cultura.CulturaRepositorio;
import br.edu.cesar.cultivafacil.domain.cultivo.cultura.NomeComumCultura;
import br.edu.cesar.cultivafacil.domain.propriedade.propriedade.PropriedadeId;

import java.util.List;
import java.util.Objects;

public class CompatibilidadeCulturasServico {

    private final CulturaRepositorio culturaRepositorio;
    private final RelacaoCompatibilidadeRepositorio relacaoRepositorio;

    public CompatibilidadeCulturasServico(CulturaRepositorio culturaRepositorio,
                                          RelacaoCompatibilidadeRepositorio relacaoRepositorio) {
        this.culturaRepositorio = Objects.requireNonNull(culturaRepositorio, "culturaRepositorio obrigatorio");
        this.relacaoRepositorio = Objects.requireNonNull(relacaoRepositorio, "relacaoRepositorio obrigatorio");
    }

    public ResultadoCompatibilidade verificar(PropriedadeId propriedadeId, List<String> nomesCulturas) {
        Objects.requireNonNull(propriedadeId, "propriedadeId obrigatoria");
        if (nomesCulturas == null || nomesCulturas.size() != 2) {
            throw new IllegalArgumentException("CULTURA_INVALIDO");
        }

        Cultura primeira = buscarCulturaAtiva(propriedadeId, nomesCulturas.get(0));
        Cultura segunda = buscarCulturaAtiva(propriedadeId, nomesCulturas.get(1));

        if (primeira.getId().equals(segunda.getId())
            || primeira.getNomeComum().chaveNormalizada().equals(segunda.getNomeComum().chaveNormalizada())) {
            throw new IllegalArgumentException("CULTURA_INVALIDO");
        }

        validarFamiliaCustomizada(primeira);
        validarFamiliaCustomizada(segunda);

        return relacaoRepositorio.buscarPorCulturas(primeira.getId(), segunda.getId())
            .map(relacao -> new ResultadoCompatibilidade(relacao.getClassificacao(), relacao.getBeneficioAgronomico()))
            .orElseGet(() -> new ResultadoCompatibilidade(ClassificacaoConsorcio.NEUTRA, ""));
    }

    private Cultura buscarCulturaAtiva(PropriedadeId propriedadeId, String nome) {
        Cultura cultura = culturaRepositorio.buscarPorNome(propriedadeId, new NomeComumCultura(nome))
            .orElseThrow(() -> new IllegalArgumentException("CULTURA_INVALIDO"));
        if (!cultura.ativa()) {
            throw new IllegalArgumentException("CULTURA_INVALIDO");
        }
        return cultura;
    }

    private void validarFamiliaCustomizada(Cultura cultura) {
        if (cultura.customizadaSemFamiliaBotanica()) {
            throw new IllegalArgumentException("CULTURA_INVALIDO");
        }
    }
}
