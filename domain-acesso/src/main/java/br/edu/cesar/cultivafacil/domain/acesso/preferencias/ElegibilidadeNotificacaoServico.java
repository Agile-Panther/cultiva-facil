package br.edu.cesar.cultivafacil.domain.acesso.preferencias;

public class ElegibilidadeNotificacaoServico {

    public boolean podeEnviar(TipoNotificacao tipo, int notificacoesHoje) {
        if (tipo == TipoNotificacao.ALERTA_CRITICO) {
            return true;
        }
        return !LimiteNotificacaoDiaria.excedido(notificacoesHoje);
    }
}
