package br.edu.cesar.cultivafacil.domain.cultivo.compatibilidade;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.NomeCultura;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CompatibilidadeCulturasServico {

    private final RelacaoCompatibilidadeRepositorio relacaoRepositorio;

    public CompatibilidadeCulturasServico(RelacaoCompatibilidadeRepositorio relacaoRepositorio) {
        Objects.requireNonNull(relacaoRepositorio, "relacaoRepositorio obrigatorio");
        this.relacaoRepositorio = relacaoRepositorio;
    }

    // RN-047, RN-048, RN-049
    public ConsorcioCultura registrarConsorcio(TalhaoId talhaoId, NomeCultura culturaAtiva,
                                               NomeCultura culturaNova, boolean consentimento) {
        Objects.requireNonNull(talhaoId, "talhaoId obrigatorio");
        Objects.requireNonNull(culturaAtiva, "culturaAtiva obrigatoria");
        Objects.requireNonNull(culturaNova, "culturaNova obrigatoria");

        Optional<RelacaoCompatibilidade> relacao = relacaoRepositorio.buscarPorCulturas(culturaAtiva, culturaNova);

        ClassificacaoConsorcio classificacao = ClassificacaoConsorcio.NEUTRA;
        boolean ciencia = false;

        if (relacao.isPresent()) {
            classificacao = relacao.get().getClassificacao();
            if (ClassificacaoConsorcio.INIMIGA.equals(classificacao)) {
                if (!consentimento) {
                    throw new IllegalStateException("INIMIGA_BLOQUEADA");
                }
                ciencia = true;
            }
        }

        ConsorcioCultura consorcio = new ConsorcioCultura(talhaoId, culturaNova, classificacao, ciencia);
        relacaoRepositorio.salvarConsorcio(consorcio);
        return consorcio;
    }

    // RN-050
    public List<ConsorcioCultura> consultarHistorico(TalhaoId talhaoId) {
        Objects.requireNonNull(talhaoId, "talhaoId obrigatorio");

        List<ConsorcioCultura> historico = relacaoRepositorio.listarConsorcioPorTalhao(talhaoId);
        if (historico.isEmpty()) {
            throw new IllegalStateException("HISTORICO_INEXISTENTE");
        }
        return historico;
    }
}
