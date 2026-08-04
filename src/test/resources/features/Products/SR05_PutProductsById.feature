@regression @Products
Feature: Validate PUT product by ID API in different scenarios

  @id=
  Scenario: Update existing product by ID - (PUT /produtos/{_id})
    Given I have a registered product
    When I send a PUT request to the product for update all fields with the created product id
    Then The response status code should be 200
    And The response should contain the message "Registro alterado com sucesso"

  @id=
  Scenario Outline: Ensure update product API validates mandatory fields - (PUT /produtos/{_id})
    Given I have a registered product
    When I send a PUT request to the product with "<condition>"
    Then The response status code should be 400
    And The response should contain the message "<expected_message>"

    Examples:
      | condition          | expected_message        |
      | missing nome       | nome é obrigatório      |
      | missing preco      | preco é obrigatório     |
      | missing descricao  | descricao é obrigatório |
      | missing quantidade | quantidade é obrigatório |

  @id=
  Scenario Outline: Attempt to update just one product field - (PUT /produtos/{_id})
    Given I have a registered product
    When I send a PUT request to the product with "<condition>"
    Then The response status code should be 400
    And The response should contain the error messages
      | <first_message>  |
      | <second_message> |
      | <third_message>  |

    Examples:
      | condition       | first_message          | second_message          | third_message            |
      | only nome       | preco é obrigatório    | descricao é obrigatório | quantidade é obrigatório |
      | only preco      | nome é obrigatório     | descricao é obrigatório | quantidade é obrigatório |
      | only descricao  | nome é obrigatório     | preco é obrigatório     | quantidade é obrigatório |
      | only quantidade | nome é obrigatório     | preco é obrigatório     | descricao é obrigatório  |

  @id=
  Scenario Outline: Attempt to update product with invalid field format - (PUT /produtos/{_id})
    Given I have a registered product
    When I send a PUT request to the product with "<condition>"
    Then The response status code should be 400
    And The response should contain the message "<expected_message>"

    Examples:
      | condition                 | expected_message              |
      | invalid preco format      | preco deve ser um número      |
      | invalid quantidade format | quantidade deve ser um número |

  @id=
  Scenario Outline: Attempt to update product with invalid numeric values - (PUT /produtos/{_id})
    Given I have a registered product
    When I send a PUT request to the product with "<condition>"
    Then The response status code should be 400
    And The response should contain the message "<expected_message>"

    Examples:
      | condition     | expected_message                       |
      | -10 preco     | preco deve ser um número positivo      |
      | 0 preco       | preco deve ser um número positivo      |
      | -1 quantidade | quantidade deve ser maior ou igual a 0 |

  @id=
  Scenario Outline: Attempt to update product with empty fields - (PUT /produtos/{_id})
    Given I have a registered product
    When I send a PUT request to the product with "<condition>"
    Then The response status code should be 400
    And The response should contain the message "<expected_message>"

    Examples:
      | condition          | expected_message                    |
      | empty nome         | nome não pode ficar em branco       |
      | empty descricao    | descricao não pode ficar em branco  |
      | empty preco        | preco é obrigatório                 |
      | empty quantidade   | quantidade é obrigatório            |

  @id=
  Scenario Outline: Attempt to update product with null values - (PUT /produtos/{_id})
    Given I have a registered product
    When I send a PUT request to the product with "<condition>"
    Then The response status code should be 400
    And The response should contain the message "<expected_message>"

    Examples:
      | condition       | expected_message        |
      | null nome       | nome é obrigatório      |
      | null preco      | preco é obrigatório     |
      | null descricao  | descricao é obrigatório |
      | null quantidade | quantidade é obrigatório |

  @id=
  Scenario: Update product with non-existent ID - (PUT /produtos/{_id})
    Given I have an authenticated user
    When I send a PUT request to the product with "inexistent id"
    Then The response status code should be 201
    And The response should contain the message "Cadastro realizado com sucesso"
    And The response should contain a product id

  @id=
  Scenario Outline: Reject product ID with invalid format - (PUT /produtos/{_id})
    Given I have an authenticated user
    When I send a PUT request to the product with "<condition>"
    Then The response status code should be 400
    And The response should contain the message "id deve ter exatamente 16 caracteres alfanuméricos"

    Examples:
      | condition         |
      | invalid id        |
      | invalid excededid |

  @id=
  Scenario: Attempt without id in the path - (PUT /produtos)
    Given I have an authenticated user
    When I send a PUT request to the products endpoint without id in the path
    Then The response status code should be 405
    And The response should contain the message "Não é possível realizar PUT em /produtos. Acesse http://localhost:3000 para ver as rotas disponíveis e como utilizá-las."

  @id=
  Scenario: Attempt to update a product with malformed JSON payload - (PUT /produtos/{_id})
    Given I have a registered product
    And I have a malformed JSON payload
    When I send a PUT request to the product with "malformed JSON payload"
    Then The response status code should be 400
    And The response should contain the message "Adicione aspas em todos os valores. Para mais informações acesse a issue https://github.com/ServeRest/ServeRest/issues/225"

  @id=
  Scenario: Attempt to update product with empty body - (PUT /produtos/{_id})
    Given I have a registered product
    When I send a PUT request to the product with "empty body"
    Then The response status code should be 400
    And The response should contain the error messages
      | nome é obrigatório       |
      | preco é obrigatório      |
      | descricao é obrigatório  |
      | quantidade é obrigatório |

  @id=
  Scenario: Attempt to update product with extra unknown fields - (PUT /produtos/{_id})
    Given I have a registered product
    When I send a PUT request to the product with "extra unknown fields"
    Then The response status code should be 400
    And The response should contain the message "campo_desconhecido não é permitido"

  @id=
  Scenario: Attempt to update product without authentication token - (PUT /produtos/{_id})
    Given I have a registered product
    When I send a PUT request to the product with "without authentication token"
    Then The response status code should be 401
    And The response should contain the message "Token de acesso ausente, inválido, expirado ou usuário do token não existe mais"

  @id=
  Scenario: Attempt to update product with invalid authentication token - (PUT /produtos/{_id})
    Given I have a registered product
    When I send a PUT request to the product with "invalid token"
    Then The response status code should be 401
    And The response should contain the message "Token de acesso ausente, inválido, expirado ou usuário do token não existe mais"

  @id=
  Scenario: Attempt to update a product using an already registered product name - (PUT /produtos/{_id})
    Given I have a registered product
    When I send a PUT request to the product using an already registered product name
    Then The response status code should be 400
    And The response should contain the message "Já existe produto com esse nome"