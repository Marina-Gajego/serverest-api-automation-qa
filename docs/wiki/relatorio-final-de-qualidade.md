# Relatório Final de Qualidade

## Resumo executivo

O projeto `serverest-bmad-tea-automation-qa` pode ser considerado uma entrega completa de automação de APIs para os principais domínios da ServeRest.

A suíte cobre fluxos positivos, negativos, regras de negócio, autenticação, validação de dados, contratos JSON Schema, execução por domínio e pipeline CI. Além da automação, o repositório documenta resultados, riscos observados e propostas de melhoria para a API.

## Escopo testado

| Domínio | Features | Cobertura |
| --- | --- | --- |
| Login | 1 | Autenticação válida, credenciais inválidas, campos obrigatórios, formatos inválidos e JSON malformado |
| Usuários | 5 | Criação, consulta, filtros, consulta por ID, atualização, exclusão e regras com carrinho ativo |
| Produtos | 5 | Criação, consulta, filtros, consulta por ID, atualização, exclusão, autenticação e regras com carrinho |
| Carrinhos | 5 | Criação, consulta, filtros, consulta por ID, conclusão, cancelamento, autenticação e regras de estoque |

## Indicadores finais

| Indicador | Resultado |
| --- | --- |
| Arquivos `.feature` | 16 |
| Cenários/scenario outlines | 113 |
| Cenários com ID formal | 113 |
| Cenários com validação de contrato | 113 |
| Domínios com execução por tag | 4 |
| Pipeline CI | Sim |
| Relatórios automatizados | Cucumber HTML, Cucumber JSON, Timeline e Surefire |

## Tipos de teste cobertos

- Fluxos positivos de criação, consulta, atualização, exclusão e autenticação.
- Validação de campos obrigatórios.
- Validação de campos vazios e nulos.
- Validação de tipos inválidos.
- Validação de valores numéricos inválidos e limites.
- Validação de query parameters aceitos e não permitidos.
- Validação de IDs inexistentes, inválidos e transformados.
- Validação de duplicidade de usuário, produto e carrinho.
- Validação de regras de negócio envolvendo estoque e vínculos com carrinho.
- Validação de token ausente e token inválido.
- Validação de contratos JSON Schema para respostas de sucesso, erro, listagem e consulta por ID.

## Evidências técnicas

A suíte possui:

- organização por camadas: steps, services, factories, models, paths e contexto;
- dados dinâmicos para reduzir colisões entre execuções;
- tags por domínio: `@Login`, `@Users`, `@Products`, `@Carts`;
- tag global de regressão: `@regression`;
- IDs rastreáveis por cenário, como `LOGIN-001`, `USERS-001`, `PRODUCTS-001` e `CARTS-001`;
- contratos JSON Schema reutilizáveis para respostas comuns;
- contratos específicos para listagens e consultas por ID;
- pipeline GitHub Actions com execução matricial por domínio e burn-in.

## Principais riscos cobertos

| Risco | Evidência de cobertura |
| --- | --- |
| API aceitar payload inválido | Cenários de campos obrigatórios, nulos, vazios, tipos inválidos e JSON malformado |
| API retornar contrato inesperado | Validação JSON Schema nos 113 cenários/scenario outlines |
| Endpoints protegidos aceitarem chamada sem token | Cenários de token ausente em produtos e carrinhos |
| Token inválido ser aceito | Cenários de token inválido em produtos e carrinhos |
| Regra de negócio ser quebrada | Cenários de carrinho duplicado, estoque insuficiente, produto em carrinho e usuário com carrinho |
| Consulta retornar dados incorretos | Cenários de filtros individuais, combinados e valores divergentes |
| Exclusão ou atualização gerar estado inconsistente | Cenários pós-delete, pós-update e tentativa de repetir operações |

## Limitações conhecidas

Estas limitações não impedem o encerramento do projeto, mas são importantes para leitura honesta da qualidade:

- A API ServeRest possui comportamentos que podem ser decisão de produto, como `PUT` com ID inexistente criando recurso e `DELETE` inexistente retornando `200`.
- A suíte valida autenticação básica, mas autorização por perfil ainda depende de regra mais explícita da API.
- Algumas propostas de melhoria, como `403`, `404`, `409`, `422`, erro estruturado e rate limit, dependem de mudança na API alvo.
- Os schemas comuns de erro validam a estrutura atual da ServeRest, que pode retornar erros por `message` ou por nome de campo.

## Propostas registradas

As propostas de evolução estão documentadas em:

- [Resultados e Propostas dos Testes de API](resultados-e-propostas-melhoria-qualidade.md)

As recomendações principais são:

- padronizar modelo de erro;
- diferenciar melhor status codes de validação, autenticação, autorização, recurso inexistente e conflito;
- expandir tratamentos de segurança;
- documentar comportamento de `PUT` como upsert, se for intencional;
- documentar estratégia de `DELETE` idempotente;
- manter contratos de erro e sucesso como parte da definição de pronto.

## Quality Gate

```text
Gate: PASS WITH IMPROVEMENTS
Motivo: suíte funcional completa, contratos aplicados em todos os cenários, CI ativo e propostas de melhoria registradas.
Pendências externas: melhorias de comportamento da API dependem de mudança no produto ServeRest.
Pendências opcionais internas: polimentos de nomenclatura, tags adicionais por intenção, badge de pipeline e publicação de relatório.
```

## Conclusão

O repositório está pronto para ser apresentado como projeto completo de automação de API. Ele não entrega apenas scripts de teste: entrega arquitetura de automação, evidência de execução, documentação de qualidade, análise crítica dos resultados e propostas técnicas para evolução da API.
