# Qualidade da API ServeRest: Achados e Propostas de Melhoria

Esta página consolida os principais resultados observados a partir da automação de testes da API ServeRest e transforma esses achados em propostas concretas de evolução técnica. O objetivo é usar a evidência dos testes para discutir qualidade, previsibilidade dos contratos e padronização do tratamento de falhas.

---

## Escopo Analisado

Foram analisadas as features em `src/test/resources/features`, cobrindo os domínios:

| Domínio | Features | Principais rotas |
| --- | --- | --- |
| Login | 1 | `POST /login` |
| Usuários | 5 | `POST /usuarios`, `GET /usuarios`, `GET /usuarios/{_id}`, `PUT /usuarios/{_id}`, `DELETE /usuarios/{_id}` |
| Produtos | 5 | `POST /produtos`, `GET /produtos`, `GET /produtos/{_id}`, `PUT /produtos/{_id}`, `DELETE /produtos/{_id}` |
| Carrinhos | 5 | `POST /carrinhos`, `GET /carrinhos`, `GET /carrinhos/{_id}`, `DELETE /carrinhos/concluir-compra`, `DELETE /carrinhos/cancelar-compra` |

**Resultado geral:** A cobertura automatizada está forte em fluxos positivos, validação de campos obrigatórios, formatos inválidos, regras de negócio, autenticação e contratos JSON Schema em consultas.

A suíte atual possui **16 arquivos `.feature`** e **113 cenários/scenario outlines**, fornecendo evidência sólida para propor melhorias com confiança.

---

## Achados Principais

### 1. Erros diferentes usam o mesmo status code `400`

**O que os testes mostram:**

As features tratam como `400 Bad Request` vários cenários semanticamente diferentes:

- Payload inválido ou incompleto
- Campo com tipo inválido
- Campo desconhecido
- ID com formato inválido
- **Recurso inexistente em consulta por ID** ← Este deveria ser `404`
- Produto inexistente ao criar carrinho
- Conflito de duplicidade ← Este deveria ser `409`
- Tentativa de excluir usuário/produto vinculado a carrinho ← Este deveria ser `409`

**Por que é um problema:**

Funciona para teste funcional, mas reduz a capacidade de diagnóstico para consumidores da API. Um cliente precisa ler a mensagem textual para entender se o problema é validação, conflito, inexistência ou violação de regra de negócio.

**Proposta:**

- `400` → payload malformado, campos inválidos, query params não permitidos
- `404` → recurso inexistente em consultas por ID
- `409` → conflitos de estado ou duplicidade
- `422` → regras de negócio em payload semanticamente válido (separar validação sintática de validação de domínio)

---

### 2. Falhas de autenticação estão cobertas, mas falta granularidade

**O que os testes mostram:**

Produtos e carrinhos validam token ausente e token inválido com `401 Unauthorized`. O comportamento é coerente para autenticação, mas não diferencia dois cenários importantes:

- Usuário **não autenticado** (ausência de token válido)
- Usuário **autenticado, mas sem permissão** (token válido, mas sem acesso ao recurso)

**Por que é um problema:**

Não há como diferenciar um erro de autenticação de um erro de autorização pelo status code. Um consumidor da API não sabe se precisa fazer login ou se simplesmente não tem permissão para aquela ação.

**Proposta:**

- `401 UNAUTHENTICATED` → token ausente, inválido ou expirado
- `403 FORBIDDEN` → usuário autenticado, mas sem permissão para o recurso
- Criar política de segurança documentada por endpoint
- Adicionar cenários explícitos para autorização, não apenas autenticação
- Padronizar respostas com `code`, `message`, `details` e `traceId` opcional

---

### 3. `PUT` com ID inexistente cria recurso novo (upsert)

**O que os testes mostram:**

As features de usuários e produtos esperam `201 Created` quando `PUT /usuarios/{_id}` ou `PUT /produtos/{_id}` recebem um ID inexistente. Isso indica que a API comporta-se como upsert (update-or-insert).

**Por que é um problema:**

Muitos consumidores esperariam `404 Not Found` para um ID inexistente. O comportamento é válido, mas deve estar muito claro, porque viola a semântica RESTful tradicional onde `PUT` atualiza e `POST` cria.

