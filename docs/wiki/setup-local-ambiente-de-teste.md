# Setup Local do Ambiente de Teste

Este guia descreve como preparar o ambiente local da API ServeRest e executar a suíte automatizada do projeto.

## Checklist rápido

- Ter Java 17, Maven e Docker instalados.
- Clonar o repositório.
- Subir a imagem do ServeRest em `localhost:3000`.
- Abrir o projeto no IDE e executar a classe runner, ou executar via Maven.

## Passos

1. Clonar o repositório e entrar na pasta:

```bash
git clone https://github.com/Marina-Gajego/serverest-bmad-tea-automation-qa.git
cd serverest-bmad-tea-automation-qa
```

2. Subir a API ServeRest via Docker:

```bash
docker run -d --name serverest -p 3000:3000 paulogoncalvesbh/serverest:latest
```

3. Abrir o projeto no IntelliJ ou outro IDE Java e executar a classe runner do Cucumber:

```text
br.com.marina.qa.runner.RunnerTest
```

Executar a classe runner no IDE é o caminho mais direto para validar a suíte localmente depois que a API estiver ativa.

## Alternativa via Maven

```bash
mvn -Dtest=br.com.marina.qa.runner.RunnerTest test
```

Também é possível executar a regressão completa por tag:

```bash
mvn test -Dcucumber.filter.tags="@regression"
```

## Encerrando o ambiente local

Quando terminar, pare e remova o container:

```bash
docker stop serverest && docker rm serverest
```
