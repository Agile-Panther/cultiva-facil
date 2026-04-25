

# **CULTIVA FÁCIL**

**Modelagem do Domínio v4**

*Subdomínios Maven · Agregados · Value Objects · Eventos · Invariantes · Domínio Compartilhado · RBAC*

| Projeto | Cultiva Fácil — Plataforma de Gestão Agrícola |
| :---- | :---- |
| **Documento** | Modelagem do Domain Layer |
| **Versão** | v3.4 · Abril 2026 · Subdomínios Maven \+ Domínio Compartilhado \+ Organização por Agregado · Alinhado ao Backlog v5.1 |
| **Features implementadas** | F-01 a F-10, F-12, F-13, F-14, F-17, F-20 — 15 features do MVP |
| **Features fora do escopo** | F-11, F-15, F-16, F-18, F-19 — não implementadas nesta entrega |
| **Subdomínios (módulos Maven)** | 9 módulos: cultivafacil-dominio-compartilhado · acesso · terreno · cultivo · agenda · sanidade · colheita · insumo · clima |
| **Módulo Compartilhado** | cultivafacil-dominio-compartilhado — IDs tipados (AgricultorId, ZonaId, CicloAgricolaId) \+ infraestrutura de eventos |
| **Referência de implementação** | profsauloaraujo/sgb-2025-01 (SGB — Sistema de Gerenciamento de Biblioteca) |
| **Entregas** | Entrega 1 (24/04): domain layer completo · Entrega 2 (18/06): todas as camadas |

**Tabela de Mudanças — v3.3 → v4**

| Seção | Mudança | Impacto |
| :---- | :---- | :---- |
| **Seção 3** | Reestruturação arquitetural do domínio: adoção de subdomínios Maven com organização interna por agregado  | Todos os membros: criar os diretórios por agregado ao implementar as classes Java. |

# **1\. Como Ler Este Documento**

Este documento descreve o modelo de domínio técnico do Cultiva Fácil: os artefatos DDD táticos que cada membro deve implementar para suas features. A versão 3.3 reestrutura os módulos Maven em subdomínios independentes seguindo o padrão SGB, e renomeia o Shared Kernel para Domínio Compartilhado.

| Seção | O que contém | Para que serve |
| :---- | :---- | :---- |
| **Badge do Subdomínio** | Nome, responsável, features e módulo Maven | Orientar onde cada classe vive |
| **Tabela de artefatos** | ARs, Entidades, VOs, Eventos, Serviços, DC, Abstract Class — com nome Java e dono | Guia de implementação por membro |
| **Tabela de invariantes** | Cada RN mapeada ao elemento que a protege e ao código de erro | Base para escrever os testes JUnit |
| **Regra de referência** | Como os subdomínios importam IDs via cultivafacil-dominio-compartilhado | Evitar acoplamento entre módulos |

| Legenda de tipos de artefato AR (Agregado) — Aggregate Root. Controla a consistência do grupo. Único ponto de entrada para mutação. AC (Abstract Class) — Classe abstrata base. Define estrutura e contrato comum a hierarquias de ARs. Entidade — Tem identidade própria (ID). Vive dentro de um Agregado e só é acessada por ele. VO (Value Object) — Imutável, sem identidade. Validado no construtor. Classe Java simples (não record). DE (Domain Event) — Classe estática interna do AR. Registra algo que aconteceu no domínio. DS (Domain Service) — Lógica que envolve mais de um Agregado ou requer consulta ao repositório. RP (Repositório) — Interface Java pura no módulo domain. Implementação fica na camada infrastructure. DC (Domínio Compartilhado) — ID tipado que vive no módulo cultivafacil-dominio-compartilhado. Importado diretamente por qualquer subdomínio. |
| :---- |

# **2\. Convenções de Implementação**

## **2.1 Value Object — Classe Simples com Validação**

| // Exemplo: Email.java — subdomínio acesso import org.apache.commons.lang3.Validate; public class Email {     private final String valor;     public Email(String valor) {         Validate.notBlank(valor, "Email nao pode ser vazio");         Validate.isTrue(valor.matches("^\[^@\]+@\[^@\]+\\\\.\[^@\]+$"), "Formato invalido");         this.valor \= valor.toLowerCase().trim();     }     public String getValor() { return valor; } } |
| :---- |

## **2.2 Identificador Tipado — Domínio Compartilhado**

| // cultivafacil-dominio-compartilhado // pacote: br.edu.cesar.cultivafacil.terreno.zona public final class ZonaId {     private final UUID valor;     public ZonaId(UUID valor) { Validate.notNull(valor, "ZonaId nao pode ser nulo"); this.valor \= valor; }     public static ZonaId novo() { return new ZonaId(UUID.randomUUID()); }     public UUID getValor() { return valor; }     @Override public boolean equals(Object o) { if (\!(o instanceof ZonaId)) return false; return valor.equals(((ZonaId) o).valor); }     @Override public int hashCode() { return valor.hashCode(); }     @Override public String toString() { return valor.toString(); } } |
| :---- |

## **2.3 Aggregate Root — Construtor Duplo e Setters Privados**

| // Construtor de criação (sem ID) \+ construtor de reconstituição (com ID) public class Zona {     private ZonaId id;     private NomeZona nome;     private SituacaoZona situacao;     public Zona(NomeZona nome, AreaZona area) {        // criação         this.id \= ZonaId.novo();         this.nome \= nome; this.situacao \= SituacaoZona.VAZIA;     }     public Zona(ZonaId id, NomeZona nome, SituacaoZona situacao) { // reconstituição         this.id \= id; this.nome \= nome; this.situacao \= situacao;     } } |
| :---- |

## **2.4 Domain Event — Classe Estática Interna**

| // Dentro de Funcionario.java (AR) public class Funcionario extends MembroPropriedade {     public static class FuncionarioConvidado {         public final FuncionarioId funcionarioId;         public final AgricultorId agricultorId;         public final TipoFuncionario tipo;         public FuncionarioConvidado(FuncionarioId f, AgricultorId a, TipoFuncionario t) {             this.funcionarioId \= f; this.agricultorId \= a; this.tipo \= t;         }     } } |
| :---- |

## **2.5 Repositório — Interface Pura no Domain**

| // FuncionarioRepositorio.java — subdomínio acesso import java.util.List; import java.util.Optional; public interface FuncionarioRepositorio {     void salvar(Funcionario funcionario);     Optional\<Funcionario\> buscarPorId(FuncionarioId id);     List\<Funcionario\> listarPorPropriedade(PropriedadeId propriedadeId); } |
| :---- |

## **2.6 Enums — SCREAMING\_SNAKE\_CASE no Pacote do Subdomínio**

| public enum TipoFuncionario { GESTOR, PEAO, FINANCEIRO } public enum PerfilAcesso    { PROPRIETARIO, GESTOR, PEAO, FINANCEIRO } public enum StatusMembro    { CONVIDADO, ATIVO, INATIVO } |
| :---- |

## **2.7 Domínio Compartilhado — Módulo cultivafacil-dominio-compartilhado**

