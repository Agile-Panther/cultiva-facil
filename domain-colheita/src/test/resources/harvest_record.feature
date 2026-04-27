# language: pt
# encoding: UTF-8
@F12
Funcionalidade: F-12 - Registro de Colheita
  Como Agricultor
  Eu quero registrar a colheita de uma Zona informando quantidade, unidade de medida e destino
  Para encerrar o ciclo e atualizar o Celeiro com o resultado real da safra

  @F12-US22-RN081
  Cenário: Colheita em Zona não pronta rejeitada
    Dado que a Zona está com Situação "Vazia" (sem Cultivo ativo)
    Quando o Agricultor tenta registrar Colheita
    Então o sistema rejeita com erro COLHEITA_NAO_PERMITIDA

  @F12-US22-RN082a
  Cenário: Quantidade zero ou negativa é rejeitada
    Dado que o Agricultor informa quantidade 0
    Quando submete o registro de Colheita
    Então o sistema rejeita com erro QUANTIDADE_COLHIDA_INVALIDA

  @F12-US22-RN082b
  Cenário: Quantidade acima da projeção atual do Celeiro rejeitada
    Dado que a projeção atual do Celeiro para a Zona é 300 kg (quantidade plantada 350 kg
    menos 50 kg de perdas acumuladas) e o Agricultor informa quantidade 320 
    Quando submete o registro de Colheita
    Então o sistema rejeita com erro QUANTIDADE_EXCEDE_PROJECAO — a quantidade
    colhida não pode superar a projeção atual do Celeiro

  @F12-US22-RN083
  Cenário: Destino inválido rejeitado
    Dado que o Agricultor informa destino "Exportação" (fora do conjunto: ConsumoPróprio, Venda,
    Cooperativa, Descarte)
    Quando submete o registro
    Então o sistema rejeita com erro DESTINO_INVALIDO

  @F12-US22-RN084
  Cenário: Unidade de medida incompatível com o cultivo rejeitada
    Dado que o cultivo ativo usa unidade "kg" e o Agricultor informa unidade "unidades" na Colheita
    Quando submete o registro
    Então o sistema rejeita com erro UNIDADE_INVALIDA

  @F12-US22-RN085
  Cenário: Encerramento de ciclo sem quantidade colhida rejeitado
    Dado que o Agricultor tenta encerrar o Ciclo Agrícola sem informar a quantidade colhida
    Quando submete o encerramento
    Então o sistema rejeita com erro CICLO_SEM_COLHEITA

  @F12-US23-RN086
  Cenário: Correção após encerramento definitivo rejeitada
    Dado que o Ciclo Agrícola da Zona foi encerrado definitivamente
    Quando o Agricultor tenta corrigir os dados da Colheita
    Então o sistema rejeita com erro CORRECAO_CICLO_ENCERRADO

  @F12-US23-RN087
  Cenário: Validações do registro original aplicadas na correção
    Dado que o Agricultor tenta corrigir o destino para "Importação" (fora do conjunto aceito)
    Quando submete a correção
    Então o sistema rejeita aplicando as mesmas validações — erro DESTINO_INVALIDO