# language: pt
# encoding: UTF-8
@F02
Funcionalidade: F-02 - Perfil da Propriedade
  Como Proprietário
  Eu quero configurar e manter o diagnóstico agroclimático da Propriedade
  Para liberar as funcionalidades do sistema e garantir dados compatíveis com a região

  # RN-017
  # Guardião: Propriedade
  # Invariante contratual: Cada Proprietário pode ser titular de exatamente uma Propriedade ativa. A tentativa de criar uma segunda Propriedade é rejeitada.
  # Código de erro: PROPRIEDADE_INVALIDO
  Cenário: Segunda Propriedade rejeitada (RN-017)
    Dado que o Proprietário já possui uma Propriedade ativa cadastrada
    Quando tenta criar uma segunda Propriedade
    Então o sistema rejeita com erro PROPRIEDADE_INVALIDO

  # RN-018
  # Guardião: Propriedade
  # Invariante contratual: A localização da Propriedade exige município e estado simultaneamente. Registros com apenas um dos dois campos são rejeitados.
  # Código de erro: PROPRIEDADE_INVALIDO
  Cenário: Localização com apenas município rejeitada (RN-018)
    Dado que o Proprietário informa apenas o município, sem o estado
    Quando tenta salvar o Perfil da Propriedade
    Então o sistema rejeita com erro PROPRIEDADE_INVALIDO

  # RN-018
  # Guardião: Propriedade
  # Invariante contratual: A localização da Propriedade exige município e estado simultaneamente. Registros com apenas um dos dois campos são rejeitados.
  # Código de erro: PROPRIEDADE_INVALIDO
  Cenário: Localização com apenas estado rejeitada (RN-018)
    Dado que o Proprietário informa apenas o estado, sem o município
    Quando tenta salvar o Perfil da Propriedade
    Então o sistema rejeita com erro PROPRIEDADE_INVALIDO

  # RN-019
  # Guardião: Propriedade
  # Invariante contratual: O tipo de solo deve ser um dos valores aceitos pelo Sistema Brasileiro de Classificação de Solos (SiBCS/EMBRAPA): Latossolo, Argissolo, Neossolo, Cambissolo, Gleissolo, Nitossolo, Vertissolo ou Plintossolo.
  # Código de erro: PROPRIEDADE_INVALIDO
  Cenário: Tipo de solo inválido rejeitado (RN-019)
    Dado que o Proprietário informa o tipo de solo "Pedregoso" (fora do conjunto aceito pelo SiBCS/EMBRAPA)
    Quando tenta salvar o Perfil
    Então o sistema rejeita com erro PROPRIEDADE_INVALIDO

  # RN-020
  # Guardião: Propriedade
  # Invariante contratual: O clima predominante deve ser um dos subtipos climáticos aceitos conforme a classificação de Köppen-Geiger para o Brasil: Tropical Úmido, Tropical Savânico, Tropical com Estação Seca no Verão, Semiárido, Subtropical Úmido, Subtropical de Altitude ou Subtropical.
  # Código de erro: PROPRIEDADE_INVALIDO
  Cenário: Clima inválido rejeitado (RN-020)
    Dado que o Proprietário informa o clima "Árido" (fora dos subtipos Köppen-Geiger aceitos para o Brasil)
    Quando tenta salvar o Perfil
    Então o sistema rejeita com erro PROPRIEDADE_INVALIDO

  # RN-021
  # Guardião: Propriedade
  # Invariante contratual: O Perfil da Propriedade transita para o estado Completo somente quando localização, tipo de solo e clima predominante estiverem todos preenchidos e válidos simultaneamente. A transição com qualquer um dos três ausente é rejeitada.
  # Código de erro: PROPRIEDADE_INVALIDO
  Cenário: Transição para Completo com campo ausente rejeitada (RN-021)
    Dado que o Proprietário informou município, estado e tipo de solo, mas não informou o clima predominante
    Quando tenta confirmar o Perfil da Propriedade
    Então o sistema rejeita a transição com erro PROPRIEDADE_INVALIDO e mantém o estado Incompleto

  # RN-022
  # Guardião: Propriedade
  # Invariante contratual: A atualização do clima predominante é rejeitada se o novo clima informado for incompatível com a região geográfica (município e estado) já registrada na Propriedade.
  # Código de erro: PROPRIEDADE_INVALIDO
  Cenário: Atualização de clima incompatível com região rejeitada (RN-022)
    Dado que o Proprietário informa um clima incompatível com o município e estado registrados
    Quando tenta atualizar o clima
    Então o sistema rejeita com erro PROPRIEDADE_INVALIDO

  # RN-023
  # Guardião: Propriedade
  # Invariante contratual: A atualização do tipo de solo é rejeitada se o novo valor não fizer parte da classificação oficial do Sistema Brasileiro de Classificação de Solos (SiBCS/EMBRAPA).
  # Código de erro: PROPRIEDADE_INVALIDO
  Cenário: Atualização de tipo de solo fora da classificação oficial (RN-023)
    Dado que o Proprietário informa um tipo de solo não aceito pelo SiBCS/EMBRAPA
    Quando tenta atualizar o tipo de solo
    Então o sistema rejeita com erro PROPRIEDADE_INVALIDO

  # RN-024
  # Guardião: Propriedade
  # Invariante contratual: A atualização do clima predominante é rejeitada se o novo valor não for um dos subtipos climáticos aceitos pela classificação de Köppen-Geiger para o Brasil.
  # Código de erro: PROPRIEDADE_INVALIDO
  Cenário: Atualização de clima fora da classificação oficial (RN-024)
    Dado que o Proprietário informa um clima não aceito pela classificação Köppen-Geiger
    Quando tenta atualizar o clima
    Então o sistema rejeita com erro PROPRIEDADE_INVALIDO
