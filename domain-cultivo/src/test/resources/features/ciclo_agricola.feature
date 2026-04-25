# language: pt

Funcionalidade: Vinculo e Ciclo de Cultivo
  Como Proprietario ou Gestor
  Eu quero vincular uma cultura a um Talhao declarando a quantidade plantada
  Para iniciar um novo Ciclo Agricola e acompanhar a evolucao produtiva

  @F06-US12-positivo
  Cenario: Ciclo Agricola iniciado com sucesso
    Dado que o Talhao esta vazio e o Intervalo de Descanso foi cumprido
    Quando o Proprietario vincula a cultura "Tomate" com quantidade 200 e unidade "QUILOGRAMA"
    Entao o Ciclo Agricola e iniciado com status ATIVO

  @F06-US12-RN043
  Cenario: Vinculo rejeitado em Talhao com Ciclo ativo
    Dado que o Talhao ja possui um Ciclo ativo de "Tomate"
    Quando o Proprietario tenta vincular a cultura "Milho" ao mesmo Talhao
    Entao o sistema rejeita com erro "TALHAO_OCUPADO"

  @F06-US12-RN044
  Cenario: Quantidade plantada zero rejeitada
    Dado que o Talhao esta vazio
    Quando o Proprietario tenta vincular "Tomate" com quantidade 0
    Entao o sistema rejeita com erro "QUANTIDADE_PLANTADA_OBRIGATORIA"

  @F06-US12-RN045
  Esquema do Cenario: Unidade de medida invalida rejeitada
    Dado que o Talhao esta vazio
    Quando o Proprietario tenta vincular "Tomate" com unidade <unidade>
    Entao o sistema rejeita com erro "UNIDADE_INVALIDA"
    Exemplos:
      | unidade    |
      | "tonelada" |
      | "litros"   |
      | "sacas"    |

  @F06-US12-RN046
  Cenario: Alteracao de quantidade apos confirmacao rejeitada
    Dado que o Ciclo Agricola de "Tomate" foi confirmado com 150 QUILOGRAMA
    Quando o Proprietario tenta alterar a quantidade plantada para 500
    Entao o sistema rejeita com erro "QUANTIDADE_PLANTADA_IMUTAVEL"
