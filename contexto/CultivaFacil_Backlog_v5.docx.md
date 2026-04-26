**CULTIVA FÁCIL**

Product Backlog 

*MVP  ·  Versão 5.0  ·  Abril 2026*

| ⚙  FEATURE COMPLEXA   —   Feature que incorporou o escopo de outra feature para reduzir o total do backlog. Story points e regras de negócio foram reorganizados de forma condizente. |
| :---- |

*Os critérios de aceite (BDD / Gherkin) foram removidos deste documento e compõem um artefato separado de especificação de aceite.*

| Épico | Features | User Stories | Story Points |
| :---- | :---- | :---- | :---- |
| Autenticação e Acesso | 4 | 9 | 25 SP |
| Gestão de Terrenos | 2 | 5 | 12 SP |
| Gestão de Cultivos | 3 | 5 | 13 SP |
| Calendário e Planejamento | 2 | 5 | 12 SP |
| Manejo Integrado de Pragas | 2 | 4 | 10 SP |
| Colheita e Estoque | 2 | 8 | 20 SP |
| Finanças Fácil | 3 | 10 | 26 SP |
| Clima e Alertas | 2 | 5 | 13 SP |
| **TOTAL** | **20** | **51** | **131 SP** |

**Épico 1 — Autenticação e Acesso**

**F-01  ·  Cadastro de Conta**

| *Contexto: Para acessar a plataforma, o Agricultor precisa criar uma conta com e-mail e senha e manifestar consentimento com os termos de uso. Ao criar a conta, o Agricultor assume automaticamente o papel de Proprietário da Propriedade que cadastrar. Essa é a porta de entrada para todas as demais funcionalidades.* |
| :---- |
| *Valor entregue: Garantir que cada Agricultor possua uma identidade única e segura dentro do sistema.* |

**US-01  (4 SP)**

| *Como Agricultor, eu quero criar minha conta com e-mail e senha para ter acesso seguro e personalizado à plataforma.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-001** | O e-mail é o identificador único do Agricultor na base. O cadastro de dois Agricultores com o mesmo e-mail é rejeitado. |
| **RN-002** | A senha deve ter no mínimo 8 caracteres e conter ao menos 1 número. Senhas fora desse padrão são rejeitadas. |
| **RN-003** | A senha não pode ser idêntica ao e-mail do Agricultor. |
| **RN-004** | O consentimento com os termos de uso é obrigatório para a ativação da conta. Contas criadas sem consentimento registrado como verdadeiro não podem ser ativadas. |

**F-02  ·  Perfil da Propriedade**

| *Contexto: No primeiro acesso, o Proprietário configura o Perfil da Propriedade informando os três dados essenciais: localização, tipo de solo e clima predominante. Somente quando os três estão presentes o sistema opera de forma personalizada. A gestão de membros e convites é tratada em F-20.* |
| :---- |
| *Valor entregue: Coletar os dados mínimos que permitem ao sistema operar de forma contextualizada à realidade do Agricultor.* |

**US-02  (5 SP)**

| *Como Proprietário, eu quero configurar o Perfil da Propriedade no primeiro acesso para que o sistema possa personalizar recomendações desde o início.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-015** | Cada Agricultor pode ser Proprietário de exatamente uma Propriedade ativa. A tentativa de criar uma segunda Propriedade é rejeitada. |
| **RN-016** | A localização da Propriedade exige município e estado simultaneamente. Registros com apenas um dos dois campos são rejeitados. |
| **RN-017** | O tipo de solo deve ser um dos valores aceitos pelo Sistema Brasileiro de Classificação de Solos (SiBCS/EMBRAPA): Latossolo, Argissolo, Neossolo, Cambissolo, Gleissolo, Nitossolo, Vertissolo ou Plintossolo. |
| **RN-018** | O clima predominante deve ser um dos subtipos climáticos aceitos conforme a classificação de Köppen-Geiger para o Brasil: Tropical Úmido, Tropical Savânico, Tropical com Estação Seca no Verão, Semiárido, Subtropical Úmido, Subtropical de Altitude ou Subtropical com Inverno Seco. |
| **RN-019** | O Perfil da Propriedade transita para o estado Completo somente quando localização, tipo de solo e clima predominante estiverem todos preenchidos e válidos simultaneamente. A transição com qualquer um dos três ausente é rejeitada. |
| **RN-020** | Nenhuma funcionalidade operacional do sistema — calendário, tarefas, alertas, relatórios — está disponível enquanto o Perfil da Propriedade estiver no estado Incompleto. |

**US-03  (2 SP)**

| *Como Proprietário, eu quero completar os três dados obrigatórios do Perfil da Propriedade para desbloquear todas as funcionalidades do sistema.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-021** | O Perfil da Propriedade transita para o estado Completo somente quando localização, tipo de solo e clima predominante estiverem todos preenchidos e válidos simultaneamente. A transição com qualquer um dos três ausente é rejeitada. |
| **RN-022** | Nenhuma funcionalidade operacional do sistema — calendário, tarefas, alertas, relatórios — está disponível enquanto o Perfil da Propriedade estiver no estado Incompleto. |

**F-03  ·  Perfil do Agricultor e Notificações**

| ⚙  FEATURE COMPLEXA   —   Incorpora: F-16  ·  Configuração de Notificações |
| :---- |
| *Contexto: O Agricultor personaliza sua experiência individual na plataforma editando dados pessoais, preferências de exibição e canais de notificação. Essas configurações determinam como o sistema se comunica com ele diariamente — desde o formato das medidas até os tipos de alerta que chegam à sua rotina.* |
| *Valor entregue: Permitir que o Agricultor adapte o sistema à sua realidade, controlando como e quando recebe as informações que importam para sua safra.* |

**US-04  (2 SP)**

| *Como Agricultor, eu quero editar meus dados pessoais de perfil para mantê-los atualizados na plataforma.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-023** | O nome do Agricultor deve ter entre 2 e 80 caracteres. Quando não informado na edição, o valor existente é mantido. |
| **RN-024** | A foto de perfil, quando enviada, deve estar no formato JPG ou PNG e ter tamanho máximo de 5 MB. Arquivos fora dessas restrições são rejeitados. |

**US-05  (2 SP)**

| *Como Agricultor, eu quero definir minhas preferências de exibição e agendamento de resumo para que o sistema se adapte à minha rotina.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-025** | O valor de área deve ser um número positivo com no máximo 2 casas decimais. Valores nulos, negativos ou com mais de 2 casas decimais são rejeitados. |
| **RN-026** | O horário de envio do resumo diário deve estar entre 05:00 e 10:00. Valores fora desse intervalo são rejeitados. |

