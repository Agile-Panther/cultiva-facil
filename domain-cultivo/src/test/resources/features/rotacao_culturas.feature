# language: pt

Funcionalidade: Rotacao de Culturas
  Como Proprietario ou Gestor
  Eu quero definir Intervalos de Descanso entre cultivos da mesma cultura em cada Zona
  Para proteger o solo da exaustao

  @F08-US15-positivo
  Cenario: Intervalo de Descanso cadastrado com sucesso
    Dado que a Zona possui ao menos um ciclo de "Tomate" encerrado
    Quando o Proprietario cadastra Intervalo de Descanso de 30 dias para "Tomate"
    Entao o Intervalo e registrado com sucesso

  @F08-US15-RN051
  Cenario: Intervalo cadastrado sem historico encerrado rejeitado
    Dado que a Zona nunca teve nenhum ciclo de "Milho" encerrado
    Quando o Proprietario tenta cadastrar Intervalo de Descanso de 20 dias para "Milho"
    Entao o sistema rejeita com erro "INTERVALO_SEM_HISTORICO"

  @F08-US15-RN052
  Esquema do Cenario: Valores fora do limite rejeitados
    Dado que a Zona possui ao menos um ciclo de "Tomate" encerrado
    Quando o Proprietario tenta cadastrar Intervalo de Descanso de <dias> dias para "Tomate"
    Entao o sistema rejeita com erro "INTERVALO_INVALIDO"
    Exemplos:
      | dias |
      | 0    |
      | 366  |

  @F08-US16-RN053
  Cenario: Vinculo bloqueado dentro do Intervalo de Descanso
    Dado que o Intervalo de 30 dias para "Tomate" nao foi cumprido com colheita ha 20 dias
    Quando o Proprietario tenta vincular "Tomate" a Zona
    Entao o sistema rejeita com erro "INTERVALO_NAO_CUMPRIDO"
