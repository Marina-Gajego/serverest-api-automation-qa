# Documentation Index

This repository contains API automation for the ServeRest project, guided by BMad TEA test architecture workflows.

## Core Documentation

- [Project Context](../project-context.md): AI-facing rules, stack details, architecture patterns, naming conventions, and critical implementation rules.
- [Setup Local do Ambiente de Teste](wiki/setup-local-ambiente-de-teste.md): instruções para subir a API ServeRest localmente e executar a suíte.
- [Uso do BMad TEA no Projeto](wiki/uso-do-bmad-tea-no-projeto.md): relato técnico de como o módulo TEA apoiou planejamento, automação, revisão, CI/CD e documentação.
- [Resultados e Propostas dos Testes de API](wiki/resultados-e-propostas-melhoria-qualidade.md): leitura dos resultados da suíte e propostas priorizadas para evolução da API.
- [Relatório Final de Qualidade](wiki/relatorio-final-de-qualidade.md): síntese executiva da cobertura, riscos, evidências, limitações e quality gate final.

## Project Summary

- Language: Java 17
- Build tool: Maven
- Test stack: REST Assured, Cucumber, JUnit 5
- Purpose: API test automation for ServeRest, with JSON Schema validation, BDD scenarios, and BMad TEA workflows for automation, review, and CI/CD evolution

## Main Areas

- `src/test/java/br/com/marina/qa/context`: shared scenario state
- `src/test/java/br/com/marina/qa/factory`: test data builders
- `src/test/java/br/com/marina/qa/model`: request and response DTOs
- `src/test/java/br/com/marina/qa/paths`: endpoint and base URL constants
- `src/test/java/br/com/marina/qa/services`: REST Assured API calls
- `src/test/java/br/com/marina/qa/stepsDefinitions`: Cucumber step bindings
- `src/test/resources/features`: feature files
- `src/test/resources/schemas`: JSON Schema contracts

## Notes

- The repository already has a strong `project-context.md`; this index is the navigation layer for humans and agents.
- The project is structured as a single test automation codebase rather than a multi-part application.
