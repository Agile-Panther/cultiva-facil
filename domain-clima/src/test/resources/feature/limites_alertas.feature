# src/test/resources/features/limites_climaticos_alertas.feature
# language: pt

Funcionalidade: Limites Climaticos, Irrigacao e Alertas

  @F17-US31-Sucesso
  Cenario: Limites climaticos e Necessidade Hidrica cadastrados com sucesso
    Dado que a Zona possui vinculo de cultura ativo de "Tomate"
    Quando o Peao informa temperatura maxima 35°C, precipitacao maxima 80 mm/24h e Necessidade Hidrica 15 mm para janela de 5Dias e confirma
    Entao os limites climaticos e a Necessidade Hidrica sao salvos com sucesso para a Zona e cultura ativa

  @F17-US31-RN135
  Cenario: Limites em Zona sem cultura ativa rejeitados
    Dado que a Zona esta Vazia
    Quando o Peao tenta cadastrar limites climaticos ou Necessidade Hidrica para essa Zona
    Entao o sistema rejeita com erro "ZONA_SEM_CULTURA_ATIVA"

  @F17-US31-RN136a
  Cenario: Temperatura abaixo de -5°C rejeitada
    Dado que o Peao informa temperatura de -10°C
    Quando submete o cadastro
    Entao o sistema rejeita com erro "TEMPERATURA_INVALIDA"

  @F17-US31-RN136b
  Cenario: Temperatura acima de 50°C rejeitada
    Dado que o Peao informa temperatura de 60°C
    Quando submete o cadastro
    Entao o sistema rejeita com erro "TEMPERATURA_INVALIDA"

  @F17-US31-RN137
  Cenario: Precipitacao acima de 300 mm/24h rejeitada
    Dado que o Peao informa precipitacao de 350 mm/24h
    Quando submete o cadastro
    Entao o sistema rejeita com erro "PRECIPITACAO_INVALIDA"

  @F17-US31-RN138a
  Cenario: Necessidade Hidrica fora do intervalo rejeitada
    Dado que o Peao informa Necessidade Hidrica de 200 mm para a janela
    Quando submete o cadastro
    Entao o sistema rejeita com erro "NECESSIDADE_HIDRICA_INVALIDA"

  @F17-US31-RN138b
  Cenario: Janela de observacao invalida rejeitada
    Dado que o Peao informa janela de observacao "10Dias"
    Quando submete o cadastro
    Entao o sistema rejeita com erro "JANELA_OBSERVACAO_INVALIDA"

  @F17-US32-Sucesso
  Cenario: Limite climatico e Necessidade Hidrica editados com sucesso
    Dado que a Zona possui cultura ativa e limites climaticos e Necessidade Hidrica cadastrados
    Quando o Peao edita o limite de temperatura para 38°C e a Necessidade Hidrica para 20 mm
    Entao os dados sao atualizados com sucesso

  @F17-US32-RN139
  Cenario: Edicao de limites sem cultura ativa rejeitada
    Dado que o ciclo da Zona foi encerrado e os limites foram cadastrados para aquele ciclo
    Quando o Peao tenta editar os limites climaticos ou a Necessidade Hidrica da Zona
    Entao o sistema rejeita com erro "LIMITE_CLIMATICO_IMUTAVEL"

  @F17-US33-Sucesso
  Cenario: Alerta Climatico e Alerta de Irrigacao gerados com sucesso
    Dado que a Zona possui cultura ativa, limites climaticos (precipitacao max. 80mm/24h) e Necessidade Hidrica 15 mm/5Dias cadastrados; a previsao indica 120 mm/24h e a precipitacao acumulada dos ultimos 5 dias e 10 mm
    Quando o sistema processa a previsao e a precipitacao acumulada
    Entao um Alerta Climatico de precipitacao e um Alerta de Necessidade de Irrigacao sao gerados para a Zona e enviados ao Peao

  @F17-US33-RN140
  Cenario: Alerta Climatico nao gerado para Zona sem limites cadastrados
    Dado que a Zona possui cultura ativa mas nao possui limites climaticos cadastrados
    Quando a previsao indica condicoes adversas
    Entao nenhum Alerta Climatico e gerado e o sistema retorna erro "ALERTA_SEM_LIMITE"

  @F17-US33-RN141
  Cenario: Segundo Alerta Climatico do mesmo tipo em 24h bloqueado
    Dado que um Alerta Climatico de precipitacao ja foi gerado para a Zona ha 6 horas
    Quando a previsao volta a ultrapassar o limite de precipitacao
    Entao o sistema bloqueia a geracao de um segundo alerta do mesmo tipo dentro do intervalo de 24h e retorna erro "FREQUENCIA_ALERTA_EXCEDIDA"

  @F17-US33-RN142
  Cenario: Alerta de Irrigacao nao gerado sem Necessidade Hidrica cadastrada
    Dado que a Zona possui cultura ativa mas nao possui Necessidade Hidrica cadastrada
    Quando a precipitacao acumulada cai abaixo de qualquer limiar
    Entao nenhum Alerta de Irrigacao e gerado e o sistema retorna erro "NECESSIDADE_HIDRICA_NAO_CADASTRADA"

  @F17-US33-RN143
  Cenario: Segundo Alerta de Irrigacao em 24h bloqueado
    Dado que um Alerta de Necessidade de Irrigacao ja foi gerado para a Zona ha 10 horas
    Quando a precipitacao acumulada continua abaixo da Necessidade Hidrica definida
    Entao o sistema bloqueia a geracao de um segundo Alerta de Irrigacao dentro do intervalo de 24 horas e retorna erro "FREQUENCIA_ALERTA_EXCEDIDA"

  @F17-US33-RN144
  Cenario: Segundo Alerta de Necessidade de Irrigacao em 24 horas nao gerado
    Dado que o sistema gerou um Alerta de Necessidade de Irrigacao para a Zona "Canteiro Norte" ha 10 horas
    Quando a precipitacao acumulada volta a cair abaixo da Necessidade Hidrica minima da mesma Zona
    Entao o sistema nao emite novo alerta