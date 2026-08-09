# ServeRest BMad TEA Automation QA

[![Badge ServeRest](https://img.shields.io/badge/API-ServeRest-green)](https://github.com/ServeRest/ServeRest/)
[![BMad TEA](https://img.shields.io/badge/BMad-TEA_Test_Architecture-blueviolet)](https://bmad-code-org.github.io/bmad-method-test-architecture-enterprise/)
[![Java](https://img.shields.io/badge/Java-17-blue)](https://www.oracle.com/java/)
[![REST Assured](https://img.shields.io/badge/REST%20Assured-5.4.0-orange)](https://rest-assured.io/)
[![Cucumber](https://img.shields.io/badge/Cucumber-BDD-brightgreen)](https://cucumber.io/)

Este projeto não é apenas um repositório convencional de automação de testes de API.

Ele usa a API [ServeRest](https://github.com/ServeRest/ServeRest) como alvo prático para aplicar uma abordagem de engenharia de testes orientada pelo [BMad Method - Test Architecture Enterprise (TEA)](https://bmad-code-org.github.io/bmad-method-test-architecture-enterprise/), combinando automação com análise, revisão, evolução de cobertura e qualidade contínua.

## Status Atual

A suíte automatizada de APIs foi concluída para os principais domínios da ServeRest:

- Login
- Usuários
- Produtos
- Carrinhos

Atualmente o projeto conta com 16 arquivos `.feature`, 113 cenários/scenario outlines, IDs formais por cenário, validação de contrato JSON Schema em todos os cenários, execução por tags de domínio e pipeline CI com matriz por área funcional.

## Proposta

Automatizar cenários da API ServeRest com Java, REST Assured, Cucumber, JUnit e JSON Schema, usando o BMad TEA como framework de apoio para pensar e evoluir a estratégia de testes.

A ideia central é fugir do formato "só escrever cenários automatizados" e tratar a automação como um produto de qualidade:

- com intenção de cobertura;
- com revisão crítica dos testes;
- com evolução contínua;
- com integração a pipeline;
- com rastreabilidade entre risco, comportamento e evidência.

## BMad TEA no Projeto

O [Test Architect (TEA)](https://bmad-code-org.github.io/bmad-method-test-architecture-enterprise/) é um módulo do BMad voltado para estratégia e automação de testes. Neste projeto, o foco está principalmente nestes workflows:

- [Test Design](https://bmad-code-org.github.io/bmad-method-test-architecture-enterprise/how-to/workflows/run-test-design): apoia o desenho da cobertura por risco, comportamento esperado, cenários positivos, negativos e contratos.
- [CI/CD Integration](https://bmad-code-org.github.io/bmad-method-test-architecture-enterprise/how-to/workflows/setup-ci): direciona a criação ou melhoria da execução automatizada em pipeline, com atenção a feedback rápido, artefatos e estabilidade.
- [Test Automation](https://bmad-code-org.github.io/bmad-method-test-architecture-enterprise/how-to/workflows/run-automate): apoia a expansão da cobertura automatizada a partir de comportamentos e riscos relevantes.
- [Test Review](https://bmad-code-org.github.io/bmad-method-test-architecture-enterprise/how-to/workflows/run-test-review): avalia a qualidade dos testes, identifica lacunas, falsos positivos, duplicações e oportunidades de melhoria.

O processo de uso do BMad TEA neste projeto está documentado em [docs/wiki/uso-do-bmad-tea-no-projeto.md](docs/wiki/uso-do-bmad-tea-no-projeto.md).

## Objetivo

Validar fluxos da API ServeRest com foco em:

- status code;
- payload de request e response;
- contratos JSON Schema;
- regras de negócio dos endpoints;
- cenários positivos e negativos;
- qualidade e manutenibilidade dos testes;
- evolução guiada por BMad TEA.

## Stack

- Java 17
- Maven
- REST Assured 5.4.0
- Cucumber 7.15.0
- JUnit Platform
- AssertJ
- JSON Schema Validator
- Java Faker
- BMad Method - Test Architecture Enterprise

## Cobertura Atual

### Login

- Autenticação com credenciais válidas.
- Validações de campos obrigatórios, formatos inválidos e credenciais incorretas.
- Validação de contrato do response.

### Usuários

- Criação de usuários.
- Busca de usuários sem filtros, com query parameters individuais e combinados.
- Busca de usuário por ID.
- Atualização de usuário por ID.
- Exclusão de usuário por ID.
- Validações de payload incompleto, campos vazios, formatos inválidos, IDs inexistentes e operações sem ID na rota.
- Validação de contratos JSON Schema.

### Produtos

- Criação de produtos.
- Busca de produtos sem filtros, com query parameters individuais e combinados.
- Busca de produto por ID.
- Atualização de produto por ID.
- Exclusão de produto por ID.
- Validações de autenticação, payload incompleto, campos vazios, valores numéricos inválidos, IDs inválidos, IDs inexistentes, nomes duplicados, JSON malformado e campos não permitidos.
- Validação de contratos JSON Schema.

### Carrinhos

- Criação de carrinhos.
- Busca de carrinhos sem filtros, com query parameters individuais e combinados.
- Busca de carrinho por ID.
- Exclusão com conclusão de compra.
- Exclusão com cancelamento de compra.
- Validações de autenticação, carrinho duplicado, produto duplicado no payload, produto inexistente, quantidade acima do estoque, campos inválidos, IDs inválidos e carrinho inexistente.
- Validação de contratos JSON Schema.

## API Utilizada

Este projeto utiliza a API ServeRest, uma API REST pública criada para estudos e práticas de testes manuais e automatizados.

Repositório oficial:
[ServeRest/ServeRest](https://github.com/ServeRest/ServeRest)

Documentação pública:
[https://serverest.dev](https://serverest.dev)

## Executando Com ServeRest Local

Para ter mais controle dos dados durante os testes, suba o ServeRest localmente:

```bash
npx serverest@latest
```

Ou com Docker:

```bash
docker run -p 3000:3000 paulogoncalvesbh/serverest:latest
```

Com a API local ativa, os testes apontam para:

```text
http://localhost:3000
```

Também existe um guia local em [docs/wiki/setup-local-ambiente-de-teste.md](docs/wiki/setup-local-ambiente-de-teste.md).

## Executando Os Testes

```bash
mvn test
```

Para executar a suíte de regressão completa:

```bash
mvn test -Dcucumber.filter.tags="@regression"
```

Para executar apenas um domínio:

```bash
mvn test -Dcucumber.filter.tags="@Login"
mvn test -Dcucumber.filter.tags="@Users"
mvn test -Dcucumber.filter.tags="@Products"
mvn test -Dcucumber.filter.tags="@Carts"
```

Para executar apenas cenários marcados com uma tag específica:

```bash
mvn test -Dcucumber.filter.tags="@teste"
```

Após a execução, os principais relatórios são gerados em:

```text
target/cucumber-report.html
target/cucumber-report.json
target/timeline/index.html
target/surefire-reports/
```

## Estrutura

```text
src/test/java/br/com/marina/qa
├── context
├── factory
├── model
├── paths
├── runner
├── services
└── stepsDefinitions

src/test/resources
├── features
└── schemas
```

## Pipeline CI

O projeto possui pipeline no GitHub Actions em `.github/workflows/test.yml`, com:

- compilação dos testes;
- execução por matriz de domínio (`@Login`, `@Users`, `@Products`, `@Carts`);
- ServeRest local via Docker;
- upload de relatórios Cucumber, timeline e Surefire;
- burn-in da suíte `@regression` em pull requests e execuções agendadas.

## Wiki e Evidências

- [Setup Local do Ambiente de Teste](docs/wiki/setup-local-ambiente-de-teste.md)
- [Uso do BMad TEA no Projeto](docs/wiki/uso-do-bmad-tea-no-projeto.md)
- [Resultados e Propostas dos Testes de API](docs/wiki/resultados-e-propostas-melhoria-qualidade.md)
- [Relatório Final de Qualidade](docs/wiki/relatorio-final-de-qualidade.md)

## Como o Projeto Deve Evoluir

O crescimento da suíte deve seguir uma lógica de arquitetura de testes:

- novos endpoints devem nascer com model, factory, service, steps, feature e schema quando aplicável;
- casos negativos devem gerar payloads realmente inválidos, evitando testes verdes que não exercitam o comportamento prometido;
- revisões com BMad Test Review devem ser usadas para encontrar lacunas de cobertura e falsos positivos;
- a pipeline deve continuar produzindo evidência de execução, relatórios e critérios de qualidade;
- melhorias futuras podem incluir sumarização automática de cobertura, publicação de relatório HTML e rastreabilidade formal entre cenários, riscos e contratos.
