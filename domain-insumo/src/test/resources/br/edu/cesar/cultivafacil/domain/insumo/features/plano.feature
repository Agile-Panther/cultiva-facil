# language: pt

Funcionalidade: F-14 — Gestão de Insumos

  Regra: US-28 — Criar Plano de Insumos mensal

    @F14-US28-RN102
    Cenário: Plano para mês passado rejeitado
      Dado que o Agricultor informa o mês de referência "Janeiro/2026" que é anterior ao mês atual
      Quando tenta criar o Plano de Insumos
      Então o sistema rejeita com erro MES_REFERENCIA_PASSADO

    @F14-US28-RN103
    Cenário: Plano duplicado para mesma Zona e mês rejeitado
      Dado que já existe um Plano de Insumos para a Zona no mês futuro válido
      Quando o Agricultor tenta criar outro plano para a mesma Zona e mesmo mês
      Então o sistema rejeita com erro PLANO_DUPLICADO

    @F14-US28-RN104
    Cenário: Tipo de insumo inválido rejeitado
      Dado que o Agricultor informa tipo de insumo inexistente no conjunto Semente, Fertilizante, Defensivo
      Quando submete o cadastro
      Então o sistema rejeita com erro TIPO_INSUMO_INVALIDO

    @F14-US28-RN105
    Cenário: Quantidade de insumo zero ou negativa rejeitada
      Dado que o Agricultor informa quantidade 0 para o insumo
      Quando submete o cadastro
      Então o sistema rejeita com erro QUANTIDADE_INSUMO_INVALIDA

    @F14-US28-positivo
    Cenário: Plano de insumos criado com sucesso
      Dado que o Agricultor informa mês de referência futuro válido, zona e agricultor
      Quando cria o Plano de Insumos com item do tipo Semente, quantidade 10 e unidade KG
      Então o plano é criado com sucesso e contém o item adicionado

  Regra: US-29 — Registrar aquisição com custo real

    @F14-US29-RN106
    Cenário: Transição para Adquirido sem Preço Unitário rejeitada
      Dado que o Agricultor tenta mudar o status do item para Adquirido sem informar o Preço Unitário
      Quando submete a mudança de status
      Então o sistema rejeita com erro PRECO_UNITARIO_OBRIGATORIO

    @F14-US29-RN107
    Cenário: Reversão de status Adquirido para Planejado rejeitada
      Dado que o item "Fertilizante NPK" já possui status Adquirido com data de registro confirmada
      Quando o Agricultor tenta reverter o status para Planejado
      Então o sistema rejeita com erro REVERSAO_STATUS_INVALIDA

    @F14-US29-positivo
    Cenário: Aquisição de item registrada com sucesso
      Dado que existe um item com status Planejado no plano
      Quando o Agricultor registra a aquisição informando preço unitário de 50,00
      Então o item passa para status Adquirido e o preço unitário é registrado

  Regra: US-30 — Consultar histórico de insumos por Zona

    @F14-US30-RN108
    Cenário: Itens Planejados ou de ciclo ativo excluídos do histórico
      Dado que a Zona possui apenas itens Planejados em ciclo ativo sem itens Adquiridos em ciclo encerrado
      Quando o Agricultor consulta o histórico de insumos
      Então o histórico retorna vazio

    @F14-US30-positivo
    Cenário: Histórico retorna apenas itens adquiridos de ciclos encerrados
      Dado que a Zona possui um plano encerrado com item no status Adquirido
      Quando o Agricultor consulta o histórico de insumos da Zona
      Então o histórico retorna somente os itens com status Adquirido do plano encerrado

  Regra: US-46 — Registrar consumo de insumo

    @F14-US46-RN109
    Cenário: Consumo de insumo sem aquisição prévia rejeitado
      Dado que a Zona não possui nenhuma aquisição registrada para o tipo Semente
      Quando o Operador tenta registrar consumo de Semente na Zona
      Então o sistema rejeita com erro INSUMO_SEM_AQUISICAO

    @F14-US46-RN110
    Cenário: Consumo acima do saldo disponível rejeitado
      Dado que o saldo disponível de Fertilizante na Zona é 30 kg
      Quando o Operador tenta registrar consumo de 50 kg de Fertilizante
      Então o sistema rejeita com erro SALDO_INSUFICIENTE

    @F14-US46-RN111
    Cenário: Consumo com unidade de medida divergente rejeitado
      Dado que o insumo Fertilizante foi adquirido com unidade KG
      Quando o Operador tenta registrar consumo de 5 LITRO de Fertilizante
      Então o sistema rejeita com erro UNIDADE_INCOMPATIVEL

    @F14-US46-positivo
    Cenário: Consumo registrado com sucesso dentro do saldo disponível
      Dado que existe 30 kg de Fertilizante adquirido no plano
      Quando o Operador registra consumo de 10 kg de Fertilizante
      Então o consumo é registrado e o saldo passa a ser 20 kg

  Regra: US-47 — Configurar limite mínimo de estoque

    @F14-US47-RN112
    Cenário: Limite mínimo zero ou negativo rejeitado
      Dado que o Gestor informa valor de limite mínimo igual a 0 para Semente na Zona
      Quando submete a configuração de limite
      Então o sistema rejeita com erro LIMITE_MINIMO_INVALIDO

    @F14-US47-RN113
    Cenário: Configuração de limite sem e-mail de destinatário rejeitada
      Dado que o Gestor tenta ativar configuração de limite sem informar e-mail do destinatário
      Quando submete a configuração
      Então o sistema rejeita com erro EMAIL_DESTINATARIO_OBRIGATORIO

    @F14-US47-RN114
    Cenário: Segunda configuração de limite para mesmo insumo e Zona rejeitada
      Dado que já existe uma configuração de limite ativa para Fertilizante na Zona
      Quando o Gestor tenta criar outra configuração para o mesmo insumo na mesma Zona
      Então o sistema rejeita com erro CONFIGURACAO_LIMITE_DUPLICADA

    @F14-US47-RN115
    Cenário: Limite configurado para insumo sem aquisição rejeitado
      Dado que a Zona não possui nenhuma aquisição registrada para Defensivo
      Quando o Gestor tenta configurar limite mínimo para Defensivo na Zona
      Então o sistema rejeita com erro INSUMO_SEM_AQUISICAO

    @F14-US47-positivo
    Cenário: Limite mínimo configurado com sucesso
      Dado que existe aquisição de Fertilizante registrada na Zona
      Quando o Gestor configura limite mínimo de 5 kg com e-mail gestor@fazenda.com
      Então a configuração de limite é criada como ativa

  Regra: US-48 — Pedido de reposição automático

    @F14-US48-positivo
    Cenário: Pedido de reposição emitido quando saldo atinge o limite mínimo
      Dado que existe configuração de limite mínimo de 10 kg para Fertilizante com e-mail gestor@fazenda.com
      E o saldo atual de Fertilizante é 15 kg adquiridos e 5 kg consumidos
      Quando o Operador registra consumo de 1 kg de Fertilizante reduzindo o saldo para 9 kg
      Então um PedidoReposicao é criado com o saldo no momento e e-mail do destinatário

    @F14-US48-RN116
    Cenário: Saldo acima do limite não gera pedido de reposição
      Dado que existe configuração de limite mínimo de 10 kg para Fertilizante com e-mail gestor@fazenda.com
      E o saldo atual de Fertilizante é 30 kg adquiridos sem consumos anteriores
      Quando o Operador registra consumo de 5 kg de Fertilizante reduzindo o saldo para 25 kg
      Então nenhum pedido de reposição é gerado pois o saldo permanece acima do limite

    @F14-US48-RN117
    Cenário: Segundo pedido automático bloqueado dentro de 24 horas
      Dado que um pedido de reposição para Fertilizante foi emitido há menos de 24 horas
      Quando o Operador registra novo consumo que reduz o saldo abaixo do limite
      Então nenhum novo pedido de reposição é gerado

    @F14-US48-RN118
    Cenário: Pedido gerado contém todas as informações obrigatórias
      Dado que existe configuração de limite de 10 kg para Fertilizante e saldo de 20 kg
      Quando o consumo reduz o saldo para 8 kg abaixo do limite
      Então o PedidoReposicao contém tipo, zona, saldo atual, quantidade sugerida e e-mail

    @F14-US48-RN119
    Cenário: PedidoReposicao gerado inicia com status Pendente e transita para Enviado
      Dado que existe configuração de limite mínimo de 10 kg para Fertilizante com e-mail gestor@fazenda.com
      E o saldo atual de Fertilizante é 15 kg adquiridos e 5 kg consumidos
      Quando o Operador registra consumo de 1 kg de Fertilizante reduzindo o saldo para 9 kg
      E o pedido de reposição é marcado como enviado
      Então o status do pedido é Enviado

    @F14-US48-RN120
    Cenário: Zona sem configuração de limite não gera pedido de reposição
      Dado que a Zona não possui configuração de limite ativa para Semente
      Quando o Operador registra consumo de Semente
      Então nenhum pedido de reposição é gerado