| Regra do Domínio Compartilhado: cultivafacil-dominio-compartilhado contém APENAS IDs tipados de agregados referenciados por múltiplos subdomínios e a infraestrutura de eventos (EventoBarramento, EventoObservador). Cada classe mantém o pacote do subdomínio ao qual pertence conceitualmente — não há pacote "compartilhado" flat. Seguindo o padrão SGB. |
| :---- |

| Classe | Pacote de origem | Dono original | Subdomínios consumidores |
| :---- | :---- | :---- | :---- |
| **AgricultorId** | br.edu.cesar.cultivafacil.acesso.agricultor | Julia (F-01) | terreno, insumo |
| **ZonaId** | br.edu.cesar.cultivafacil.terreno.zona | Jera (F-05) | cultivo, agenda, sanidade, colheita, insumo, clima |
| **CicloAgricolaId** | br.edu.cesar.cultivafacil.cultivo.ciclo | Vinicius (F-06) | agenda, sanidade, colheita |
| **EventoBarramento** | br.edu.cesar.cultivafacil.evento | — | todos os subdomínios |
| **EventoObservador** | br.edu.cesar.cultivafacil.evento | — | todos os subdomínios |

## **2.8 Separação Identidade \+ Role (RBAC)**

| LEITURA OBRIGATÓRIA antes de implementar qualquer classe de perfil de usuário. |
| :---- |

O Cultiva Fácil adota o padrão RBAC (Role-Based Access Control) com separação explícita entre identidade e autorização. Essa decisão arquitetural afeta diretamente como as classes do subdomínio acesso são implementadas e como o Spring Security é configurado na Entrega 2\.

| Camada | Responsabilidade | Artefato principal |
| :---- | :---- | :---- |
| Domain Layer  | Identidade do usuário (quem você é) | Agricultor (AR) — sem papel |
| Domain Layer  | Vínculo do Agricultor a uma Propriedade com um papel | MembroPropriedade (AC) → Proprietario / Funcionario |
| Security Layer  | Verificar se o papel autoriza a operação | @PreAuthorize \+ GrantedAuthority via PerfilAcesso |

**O que o domínio NÃO faz**

| //  ERRADO — domínio nunca faz if/switch por perfil: if (membro instanceof Gestor gestor && gestor.podeConvidar()) { ... } if (membro.getPerfil() \== PerfilAcesso.GESTOR) { ... } //  CORRETO — Spring Security intercepta antes do domínio (Entrega 2): @PreAuthorize("hasRole('GESTOR')") public void convidarFuncionario(AgricultorId convidadoId, ...) { ... } |
| :---- |

**Hierarquia de classes no domínio**

| // MembroPropriedade.java — subdomínio acesso public abstract class MembroPropriedade {     protected final AgricultorId agricultorId;  // importado de cultivafacil-dominio-compartilhado     protected final PropriedadeId propriedadeId;     protected StatusMembro status;     protected MembroPropriedade(AgricultorId agricultorId, PropriedadeId propriedadeId) {         Validate.notNull(agricultorId, "agricultorId obrigatorio");         Validate.notNull(propriedadeId, "propriedadeId obrigatorio");         this.agricultorId  \= agricultorId;         this.propriedadeId \= propriedadeId;     }     public abstract PerfilAcesso getPerfilAcesso(); } |
| :---- |

| // Proprietario.java — AR (subdomínio acesso) public class Proprietario extends MembroPropriedade {     private final ProprietarioId id;     public Proprietario(AgricultorId agricultorId, PropriedadeId propriedadeId) {         super(agricultorId, propriedadeId);         this.id \= ProprietarioId.novo(); this.status \= StatusMembro.ATIVO;     }     public Proprietario(ProprietarioId id, AgricultorId a, PropriedadeId p, StatusMembro s) {         super(a, p); this.id \= id; this.status \= s;     }     @Override public PerfilAcesso getPerfilAcesso() { return PerfilAcesso.PROPRIETARIO; }     public static class PropriedadeAssumida {         public final ProprietarioId proprietarioId; public final AgricultorId agricultorId;         public PropriedadeAssumida(ProprietarioId p, AgricultorId a) { ... }     } } |
| :---- |

| // Funcionario.java — AR (subdomínio acesso) public class Funcionario extends MembroPropriedade {     private final FuncionarioId id;     private TipoFuncionario tipo;     private ZonaId zonaAtribuida; // importado de cultivafacil-dominio-compartilhado     // ... construtores, métodos e Domain Events (FuncionarioConvidado, TipoAlterado, AcessoRevogado, ZonaRealocada)     @Override public PerfilAcesso getPerfilAcesso() { return PerfilAcesso.valueOf(tipo.name()); } } |
| :---- |

**Configuração Spring Security — Entrega 2 (referência)**

| @PreAuthorize("hasRole('PROPRIETARIO') or hasRole('GESTOR')") public ResponseEntity\<?\> convidarFuncionario(@RequestBody ConviteRequest req) { ... } @PreAuthorize("hasRole('PEAO')") public ResponseEntity\<?\> registrarColheita(@RequestBody ColheitaRequest req) { ... } @PreAuthorize("hasRole('FINANCEIRO') or hasRole('PROPRIETARIO')") public ResponseEntity\<?\> consultarDRE(@RequestParam String periodo) { ... } |
| :---- |

## **2.9 Testes Unitários — JUnit 5 \+ Mockito**

Os testes do domain layer seguem o padrão SGB: classes por agregado, anotadas com @ExtendWith(MockitoExtension.class), usando @Mock para repositórios e @InjectMocks para serviços de domínio. Value Objects são testados diretamente. Cada @Test valida um único comportamento.  
**2.9.1  Teste de Value Object  sem mock**  
Value Objects não dependem de nada externo. O teste instancia diretamente e verifica que invariantes são lançadas como IllegalArgumentException.

| // EmailTest.java — subdomínio acesso/agricultor/ |
| :---- |
| import org.junit.jupiter.api.Test; |
| import static org.junit.jupiter.api.Assertions.\*; |
|   |
| class EmailTest { |
|   |
|     @Test |
|     void deveAceitarEmailValido() { |
|         var email \= new Email("agricultor@fazenda.com"); |
|         assertEquals("agricultor@fazenda.com", email.getValor()); |
|     } |
|   |
|     @Test |
|     void deveRejeitarEmailEmBranco() { |
|         assertThrows(IllegalArgumentException.class, |
|             () \-\> new Email("")); |
|     } |
|   |
|     @Test |
|     void deveRejeitarEmailSemArroba() { |
|         assertThrows(IllegalArgumentException.class, |
|             () \-\> new Email("semformato.com")); |
|     } |
| } |

**2.9.2  Teste de Aggregate Root com regra de negócio**  
Testa invariantes do AR isoladamente. O AR não chama repositório apenas o Application Service faz isso. Nenhum mock necessário.

