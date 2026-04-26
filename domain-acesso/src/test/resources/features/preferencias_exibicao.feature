# language: pt
Funcionalidade: Preferencias de exibicao e agendamento de resumo
  Como Agricultor
  Eu quero definir minhas preferencias de exibicao e agendamento de resumo
  Para que o sistema se adapte a minha rotina

  @F03-US05 @positivo
  Cenario: Preferencias de exibicao salvas com sucesso
    Dado que existe uma Preferencia associada a uma Conta
    Quando ele define unidade "HECTARE", area "1.50" e horario de resumo "07:00"
    Entao as preferencias sao persistidas no agregado
    E o evento PreferenciasDefinidas e publicado

  @F03-US05-RN025a
  Cenario: Valor de area negativo rejeitado
    Dado que existe uma Preferencia associada a uma Conta
    Quando ele tenta definir area "-1.5"
    Entao o sistema rejeita com erro "VALOR_AREA_INVALIDO"

  @F03-US05-RN025b
  Cenario: Valor de area com mais de 2 casas decimais rejeitado
    Dado que existe uma Preferencia associada a uma Conta
    Quando ele tenta definir area "1.567"
    Entao o sistema rejeita com erro "VALOR_AREA_INVALIDO"

  @F03-US05-RN026
  Cenario: Horario de resumo abaixo do minimo rejeitado
    Dado que existe uma Preferencia associada a uma Conta
    Quando ele tenta definir horario de resumo "04:00"
    Entao o sistema rejeita com erro "HORARIO_RESUMO_INVALIDO"
