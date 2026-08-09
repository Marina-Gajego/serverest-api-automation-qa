# Índice da Documentação

Este repositório contém a automação de testes da API ServeRest, guiada por workflows de arquitetura de testes do BMad TEA.

## Documentação Principal

- [Contexto do Projeto](../project-context.md): regras para uso com IA, detalhes da stack, padrões de arquitetura, convenções de nomenclatura e regras críticas de implementação.
- [Setup Local do Ambiente de Teste](wiki/setup-local-ambiente-de-teste.md): instruções para subir a API ServeRest localmente e executar a suíte.
- [Uso do BMad TEA no Projeto](wiki/uso-do-bmad-tea-no-projeto.md): relato técnico de como o módulo TEA apoiou planejamento, automação, revisão, CI/CD e documentação.
- [Resultados e Propostas dos Testes de API](wiki/resultados-e-propostas-melhoria-qualidade.md): leitura dos resultados da suíte e propostas priorizadas para evolução da API.
- [Relatório Final de Qualidade](wiki/relatorio-final-de-qualidade.md): síntese executiva da cobertura, riscos, evidências, limitações e quality gate final.

## Resumo do Projeto

- Linguagem: Java 17
- Ferramenta de build: Maven
- Stack de testes: REST Assured, Cucumber e JUnit 5
- Objetivo: automação de testes da API ServeRest, com validação JSON Schema, cenários BDD e workflows BMad TEA para automação, revisão e evolução de CI/CD

## Principais Áreas

- `src/test/java/br/com/marina/qa/context`: estado compartilhado entre cenários
- `src/test/java/br/com/marina/qa/factory`: builders de dados de teste
- `src/test/java/br/com/marina/qa/model`: DTOs de request e response
- `src/test/java/br/com/marina/qa/paths`: constantes de endpoints e URL base
- `src/test/java/br/com/marina/qa/services`: chamadas da API com REST Assured
- `src/test/java/br/com/marina/qa/stepsDefinitions`: bindings dos steps do Cucumber
- `src/test/resources/features`: arquivos de features
- `src/test/resources/schemas`: contratos JSON Schema

## Observações

- O repositório já possui um `project-context.md` forte; este índice funciona como camada de navegação para pessoas e agentes de IA.
- O projeto está estruturado como uma base única de automação de testes, não como uma aplicação dividida em múltiplas partes.