| // FuncionarioTest.java — subdomínio acesso/membro/ |
| :---- |
| import org.junit.jupiter.api.Test; |
| import static org.junit.jupiter.api.Assertions.\*; |
|   |
| class FuncionarioTest { |
|   |
|     // F-20 RN-008: TipoFuncionario nulo rejeitado no construtor |
|     @Test |
|     void deveRejeitarTipoFuncionarioNulo() { |
|         var agId \= new AgricultorId(UUID.randomUUID()); |
|         var prId \= new PropriedadeId(UUID.randomUUID()); |
|         assertThrows(IllegalArgumentException.class, |
|             () \-\> new Funcionario(agId, prId, null)); |
|     } |
|   |
|     // F-20 RN-010: Peao exige Zona antes de ser ativado |
|     @Test |
|     void deveRejeitarAtivacaoDePeaoSemZona() { |
|         var agId \= new AgricultorId(UUID.randomUUID()); |
|         var prId \= new PropriedadeId(UUID.randomUUID()); |
|         var func \= new Funcionario(agId, prId, TipoFuncionario.PEAO); |
|         assertThrows(IllegalArgumentException.class, |
|             () \-\> func.validarZonaObrigatoria()); |
|     } |
|   |
|     // F-20 RN-012: Gestor nao pode reatribuir outro Gestor |
|     @Test |
|     void deveRejeitarGestorAlterandoOutroGestor() { |
|         var agId \= new AgricultorId(UUID.randomUUID()); |
|         var prId \= new PropriedadeId(UUID.randomUUID()); |
|         var gestor \= new Funcionario(agId, prId, TipoFuncionario.GESTOR); |
|         assertThrows(IllegalArgumentException.class, |
|             () \-\> gestor.alterarTipo(TipoFuncionario.GESTOR, PerfilAcesso.GESTOR)); |
|     } |
| } |

**2.9.3  Teste de Domain Service com Mock de Repositório**  
Domain Services dependem de repositórios. O teste usa @Mock e @InjectMocks seguindo o padrão SGB. O verify() confirma que o repositório foi chamado o número correto de vezes.

| // CompatibilidadeCulturasServicoTest.java — cultivo/compatibilidade/ |
| :---- |
| import org.junit.jupiter.api.Test; |
| import org.junit.jupiter.api.extension.ExtendWith; |
| import org.mockito.InjectMocks; |
| import org.mockito.Mock; |
| import org.mockito.junit.jupiter.MockitoExtension; |
| import java.util.Optional; |
| import static org.junit.jupiter.api.Assertions.\*; |
| import static org.mockito.Mockito.\*; |
|   |
| @ExtendWith(MockitoExtension.class) |
| class CompatibilidadeCulturasServicoTest { |
|   |
|     @Mock |
|     private RelacaoCompatibilidadeRepositorio repositorio; |
|   |
|     @InjectMocks |
|     private CompatibilidadeCulturasServico servico; |
|   |
|     // F-07 RN-047: culturas incompatíveis rejeitadas |
|     @Test |
|     void deveRejeitarConsorcioDeCulturasIncompativeis() { |
|         var milho  \= new NomeCultura("Milho"); |
|         var batata \= new NomeCultura("Batata-Doce"); |
|         var relacao \= new RelacaoCompatibilidade( |
|             milho, batata, ClassificacaoConsorcio.INCOMPATIVEL); |
|         when(repositorio.buscarRelacao(milho, batata)) |
|             .thenReturn(Optional.of(relacao)); |
|   |
|         assertThrows(IllegalArgumentException.class, |
|             () \-\> servico.validarConsorcio(milho, batata)); |
|   |
|         verify(repositorio, times(1)).buscarRelacao(milho, batata); |
|     } |
|   |
|     @Test |
|     void devePermitirConsorcioBenefico() { |
|         var milho \= new NomeCultura("Milho"); |
|         var abob  \= new NomeCultura("Abobora"); |
|         var relacao \= new RelacaoCompatibilidade( |
|             milho, abob, ClassificacaoConsorcio.BENEFICA); |
|         when(repositorio.buscarRelacao(milho, abob)) |
|             .thenReturn(Optional.of(relacao)); |
|   |
|         assertDoesNotThrow(() \-\> servico.validarConsorcio(milho, abob)); |
|     } |
| } |

# **2.10 BDD — Cucumber \+ Gherkin (padrão SGB)**

# O Cultiva Fácil usa Cucumber com Gherkin em português, seguindo o padrão SGB. Cada feature entrega dois artefatos: (1) o arquivo `.feature` em `src/test/resources/features/` e (2) a classe runner em `src/test/java/`. Os steps não fazem parte do escopo da Entrega 1 e serão implementados na Entrega 2, junto às camadas de aplicação e infraestrutura.

# **2.10.1 Arquivo .feature — Dado/Quando/Então**

| \# src/test/resources/features/compatibilidade.feature \# language: pt Funcionalidade: Compatibilidade de Culturas   Como Proprietario ou Gestor   Eu quero verificar a compatibilidade entre duas culturas   Para evitar consorcio de plantas biologicamente incompativeis   @F07-US13-RN047   Cenario: Consorcio de culturas incompativeis rejeitado     Dado que existe uma relacao de incompatibilidade entre "Milho" e "Batata-Doce"     Quando o sistema verifica o consorcio entre "Milho" e "Batata-Doce"     Entao o sistema deve rejeitar com erro "CONSORCIO\_INCOMPATIVEL"   @F07-US13-RN048   Cenario: Consorcio de culturas beneficas aceito     Dado que existe uma relacao benefica entre "Milho" e "Abobora"     Quando o sistema verifica o consorcio entre "Milho" e "Abobora"     Entao o sistema deve aceitar o consorcio sem erros   @F07-US13-RN047   Esquema do Cenario: Multiplas combinacoes de incompatibilidade     Dado que existe uma relacao de incompatibilidade entre \<culturaA\> e \<culturaB\>     Quando o sistema verifica o consorcio entre \<culturaA\> e \<culturaB\>     Entao o sistema deve rejeitar com erro "CONSORCIO\_INCOMPATIVEL"     Exemplos:       | culturaA | culturaB    |       | Milho    | Batata-Doce |       | Girassol | Batata      |       | Ervilha  | Cebola      | |
| :---- |

# **2.10.2 Runner Cucumber** 

| // src/test/java/br/edu/cesar/cultivafacil/(seu-subdominio)/RunCucumberTest.java package br.edu.cesar.cultivafacil.(sua-package); import static io.cucumber.core.options.Constants.PLUGIN\_PROPERTY\_NAME; import org.junit.platform.suite.api.ConfigurationParameter; import org.junit.platform.suite.api.IncludeEngines; import org.junit.platform.suite.api.SelectPackages; import org.junit.platform.suite.api.Suite; @Suite @IncludeEngines("cucumber") @SelectPackages("br.edu.cesar.cultivafacil.cultivo") @ConfigurationParameter(key \= PLUGIN\_PROPERTY\_NAME, value \= "pretty") public class RunCucumberTest { } |
| :---- |

# 

| \# src/test/resources/junit-platform.properties cucumber.glue=br.edu.cesar.cultivafacil.cultivo |
| :---- |

# **2.10.3 Dependências Maven — Cucumber** 