**US-06  (2 SP)**

| *Como Agricultor, eu quero escolher os tipos de notificação que desejo receber e configurar limites de volume para não ser sobrecarregado por alertas não críticos.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-027** | O tipo de notificação deve ser um dos valores aceitos: ResumoDiario, TarefaAtrasada ou AlertaCritico. Tipos fora desse conjunto são rejeitados. |
| **RN-028** | O sistema não persiste mais de 5 notificações dos tipos ResumoDiario ou TarefaAtrasada para o mesmo Agricultor na mesma data. A tentativa além desse limite é rejeitada. |

**F-20  ·  Gestão de Membros da Propriedade**

| *Contexto: Uma vez cadastrada a Propriedade, o Proprietário precisa gerenciar quem opera o sistema em seu nome. O Cultiva Fácil adota um modelo de controle de acesso baseado em perfis (RBAC) com quatro papéis: Proprietário, Gestor, Peão e Financeiro. O Proprietário é criado automaticamente no momento do cadastro da Propriedade e é o único papel irrevogável. Os demais membros — coletivamente chamados de Funcionários — são adicionados por convite e podem ter seu tipo editado ou seu acesso revogado.* |
| :---- |
| *Valor entregue: Garantir que cada ação no sistema seja executada por um membro com o perfil adequado, protegendo a integridade das operações da Propriedade e evitando acessos indevidos.* |

**US-49  (3 SP)**

| *Como Proprietário ou Gestor, eu quero convidar um Agricultor por e-mail atribuindo um Tipo de Funcionário para que ele acesse a Propriedade como Funcionário com as permissões corretas desde o primeiro acesso.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-005** | O convite é realizado por e-mail. O usuário convidado recebe um link de ativação com validade de 72 horas para definir sua própria senha e ativar o acesso. |
| **RN-006** | O Proprietário pode convidar Funcionários de qualquer tipo: Gestor, Peão ou Financeiro. O Gestor pode convidar apenas Peão e Financeiro. Peão e Financeiro não podem convidar Funcionários. |
| **RN-007** | Se o e-mail convidado já possuir conta ativa na plataforma, o link de ativação vincula essa conta à Propriedade como Funcionário com o Tipo de Funcionário definido, sem criar nova conta. |
| **RN-008** | O Tipo de Funcionário atribuído no convite deve ser um dos valores aceitos: Gestor, Peão ou Financeiro. O papel de Proprietário não pode ser atribuído por convite — é concedido exclusivamente ao criador da Propriedade. |
| **RN-009** | Não é permitido ter dois Membros com o papel de Proprietário em uma mesma Propriedade. A tentativa de atribuir o papel de Proprietário a outro Agricultor é rejeitada. |
| **RN-010** | Quando o Tipo de Funcionário atribuído no convite for Peão, deve ser vinculada ao menos uma Zona da Propriedade ao convite. Convites de Peão sem Zona atribuída são rejeitados. Funcionários do tipo Gestor ou Financeiro, e o Proprietário, não possuem restrição de Zona. |

**US-50  (3 SP)**

| *Como Proprietário ou Gestor, eu quero editar o Tipo de Funcionário ou revogar o acesso de um Funcionário da Propriedade para manter o controle de quem opera o sistema.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-011** | O Proprietário pode editar o Tipo de Funcionário ou revogar o acesso de qualquer Funcionário da Propriedade. O Gestor pode editar ou revogar apenas Funcionários do tipo Peão ou Financeiro. |
| **RN-012** | O acesso do Proprietário não pode ser revogado por nenhum outro usuário. A tentativa de remoção do Proprietário é rejeitada. |
| **RN-013** | A alteração do Tipo de Funcionário entra em vigor na próxima sessão do Funcionário afetado. Sessões ativas não são interrompidas imediatamente. |
| **RN-014** | O Gestor pode alterar a Zona atribuída a um Peão a qualquer momento enquanto o acesso estiver ativo. A realocação de Zona entra em vigor imediatamente, sem aguardar nova sessão. |

**US-51  (2 SP)**

| *Como sistema, eu quero criar automaticamente o vínculo de Proprietário quando a Propriedade é cadastrada, para que o criador tenha acesso imediato como responsável principal sem etapas adicionais.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-051** | Ao concluir o cadastro da Propriedade, o sistema cria automaticamente um Proprietário vinculando o AgricultorId ao identificador da Propriedade com status Ativo. |
| **RN-052** | Cada Propriedade admite exatamente um Proprietário. A tentativa de criar um segundo Proprietário para a mesma Propriedade é rejeitada. |

**Épico 2 — Gestão de Terrenos**

**F-04  ·  Terrenos**

| *Contexto: O Terreno é a entidade física central do sistema. Sem ele, nenhuma funcionalidade de calendário, alerta ou estoque opera. Cada Terreno possui dados edáficos, climáticos e de luminosidade próprios que determinam o que pode ser plantado ali.* |
| :---- |
| *Valor entregue: Criar a base de dados do espaço físico que habilita todas as demais funcionalidades.* |

**US-07  (3 SP)**

| *Como Proprietário ou Gestor, eu quero cadastrar um Terreno informando seus dados físicos, edáficos e de luminosidade para que o sistema possa gerar recomendações adaptadas à sua realidade.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-029** | O nome do Terreno é obrigatório e deve ter entre 2 e 100 caracteres. |
| **RN-030** | A área do Terreno deve estar entre 50 m² e 100.000 ha. Valores fora desse intervalo são rejeitados. |
| **RN-031** | O tipo de solo deve ser um dos valores aceitos pelo Sistema Brasileiro de Classificação de Solos (SiBCS/EMBRAPA): Latossolo, Argissolo, Neossolo, Cambissolo, Gleissolo, Nitossolo, Vertissolo ou Plintossolo. |
| **RN-032** | O clima predominante deve ser um dos subtipos climáticos aceitos conforme a classificação de Köppen-Geiger para o Brasil: Tropical Úmido, Tropical Savânico, Tropical com Estação Seca no Verão, Semiárido, Subtropical Úmido, Subtropical de Altitude ou Subtropical com Inverno Seco. |
| **RN-033** | O pH, quando informado, deve estar entre 3,0 e 9,0. Quando omitido, o sistema assume o valor padrão 6,5. |
| **RN-034** | O índice de luminosidade, quando informado, representa a média de horas de insolação diária e deve estar entre 2 e 16 horas. Quando omitido, o campo permanece indefinido e não alimenta recomendações de cultivo dependentes de luminosidade. |

