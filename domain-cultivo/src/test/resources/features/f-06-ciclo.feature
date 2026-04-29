# language: pt

Funcionalidade: Vinculo e Ciclo de Cultivo
  Como Proprietario ou Gestor
  Eu quero vincular uma cultura a um Talhao declarando a quantidade plantada
  Para iniciar um novo Ciclo Agricola e acompanhar a evolucao produtiva

  @F06-positivo
  Cenario: Ciclo Agricola aberto com sucesso
    Dado que o Talhao esta livre e sem Ciclo ativo
    Quando o Proprietario vincula a cultura "Tomate" com quantidade 200.0 e unidade "KG"
    Entao o Ciclo Agricola e criado com status ATIVO

  @F06-RN051-falha-talhao-com-ciclo-ativo
  Cenario: Abertura rejeitada em Talhao com Ciclo ativo
    Dado que o Talhao ja possui um Ciclo ativo de "Tomate"
    Quando o Proprietario tenta vincular a cultura "Milho" ao mesmo Talhao
    Entao o sistema rejeita com erro "TALHAO_INVALIDO"

  @F06-RN052-falha-quantidade-zero
  Cenario: Quantidade plantada zero rejeitada
    Quando o Proprietario tenta criar quantidade plantada com valor "0"
    Entao o sistema rejeita quantidade com erro "QUANTIDADE_INVALIDA"

  @F06-RN052-falha-quantidade-mais-de-3-casas
  Cenario: Quantidade plantada com mais de 3 casas decimais rejeitada
    Quando o Proprietario tenta criar quantidade plantada com valor "10.1234"
    Entao o sistema rejeita quantidade com erro "QUANTIDADE_INVALIDA"

  @F06-RN055-cancelamento-sucesso
  Cenario: Ciclo cancelado com justificativa valida
    Dado que existe um Ciclo Agricola ativo de "Tomate"
    Quando o Proprietario cancela o Ciclo com justificativa "O solo apresentou contaminacao e nao e seguro continuar"
    Entao o Ciclo Agricola fica com status CANCELADO

  @F06-RN055-falha-justificativa-curta
  Cenario: Cancelamento rejeitado com justificativa curta
    Dado que existe um Ciclo Agricola ativo de "Tomate"
    Quando o Proprietario tenta cancelar o Ciclo com justificativa "curto"
    Entao o sistema rejeita com erro "JUSTIFICATIVA_INVALIDA"

  @F06-RN056-falha-cancelar-ciclo-encerrado
  Cenario: Cancelamento rejeitado para Ciclo encerrado
    Dado que existe um Ciclo Agricola encerrado de "Tomate"
    Quando o Proprietario tenta cancelar o Ciclo com justificativa "O solo apresentou contaminacao e nao e seguro continuar"
    Entao o sistema rejeita com erro "CICLO_INVALIDO"
