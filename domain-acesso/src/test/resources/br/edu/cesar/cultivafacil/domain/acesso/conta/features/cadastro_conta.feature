# language: pt
Funcionalidade: Cadastro de Conta
  Como Agricultor
  Eu quero criar minha conta com e-mail e senha
  Para ter acesso seguro e personalizado à plataforma

  @F01-US01-POSITIVO
  Cenario: Conta criada com sucesso
    Dado que o Agricultor informa o email "agricultor@fazenda.com" a senha "Senha123" e consentimento verdadeiro
    Quando submete o formulario de cadastro
    Entao a conta e criada com status ativo

  @F01-US01-RN001
  Cenario: Email duplicado rejeitado
    Dado que o email "joao@email.com" ja existe na base de dados
    Quando um novo Agricultor tenta se cadastrar com o mesmo email "joao@email.com" senha "Senha123" e consentimento verdadeiro
    Entao o sistema rejeita o cadastro com erro "EMAIL_JA_CADASTRADO"

  @F01-US01-RN002a
  Cenario: Senha com menos de 8 caracteres rejeitada
    Dado que o Agricultor informa o email "novo@fazenda.com" a senha "abc12" e consentimento verdadeiro
    Quando submete o formulario de cadastro
    Entao o sistema rejeita com erro "SENHA_INVALIDA"

  @F01-US01-RN002b
  Cenario: Senha sem numero rejeitada
    Dado que o Agricultor informa o email "novo@fazenda.com" a senha "abcdefgh" e consentimento verdadeiro
    Quando submete o formulario de cadastro
    Entao o sistema rejeita com erro "SENHA_INVALIDA"

  @F01-US01-RN003
  Cenario: Senha identica ao email rejeitada
    Dado que o Agricultor informa o email "abc@abc.com" a senha "abc@abc.com" e consentimento verdadeiro
    Quando submete o formulario de cadastro
    Entao o sistema rejeita com erro "SENHA_IGUAL_EMAIL"

  @F01-US01-RN004
  Cenario: Cadastro sem consentimento rejeitado
    Dado que o Agricultor informa o email "novo@fazenda.com" a senha "Senha123" e consentimento falso
    Quando submete o formulario de cadastro
    Entao o sistema rejeita com erro "CONSENTIMENTO_AUSENTE"

  @F01-US01-RN002a
  Esquema do Cenario: Senhas invalidas por formato
    Dado que o Agricultor informa o email "test@test.com" a senha <senha> e consentimento verdadeiro
    Quando submete o formulario de cadastro
    Entao o sistema rejeita com erro "SENHA_INVALIDA"

    Exemplos:
      | senha       |
      | "abc12"     |
      | "1234567"   |
      | "abcdefgh"  |
      | "ABCDEFGH"  |