**US-08  (2 SP)**

| *Como Proprietário ou Gestor, eu quero editar os dados de um Terreno existente para manter as informações físicas e edáficas sempre atualizadas.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-035** | Todas as regras de validação aplicadas no cadastro — incluindo tipo de solo, clima predominante, índice de luminosidade e pH — aplicam-se igualmente à edição. |
| **RN-036** | A área do Terreno não pode ser reduzida para um valor inferior à soma das áreas das Zonas já cadastradas nele. |

**US-09  (2 SP)**

| *Como Proprietário ou Gestor, eu quero excluir um Terreno que não está mais em uso para manter o cadastro da Propriedade organizado.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-037** | Um Terreno só pode ser excluído se não possuir nenhum Cultivo com status ativo associado a nenhuma de suas Zonas. |

**F-05  ·  Zonas de Plantio**

| *Contexto: Cada Terreno é dividido em Zonas — recortes físicos onde as culturas crescem. A Situação da Zona reflete o que está acontecendo naquele espaço naquele momento: Vazia, com Cultivo ativo, Pronta para Colheita ou com Alerta.* |
| :---- |
| *Valor entregue: Dar ao Agricultor uma visão organizada do que está plantado e onde dentro de cada Terreno.* |

**US-10  (3 SP)**

| *Como Proprietário ou Gestor, eu quero criar Zonas dentro de um Terreno para organizar o que será cultivado em cada parte do espaço disponível.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-038** | Cada Terreno suporta no máximo 20 Zonas. A criação de uma 21ª Zona é rejeitada. |
| **RN-039** | A área de uma Zona deve ser de no mínimo 1 m² e não pode ultrapassar a área total do Terreno ao qual pertence. |
| **RN-040** | O nome da Zona deve ter entre 2 e 80 caracteres e ser único dentro do mesmo Terreno. |

**US-11  (2 SP)**

| *Como Proprietário ou Gestor, eu quero editar ou remover Zonas para ajustar a organização espacial do Terreno conforme meu planejamento evolui.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-041** | Uma Zona não pode ser removida enquanto possuir Cultivo com status ativo. |
| **RN-042** | A área editada de uma Zona deve respeitar os limites mínimo de 1 m² e máximo equivalente à área total do Terreno. |

**Épico 3 — Gestão de Cultivos**

**F-06  ·  Vínculo e Ciclo de Cultivo**

| *Contexto: Plantar é um compromisso com o tempo. Ao vincular uma cultura a uma Zona, inicia-se um Ciclo Agrícola com início, meio e fim. O sistema só aceita esse vínculo se a Zona estiver disponível e as restrições de descanso tiverem sido cumpridas. No momento do vínculo, o Proprietário ou Gestor declara a quantidade plantada — dado que inicializa a projeção do Celeiro e sustenta o cálculo de perdas e alertas ao longo de todo o ciclo.* |
| :---- |
| *Valor entregue: Controlar o início e o encerramento dos ciclos agrícolas garantindo a integridade do histórico de cultivo de cada Zona e fornecendo a base quantitativa para o acompanhamento produtivo do ciclo.* |

**US-12  (3 SP)**

| *Como Proprietário ou Gestor, eu quero vincular uma cultura a uma Zona declarando a quantidade plantada para iniciar um novo Ciclo Agrícola e permitir que o sistema acompanhe a evolução produtiva daquele espaço.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-043** | Uma Zona só pode receber um novo vínculo de cultura quando não houver nenhum vínculo ativo. Vincular uma cultura a uma Zona com Ciclo Agrícola ativo é rejeitado. |
| **RN-044** | A quantidade plantada é obrigatória no momento do vínculo e deve ser um valor positivo superior a zero com no máximo 2 casas decimais. |
| **RN-045** | A unidade de medida da quantidade plantada deve ser uma das opções aceitas: kg, g ou unidades. Uma vez confirmado o vínculo, a unidade de medida do ciclo não pode ser alterada. |
| **RN-046** | A quantidade plantada declarada no vínculo não pode ser alterada após a confirmação do Ciclo Agrícola. Ajustes na produção esperada ocorrem exclusivamente por meio dos Registros de Perda do Manejo Integrado de Pragas. |

**F-07  ·  Compatibilidade de Culturas**

| *Contexto: Algumas culturas se beneficiam quando cultivadas juntas — são Companheiras. Outras se prejudicam mutuamente — são Inimigas. O sistema conhece essas relações e orienta o Proprietário ou Gestor antes que uma combinação inadequada cause dano à produção.* |
| :---- |
| *Valor entregue: Prevenir perdas causadas por consórcios prejudiciais entre culturas.* |

**US-13  (3 SP)**

| *Como Proprietário ou Gestor, eu quero registrar um consórcio de culturas na mesma Zona e ser alertado quando a combinação for prejudicial para decidir conscientemente sobre o plantio.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-047** | O vínculo de uma cultura classificada como Inimiga em relação à cultura ativa da Zona é rejeitado pelo sistema. |
| **RN-048** | O vínculo entre culturas Inimigas pode ser confirmado mediante consentimento explícito do Agricultor, e essa decisão deve ser registrada com indicador de ciência do Agricultor. |
| **RN-049** | O vínculo entre culturas classificadas como Companheiras é registrado com status Companheira na relação de consórcio da Zona. |

**US-14  (2 SP)**

| *Como Proprietário ou Gestor, eu quero consultar o histórico de consórcios de uma Zona para embasar decisões de plantio conjunto em ciclos futuros.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-050** | O histórico de consórcios de uma Zona está disponível somente se aquela Zona possuir ao menos um vínculo de cultura registrado — ativo ou encerrado. |

**F-08  ·  Rotação de Culturas**

| *Contexto: Plantar a mesma cultura repetidamente no mesmo local esgota o solo. O Proprietário ou Gestor define, com base em sua experiência ou orientação técnica, um Intervalo de Descanso mínimo que a Zona deve cumprir antes de receber aquela cultura novamente.* |
| :---- |
| *Valor entregue: Proteger a qualidade do solo prevenindo o plantio precoce da mesma cultura em uma mesma Zona.* |

**US-15  (2 SP)**

| *Como Proprietário ou Gestor, eu quero definir um Intervalo de Descanso mínimo entre cultivos da mesma cultura em cada Zona para que o sistema proteja o solo da exaustão.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-051** | Um Intervalo de Descanso só pode ser cadastrado para uma Zona que possua ao menos um ciclo de cultivo encerrado para a cultura informada. Zonas sem histórico para aquela cultura não aceitam o cadastro. |
| **RN-052** | O valor do Intervalo de Descanso deve ser um número inteiro entre 1 e 365 dias. |

