# language: pt
# encoding: UTF-8
@F07
Funcionalidade: F-07 Compatibilidade de Culturas
  Como Agricultor
  Quero registrar consórcios de culturas na mesma Zona
  Para ser orientado sobre combinações prejudiciais antes do plantio

  @F07-US13-positivo
  Cenário: Consórcio Companheiro registrado com status Companheira
    Dado que a Zona possui Cultivo ativo de "Tomate"
    E "Manjericão" é classificado como Companheiro de "Tomate"
    Quando o Agricultor vincula "Manjericão" à Zona
    Então o vínculo é registrado com classificação COMPANHEIRA

  @F07-US13-RN047
  Cenário: Vínculo de cultura Inimiga sem consentimento é rejeitado
    Dado que "Funcho" é classificado como Inimigo de "Tomate"
    E o Agricultor não confirma ciência do risco
    Quando tenta vincular "Funcho" à Zona com "Tomate" ativo
    Então o sistema rejeita com erro "INIMIGA_BLOQUEADA"

  @F07-US13-RN048
  Cenário: Vínculo de Inimiga com consentimento é aceito com indicador de ciência
    Dado que "Funcho" é classificado como Inimigo de "Tomate"
    E o Agricultor confirma explicitamente ciência do risco
    Quando submete o consórcio com consentimento registrado
    Então o vínculo é aceito com indicador cienciaDoAgricultor verdadeiro

  @F07-US13-RN049
  Cenário: Cultura sem relação definida não recebe status Companheira
    Dado que a Zona possui "Tomate" ativo
    E "Cenoura" não possui relação de consórcio definida com "Tomate"
    Quando o Agricultor registra o consórcio
    Então o vínculo é aceito com classificação NEUTRA

  @F07-US14-positivo
  Cenário: Histórico de consórcios exibido quando há vínculos registrados
    Dado que a Zona possui ao menos um vínculo de cultura registrado
    Quando o Agricultor consulta o histórico de consórcios
    Então todos os consórcios da Zona são retornados

  @F07-US14-RN050
  Cenário: Histórico indisponível para Zona sem nenhum consórcio
    Dado que a Zona nunca recebeu nenhum vínculo de cultura
    Quando o Agricultor tenta consultar o histórico de consórcios
    Então o sistema rejeita com erro "HISTORICO_INEXISTENTE"