**Proposta:**

- Documentar formalmente que `PUT` funciona como upsert
- **Ou** alterar a API para retornar `404` em ID inexistente e reservar criação para `POST`
- Se o upsert continuar, garantir que o response indique claramente se houve criação ou atualização

---

### 4. `DELETE` inexistente retorna `200`

**O que os testes mostram:**

Usuários e produtos retornam `200 OK` com a mensagem "Nenhum registro excluído" ao tentar excluir recurso inexistente, inválido ou já excluído. Esse padrão é idempotente.

**Por que é um problema:**

O mesmo status representa tanto "exclusão bem-sucedida" quanto "nada foi alterado". Sem um campo estrutural na resposta, é impossível saber pelo status code se algo foi realmente excluído.

**Proposta:**

- Manter `200` se a API priorizar idempotência simples
- **Ou** usar `204 No Content` para exclusão bem-sucedida (sem body)
- **Ou** usar `404` para recurso inexistente, se a API quiser diagnóstico mais explícito
- Incluir um campo estrutural no response, como `deleted: true/false`, para deixar claro o resultado

---

### 5. Contratos JSON Schema ainda podem ser expandidos

**O que os testes mostram:**

As features validam contrato em vários cenários de consulta e criação de carrinho, mas nem todos os fluxos de erro e mutação parecem ter schema associado. Respostas de erro são validadas principalmente pela mensagem textual.

**Por que é um problema:**

Sem schema, não há validação estruturada de erros. Mudanças no formato de erro não são detectadas automaticamente. Além disso, campos sensíveis (como `password`) podem aparecer acidentalmente em responses de erro.

**Proposta:**

- Criar schema padrão para erros
- Validar contrato em **todos** os endpoints de criação, atualização e exclusão
- Separar schemas de sucesso e erro por domínio
- Validar ausência de campos sensíveis em respostas

---

### 6. Mensagens de erro são úteis, mas frágeis como contrato principal

**O que os testes mostram:**

Muitos cenários validam mensagens exatas: `"Nenhum registro excluído"`, `"Já existe produto com esse nome"`, etc. Isso ajuda a detectar regressões de texto, mas torna os testes sensíveis a mudanças de copy (e.g., correção ortográfica).

**Por que é um problema:**

Se a mensagem mudar por razão legítima (melhorar clareza, tradução), todos os testes quebram. Não há separação entre contrato (o que é garantido) e detalhe (mensagem legível).

**Proposta:**

- Manter validação de mensagem onde ela é requisito de produto
- Adicionar códigos de erro estáveis, como `USER_NOT_FOUND`, `INVALID_TOKEN`, `PRODUCT_ALREADY_EXISTS`
- Validar **primeiro** o código de erro (contrato) e **depois** a mensagem como evidência complementar

---

## Mapa de Status Codes Proposto

| Situação | Status atual | Status sugerido | Exemplo |
| --- | --- | --- | --- |
| **Sucesso** | | | |
| Sucesso em consulta | `200` | `200` | `GET /usuarios`, `GET /produtos` |
| Criação bem-sucedida | `201` | `201` | `POST /usuarios`, `POST /produtos` |
| Login bem-sucedido | `200` | `200` | `POST /login` |
| Exclusão bem-sucedida | `200` | `200` ou `204` | `DELETE /usuarios/{_id}` |
| | | | |
| **Erro do cliente (4xx)** | | | |
| Payload malformado | `400` | `400` | JSON sem aspas em login |
| Campo obrigatório ausente | `400` | `400` ou `422` | Criação/atualização de usuários |
| Campo com tipo inválido | `400` | `400` ou `422` | `preco`, `email`, `administrador` |
| Query parameter não permitido | `400` | `400` | `idade`, `categoria` |
| ID com formato inválido | `400` | `400` | ID fora do padrão de 16 caracteres |
| **Recurso inexistente em consulta por ID** | `400` ❌ | `404` ✓ | `GET /usuarios/{_id}` com ID inexistente |
| Token ausente ou inválido | `401` | `401` | Produtos e carrinhos protegidos |
| Usuário autenticado sem permissão | não evidenciado | `403` | Proposta para endpoints administrativos |
| **Duplicidade de recurso** | `400` ❌ | `409` ✓ | Email/produto/carrinho duplicado |
| **Regra de negócio violada** | `400` ❌ | `409` ou `422` ✓ | Excluir item em carrinho, estoque insuficiente |
| Método sem ID na rota | `405` | `405` | `PUT /usuarios`, `DELETE /usuarios` |
| **Exclusão de inexistente** | `200` | `200`, `204` ou `404` | `DELETE /usuarios/{_id}` inexistente |

