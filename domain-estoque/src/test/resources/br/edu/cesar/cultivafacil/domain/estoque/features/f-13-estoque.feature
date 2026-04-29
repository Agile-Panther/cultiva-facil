# language: pt
Funcionalidade: Gestao de Estoque de Insumos e Colheita
  Como Proprietario ou Gestor
  Quero registrar movimentacoes de estoque e configurar limites minimos
  Para acompanhar a disponibilidade de insumos e antecipar reposicoes

  Contexto:
    Dado que existe um estoque para a propriedade "Fazenda Boa Vista"
    E que existe o item de estoque "Fertilizante NPK" com unidade de medida "kg"

  @F13-US28-RN097
  Esquema do Cenario: Validacao da origem da entrada de insumo
    Quando registro entrada do item "Fertilizante NPK" com quantidade 100, unidade "kg", origem "<origem>" e referencia "NF-1"
    Entao o sistema "<resultado>" o registro

    Exemplos:
      | origem                | resultado |
      | Compra Confirmada     | aceita    |
      | Devolucao Operacional | aceita    |
      | Doacao                | rejeita   |
      | Encontrado            | rejeita   |
      |                       | rejeita   |

  @F13-US28-RN098
  Esquema do Cenario: Validacao da quantidade de entrada
    Quando registro entrada do item "Fertilizante NPK" com quantidade "<quantidade>", unidade "kg", origem "Compra Confirmada" e referencia "NF-2"
    Entao o sistema "<resultado>" o registro

    Exemplos:
      | quantidade | resultado |
      | 0.1        | aceita    |
      | 100        | aceita    |
      | 0          | rejeita   |
      | -1         | rejeita   |
      |            | rejeita   |

  @F13-US28-RN099
  Esquema do Cenario: Validacao da unidade de medida da entrada
    Quando registro entrada do item "Fertilizante NPK" com quantidade 100, unidade "<unidade>", origem "Compra Confirmada" e referencia "NF-3"
    Entao o sistema "<resultado>" o registro

    Exemplos:
      | unidade   | resultado |
      | kg        | aceita    |
      | g         | rejeita   |
      | toneladas | rejeita   |
      | sacas     | rejeita   |

  @F13-US28-RN100
  Cenario: Entrada duplicada com mesma origem, item e quantidade e rejeitada
    Dado que existe entrada para item "Fertilizante NPK" com quantidade 100, unidade "kg", origem "Compra Confirmada" e referencia "NF-1234"
    Quando registro entrada do item "Fertilizante NPK" com quantidade 100, unidade "kg", origem "Compra Confirmada" e referencia "NF-1234"
    Entao o sistema rejeita o registro
    E a mensagem de erro contem "entrada duplicada"

  @F13-US29-RN101
  Esquema do Cenario: Validacao da quantidade de saida
    Dado que existe entrada para item "Fertilizante NPK" com quantidade 100, unidade "kg", origem "Compra Confirmada" e referencia "NF-SAIDA"
    Quando registro saida do item "Fertilizante NPK" com quantidade "<quantidade>", motivo "Venda" e data "2026-04-28"
    Entao o sistema "<resultado>" o registro

    Exemplos:
      | quantidade | resultado |
      | 0.1        | aceita    |
      | 50         | aceita    |
      | 0          | rejeita   |
      | -1         | rejeita   |
      |            | rejeita   |

  @F13-US29-RN102
  Esquema do Cenario: Validacao do motivo da saida
    Dado que existe entrada para item "Fertilizante NPK" com quantidade 100, unidade "kg", origem "Compra Confirmada" e referencia "NF-MOTIVO"
    Quando registro saida do item "Fertilizante NPK" com quantidade 50, motivo "<motivo>" e data "2026-04-28"
    Entao o sistema "<resultado>" o registro

    Exemplos:
      | motivo            | resultado |
      | Venda             | aceita    |
      | Doacao            | aceita    |
      | Descarte          | aceita    |
      | AplicacaoEmTalhao | aceita    |
      | Perda             | rejeita   |
      | Roubo             | rejeita   |
      |                   | rejeita   |

  @F13-US29-RN103
  Esquema do Cenario: Justificativa obrigatoria quando saida e Descarte
    Dado que existe entrada para item "Fertilizante NPK" com quantidade 100, unidade "kg", origem "Compra Confirmada" e referencia "NF-DESCARTE"
    Quando registro saida do item "Fertilizante NPK" com quantidade 50, motivo "Descarte", data "2026-04-28" e justificativa de "<tamanho>" caracteres
    Entao o sistema "<resultado>" o registro

    Exemplos:
      | tamanho | resultado |
      | 20      | aceita    |
      | 200     | aceita    |
      | 500     | aceita    |
      | 19      | rejeita   |
      | 501     | rejeita   |
      | 0       | rejeita   |

  @F13-US29-RN104
  Esquema do Cenario: Validacao da data de saida
    Dado que existe entrada para item "Fertilizante NPK" com quantidade 100, unidade "kg", origem "Compra Confirmada" e referencia "NF-DATA"
    E que a data atual do estoque e "2026-04-28"
    Quando registro saida do item "Fertilizante NPK" com quantidade 50, motivo "Venda" e data "<data>"
    Entao o sistema "<resultado>" o registro

    Exemplos:
      | data       | resultado |
      | 2026-04-28 | aceita    |
      | 2026-04-27 | aceita    |
      | 2026-01-01 | aceita    |
      | 2026-04-29 | rejeita   |
      | 2027-01-01 | rejeita   |

  @F13-US30-RN105
  Esquema do Cenario: Validacao do valor do limite minimo
    Quando configuro limite minimo do item "Fertilizante NPK" com valor "<valor>" e unidade "kg"
    Entao o sistema "<resultado>" a configuracao

    Exemplos:
      | valor | resultado |
      | 0.1   | aceita    |
      | 50    | aceita    |
      | 0     | rejeita   |
      | -10   | rejeita   |
      |       | rejeita   |

  @F13-US30-RN106
  Cenario: Segunda configuracao ativa para o mesmo item e rejeitada
    Dado que existe configuracao ATIVA de limite minimo para o item "Fertilizante NPK"
    Quando configuro limite minimo do item "Fertilizante NPK" com valor 30 e unidade "kg"
    Entao o sistema rejeita a configuracao
    E a mensagem de erro contem "ja existe configuracao ativa para o item"

  @F13-US30-RN107
  Esquema do Cenario: Validacao da unidade do limite minimo
    Quando configuro limite minimo do item "Fertilizante NPK" com valor 50 e unidade "<unidade>"
    Entao o sistema "<resultado>" a configuracao

    Exemplos:
      | unidade   | resultado |
      | kg        | aceita    |
      | g         | rejeita   |
      | toneladas | rejeita   |