| \<\!-- cultivafacil-dominio-X/pom.xml \--\> \<dependencyManagement\>   \<dependencies\>     \<dependency\>       \<groupId\>io.cucumber\</groupId\>       \<artifactId\>cucumber-bom\</artifactId\>       \<version\>${cucumber.version}\</version\>       \<type\>pom\</type\>       \<scope\>import\</scope\>     \</dependency\>   \</dependencies\> \</dependencyManagement\> \<dependencies\>   \<dependency\>     \<groupId\>io.cucumber\</groupId\>     \<artifactId\>cucumber-java\</artifactId\>     \<scope\>test\</scope\>   \</dependency\>   \<dependency\>     \<groupId\>io.cucumber\</groupId\>     \<artifactId\>cucumber-junit-platform-engine\</artifactId\>     \<scope\>test\</scope\>   \</dependency\>   \<dependency\>     \<groupId\>org.junit.jupiter\</groupId\>     \<artifactId\>junit-jupiter\</artifactId\>     \<scope\>test\</scope\>   \</dependency\>   \<dependency\>     \<groupId\>org.junit.platform\</groupId\>     \<artifactId\>junit-platform-suite\</artifactId\>     \<scope\>test\</scope\>   \</dependency\> \</dependencies\> |
| :---- |

# **3\. Estrutura de Módulos Maven**

O projeto segue o padrão maven multi-módulo: cada subdomínio é um módulo Maven independente. O módulo cultivafacil-domain-shared  centraliza os IDs tipados e a infraestrutura de eventos. Apenas as 14 features ativas geram classes nesta entrega.

**Dependência Maven entre módulos:** cada módulo de subdomínio declara dependência em cultivafacil-domain-shared no pom.xml. Nenhum subdomínio depende de outro subdomínio diretamente.