---

## Modelo de Erro Proposto

Hoje os testes validam principalmente a mensagem textual. Para melhorar a estabilidade e usabilidade dos contratos, a API deveria retornar um modelo padronizado:

### Estrutura recomendada

```json
{
  "code": "RESOURCE_NOT_FOUND",
  "message": "Produto não encontrado",
  "details": [
    {
      "field": "_id",
      "issue": "not_found"
    }
  ],
  "traceId": "req-abc123def456"
}
```

### Campos

| Campo | Obrigatório | Tipo | Uso |
| --- | --- | --- | --- |
| `code` | Sim | string | Código estável para automação e clientes (ex: `RESOURCE_NOT_FOUND`, `VALIDATION_ERROR`) |
| `message` | Sim | string | Texto legível para pessoa usuária (pode mudar) |
| `details` | Não | array | Lista de campos ou regras que falharam (útil para formulários) |
| `traceId` | Não | string | ID único da requisição para correlação com logs da API |

### Exemplos por situação

**Recurso não encontrado (404):**
```json
{
  "code": "RESOURCE_NOT_FOUND",
  "message": "Usuário com ID 123abc não foi encontrado",
  "details": [{"field": "_id", "issue": "not_found"}],
  "traceId": "req-xyz789"
}
```

**Validação de campo (400):**
```json
{
  "code": "VALIDATION_ERROR",
  "message": "Erro ao validar entrada",
  "details": [
    {"field": "email", "issue": "invalid_format"},
    {"field": "nome", "issue": "required"}
  ],
  "traceId": "req-xyz789"
}
```

**Duplicidade (409):**
```json
{
  "code": "DUPLICATED_RESOURCE",
  "message": "Já existe um produto com o nome 'Notebook'",
  "details": [{"field": "nome", "issue": "already_exists"}],
  "traceId": "req-xyz789"
}
```

**Regra de negócio (422):**
```json
{
  "code": "BUSINESS_RULE_VIOLATION",
  "message": "Estoque insuficiente para este produto",
  "details": [{"field": "quantidade", "issue": "insufficient_stock", "available": 5, "requested": 10}],
  "traceId": "req-xyz789"
}
```

**Autenticação inválida (401):**
```json
{
  "code": "UNAUTHENTICATED",
  "message": "Token inválido ou expirado",
  "traceId": "req-xyz789"
}
```

**Sem permissão (403):**
```json
{
  "code": "FORBIDDEN",
  "message": "Você não tem permissão para acessar este recurso",
  "traceId": "req-xyz789"
}
```

---

## Códigos de Erro Sugeridos

| Código | Status | Quando usar |
| --- | --- | --- |
| `VALIDATION_ERROR` | 400 | Campos obrigatórios ausentes, tipos inválidos, campos vazios |
| `MALFORMED_JSON` | 400 | Body com JSON inválido ou incompleto |
| `INVALID_QUERY_PARAMETER` | 400 | Query param não permitido ou formato inválido |
| `INVALID_ID_FORMAT` | 400 | ID fora do padrão esperado (ex: menos de 16 caracteres) |
| `RESOURCE_NOT_FOUND` | 404 | Usuário, produto ou carrinho inexistente em consulta por ID |
| `UNAUTHENTICATED` | 401 | Token ausente, inválido ou expirado |
| `TOKEN_EXPIRED` | 401 | Token expirado (se a API controlar expiração) |
| `FORBIDDEN` | 403 | Usuário autenticado, mas sem permissão |
| `DUPLICATED_RESOURCE` | 409 | Email, produto ou carrinho já existente |
| `BUSINESS_RULE_VIOLATION` | 409 ou 422 | Estoque insuficiente, vínculo ativo com carrinho, compra já concluída/cancelada |
| `METHOD_NOT_ALLOWED` | 405 | Método HTTP não aceito para a rota |