**US-16  (3 SP)**

| *Como Proprietário ou Gestor, eu quero ser bloqueado de vincular uma cultura a uma Zona antes que o Intervalo de Descanso definido tenha sido cumprido para garantir que o solo se recupere adequadamente.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-053** | A tentativa de vincular uma cultura a uma Zona dentro do Intervalo de Descanso definido — contado a partir da data de colheita do ciclo anterior — é rejeitada. |

**Épico 4 — Calendário e Planejamento**

**F-09  ·  Calendário Agrícola**

| *Contexto: O calendário é o recurso mais acessado diariamente. O sistema gera automaticamente as Tarefas base do ciclo ao vincular uma cultura a uma Zona. O Proprietário ou Gestor pode editar, excluir e criar Tarefas dentro dos limites do ciclo ativo.* |
| :---- |
| *Valor entregue: Dar clareza total sobre o que fazer e quando em cada Ciclo Agrícola.* |

**US-17  (3 SP)**

| *Como Proprietário ou Gestor, eu quero editar as Tarefas geradas automaticamente para o ciclo ativo para ajustá-las à realidade do meu campo.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-054** | O nome de uma Tarefa editada deve ter entre 2 e 100 caracteres. |
| **RN-055** | A data de uma Tarefa editada deve estar dentro do intervalo do ciclo ativo da Zona — entre a data de início e a data fim do ciclo. |

**US-18  (3 SP)**

| *Como Proprietário ou Gestor, eu quero criar Tarefas manuais no ciclo ativo para registrar atividades específicas que não foram geradas automaticamente.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-056** | A data de uma Tarefa manual deve estar entre a data atual e a data fim do ciclo ativo da Zona. Datas anteriores à data atual ou posteriores ao fim do ciclo são rejeitadas. |
| **RN-057** | O nome da Tarefa manual deve ter entre 2 e 100 caracteres. |

**US-19  (1 SP)**

| *Como Proprietário ou Gestor, eu quero excluir Tarefas do ciclo ativo que não são mais necessárias para manter o calendário limpo e relevante.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-058** | Apenas Tarefas pertencentes ao ciclo ativo da Zona podem ser excluídas. Tarefas de ciclos encerrados são imutáveis e não podem ser removidas. |

**F-19  ·  Janela de Plantio e Prévia de Ciclo**

| *Contexto: Antes de iniciar um ciclo, o Proprietário ou Gestor precisa saber quando é o momento ideal para plantar determinada cultura em uma Zona, levando em conta o tipo de solo da Zona, o subtipo climático predominante e os requisitos agronômicos da cultura. O sistema calcula e apresenta as Janelas de Plantio recomendadas para o ano corrente, indicando os períodos com maior probabilidade de sucesso. Ao selecionar uma janela, é apresentada uma prévia do ciclo — data estimada de colheita e principais Tarefas que serão geradas automaticamente — antes de confirmar o plantio.* |
| :---- |
| *Valor entregue: Substituir a intuição sobre época de plantio por recomendações agronômicas baseadas nas condições reais de cada Zona, reduzindo o risco de perdas causadas pelo início de ciclo em período desfavorável.* |

**US-44  (3 SP)**

| *Como Proprietário ou Gestor, eu quero consultar as Janelas de Plantio recomendadas para uma cultura em uma Zona para decidir o melhor momento de iniciar um novo ciclo.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-059** | A consulta de Janelas de Plantio só pode ser realizada para Zonas que estejam no estado Vazia — sem ciclo ativo. Zonas com ciclo ativo não apresentam recomendações de novo plantio. |
| **RN-060** | As Janelas de Plantio são calculadas com base no tipo de solo da Zona, no subtipo climático predominante e nos requisitos agronômicos da cultura consultada. A ausência de qualquer um desses dados torna a consulta indisponível para aquela Zona. |
| **RN-061** | O sistema apresenta no máximo 3 Janelas de Plantio recomendadas por cultura por Zona no ano corrente, ordenadas por nível de adequação: Ideal, Adequado ou Marginal. |

**US-45  (2 SP)**

| *Como Proprietário ou Gestor, eu quero visualizar a prévia de um ciclo ao selecionar uma Janela de Plantio para entender o cronograma estimado e as Tarefas que serão geradas antes de confirmar o plantio.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-062** | A prévia de ciclo só está disponível quando o Proprietário ou Gestor seleciona uma das Janelas de Plantio apresentadas pelo sistema. Prévias sem janela selecionada não são geradas. |
| **RN-063** | A prévia exibe: data estimada de início, data estimada de colheita e lista das Tarefas principais que serão geradas automaticamente ao confirmar o vínculo de cultura. |
| **RN-064** | A confirmação do vínculo de cultura a partir de uma Janela de Plantio selecionada respeita todas as regras de F-06 — incluindo obrigatoriedade da quantidade plantada e restrições de Intervalo de Descanso. |

**Épico 5 — Manejo Integrado de Pragas**

**F-10  ·  Monitoramento de Focos Fitossanitários**

| *Contexto: No Manejo Integrado de Pragas, a base de qualquer decisão de intervenção é a observação sistemática do campo. O Peão percorre as Zonas atribuídas e registra os Focos Fitossanitários identificados — eventos de praga, doença ou dano físico — classificando-os por tipo agronômico, nível de infestação e severidade. Esses registros constroem o histórico de pressão fitossanitária da Propriedade e sustentam as decisões de manejo ao longo do ciclo ativo.* |
| :---- |
| *Valor entregue: Substituir o relato informal de problemas por um registro estruturado de eventos fitossanitários que permite comparar ciclos, antecipar reincidências e justificar intervenções com dados concretos do campo.* |

**US-20  (3 SP)**

| *Como Peão, eu quero registrar um Foco Fitossanitário em uma Zona informando o tipo agronômico, o nível de infestação e a severidade, para documentar a pressão fitossanitária sobre o ciclo ativo.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-065** | Um Foco Fitossanitário só pode ser registrado em uma Zona que possua ciclo ativo. Zonas sem ciclo ativo não aceitam registro de foco. |
| **RN-066** | O tipo agronômico do Foco deve ser um dos valores aceitos: Praga, Doença ou DanoFísico. |
| **RN-067** | O nível de infestação deve ser um dos valores aceitos: Baixo, Médio ou Alto. |
| **RN-068** | A severidade do Foco deve ser um dos valores aceitos: Baixa, Média ou Alta. |
| **RN-069** | A descrição do Foco é obrigatória e deve ter entre 10 e 500 caracteres. |
| **RN-070** | Não é possível registrar dois Focos do mesmo tipo agronômico na mesma Zona na mesma data. |