| cultivafacil-domain-shared |
| :---- |
| cultivafacil/domain-shared/src/main/java/ └── br.edu.cesar.cultivafacil.domain.shared/     ├── acesso/     │   └── conta/     │       └── ContaId.java                  \[DC — dono: Julia\]     ├── terreno/     │   └── talhao/     │       └── TalhaoId.java                 \[DC — dono: Jera\]     ├── cultivo/     │   └── ciclo/     │       └── CicloAgricolaId.java          \[DC — dono: Vinicius\]     └── evento/         ├── EventoBarramento.java         └── EventoObservador.java |
|  |
| **cultivafacil-domain-acesso** |
| cultivafacil/domain-acesso/src/main/java/ └── br.edu.cesar.cultivafacil.domain.acesso/     ← F-01, F-02, F-03, F-20  (Julia, Clara, Jera, Matheus)     ├── conta/                                 ← Agregado de identidade (Julia)     │   ├── Conta.java                         \[AR — Julia\]     │   ├── ContaId.java                       \[VO — Julia\]     │   ├── Credenciais.java                   \[Entidade — Julia\]     │   ├── Email.java                         \[VO — Julia\]     │   ├── Senha.java                         \[VO — Julia\]     │   ├── ContaRepositorio.java              \[RP — Julia\]     │   └── ContaCriada.java                   \[DE — Julia\]     ├── propriedade/                           ← Agregado da propriedade (Clara, Matheus)     │   ├── Propriedade.java                   \[AR — Clara\]     │   ├── PropriedadeId.java                 \[VO — Clara\]     │   ├── Localizacao.java                   \[VO — Clara\]     │   ├── TipoSolo.java                      \[enum VO — Clara\]     │   ├── ClimaRegiao.java                   \[enum VO — Clara\]     │   ├── StatusPerfil.java                  \[enum VO — Clara\]     │   ├── Membro.java                        \[Entidade — Matheus\]     │   ├── PerfilAcesso.java                  \[enum VO — Matheus\]     │   ├── StatusMembro.java                  \[enum VO — Matheus\]     │   ├── Convite.java                       \[Entidade — Matheus\]     │   ├── PropriedadeRepositorio.java        \[RP — Clara\]     │   ├── PropriedadeCompleta.java           \[DE — Clara\]     │   ├── MembroAdicionado.java              \[DE — Matheus\]     │   ├── MembroAtivado.java                 \[DE — Matheus\]     │   ├── AcessoRevogado.java                \[DE — Matheus\]     │   ├── PerfilAlterado.java                \[DE — Matheus\]     │   └── TalhaoRealocado.java               \[DE — Matheus\]     └── preferencias/                          ← Agregado de configurações (Jera)         ├── Preferencias.java                  \[AR — Jera\]         ├── PreferenciasId.java                \[VO — Jera\]         ├── TipoNotificacao.java               \[enum VO — Jera\]         ├── UnidadeArea.java                   \[enum VO — Jera\]         └── PreferenciasRepositorio.java       \[RP — Jera\]     (ContaId ← cultivafacil-domain-shared) |
|  |
| **cultivafacil-domain-terreno** |
| cultivafacil/domain-terreno/src/main/java/ └── br.edu.cesar.cultivafacil.domain.terreno/     ← F-04, F-05  (Claudia, Jera)     └── terreno/                               ← Agregado de espaço físico (Claudia, Jera)         ├── Terreno.java                       \[AR — Claudia\]         ├── TerrenoId.java                     \[VO — Claudia\]         ├── NomeTerreno.java                   \[VO — Claudia\]         ├── AreaTerreno.java                   \[VO — Claudia\]         ├── TipoSoloTerreno.java               \[enum VO — Claudia\]         ├── ClimaRegiaoTerreno.java            \[enum VO — Claudia\]         ├── pH.java                            \[VO — Claudia\]         ├── IndiceIluminosidade.java           \[VO — Claudia\]         ├── TerrenoRepositorio.java            \[RP — Claudia\]         ├── Talhao.java                        \[Entidade — Jera\]         ├── TalhaoId.java                      \[VO — Jera\]         ├── NomeTalhao.java                    \[VO — Jera\]         ├── AreaTalhao.java                    \[VO — Jera\]         ├── SituacaoTalhao.java                \[enum VO — Jera\]         ├── TalhaoCriado.java                  \[DE — Jera\]         └── SituacaoTalhaoAlterada.java        \[DE — Jera\]     (ContaId, TalhaoId ← cultivafacil-domain-shared) |
|  |
| **cultivafacil-domain-cultivo** |
| cultivafacil/domain-cultivo/src/main/java/ └── br.edu.cesar.cultivafacil.domain.cultivo/     ← F-06, F-07, F-08  (Vinicius, Matheus)     ├── ciclo/                                 ← Agregado de ciclo agrícola (Vinicius)     │   ├── CicloAgricola.java                 \[AR — Vinicius\]     │   ├── CicloAgricolaId.java               \[VO — Vinicius\]     │   ├── NomeCultura.java                   \[VO — Vinicius\]     │   ├── StatusCiclo.java                   \[enum VO — Vinicius\]     │   ├── QuantidadePlantada.java            \[VO — Vinicius\]     │   ├── UnidadeMedidaCiclo.java            \[enum VO — Vinicius\]     │   ├── IntervalodeDescanso.java           \[Entidade — Vinicius\]     │   ├── DiasDescanso.java                  \[VO — Vinicius\]     │   ├── RotacaoCulturasServico.java        \[DS — Vinicius\]     │   ├── CicloAgricolaRepositorio.java      \[RP — Vinicius\]     │   ├── IntervalodeDescansoRepositorio.java \[RP — Vinicius\]     │   ├── CicloIniciado.java                 \[DE — Vinicius\]     │   └── CicloEncerrado.java                \[DE — Vinicius\]     └── compatibilidade/                       ← Agregado de compatibilidade (Matheus)         ├── RelacaoCompatibilidade.java        \[AR — Matheus\]         ├── RelacaoCompatibilidadeId.java      \[VO — Matheus\]         ├── ConsorcioCultura.java              \[Entidade — Matheus\]         ├── ClassificacaoConsorcio.java        \[enum VO — Matheus\]         ├── CompatibilidadeCulturasServico.java \[DS — Matheus\]         └── RelacaoCompatibilidadeRepositorio.java \[RP — Matheus\]     (TalhaoId, CicloAgricolaId ← cultivafacil-domain-shared) |
|  |
| **cultivafacil-domain-agenda** |
| cultivafacil/domain-agenda/src/main/java/ └── br.edu.cesar.cultivafacil.domain.agenda/     ← F-09  (Julia)     └── tarefa/                                ← Agregado de tarefas agrícolas (Julia)         ├── Tarefa.java                        \[AR — Julia\]         ├── TarefaId.java                      \[VO — Julia\]         ├── NomeTarefa.java                    \[VO — Julia\]         ├── DataTarefa.java                    \[VO — Julia\]         ├── TarefaRepositorio.java             \[RP — Julia\]         └── TarefaCriada.java                  \[DE — Julia\]     (TalhaoId, CicloAgricolaId ← cultivafacil-domain-shared) |
|  |
| **cultivafacil-domain-sanidade** |
| cultivafacil/domain-sanidade/src/main/java/ └── br.edu.cesar.cultivafacil.domain.sanidade/     ← F-10  (Bernardo)     └── foco/                                  ← Agregado fitossanitário (Bernardo)         ├── FocoFitossanitario.java            \[AR — Bernardo\]         ├── FocoFitossanitarioId.java          \[VO — Bernardo\]         ├── TipoAgronomicoFoco.java            \[enum VO — Bernardo\]         ├── NivelInfestacao.java               \[enum VO — Bernardo\]         ├── SeveridadeFoco.java                \[enum VO — Bernardo\]         ├── DescricaoFoco.java                 \[VO — Bernardo\]         ├── FocoFitossanitarioRepositorio.java \[RP — Bernardo\]         └── FocoRegistrado.java                \[DE — Bernardo\]     (TalhaoId, CicloAgricolaId ← cultivafacil-domain-shared) |
|  |
| **cultivafacil-domain-colheita** |
| cultivafacil/domain-colheita/src/main/java/ └── br.edu.cesar.cultivafacil.domain.colheita/     ← F-12, F-13  (Clara, Matheus)     ├── colheita/                              ← Agregado de registro de colheita (Clara)     │   ├── Colheita.java                      \[AR — Clara\]     │   ├── ColheitaId.java                    \[VO — Clara\]     │   ├── QuantidadeColhida.java             \[VO — Clara\]     │   ├── UnidadeMedida.java                 \[enum VO — Clara\]     │   ├── DestinoColheita.java               \[enum VO — Clara\]     │   ├── ColheitaRepositorio.java           \[RP — Clara\]     │   └── ColheitaRegistrada.java            \[DE — Clara\]     └── celeiro/                               ← Agregado de estoque e projeção (Matheus)         ├── Celeiro.java                       \[AR — Matheus\]         ├── CeleiroId.java                     \[VO — Matheus\]         ├── ItemCeleiro.java                   \[Entidade — Matheus\]         ├── SaidaCeleiro.java                  \[Entidade — Matheus\]         ├── MetaComerciavel.java               \[Entidade — Matheus\]         ├── ConfiguracaoRelatorio.java         \[Entidade — Matheus\]         ├── MotiveSaida.java                   \[enum VO — Matheus\]         ├── FiltroPeriodo.java                 \[enum VO — Matheus\]         ├── CeleiroRepositorio.java            \[RP — Matheus\]         ├── ConfiguracaoRelatorioRepositorio.java \[RP — Matheus\]         └── AlertaProjecao.java                \[DE — Matheus\]     (TalhaoId, CicloAgricolaId ← cultivafacil-domain-shared) |
|  |
| **cultivafacil-domain-insumo** |
| cultivafacil/domain-insumo/src/main/java/ └── br.edu.cesar.cultivafacil.domain.insumo/     ← F-14  (Claudia)     └── plano/                                 ← Agregado de plano de insumos (Claudia)         ├── PlanoInsumos.java                  \[AR — Claudia\]         ├── PlanoInsumosId.java                \[VO — Claudia\]         ├── ItemInsumo.java                    \[Entidade — Claudia\]         ├── MesReferencia.java                 \[VO — Claudia\]         ├── TipoInsumo.java                    \[enum VO — Claudia\]         ├── QuantidadeInsumo.java              \[VO — Claudia\]         ├── StatusItemInsumo.java              \[enum VO — Claudia\]         ├── PrecoUnitario.java                 \[VO — Claudia\]         └── PlanoInsumosRepositorio.java       \[RP — Claudia\]     (TalhaoId, ContaId ← cultivafacil-domain-shared) |
|   |
| **cultivafacil-domain-clima** |
| cultivafacil/domain-clima/src/main/java/ └── br.edu.cesar.cultivafacil.domain.clima/     ← F-17  (Bernardo)     ├── limite/                                ← Agregado de limites climáticos (Bernardo)     │   ├── LimiteClimatico.java               \[AR — Bernardo\]     │   ├── LimiteClimaticoId.java             \[VO — Bernardo\]     │   ├── TemperaturaLimite.java             \[VO — Bernardo\]     │   ├── PrecipitacaoLimite.java            \[VO — Bernardo\]     │   ├── NecessidadeHidrica.java            \[VO — Bernardo\]     │   ├── JanelaObservacao.java              \[enum VO — Bernardo\]     │   └── LimiteClimaticoRepositorio.java    \[RP — Bernardo\]     ├── alerta-climatico/                      ← Agregado de alerta climático (Bernardo)     │   ├── AlertaClimatico.java               \[AR — Bernardo\]     │   ├── AlertaClimaticoId.java             \[VO — Bernardo\]     │   ├── TipoAlerta.java                    \[enum VO — Bernardo\]     │   └── AlertaClimaticoRepositorio.java    \[RP — Bernardo\]     └── alerta-irrigacao/                      ← Agregado de alerta de irrigação (Bernardo)         ├── AlertaIrrigacao.java               \[AR — Bernardo\]         ├── AlertaIrrigacaoId.java             \[VO — Bernardo\]         └── AlertaIrrigacaoRepositorio.java    \[RP — Bernardo\]     (TalhaoId ← cultivafacil-domain-shared) |
|  |

# **4\. Mapa Geral de Subdomínios**

