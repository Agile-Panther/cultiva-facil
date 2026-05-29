package br.edu.cesar.cultivafacil.domain.cultivo.rotacao;

import br.edu.cesar.cultivafacil.domain.cultivo.ciclo.NomeCultura;
import br.edu.cesar.cultivafacil.domain.terreno.talhao.TalhaoId;

import java.util.Optional;

public interface PoliticaDescansoSoloRepositorio {

    void salvar(PoliticaDescansoSolo politica);

    Optional<PoliticaDescansoSolo> buscarPorTalhaoECultura(TalhaoId talhaoId, NomeCultura cultura);

    boolean existeCicloEncerrado(TalhaoId talhaoId, NomeCultura cultura);
}