**US-21  (2 SP)**

| *Como Peão, eu quero atualizar ou remover um Foco Fitossanitário registrado para corrigir informações equivocadas antes que o ciclo seja encerrado.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-071** | Um Foco Fitossanitário só pode ser atualizado ou removido enquanto o ciclo ativo da Zona à qual pertence não tiver sido encerrado. |
| **RN-072** | As mesmas regras de validação aplicadas no registro — tipo agronômico, nível de infestação, severidade e descrição — aplicam-se igualmente à atualização. |
| **RN-073** | A remoção de um Foco Fitossanitário que possua Registros de Perda vinculados é rejeitada. O Peão deve remover os Registros de Perda associados antes de excluir o Foco. |

**F-11  ·  Registro de Perda e Intervenção Fitossanitária**

| *Contexto: Identificado o Foco, o Peão quantifica o impacto real sobre a produção — registrando a quantidade estimada de produto perdido e a intervenção realizada em resposta ao problema. Cada Registro de Perda é vinculado a um Foco Fitossanitário da Zona e reduz progressivamente a quantidade esperada no Celeiro. Ao encerrar o ciclo, a soma de todas as perdas compõe o Relatório de Perdas da safra.* |
| :---- |
| *Valor entregue: Quantificar o impacto econômico das adversidades fitossanitárias, criar insumo direto para o cálculo da produção real esperada no Celeiro e embasar decisões de manejo nas safras seguintes.* |

**US-34  (3 SP)**

| *Como Peão, eu quero registrar a perda de produção associada a um Foco Fitossanitário informando a quantidade estimada perdida, a unidade de medida e a intervenção realizada, para que o Celeiro desconte essa perda da quantidade esperada do ciclo.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-074** | Um Registro de Perda deve estar obrigatoriamente vinculado a um Foco Fitossanitário previamente registrado na mesma Zona e no mesmo ciclo ativo. |
| **RN-075** | A quantidade estimada de perda deve ser um valor positivo superior a zero. |
| **RN-076** | A unidade de medida da perda deve coincidir com a unidade de medida do cultivo ativo da Zona. |
| **RN-077** | O tipo de intervenção realizada deve ser um dos valores aceitos: AplicaçãoDeDefensivo, PodaFitossanitária, ControleBiológico ou SemIntervenção. |
| **RN-078** | A soma acumulada das quantidades perdidas registradas em uma Zona não pode ultrapassar a quantidade plantada declarada para aquele ciclo. Registros que resultem em soma superior são rejeitados. |

**US-35  (2 SP)**

| *Como Peão, eu quero consultar o histórico de perdas registradas em uma Zona para acompanhar o impacto fitossanitário acumulado do ciclo ativo.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-079** | O histórico de perdas de uma Zona exibe exclusivamente os Registros de Perda vinculados ao ciclo selecionado — ativo ou encerrado. |
| **RN-080** | A consulta do histórico de um ciclo encerrado é permitida mas não admite alterações nos registros exibidos. |

**Épico 6 — Colheita e Estoque**

**F-12  ·  Registro de Colheita**

| *Contexto: A Colheita encerra o Ciclo Agrícola de uma Zona e consolida no Celeiro a quantidade efetivamente produzida. A quantidade colhida parte da projeção atual do Celeiro — quantidade plantada descontadas as perdas acumuladas pelo Manejo Integrado de Pragas — e deve ser consistente com esse histórico. O encerramento do ciclo aciona automaticamente a geração do Relatório de Perdas.* |
| :---- |
| *Valor entregue: Criar um inventário real da produção que reflita o trabalho concreto realizado no campo e feche formalmente o histórico fitossanitário e produtivo do ciclo.* |

**US-22  (3 SP)**

| *Como Peão, eu quero registrar a colheita de uma Zona informando quantidade, unidade de medida e destino para encerrar o ciclo e atualizar o Celeiro com o resultado real da safra.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-081** | Uma Colheita só pode ser registrada em uma Zona com Situação Pronta para Colheita. Registros em Zonas com Situação diferente são rejeitados. |
| **RN-082** | A quantidade colhida deve ser um número positivo superior a zero e não pode exceder a projeção atual do Celeiro para aquele ciclo — calculada como a quantidade plantada menos a soma de perdas registradas. |
| **RN-083** | O destino da colheita deve ser um dos valores aceitos: ConsumoPróprio, Venda, Cooperativa ou Descarte. |
| **RN-084** | A unidade de medida deve coincidir com a unidade de medida do cultivo ativo da Zona. |
| **RN-085** | O encerramento do Ciclo Agrícola ocorre no momento do registro da Colheita. O encerramento aciona automaticamente a consolidação do Relatório de Perdas do ciclo. Um ciclo não pode ser encerrado sem a quantidade colhida associada. |

**US-23  (2 SP)**

| *Como Proprietário ou Gestor, eu quero corrigir os dados de uma Colheita registrada para garantir a acurácia do inventário do Celeiro.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-086** | A correção de uma Colheita só é permitida enquanto o Ciclo Agrícola da Zona não tiver sido encerrado definitivamente. |
| **RN-087** | As mesmas regras de validação do registro original — quantidade, destino e unidade de medida — aplicam-se à correção. |

**F-13  ·  Controle do Celeiro com Projeção e Alertas**

| ⚙  FEATURE COMPLEXA   —   Incorpora: F-17  ·  Relatório de Produtividade |
| :---- |
| *Contexto: O Celeiro é a representação do potencial produtivo de cada ciclo da Propriedade. Desde o início do ciclo, o Celeiro registra a quantidade plantada como ponto de partida. À medida que Registros de Perda são lançados pelo Manejo Integrado de Pragas, essa projeção diminui progressivamente. O Proprietário ou Gestor pode definir uma Meta Comercializável para o ciclo — a quantidade mínima que espera destinar à venda ou cooperativa — e o sistema emite um Alerta de Projeção Abaixo do Esperado sempre que as perdas acumuladas indiquem que essa meta está em risco. Ao encerrar o ciclo, o Relatório de Perdas consolida tudo que foi perdido ao longo da safra. Os Relatórios de Produtividade transformam o histórico de colheitas em dados analíticos por período e cultura.* |
| *Valor entregue: Oferecer ao Proprietário ou Gestor visibilidade contínua sobre o quanto da sua safra ainda pode ser salvo, com alertas preventivos antes do encerramento do ciclo e relatórios analíticos ao final de cada safra.* |