| Subdomínio (módulo Maven) | Features ativas | Responsável(is) | Depende de (compartilhado) |
| :---- | :---- | :---- | :---- |
| acessocultivafacil-domain-acesso | F-01, F-02, F-03, F-20 | Julia (F-01,F-09) · Clara (F-02,F-12,F-20) · Jera (F-03,F-05) · Vinicius · Matheus (F-20) | AgricultorId |
| terrenocultivafacil-dominio-terreno | F-04, F-05 | Claudia (F-04) · Jera (F-05) | AgricultorId \+ ZonaId |
| cultivocultivafacil-dominio-cultivo | F-06, F-07, F-08 | Vinicius (F-06,F-08) · Matheus (F-07) | ZonaId \+ CicloAgricolaId |
| agendacultivafacil-dominio-agenda | F-09 | Julia (F-09) | ZonaId \+ CicloAgricolaId |
| sanidadecultivafacil-dominio-sanidade | F-10 | Bernardo (F-10) | ZonaId \+ CicloAgricolaId |
| colheitacultivafacil-dominio-colheita | F-12, F-13 | Clara (F-12) · Matheus (F-13) | ZonaId \+ CicloAgricolaId |
| insumocultivafacil-dominio-insumo | F-14 | Claudia (F-14) | ZonaId \+ AgricultorId |
| climacultivafacil-dominio-clima | F-17 | Bernardo (F-17) | ZonaId |

| Regra universal: NUNCA importe uma classe de negócio de outro subdomínio. Use sempre IDs tipados de cultivafacil-dominio-compartilhado. Nenhum módulo de subdomínio depende de outro módulo de subdomínio. F-11 não implementada nesta entrega. |
| :---- |

# **5\. Subdomínio acesso — Autenticação e Acesso**

Gerencia a identidade do Agricultor, sua Propriedade, preferências de perfil e os Membros da Propriedade (Proprietário e Funcionários). É o subdomínio raiz: nenhuma outra funcionalidade opera sem um Agricultor autenticado com Propriedade Completa configurada.

| Responsável(is): Julia (F-01, F-09) · Clara (F-02 — Propriedade) · Jera (F-03) · Matheus (F-20 — Membros)   │   Features: F-01 · F-02 · F-03 · F-20 |
| :---- |

## **5.1 Artefatos do domínio**

| Tipo | Nome (Java) | Responsabilidade | Dono |
| :---- | :---- | :---- | :---- |
| **AR** | *Agricultor* | Aggregate Root de identidade. Contém Conta, Propriedade e Preferencias. NÃO conhece papel — separação explícita identidade/role (ver 2.8). Publica ContaCriada e PropriedadeCompleta. | Julia |
| **Entidade** | *Conta* | Credenciais de acesso (email \+ senha). Controla ativação por consentimento. | Julia |
| **Entidade** | *Propriedade* | Dados da fazenda/sítio. Só existe uma por Agricultor. Controla estado Incompleto/Completo. | Clara |
| **Entidade** | *Preferencias* | Configurações de exibição, horário de resumo, notificações e unidade de área. | Jera |
| **AC** | *MembroPropriedade* | Classe abstrata base. Encapsula agricultorId, propriedadeId e StatusMembro. Declara contrato getPerfilAcesso(). Não instanciada diretamente. | Matheus |
| **AR** | *Proprietario* | Estende MembroPropriedade. Criado automaticamente na criação da Propriedade. Status sempre ATIVO. Papel irrevogável. Publica PropriedadeAssumida. | Matheus |
| **AR** | *Funcionario* | Estende MembroPropriedade. Criado via Convite. TipoFuncionario: GESTOR, PEAO ou FINANCEIRO. Publica FuncionarioConvidado, TipoAlterado, AcessoRevogado, ZonaRealocada. | Matheus |
| **VO** | *Email* | Formato RFC válido. Imutável. Identifica o Agricultor de forma única. | Julia |
| **VO** | *Senha* | Mínimo 8 chars, 1 número, diferente do email. Armazenada como hash. | Julia |
| **VO** | *Localizacao* | Município \+ Estado obrigatórios simultaneamente. | Clara |
| **VO** | *TipoSolo* | Enum SiBCS/EMBRAPA: LATOSSOLO, ARGISSOLO, NEOSSOLO, CAMBISSOLO, GLEISSOLO, NITOSSOLO, VERTISSOLO, PLINTOSSOLO. | Clara |
| **VO** | *ClimaRegiao* | Enum Köppen-Geiger BR: TROPICAL\_UMIDO, TROPICAL\_SAVANICO, TROPICAL\_ESTACAO\_SECA, SEMIARIDO, SUBTROPICAL\_UMIDO, SUBTROPICAL\_ALTITUDE, SUBTROPICAL\_INVERNO\_SECO. | Clara |
| **VO** | *StatusPerfil* | Enum: INCOMPLETO, COMPLETO. Transição para COMPLETO exige os 3 dados preenchidos. | Clara |
| **VO** | *TipoNotificacao* | Enum: RESUMO\_DIARIO, TAREFA\_ATRASADA, ALERTA\_CRITICO. | Jera |
| **VO** | *UnidadeArea* | Enum: HECTARE, METRO\_QUADRADO, ALQUEIRE. | Jera |
| **VO** | *PerfilAcesso* | Enum: PROPRIETARIO, GESTOR, PEAO, FINANCEIRO. Retornado por getPerfilAcesso(). Na Entrega 2 converte-se em GrantedAuthority via ROLE\_{valor}. | Matheus |
| **VO** | *TipoFuncionario* | Enum: GESTOR, PEAO, FINANCEIRO. Subconjunto de PerfilAcesso excluindo PROPRIETARIO. | Matheus |
| **VO** | *StatusMembro* | Enum: CONVIDADO, ATIVO, INATIVO. Controla ciclo de vida do MembroPropriedade. | Matheus |
| **VO** | *ProprietarioId* | Identificador tipado do Proprietario (UUID). Interno ao subdomínio acesso — não entra no compartilhado. | Matheus |
| **VO** | *FuncionarioId* | Identificador tipado do Funcionario (UUID). Interno ao subdomínio acesso — não entra no compartilhado. | Matheus |
| **DC** | *AgricultorId* | Identificador tipado (UUID). Vive em cultivafacil-dominio-compartilhado. Exportado para terreno e insumo. | Julia |
| **DE** | *Agricultor.ContaCriada* | Publicada quando Agricultor cria conta com sucesso. | Julia |
| **DE** | *Agricultor.PropriedadeCompleta* | Publicada quando Propriedade atinge estado COMPLETO. | Clara |
| **DE** | *Proprietario.PropriedadeAssumida* | Publicada quando Proprietario é criado junto com a Propriedade. | Matheus |
| **DE** | *Funcionario.FuncionarioConvidado* | Publicada quando Funcionario é criado via Convite com sucesso. | Matheus |
| **DE** | *Funcionario.TipoAlterado* | Publicada quando TipoFuncionario é alterado. Contém tipo anterior e novo. | Matheus |
| **DE** | *Funcionario.AcessoRevogado* | Publicada quando StatusMembro transita para INATIVO. | Matheus |
| **DE** | *Funcionario.ZonaRealocada* | Publicada quando Zona atribuída a um Peão é alterada. | Matheus |
| **RP** | *AgricultorRepositorio* | Interface: salvar(Agricultor), buscarPorEmail(Email), buscarPorId(AgricultorId). | Julia |
| **RP** | *ProprietarioRepositorio* | Interface: salvar(Proprietario), buscarPorPropriedadeId(PropriedadeId), existePorPropriedade(PropriedadeId). | Matheus |
| **RP** | *FuncionarioRepositorio* | Interface: salvar(Funcionario), buscarPorId(FuncionarioId), listarPorPropriedade(PropriedadeId), buscarAtivoPorAgricultorEPropriedade(AgricultorId, PropriedadeId). | Matheus |

