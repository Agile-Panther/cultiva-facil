package br.edu.cesar.cultivafacil.domain.acesso.preferencias.steps;

import br.edu.cesar.cultivafacil.domain.acesso.preferencias.ContagemDiariaNotificacoes;
import br.edu.cesar.cultivafacil.domain.acesso.preferencias.Preferencias;

import java.util.ArrayList;
import java.util.List;

public class MundoTeste {

    private static final MundoTeste INSTANCIA = new MundoTeste();

    public Preferencias preferencias;
    public ContagemDiariaNotificacoes contagemHoje = ContagemDiariaNotificacoes.zerada();
    public Exception erroCapturado;
    public List<Object> eventosPublicados = new ArrayList<>();

    private MundoTeste() {
    }

    public static MundoTeste get() {
        return INSTANCIA;
    }

    public void reset() {
        preferencias = null;
        contagemHoje = ContagemDiariaNotificacoes.zerada();
        erroCapturado = null;
        eventosPublicados = new ArrayList<>();
    }
}