**US-24  (2 SP)**

| *Como Proprietário ou Gestor, eu quero visualizar o estado atual do Celeiro de uma Zona para acompanhar a quantidade plantada, as perdas acumuladas e a projeção real de produção do ciclo ativo.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-088** | O Celeiro exibe para cada Zona com ciclo ativo: a quantidade plantada declarada, a soma das perdas registradas pelo Manejo Integrado de Pragas e a projeção atual — calculada como quantidade plantada menos perdas acumuladas. |
| **RN-089** | Ajustes manuais diretos na quantidade plantada ou na projeção do Celeiro não são permitidos. Esses valores só se alteram por meio de registros originados no ciclo de cultivo ou nos Registros de Perda. |

**US-36  (3 SP)**

| *Como Proprietário ou Gestor, eu quero definir uma Meta Comercializável para o ciclo ativo de uma Zona para que o sistema me avise quando as perdas acumuladas indicarem que essa meta está em risco.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-090** | A Meta Comercializável deve ser um valor positivo superior a zero e inferior ou igual à quantidade plantada declarada para o ciclo. |
| **RN-091** | A Meta Comercializável só pode ser definida ou atualizada enquanto o ciclo da Zona estiver ativo. |
| **RN-092** | O sistema emite um Alerta de Projeção Abaixo do Esperado sempre que a projeção atual do Celeiro — quantidade plantada menos perdas acumuladas — for inferior à Meta Comercializável definida. |
| **RN-093** | O sistema não emite mais de um Alerta de Projeção Abaixo do Esperado para a mesma Zona em um intervalo de 24 horas. |

**US-25  (3 SP)**

| *Como Proprietário ou Gestor, eu quero registrar a saída de produtos do Celeiro para manter o estoque sempre fiel ao que realmente tenho disponível após a colheita.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-094** | A quantidade de saída não pode ser superior ao saldo disponível do produto no Celeiro no momento do registro. |
| **RN-095** | O motivo da saída deve ser um dos valores aceitos: Consumo, Venda, Doação ou Descarte. Saídas classificadas como Perda devem ser originadas exclusivamente por Registros de Perda do Manejo Integrado de Pragas. |

**US-37  (2 SP)**

| *Como Proprietário ou Gestor, eu quero consultar o Relatório de Perdas de um ciclo encerrado para entender o volume total perdido, os tipos fitossanitários responsáveis e as intervenções realizadas.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-096** | O Relatório de Perdas só está disponível para ciclos encerrados. Ciclos ativos não geram relatório consolidado. |
| **RN-097** | O Relatório de Perdas exibe: quantidade plantada, soma total de perdas, projeção original antes das perdas, quantidade efetivamente colhida e detalhamento por tipo agronômico de foco. |

**US-26  (2 SP)**

| *Como Proprietário ou Gestor, eu quero salvar configurações de relatório de produtividade com nome e período para consultar minha produção rapidamente no formato que prefiro.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-098** | O nome da configuração é obrigatório e deve ter entre 2 e 100 caracteres. Nomes duplicados para o mesmo Agricultor são rejeitados. |
| **RN-099** | O filtro de período deve ser um dos valores aceitos: UltimoMes, Trimestre, Semestre ou Ano. |
| **RN-100** | Um Agricultor pode ter no máximo 5 configurações de relatório salvas simultaneamente. |

**US-27  (3 SP)**

| *Como Proprietário ou Gestor, eu quero consultar a produção consolidada com base em uma configuração salva para entender minha produtividade por período e cultura.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-101** | Uma consulta de relatório de produtividade só pode ser executada a partir de uma configuração salva válida. Consultas sem configuração persistida associada são rejeitadas. |

**Épico 7 — Finanças Fácil**

**F-14  ·  Plano de Insumos**

| *Contexto: Sementes, fertilizantes e defensivos precisam estar disponíveis no momento certo. O Proprietário ou Gestor antecipa o que precisará mês a mês por Zona. Quando adquire o insumo, registra o custo real — e esse registro torna-se permanente.* |
| :---- |
| *Valor entregue: Evitar interrupções de ciclo por falta de material e preservar o histórico financeiro real de cada safra.* |

**US-28  (3 SP)**

| *Como Proprietário ou Gestor, eu quero criar um Plano de Insumos mensal para uma Zona para antecipar o que preciso adquirir antes do próximo ciclo.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-102** | O mês de referência do plano deve ser igual ou posterior ao mês atual. Registros para meses passados são rejeitados. |
| **RN-103** | Não é permitido criar dois planos de insumos para a mesma Zona no mesmo mês de referência. |
| **RN-104** | O tipo de insumo deve ser um dos valores aceitos: Semente, Fertilizante ou Defensivo. |
| **RN-105** | A quantidade de insumo deve ser um valor positivo e superior a zero. |

**US-29  (3 SP)**

| *Como Proprietário ou Gestor, eu quero registrar a aquisição de um item de insumo com o custo real pago para manter o controle financeiro da safra.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-106** | A transição de um item para o status Adquirido exige o preenchimento do Preço Unitário. A alteração de status sem esse valor é rejeitada. |
| **RN-107** | Um item com status Adquirido e data de registro confirmada não pode retornar ao status Planejado. A reversão é rejeitada para preservar a integridade do histórico financeiro. |

**US-30  (2 SP)**

| *Como Proprietário ou Gestor, eu quero consultar o histórico de insumos adquiridos por Zona para entender os custos reais de cada safra encerrada.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-108** | O histórico de insumos de uma Zona abrange exclusivamente os itens com status Adquirido de planos vinculados a ciclos já encerrados naquela Zona. |

**US-46  (2 SP)**

| *Como Peão, eu quero registrar o consumo de um insumo adquirido em uma Zona para manter o saldo de estoque atualizado e permitir que o sistema monitore o ponto de reposição.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-109** | O consumo só pode ser registrado para tipos de insumo com ao menos uma aquisição registrada naquela Zona. Insumos sem aquisição prévia não aceitam registro de consumo. |
| **RN-110** | A quantidade consumida não pode exceder o saldo disponível atual do insumo na Zona. O saldo é calculado como: soma das quantidades adquiridas menos soma das quantidades já consumidas registradas. |
| **RN-111** | A unidade de medida do consumo deve coincidir com a unidade de medida da aquisição do insumo na Zona. Registros com unidade divergente são rejeitados. |

**US-47  (2 SP)**