## **5.2 Invariantes de domínio**

| RN | Elemento | Invariante de domínio | Código de erro |
| :---- | :---- | :---- | :---- |
| **F-01 RN-01** | *Email (VO)* | E-mail deve ser único na base. Dois Agricultores com mesmo e-mail são rejeitados. | EMAIL\_DUPLICADO |
| **F-01 RN-02** | *Senha (VO)* | Mínimo 8 caracteres com ao menos 1 número. | SENHA\_INVALIDA |
| **F-01 RN-03** | *Senha (VO)* | Senha não pode ser idêntica ao e-mail do Agricultor. | SENHA\_IGUAL\_EMAIL |
| **F-01 RN-04** | *Conta (Entidade)* | Conta só é ativada com consentimento registrado como verdadeiro. | CONSENTIMENTO\_OBRIGATORIO |
| **F-02 RN-01** | *Proprietario (AR)* | Cada Agricultor pode ser Proprietario de exatamente uma Propriedade ativa. | PROPRIEDADE\_JA\_EXISTE |
| **F-02 RN-02** | *Localizacao (VO)* | Município e estado obrigatórios simultaneamente. | LOCALIZACAO\_INVALIDA |
| **F-02 RN-03** | *TipoSolo (VO)* | Valor deve pertencer ao conjunto SiBCS/EMBRAPA aceito. | TIPO\_SOLO\_INVALIDO |
| **F-02 RN-04** | *ClimaRegiao (VO)* | Valor deve pertencer ao conjunto Köppen-Geiger aceito. | CLIMA\_INVALIDO |
| **F-02 RN-05** | *Propriedade (Entidade)* | Perfil transita para COMPLETO somente quando localização, solo e clima estiverem todos preenchidos simultaneamente. | PERFIL\_INCOMPLETO |
| **F-02 RN-06** | *Propriedade (Entidade)* | Nenhuma funcionalidade operacional está disponível enquanto Perfil estiver no estado INCOMPLETO. | PERFIL\_INCOMPLETO |
| **F-20 RN-007** | *Funcionario (AR)* | Link de ativação do Convite válido por 72 horas. Após expiração, Funcionario permanece CONVIDADO. | LINK\_CONVITE\_EXPIRADO |
| **F-20 RN-008** | *Funcionario.convidar()* | TipoFuncionario atribuído no convite deve ser GESTOR, PEAO ou FINANCEIRO. Gestor só pode convidar PEAO ou FINANCEIRO. | TIPO\_FUNCIONARIO\_INVALIDO / ACAO\_NAO\_PERMITIDA |
| **F-20 RN-009** | *TipoFuncionario (VO)* | Valor deve pertencer ao conjunto {GESTOR, PEAO, FINANCEIRO}. Qualquer valor fora desse conjunto é rejeitado no construtor. | TIPO\_FUNCIONARIO\_INVALIDO |
| **F-20 RN-010** | *ProprietarioRepositorio* | Apenas um Proprietario por PropriedadeId. existePorPropriedade() retorna true → criação rejeitada. | PROPRIETARIO\_DUPLICADO |
| **F-20 RN-011** | *Funcionario.atribuirZona()* | Convite de PEAO sem ao menos uma ZonaId atribuída é rejeitado. Gestor e Financeiro não têm restrição de Zona. | ZONA\_OBRIGATORIA\_PARA\_PEAO |
| **F-20 RN-012** | *Funcionario.alterarTipo()* | Proprietario pode alterar tipo de qualquer Funcionario. Gestor só pode alterar tipo de PEAO ou FINANCEIRO — nunca outro GESTOR. | ACAO\_NAO\_PERMITIDA |
| **F-20 RN-013** | *Proprietario (AR)* | Proprietario não possui método revogar(). Tentativa de remoção é rejeitada no Application Service. | PROPRIETARIO\_INAMOVIVEL |
| **F-20 RN-051** | *Proprietario (AR)* | Ao concluir o cadastro da Propriedade, o sistema cria automaticamente um Proprietario vinculando AgricultorId ao PropriedadeId com status ATIVO. | CRIACAO\_PROPRIETARIO\_FALHOU |
| **F-20 RN-052** | *ProprietarioRepositorio* | Cada PropriedadeId admite exatamente um Proprietario. existePorPropriedade() retornando verdadeiro rejeita a criacao. | PROPRIETARIO\_DUPLICADO |
| **F-03 RN-01** | *Preferencias (Entidade)* | Nome do Agricultor entre 2 e 80 caracteres. | NOME\_INVALIDO |
| **F-03 RN-02** | *Preferencias (Entidade)* | Foto de perfil: JPG ou PNG, máximo 5 MB. | FOTO\_FORMATO\_INVALIDO / FOTO\_TAMANHO\_EXCEDIDO |
| **F-03 RN-03** | *Preferencias (Entidade)* | Valor de área: positivo, máximo 2 casas decimais. | VALOR\_AREA\_INVALIDO |
| **F-03 RN-04** | *Preferencias (Entidade)* | Horário de resumo diário entre 05:00 e 10:00. | HORARIO\_RESUMO\_INVALIDO |
| **F-03 RN-05** | *TipoNotificacao (VO)* | Tipo deve pertencer ao conjunto aceito. | TIPO\_NOTIFICACAO\_INVALIDO |
| **F-03 RN-06** | *Preferencias (Entidade)* | Máximo 5 notificações dos tipos RESUMO\_DIARIO / TAREFA\_ATRASADA por Agricultor por dia. | LIMITE\_NOTIFICACOES\_EXCEDIDO |

## **5.3 Decisão Arquitetural Por que não herança polimórfica para permissão?**

| Decisão | Alternativa rejeitada | Razão da escolha |
| :---- | :---- | :---- |
| Herança Proprietario/Funcionario extends MembroPropriedade | Single class com enum tipo | Herança expressa o invariante mais fortemente: Proprietario literalmente NÃO TEM método revogar(). É impossível chamar membro.revogar() se o objeto for Proprietario. |
| PerfilAcesso como VO separado | TipoFuncionario já seria suficiente | PerfilAcesso unifica PROPRIETARIO \+ tipos de Funcionario em um único contrato para o Spring Security. TipoFuncionario existe separado para garantir que PROPRIETARIO não apareça em formulários de convite. |
| Domínio nunca verifica PerfilAcesso em if/switch | Verificar na Application Service | Application Service também não verifica — delega ao Spring Security via @PreAuthorize. O domínio garante consistência de estado; autorização é preocupação ortogonal. |
| StatusMembro como VO separado | Boolean ativo | StatusMembro com 3 estados modela o ciclo de vida do Convite explicitamente. Permite distinguir "ainda não ativou o link" de "acesso revogado". |
| ZonaId via cultivafacil-dominio-compartilhado | Cópia local de ZonaId em acesso | Consistente com o padrão Domínio Compartilhado. ZonaId já vive no módulo compartilhado — Funcionario importa diretamente como os demais subdomínios. |