---

## Melhorias por Domínio

### Login

**O que já está bem coberto:**
- Login válido
- Campos obrigatórios
- Tipos inválidos
- Email em formato inválido
- JSON malformado
- Credenciais inválidas com `401`

**Melhorias propostas:**
- Retornar erro estruturado com `code` para credenciais inválidas (não expor se o email existe ou não)
- Adicionar política de rate limit para tentativas repetidas (`429 TOO_MANY_REQUESTS`)
- Avaliar cenários de senha muito curta, muito longa e caracteres especiais
- Testar token gerado quanto a formato e validade, se a API expuser essa regra
- Documentar tempo de expiração do token, se houver

---

### Usuários

**O que já está bem coberto:**
- Criação, consulta, atualização e exclusão
- Validação de campos obrigatórios e formatos
- Busca por filtros individuais e combinados
- Usuário inexistente/deletado
- Tentativa de exclusão com carrinho ativo

**Melhorias propostas:**
- Usar `404` para `GET /usuarios/{_id}` quando o usuário não existir (atualmente retorna `400`)
- Usar `409 DUPLICATED_RESOURCE` para email já utilizado (atualmente retorna `400`)
- Usar `409 BUSINESS_RULE_VIOLATION` para exclusão de usuário com carrinho ativo (atualmente retorna `400`)
- Documentar se `PUT /usuarios/{_id}` deve criar usuário quando o ID não existe (comportamento upsert)
- Validar e garantir ausência de `password` em responses, especialmente em respostas de erro
- Adicionar cenários de autorização se usuário comum e administrador tiverem permissões diferentes

---

### Produtos

**O que já está bem coberto:**
- Criação, consulta, atualização e exclusão
- Autenticação obrigatória em criação, atualização e exclusão
- Validação de campos obrigatórios, tipos e valores numéricos
- Produto inexistente/deletado
- Duplicidade de nome
- Produto vinculado a carrinho
- Campos desconhecidos e JSON malformado

**Melhorias propostas:**
- Usar `404` para `GET /produtos/{_id}` quando o produto não existir (atualmente retorna `400`)
- Usar `409 DUPLICATED_RESOURCE` para produto com nome duplicado (atualmente retorna `400`)
- Usar `409 BUSINESS_RULE_VIOLATION` para excluir produto que faz parte de carrinho (atualmente retorna `400`)
- Separar `401 UNAUTHENTICATED` de `403 FORBIDDEN`, caso somente administradores possam criar, alterar ou excluir
- Revisar se `nome` e `descricao` numéricos deveriam ser aceitos como sucesso
- Documentar limites máximos reais para nome, descrição, preço e quantidade

---

### Carrinhos

**O que já está bem coberto:**
- Criação de carrinho
- Filtros de consulta
- Consulta por ID
- Conclusão e cancelamento de compra
- Autenticação ausente ou inválida
- Duplicidade de carrinho
- Produto inexistente
- Produto duplicado no payload
- Quantidade acima do estoque
- Campos inválidos

**Melhorias propostas:**
- Usar `404` para carrinho inexistente em `GET /carrinhos/{_id}` (atualmente retorna `400`)
- Usar `409 DUPLICATED_RESOURCE` para tentativa de criar mais de um carrinho para o mesmo usuário
- Usar `409 BUSINESS_RULE_VIOLATION` para compra já concluída ou cancelada quando a ação não puder ser repetida
- Usar `422 BUSINESS_RULE_VIOLATION` para quantidade acima do estoque (com detalhes de disponibilidade)
- Adicionar resposta estruturada contendo o item/produto que causou falha
- Validar comportamento quando o token pertence a usuário diferente do carrinho, se essa regra existir

---

## Backlog Priorizado

