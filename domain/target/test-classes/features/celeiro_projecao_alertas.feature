# language: pt
# encoding: UTF-8
@F13
Funcionalidade: F-13 - Controle do Celeiro com Projeção e Alertas
  Como Agricultor
  Eu quero acompanhar a projeção de produção e receber alertas preventivos
  Para gerenciar minha safra antes do encerramento do ciclo

  @F13-US24-positivo
  Cenário: Estado do Celeiro exibido corretamente com projeção calculada
    Dado que a Zona possui ciclo ativo com 200 kg plantados
    E foram registradas perdas de 30 kg nesse ciclo
    Quando o Agricultor acessa a visualização do Celeiro da Zona
    Então o sistema exibe quantidade plantada 200 kg
    E o sistema exibe perdas acumuladas 30 kg
    E o sistema exibe projeção atual 170 kg

  @F13-US24-RN088
  Cenário: Ajuste manual direto na projeção do Celeiro é rejeitado
    Dado que a Zona possui ciclo ativo com projeção calculada
    Quando o Agricultor tenta alterar manualmente a quantidade plantada do Celeiro
    Então o sistema rejeita com erro "AJUSTE_MANUAL_NAO_PERMITIDO"

  @F13-US36-positivo
  Cenário: Alerta de Projeção emitido quando projeção cai abaixo da Meta
    Dado que a Zona possui ciclo ativo com 200 kg plantados
    E o Agricultor definiu Meta Comercializável de 150 kg
    Quando são registradas perdas de 60 kg tornando a projeção 140 kg
    Então o sistema emite um Alerta de Projeção Abaixo do Esperado para a Zona

  @F13-US36-RN090a
  Cenário: Meta Comercializável zero é rejeitada
    Dado que a Zona possui ciclo ativo com 100 kg plantados
    Quando o Agricultor tenta definir Meta Comercializável de 0 kg
    Então o sistema rejeita com erro "META_COMERCIALIZAVEL_INVALIDA"

  @F13-US36-RN090b
  Cenário: Meta Comercializável superior à quantidade plantada é rejeitada
    Dado que a Zona possui ciclo ativo com 100 kg plantados
    Quando o Agricultor tenta definir Meta Comercializável de 120 kg
    Então o sistema rejeita com erro "META_COMERCIALIZAVEL_INVALIDA"

  @F13-US36-RN091
  Cenário: Meta Comercializável com ciclo encerrado é rejeitada
    Dado que o ciclo da Zona foi encerrado
    Quando o Agricultor tenta definir ou atualizar a Meta Comercializável
    Então o sistema rejeita com erro "CICLO_INATIVO"

  @F13-US36-RN092
  Cenário: Segundo Alerta de Projeção em 24 horas é bloqueado
    Dado que um Alerta de Projeção Abaixo do Esperado foi emitido para a Zona há 6 horas
    Quando as perdas acumuladas continuam abaixo da Meta Comercializável
    Então o sistema bloqueia a geração do segundo alerta com erro "FREQUENCIA_ALERTA_EXCEDIDA"

  @F13-US25-positivo
  Cenário: Saída de produto do Celeiro registrada com sucesso
    Dado que o Celeiro da Zona possui saldo disponível de 170 kg
    Quando o Agricultor registra saída de 50 kg com motivo "VENDA"
    Então a saída é registrada e o saldo disponível fica em 120 kg

  @F13-US25-RN094
  Cenário: Saída superior ao saldo disponível é rejeitada
    Dado que o Celeiro da Zona possui saldo disponível de 50 kg
    Quando o Agricultor tenta registrar saída de 80 kg
    Então o sistema rejeita com erro "SALDO_INSUFICIENTE"

  @F13-US26-positivo
  Cenário: Configuração de relatório salva com sucesso
    Dado que o Agricultor possui 0 configurações de relatório salvas
    Quando o Agricultor salva configuração com nome "Relatório Trimestral" e filtro "TRIMESTRE"
    Então a configuração é persistida com sucesso

  @F13-US26-RN009
  Cenário: Limite de 5 configurações de relatório excedido é rejeitado
    Dado que o Agricultor já possui 5 configurações de relatório salvas
    Quando o Agricultor tenta salvar uma sexta configuração
    Então o sistema rejeita com erro "LIMITE_CONFIGURACOES_EXCEDIDO"

  @F13-US26-RN007
  Cenário: Nome duplicado de configuração de relatório é rejeitado
    Dado que o Agricultor já possui configuração de relatório com nome "Mensal"
    Quando o Agricultor tenta salvar nova configuração com o mesmo nome "Mensal"
    Então o sistema rejeita com erro "NOME_CONFIG_DUPLICADO"
