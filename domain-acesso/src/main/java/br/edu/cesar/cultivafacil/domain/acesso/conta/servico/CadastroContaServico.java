package br.edu.cesar.cultivafacil.domain.acesso.conta.servico;

import br.edu.cesar.cultivafacil.domain.acesso.conta.Conta;
import br.edu.cesar.cultivafacil.domain.acesso.conta.ContaRepositorio;
import br.edu.cesar.cultivafacil.domain.acesso.conta.Email;
import br.edu.cesar.cultivafacil.domain.acesso.conta.Senha;
import br.edu.cesar.cultivafacil.domain.evento.EventoBarramento;

public class CadastroContaServico {

    private final ContaRepositorio repositorio;
    private final EventoBarramento barramento;

    public CadastroContaServico(ContaRepositorio repositorio, EventoBarramento barramento) {
        this.repositorio = repositorio;
        this.barramento = barramento;
    }

    public Conta cadastrar(String emailStr, String senhaStr, boolean consentimento) {
        Email email = new Email(emailStr);

        if (repositorio.buscarPorEmail(email).isPresent()) {
            throw new IllegalArgumentException("EMAIL_JA_CADASTRADO");
        }

        if (senhaStr != null && senhaStr.equals(email.getValor())) {
            throw new IllegalArgumentException("SENHA_IGUAL_EMAIL");
        }

        Senha senha = new Senha(senhaStr);
        Conta conta = new Conta(email, senha, consentimento);

        repositorio.salvar(conta);
        barramento.publicar(new Conta.ContaCriada(conta.getId(), email));

        return conta;
    }
}
