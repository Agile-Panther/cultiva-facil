# language: pt

Funcionalidade: Rotação de Culturas
  Como Proprietário ou Gestor
  Eu quero definir Intervalos de Descanso entre cultivos da mesma cultura em cada Zona
  Para proteger o solo da exaustão

  @F08-US15-positivo
  Cenário: Intervalo de Descanso cadastrado com sucesso
    Dado que a Zona possui ao menos um ciclo de "Tomate" encerrado
    Quando o Proprietário cadastra Intervalo de Descanso de 30 dias para "Tomate"
    Então o Intervalo é registrado com sucesso

  @F08-US15-RN051
  Cenário: Intervalo cadastrado sem histórico encerrado rejeitado
    Dado que a Zona nunca teve nenhum ciclo de "Milho" encerrado
    Quando o Proprietário tenta cadastrar Intervalo de Descanso de 20 dias para "Milho"
    Então o sistema rejeita com erro "INTERVALO_SEM_HISTORICO"

  @F08-US15-RN052
  Esquema do Cenário: Valores fora do limite rejeitados
    Dado que a Zona possui ao menos um ciclo de "Tomate" encerrado
    Quando o Proprietário tenta cadastrar Intervalo de Descanso de <dias> dias para "Tomate"
    Então o sistema rejeita com erro "INTERVALO_INVALIDO"
    Exemplos:
      | dias |
      | 0    |
      | 366  |

  @F08-US16-RN053
  Cenário: Vínculo bloqueado dentro do Intervalo de Descanso
    Dado que o Intervalo de 30 dias para "Tomate" não foi cumprido com colheita há 20 dias
    Quando o Proprietário tenta vincular "Tomate" à Zona
    Então o sistema rejeita com erro "INTERVALO_NAO_CUMPRIDO"
