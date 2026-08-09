# Uso do BMad TEA no Projeto

Esta página registra como o BMad Method, especialmente o módulo **Test Architecture Enterprise (TEA)**, foi usado neste projeto de automação da API ServeRest.

O objetivo não é apresentar o BMad como uma ferramenta que substituiu o raciocínio de QA, mas como uma camada de apoio para planejamento, expansão, revisão e organização da suíte. A IA passou a executar grande parte do trabalho operacional, enquanto a pessoa responsável pelo projeto atuou mais como operadora, revisora, direcionadora técnica e curadora das decisões.

## Contexto Antes do BMad

Antes de usar o BMad TEA, a estrutura inicial do projeto já havia sido criada manualmente. A automação também já tinha começado sem apoio do framework, cobrindo os primeiros domínios da API, como **Login** e **Usuários**.

Essa primeira fase foi importante porque definiu a base técnica do projeto:

- linguagem e stack principal: Java, Maven, REST Assured, Cucumber e JUnit;
- organização em camadas: steps, services, factories, models, paths e contexto;
- padrão de escrita dos cenários BDD;
- execução local contra a API ServeRest;
- primeiras validações de status code, payload e contrato.

Depois dessa base existir, o BMad TEA entrou como apoio para escalar o projeto com mais consistência. Em vez de criar tudo do zero manualmente, o fluxo passou a combinar intenção humana, execução assistida por IA e revisão crítica.

## Mudança de Papel no Processo

Com o uso do BMad, o papel humano no projeto mudou de execução direta para condução do processo.

Na prática, a pessoa responsável deixou de ser apenas quem escrevia cada classe, cenário e schema manualmente, e passou a atuar principalmente como:

- **operadora da IA**, guiando o que precisava ser construído;
- **revisora**, validando se a implementação fazia sentido para a API e para o objetivo do projeto;
- **planejadora**, ajudando a definir ordem de cobertura, riscos e prioridades;
- **curadora técnica**, aceitando, ajustando ou recusando decisões propostas pela IA;
- **dona do contexto**, garantindo que a automação continuasse coerente com a proposta original.

Esse modelo não eliminou a responsabilidade técnica humana. Pelo contrário: exigiu revisar melhor, fazer perguntas melhores e manter clareza sobre o que era comportamento esperado da API, limitação do produto ou oportunidade de melhoria.

## Workflows TEA Utilizados

O módulo TEA foi usado como referência principalmente em quatro frentes: desenho de testes, automação, revisão e CI/CD.

| Workflow TEA | Uso no projeto |
| --- | --- |
| **Test Design** | Apoio para pensar cobertura por risco, fluxos positivos, negativos, regras de negócio, autenticação, contratos e dados de teste. |
| **Test Automation** | Expansão da suíte para novos domínios e endpoints, mantendo o padrão de camadas já criado no projeto. |
| **Test Review** | Revisão crítica da qualidade dos testes, buscando lacunas, falsos positivos, duplicações, fragilidade de assertions e oportunidades de melhoria. |
| **CI Setup / CI/CD** | Apoio para estruturar a execução automatizada em pipeline, com matriz por domínio, relatórios e evidência de execução. |

Além desses workflows centrais, a lógica geral do TEA influenciou decisões sobre rastreabilidade, quality gate, documentação de evidências e propostas de evolução da API.

## Como a IA Foi Usada na Prática

O uso da IA não foi apenas para gerar código isolado. O processo funcionou como uma colaboração contínua:

1. A base manual do projeto serviu como referência de arquitetura.
2. A pessoa responsável indicava o domínio, endpoint ou objetivo de qualidade.
3. A IA analisava o padrão existente e propunha a expansão.
4. A IA criava ou ajustava features, steps, services, factories, models e schemas.
5. A pessoa responsável revisava o resultado, validava a intenção dos cenários e corrigia direção quando necessário.
6. O ciclo se repetia para ampliar cobertura, organizar documentação e fortalecer evidências.

Com isso, a automação deixou de crescer apenas arquivo por arquivo e passou a crescer como um sistema: cobertura, arquitetura, execução, documentação e análise final caminhando juntas.

## Onde o BMad Mais Ajudou

O maior ganho do BMad TEA foi transformar uma automação que já existia em um projeto de qualidade mais completo.

Os principais benefícios observados foram:

- expansão mais rápida da cobertura para Produtos e Carrinhos;
- padronização entre domínios;
- maior atenção a cenários negativos e regras de negócio;
- criação e ampliação de contratos JSON Schema;
- revisão da suíte com foco em lacunas e fragilidades;
- organização da pipeline de CI;
- documentação dos resultados e propostas de melhoria;
- consolidação de um relatório final de qualidade.

Esse apoio foi especialmente útil porque a IA conseguia repetir padrões com velocidade, enquanto a revisão humana mantinha o projeto alinhado ao objetivo: demonstrar uma automação de API robusta, legível e tecnicamente justificável.

## Limites e Cuidados

Mesmo com o apoio do BMad, algumas decisões continuaram exigindo julgamento humano.

Exemplos:

- distinguir bug da API, comportamento esperado e decisão de produto;
- decidir quando uma mensagem textual deveria ser validada exatamente;
- avaliar se um status code atual era aceitável ou merecia proposta de melhoria;
- revisar se os cenários negativos realmente exercitavam o comportamento desejado;
- garantir que a IA seguisse a arquitetura já criada em vez de inventar outro padrão.

O BMad ajudou a acelerar, estruturar e revisar. A responsabilidade final pela coerência do projeto continuou sendo humana.

## Resultado do Processo

Ao final, o projeto passou de uma automação iniciada manualmente para uma suíte completa cobrindo Login, Usuários, Produtos e Carrinhos.

O resultado inclui:

- 16 arquivos `.feature`;
- 113 cenários/scenario outlines;
- IDs formais em todos os cenários;
- validação JSON Schema em todos os cenários;
- execução por tags de domínio;
- pipeline CI com matriz funcional;
- documentação de setup, resultados, propostas de melhoria e relatório final.

O ponto mais importante é que o BMad TEA não foi usado apenas para "gerar testes". Ele foi usado para apoiar um processo de engenharia de qualidade: planejar, automatizar, revisar, integrar, documentar e transformar evidências de teste em propostas concretas para evolução da API.

## Conclusão

Este projeto mostra um uso prático de IA em QA onde a pessoa responsável não desaparece do processo. Ela muda de posição.

Em vez de escrever manualmente cada parte da automação, passa a conduzir a IA, revisar entregas, validar decisões e preservar o objetivo técnico. O BMad TEA funcionou como estrutura para essa colaboração, ajudando a transformar uma suíte automatizada em um projeto completo de qualidade de API.