| *Como Gestor, eu quero configurar um limite mínimo de estoque por tipo de insumo em uma Zona para que o sistema acione automaticamente um pedido de reposição ao atingir esse ponto.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-112** | O limite mínimo de estoque deve ser um valor positivo superior a zero, na mesma unidade de medida do insumo cadastrado na Zona. |
| **RN-113** | O e-mail do destinatário do pedido de reposição é obrigatório para ativar a configuração de limite. Configurações sem e-mail de destinatário são rejeitadas. |
| **RN-114** | É permitida apenas uma configuração de limite ativa por tipo de insumo por Zona. A tentativa de criar uma segunda configuração para o mesmo insumo na mesma Zona é rejeitada. |
| **RN-115** | O limite mínimo só pode ser configurado para tipos de insumo com ao menos uma aquisição registrada na Zona. |

**US-48  (2 SP)**

| *Como Gestor, eu quero que o sistema envie automaticamente um pedido de reposição por e-mail quando o saldo de um insumo atingir o limite mínimo configurado, para garantir a continuidade do ciclo sem interrupção.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-116** | O pedido automatizado é emitido quando o saldo do insumo atingir ou ficar abaixo do limite mínimo configurado. O saldo é calculado como: soma das quantidades adquiridas menos soma das quantidades consumidas registradas na Zona. |
| **RN-117** | O sistema não emite mais de um pedido automático do mesmo tipo de insumo na mesma Zona em um intervalo de 24 horas. |
| **RN-118** | O pedido gerado contém: tipo de insumo, Zona de origem, saldo atual no momento do disparo, quantidade sugerida de reposição — calculada como quantidade do plano mensal ativo menos o saldo atual — e e-mail do destinatário configurado. |
| **RN-119** | O envio do pedido gera um registro de PedidoReposição no histórico da Zona com: data e hora do disparo, saldo no momento do acionamento, destinatário e status de envio. |
| **RN-120** | Pedidos de reposição automáticos só são gerados para Zonas com configuração de limite ativa e insumo com saldo monitorado. Zonas sem configuração de limite não geram pedidos. |

**F-15  ·  Escrituração Financeira e Fluxo de Caixa**

| *Contexto: A gestão da safra não termina no campo — ela passa pelas contas. O Financeiro ou Proprietário precisa registrar cada receita obtida (venda de produto, cooperativa, subsídio) e cada despesa incorrida (mão de obra, frete, aluguel de equipamento, energia) ao longo do ciclo produtivo. Com esses lançamentos, o sistema compõe o Fluxo de Caixa do período, mostrando entradas, saídas e o saldo acumulado mês a mês. Lançamentos de insumos adquiridos pelo Plano de Insumos alimentam automaticamente as despesas, evitando retrabalho.* |
| :---- |
| *Valor entregue: Dar ao Financeiro ou Proprietário uma visão financeira real e contínua da safra, permitindo identificar meses de caixa negativo antes que representem um problema de capital de giro.* |

**US-38  (3 SP)**

| *Como Financeiro ou Proprietário, eu quero registrar lançamentos de receita e despesa na minha escrituração financeira para manter o controle do fluxo de caixa da safra.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-121** | O tipo do lançamento deve ser Receita ou Despesa. |
| **RN-122** | A categoria da Receita deve ser um dos valores aceitos: VendaDireta, Cooperativa, Subsídio ou OutraReceita. |
| **RN-123** | A categoria da Despesa deve ser um dos valores aceitos: MãoDeObra, Frete, AluguelDeEquipamento, Energia, Arrendamento ou OutraDespesa. Despesas com insumos são originadas exclusivamente pelo Plano de Insumos e não podem ser lançadas manualmente nesta categoria. |
| **RN-124** | O valor do lançamento deve ser um número positivo superior a zero com no máximo 2 casas decimais. |
| **RN-125** | A data do lançamento não pode ser posterior à data atual do registro. |

**US-39  (3 SP)**

| *Como Financeiro ou Proprietário, eu quero visualizar o Fluxo de Caixa por período para acompanhar o saldo acumulado de entradas e saídas da minha operação.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-126** | O Fluxo de Caixa consolida todas as Receitas e Despesas lançadas no período selecionado, incluindo as despesas de insumos originadas pelo Plano de Insumos. |
| **RN-127** | O filtro de período deve ser um dos valores aceitos: MêsAtual, Trimestre, Semestre ou Ano. |
| **RN-128** | O Fluxo de Caixa exibe: total de receitas, total de despesas e saldo do período. Períodos com saldo negativo são destacados como Deficitários. |

**F-16  ·  Demonstrações Financeiras e Indicadores**

| ⚙  FEATURE COMPLEXA   —   Feature que incorporou o escopo de outra feature para reduzir o total do backlog. Story points e regras de negócio foram reorganizados de forma condizente. |
| :---- |
| *Contexto: Com a escrituração completa, o Financeiro ou Proprietário tem acesso às três demonstrações financeiras essenciais para avaliar a saúde econômica da atividade: a Demonstração do Resultado do Exercício (DRE), que apura o resultado líquido da safra; o Balanço Patrimonial simplificado, que confronta o que a Propriedade possui e o que deve; e os Indicadores Financeiros que traduzem esses números em linguagem de campo — margem bruta, custo por hectare e retorno sobre o investimento da safra. Todas as demonstrações são geradas a partir da escrituração lançada, sem necessidade de entrada adicional de dados.* |
| *Valor entregue: Transformar os registros financeiros cotidianos em demonstrações que revelam se a atividade agrícola está sendo economicamente viável, e onde estão as oportunidades de melhoria de margem.* |

**US-40  (3 SP)**

| *Como Financeiro ou Proprietário, eu quero consultar a Demonstração do Resultado do Exercício (DRE) de um período para saber se minha safra gerou lucro ou prejuízo.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-129** | A DRE só pode ser gerada para períodos que possuam ao menos um lançamento de receita e um de despesa registrados. Períodos sem movimentação completa resultam em DRE indisponível. |
| **RN-130** | A DRE apura: Receita Bruta, total de Despesas Operacionais e Resultado Líquido do período. O Resultado Líquido é calculado como Receita Bruta menos total de Despesas Operacionais. |
| **RN-131** | O filtro de período para geração da DRE deve ser um dos valores aceitos: Trimestre, Semestre ou Ano. |

**US-41  (3 SP)**

