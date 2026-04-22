package br.edu.cesar.cultivafacil.domain.cultivos;

import br.edu.cesar.cultivafacil.domain.cultivos.repository.RelacaoCompatibilidadeRepositorio;
import br.edu.cesar.cultivafacil.shared.ZonaId;
import org.apache.commons.lang3.Validate;

import java.util.List;
import java.util.Optional;

public class CompatibilidadeCulturasServico {

    private final RelacaoCompatibilidadeRepositorio relacaoRepositorio;

    public CompatibilidadeCulturasServico(RelacaoCompatibilidadeRepositorio relacaoRepositorio) {
        Validate.notNull(relacaoRepositorio, "relacaoRepositorio obrigatorio");
        this.relacaoRepositorio = relacaoRepositorio;
    }

    // RN-047, RN-048, RN-049
    public ConsorcioCultura registrarConsorcio(ZonaId zonaId, NomeCultura culturaAtiva,
                                               NomeCultura culturaNova, boolean consentimento) {
        Validate.notNull(zonaId, "zonaId obrigatorio");
        Validate.notNull(culturaAtiva, "culturaAtiva obrigatoria");
        Validate.notNull(culturaNova, "culturaNova obrigatoria");

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

        ConsorcioCultura consorcio = new ConsorcioCultura(zonaId, culturaNova, classificacao, ciencia);
        relacaoRepositorio.salvarConsorcio(consorcio);
        return consorcio;
    }

    // RN-050
    public List<ConsorcioCultura> consultarHistorico(ZonaId zonaId) {
        Validate.notNull(zonaId, "zonaId obrigatorio");

        List<ConsorcioCultura> historico = relacaoRepositorio.listarConsorcioPorZona(zonaId);
        if (historico.isEmpty()) {
            throw new IllegalStateException("HISTORICO_INEXISTENTE");
        }
        return historico;
    }
}