| Prioridade | Proposta | Impacto | Esforço |
| --- | --- | --- | --- |
| **Alta** | Padronizar modelo de erro (código, mensagem, detalhes, traceId) | Melhora consumo, debug e estabilidade dos testes | Médio |
| **Alta** | Separar `400`, `401`, `403`, `404`, `409` e `422` por tipo de falha | Status codes mais condizentes, diagnósticos mais claros | Médio |
| **Alta** | Expandir validações de segurança para autorização (`403`) | Reduz risco de endpoints protegidos aceitarem perfis indevidos | Médio |
| **Média** | Revisar e documentar comportamento de upsert em `PUT` | Evita ambiguidade entre criação e atualização | Baixo |
| **Média** | Revisar estratégia de `DELETE` idempotente | Deixa claro quando algo foi excluído ou não | Baixo |
| **Média** | Ampliar JSON Schema para responses de erro | Aumenta confiança nos contratos | Médio |
| **Média** | Adicionar códigos de erro estáveis | Desacopla testes de mudanças de mensagens | Baixo |
| **Baixa** | Criar relatório consolidado por domínio e tipo de falha | Facilita acompanhamento de qualidade ao longo do tempo | Baixo |

### Ações concretas (por prioridade)

**Sprint 1 (Alta prioridade):**
1. Criar uma matriz de status code esperado por endpoint e tipo de erro
2. Criar schema padrão JSON para erro (com `code`, `message`, `details`, `traceId`)
3. Implementar modelo de erro em todos os endpoints
4. Adicionar validação de schema em testes de erro

**Sprint 2 (Alta prioridade):**
5. Migrar endpoints para retornar `404` em lugar de `400` para recurso inexistente
6. Migrar endpoints para retornar `409` em lugar de `400` para duplicidade
7. Migrar endpoints para retornar `409` ou `422` em lugar de `400` para regras de negócio
8. Adicionar testes para autorização (`403`) quando houver diferença entre usuário comum e administrador

**Sprint 3 (Média prioridade):**
9. Revisar endpoints que retornam `400` para ID inválido — considerar separar em `400` (formato) e `404` (inexistência)
10. Expandir JSON Schema para todas as responses de erro
11. Adicionar validação de ausência de dados sensíveis em responses
12. Documentar comportamento de upsert em `PUT` ou alterar implementação

**Sprint 4 (Média/Baixa):**
13. Revisar estratégia de `DELETE` idempotente
14. Adicionar tags específicas a cenários: `@contract`, `@security`, `@negative`, `@business-rule`, `@status-code`
15. Criar relatório consolidado por domínio e tipo de falha
16. Integrar validação de relatório no pipeline de CI/CD

---

## Critério de Qualidade Proposto

Uma resposta de erro pode ser considerada madura quando atende aos seguintes critérios:

- ✓ Status code representa corretamente a **categoria** da falha (4xx, 5xx, etc.)
- ✓ Corpo JSON possui `code` **estável** e único por tipo de erro
- ✓ `message` é clara para pessoa consumidora (pode ser internacionalizada)
- ✓ `details` aponta **campo específico** ou **regra violada** (se aplicável)
- ✓ Contrato JSON Schema cobre o **formato esperado** do erro
- ✓ Teste automatizado valida **status, código, message e ausência de dados sensíveis**
- ✓ Documentação API especifica todos os `code` possíveis por endpoint
- ✓ Respostas não expõem stack trace, paths internos ou informações de segurança

---

## Conclusão

A suíte de testes atual já cumpre o papel de regressão funcional e também revela oportunidades claras de amadurecimento da API. Os cenários automatizados deixam evidente como a API responde hoje em situações de sucesso, erro de entrada, falha de autenticação, inexistência de recurso e conflito de regra de negócio.

O próximo salto de qualidade é transformar esses cenários em insumo de governança:

1. **Status codes mais expressivos** — separar 400 em 400, 404, 409, 422
2. **Erros estruturados** — código estável + mensagem legível + detalhes + traceId
3. **Contratos completos** — JSON Schema para sucesso e erro
4. **Segurança explícita** — autenticação vs autorização diferenciadas
5. **Testes mais robustos** — validar código, não apenas mensagem; validar ausência de dados sensíveis

Implementar essas melhorias elevará o nível de confiança e previsibilidade da API, reduzindo ambiguidades e melhorando a experiência dos consumidores.