| *Como Financeiro ou Proprietário, eu quero consultar o Balanço Patrimonial simplificado e os Indicadores Financeiros para avaliar a saúde econômica da minha atividade.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-132** | O Balanço Patrimonial exibe: Ativo Circulante (saldo de caixa do período), Ativo Imobilizado (valor declarado dos terrenos e equipamentos cadastrados) e Passivo Circulante (despesas não pagas registradas no período). |
| **RN-133** | Os Indicadores Financeiros calculados são: Margem Bruta Agrícola (Receita Bruta menos Custo dos Insumos, dividido pela Receita Bruta), Custo por Hectare (total de despesas dividido pela área total das Zonas ativas) e Retorno sobre Investimento da Safra (Resultado Líquido dividido pelo total de despesas). |
| **RN-134** | Os Indicadores Financeiros só são exibidos quando existirem ao menos uma colheita encerrada e lançamentos financeiros no mesmo período. Indicadores sem base de cálculo suficiente são sinalizados como Indisponíveis. |

**Épico 8 — Clima e Alertas**

**F-17  ·  Limites Climáticos, Irrigação e Alertas**

| ⚙  FEATURE COMPLEXA   —   Incorpora: F-15  ·  Alertas Climáticos |
| :---- |
| *Contexto: O Proprietário ou Gestor conhece o que cada cultura suporta e define dois tipos de limites por Zona: os Limites Climáticos de temperatura e precipitação, e a Necessidade Hídrica mínima do cultivo ativo. Quando a previsão meteorológica indica que os limites de temperatura ou precipitação serão ultrapassados, o sistema emite um Alerta Climático direcionado. Quando a precipitação acumulada nos últimos dias cai abaixo da Necessidade Hídrica definida para a cultura, o sistema emite um Alerta de Necessidade de Irrigação — sinalizando que a Zona precisa de irrigação manual para não comprometer o ciclo.* |
| *Valor entregue: Agir preventivamente antes que o clima adverso cause dano à produção e antecipar a necessidade de irrigação antes que a cultura sofra déficit hídrico irreversível.* |

**US-31  (3 SP)**

| *Como Proprietário ou Gestor, eu quero cadastrar os limites climáticos toleráveis e a necessidade hídrica mínima para a cultura ativa em cada Zona para que o sistema identifique riscos com base na previsão e na precipitação acumulada.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-135** | Limites climáticos e Necessidade Hídrica só podem ser cadastrados em uma Zona que possua vínculo de cultura ativo. Zonas sem cultura ativa não aceitam esses cadastros. |
| **RN-136** | O limite de temperatura deve estar entre \-5°C e 50°C, intervalo agronomicamente válido para culturas no Brasil. |
| **RN-137** | O limite de precipitação deve estar entre 1 mm e 300 mm/24h. |
| **RN-138** | A Necessidade Hídrica mínima deve ser um valor entre 1 mm e 150 mm por janela de observação. A janela de observação deve ser um dos valores aceitos: 3Dias, 5Dias ou 7Dias. |

**US-32  (2 SP)**

| *Como Proprietário ou Gestor, eu quero editar ou remover os limites climáticos e a necessidade hídrica de uma Zona para ajustá-los conforme o cultivo evolui.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-139** | Limites climáticos e Necessidade Hídrica só podem ser editados ou removidos enquanto a Zona possuir vínculo de cultura ativo para o qual foram cadastrados. |

**US-33  (3 SP)**

| *Como Proprietário ou Gestor, eu quero receber Alertas Climáticos quando a previsão ultrapassar os limites que defini, e Alertas de Irrigação quando a precipitação acumulada cair abaixo da necessidade hídrica da cultura, para agir antes que o dano aconteça.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-140** | Um Alerta Climático só é gerado para Zonas que possuam simultaneamente vínculo de cultura ativo e limites climáticos cadastrados. |
| **RN-141** | O sistema não gera mais de um Alerta Climático do mesmo tipo — temperatura ou precipitação — para a mesma Zona em um intervalo de 24 horas. |
| **RN-142** | Um Alerta de Necessidade de Irrigação só é gerado para Zonas que possuam simultaneamente vínculo de cultura ativo e Necessidade Hídrica cadastrada. |
| **RN-143** | O Alerta de Necessidade de Irrigação é emitido quando a precipitação acumulada na janela de observação definida for inferior à Necessidade Hídrica mínima cadastrada para aquela Zona. |
| **RN-144** | O sistema não gera mais de um Alerta de Necessidade de Irrigação para a mesma Zona em um intervalo de 24 horas. |

**F-18  ·  Consulta de Previsão do Tempo e Histórico Climático**

| *Contexto: Além de receber alertas, o Agricultor precisa consultar ativamente os dados climáticos para tomar decisões estratégicas — definir quando plantar, colher ou aplicar insumos. O sistema disponibiliza a previsão dos próximos 7 dias para a região de cada Zona e mantém um Histórico Climático por Zona construído a partir dos ciclos encerrados. Com esse histórico, o Agricultor identifica padrões sazonais, compara ciclos anteriores e embasa o planejamento de safras futuras com dados da própria Propriedade.* |
| :---- |
| *Valor entregue: Transformar o sistema de reativo em proativo — dar ao Agricultor acesso antecipado às condições climáticas previstas e a memória climática acumulada da Propriedade para que cada decisão de plantio seja baseada em dados, não em intuição.* |

**US-42  (3 SP)**

| *Como Agricultor, eu quero consultar a previsão do tempo dos próximos 7 dias para a região das minhas Zonas para planejar atividades de campo com antecedência.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-145** | A previsão do tempo só pode ser consultada para Zonas pertencentes a uma Propriedade com Perfil Completo e localização — município e estado — devidamente preenchidos. |
| **RN-146** | A previsão exibe os dados dos próximos 7 dias: temperatura mínima e máxima, precipitação estimada em mm e condição geral do dia. A condição geral deve ser um dos valores: Ensolarado, Parcialmente Nublado, Nublado, Chuvoso ou Tempestuoso. |
| **RN-147** | Quando a previsão indicar temperatura ou precipitação que ultrapasse os Limites Climáticos cadastrados para a Zona, o dia correspondente é destacado como dia de risco na consulta. |

**US-43  (2 SP)**

| *Como Agricultor, eu quero consultar o Histórico Climático de uma Zona filtrado por período para identificar padrões sazonais e comparar as condições de ciclos anteriores.* |
| :---- |

**REGRAS DE NEGÓCIO**

| ID | Regra de Negócio |
| :---- | :---- |
| **RN-148** | O Histórico Climático de uma Zona está disponível somente para Zonas que possuam ao menos um ciclo de cultivo encerrado. Zonas sem histórico de ciclos não geram consulta. |
| **RN-149** | O filtro de período do Histórico Climático deve ser um dos valores aceitos: UltimaSafra, Trimestre, Semestre ou Ano. |