# **6\. Subdomínio terreno — Gestão de Terrenos**

Gerencia o espaço físico da propriedade: Terrenos e suas Zonas. ZonaId vive no cultivafacil-dominio-compartilhado e é consumido por 6 outros subdomínios.

| Responsável(is): Claudia (F-04) · Jera (F-05)   │   Features: F-04 · F-05 |
| :---- |

## **6.1 Máquina de estados — SituacaoZona**

| De | Para | Transição disparada por | Subdomínio responsável |
| :---- | :---- | :---- | :---- |
| VAZIA | CULTIVO\_ATIVO | Zona.iniciarCultivo() chamado por CicloAgricola (F-06) | cultivo |
| CULTIVO\_ATIVO | PRONTA\_PARA\_COLHEITA | Zona.encerrarCultivo() — lógica do ciclo agrícola | cultivo |
| CULTIVO\_ATIVO | COM\_ALERTA | Zona.sinalizarAlerta() — AlertaClimatico gerado (F-17) | clima |
| COM\_ALERTA | CULTIVO\_ATIVO | Zona.resolverAlerta() — Alerta resolvido/expirado | clima |
| PRONTA\_PARA\_COLHEITA | VAZIA | Zona.iniciarCultivo() revertido após Colheita registrada (F-12) | colheita |

| Quem pode alterar SituacaoZona? SOMENTE Jera (dona de Zona) define os métodos de transição. Outros membros chamam esses métodos via método público — nunca setam o estado diretamente. |
| :---- |

# **7\. Subdomínio cultivo — Gestão de Cultivos**

Gerencia ciclos agrícolas, compatibilidade de culturas e rotação de solo. Subdomínio core do Motor Agronômico (F-07, F-08).

| Responsável(is): Vinicius (F-06, F-08) · Matheus (F-07)   │   Features: F-06 · F-07 · F-08 |
| :---- |

# **8\. Subdomínio agenda — Calendário Agrícola**

Gerencia tarefas e agendamentos vinculados a zonas e ciclos agrícolas.

| Responsável(is): Julia (F-09)   │   Features: F-09 |
| :---- |

# **9\. Subdomínio sanidade — Monitoramento Fitossanitário**

Registra e monitora focos de pragas e doenças nas zonas de cultivo. F-11 fora do escopo.

| Responsável(is): Bernardo (F-10)   │   Features: F-10 |
| :---- |

# **10\. Subdomínio colheita — Colheita e Celeiro**

Registra colheitas e gerencia o celeiro com projeções e alertas de estoque.

| Responsável(is): Clara (F-12) · Matheus (F-13)   │   Features: F-12 · F-13 |
| :---- |

# **11\. Subdomínio insumo — Plano de Insumos**

Gerencia o planejamento financeiro de insumos agrícolas. F-15/F-16 fora do escopo.

| Responsável(is): Claudia (F-14)   │   Features: F-14 |
| :---- |

# **12\. Subdomínio clima — Limites Climáticos e Alertas**

Monitora limites climáticos e gera alertas de irrigação. Subdomínio core do Motor Agronômico (F-17). F-18 fora do escopo.

| Responsável(is): Bernardo (F-17)   │   Features: F-17 |
| :---- |

# **13\. Ownership por Membro**

Cada classe Java do domain layer tem um dono único no time.

| Ownership das classes Domínio Compartilhado: AgricultorId → dono: Julia (F-01). ZonaId → dona: Jera (F-05). CicloAgricolaId → dono: Vinicius (F-06). Qualquer alteração nessas classes deve ser alinhada com o dono antes de commitar. |
| :---- |

| Membro | Classes Java sob ownership | Features |
| :---- | :---- | :---- |
| **Matheus** | acesso: MembroPropriedade (AC), Proprietario, Funcionario, ProprietarioId, FuncionarioId, PerfilAcesso, TipoFuncionario, StatusMembro, ProprietarioRepositorio, FuncionarioRepositorio, todos os DE de Proprietario e Funcionariocultivo: RelacaoCompatibilidade, ConsorcioCultura, ClassificacaoConsorcio, CompatibilidadeCulturasServico, RelacaoCompatibilidadeRepositorio colheita: Celeiro, ItemCeleiro, SaidaCeleiro, MetaComerciavel, ConfiguracaoRelatorio, MotiveSaida, FiltroPeriodo, CeleiroRepositorio, ConfiguracaoRelatorioRepositorio, Celeiro.AlertaProjecao | F-07, F-13, F-20 |
| **Clara** | acesso: Propriedade, Localizacao, TipoSolo, ClimaRegiao, StatusPerfil, Proprietario.PropriedadeAssumida colheita: Colheita, ColheitaId, QuantidadeColhida, UnidadeMedida, DestinoColheita, ColheitaRepositorio, Colheita.ColheitaRegistrada | F-02, F-12 |
| **Julia** | acesso: Agricultor, Conta, Email, Senha, AgricultorRepositorio, Agricultor.ContaCriada agenda: Tarefa, TarefaId, NomeTarefa, DataTarefa, TarefaRepositorio, Tarefa.TarefaCriada compartilhado: AgricultorId | F-01, F-09 |
| **Claudia** | terreno: Terreno, TerrenoId, NomeTerreno, AreaTerreno, TipoSoloTerreno, ClimaRegiaoTerreno, pH, IndiceIluminosidade, TerrenoRepositorio insumo: PlanoInsumos, ItemInsumo, PlanoInsumosId, MesReferencia, TipoInsumo, QuantidadeInsumo, StatusItemInsumo, PrecoUnitario, PlanoInsumosRepositorio | F-04, F-14 |
| **Vinicius** | acesso: (classes RBAC migradas para F-20 — Matheus), todos os DE de Funcionario cultivo: CicloAgricola, IntervalodeDescanso, NomeCultura, StatusCiclo, QuantidadePlantada, UnidadeMedidaCiclo, DiasDescanso, RotacaoCulturasServico, CicloAgricolaRepositorio, IntervalodeDescansoRepositorio, CicloAgricola.CicloIniciado, CicloAgricola.CicloEncerrado compartilhado: CicloAgricolaId | F-06, F-08 |
| **Bernardo** | sanidade: FocoFitossanitario, FocoFitossanitarioId, TipoAgronomicoFoco, NivelInfestacao, SeveridadeFoco, DescricaoFoco, FocoFitossanitarioRepositorio, FocoFitossanitario.FocoRegistrado clima: LimiteClimatico, AlertaClimatico, AlertaIrrigacao, todos os IDs internos, VOs, Repositórios e Eventos clima | F-10, F-17 |
| **Jera** | acesso: Preferencias, TipoNotificacao, UnidadeArea terreno: Zona, NomeZona, AreaZona, SituacaoZona, Terreno.ZonaCriada, Terreno.SituacaoZonaAlterada compartilhado: ZonaId | F-03, F-05 |

