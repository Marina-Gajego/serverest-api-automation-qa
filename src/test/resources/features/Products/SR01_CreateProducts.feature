@regression @Products
Feature: Validate create products API in different scenarios

  @id=
  Scenario: Create a product with valid information - (POST /produtos)
    Given I have an authenticated user
    And I have a valid product payload
    When I send a POST request to create a product
    Then The response status code should be 201
    And The response should contain the message "Cadastro realizado com sucesso"
    And The response should contain a product id

  @id=
  Scenario Outline: Ensure create product API validates mandatory fields - (POST /produtos)
    Given I have an authenticated user
    And I have a product payload with the "<field>" as "<condition>"
    When I send a POST request to create a product
    Then The response status code should be 400
    And The response should contain the message "<expected_message>"

    Examples:
      | field       | condition | expected_message                      |
      | nome        | missing   | nome é obrigatório                    |
      | preco       | missing   | preco é obrigatório                   |
      | descricao   | missing   | descricao é obrigatório               |
      | quantidade  | missing   | quantidade é obrigatório              |
      | nome        | empty     | nome não pode ficar em branco         |
      | preco       | empty     | preco é obrigatório                   |
      | descricao   | empty     | descricao não pode ficar em branco    |
      | quantidade  | empty     | quantidade é obrigatório              |

  @id=
  Scenario Outline: Create product with invalid data types - (POST /produtos)
    Given I have an authenticated user
    And I have a product payload with "<field>" as "<invalid_value>"
    When I send a POST request to create a product
    Then The response status code should be <status_code>
    And The response should contain the message "<expected_message>"

    Examples:
      | field      | invalid_value | status_code | expected_message                |
      | preco      | abc           | 400         | preco deve ser um número        |
      | preco      | 12.5xyz       | 400         | preco deve ser um número        |
      | quantidade | texto         | 400         | quantidade deve ser um número   |
      | quantidade | 10.5          | 400         | quantidade deve ser um inteiro  |
      | nome       | 123           | 201         | Cadastro realizado com sucesso  |
      | nome       | 45.67         | 201         | Cadastro realizado com sucesso  |
      | descricao  | 999           | 201         | Cadastro realizado com sucesso  |

  @id=
  Scenario: Create product with malformed JSON payload - (POST /produtos)
    Given I have an authenticated user
    And I have a malformed JSON payload
    When I send a POST request to create a product
    Then The response status code should be 400
    And The response should contain the message "Adicione aspas em todos os valores. Para mais informações acesse a issue https://github.com/ServeRest/ServeRest/issues/225"

  @id=
  Scenario: Create product without authentication token - (POST /produtos)
    Given I have a valid product payload
    And I have no authentication token
    When I send a POST request to create a product
    Then The response status code should be 401
    And The response should contain the message "Token de acesso ausente, inválido, expirado ou usuário do token não existe mais"

  @id=
  Scenario: Create product with invalid authentication token - (POST /produtos)
    Given I have an authenticated user
    And I have a product payload with an invalid token
    When I send a POST request to create a product
    Then The response status code should be 401
    And The response should contain the message "Token de acesso ausente, inválido, expirado ou usuário do token não existe mais"

  @id=
  Scenario Outline: Create product with invalid numeric values - (POST /produtos)
    Given I have an authenticated user
    And I have a product payload with "<field>" as "<value>"
    When I send a POST request to create a product
    Then The response status code should be 400
    And The response should contain the message "<expected_message>"

    Examples:
      | field      | value  | expected_message                       |
      | preco      | -10    | preco deve ser um número positivo      |
      | preco      | -100   | preco deve ser um número positivo      |
      | preco      | 100.50 | preco deve ser um inteiro              |
      | preco      | 0      | preco deve ser um número positivo      |
      | quantidade | -1     | quantidade deve ser maior ou igual a 0 |

  @id=
  Scenario Outline: Create product with boundary field values - (POST /produtos)
    Given I have an authenticated user
    And I have a product payload with "<field>" as "<value>"
    When I send a POST request to create a product
    Then The response status code should be <status_code>
    And The response should contain the message "<expected_message>"

    Examples:
      | field      | value                            | status_code | expected_message                  |
      | descricao  | !@#$%^&*()_+-=[]{};\:',.<>?/~`   | 201         | Cadastro realizado com sucesso    |
      | descricao  | Produto com acentuação           | 201         | Cadastro realizado com sucesso    |
      | preco      | 0                                | 400         | preco deve ser um número positivo |
      | quantidade | 1                                | 201         | Cadastro realizado com sucesso    |
      | quantidade | 0                                | 201         | Cadastro realizado com sucesso    |
      | quantidade | 999999                           | 201         | Cadastro realizado com sucesso    |

  @id=
  Scenario: Create product with minimum name length - (POST /produtos)
    Given I have an authenticated user
    And I have a product payload with a short name
    When I send a POST request to create a product
    Then The response status code should be 201
    And The response should contain the message "Cadastro realizado com sucesso"

  @id=
  Scenario: Create product with long name - (POST /produtos)
    Given I have an authenticated user
    And I have a product payload with a long name
    When I send a POST request to create a product
    Then The response status code should be 201
    And The response should contain the message "Cadastro realizado com sucesso"

  @id=
  Scenario: Create product with extra unknown fields in payload - (POST /produtos)
    Given I have an authenticated user
    And I have a product payload with extra unknown fields
    When I send a POST request to create a product
    Then The response status code should be 400
    And The response should contain the message "campo_desconhecido não é permitido"

  @id=
  Scenario Outline: Create product with null values - (POST /produtos)
    Given I have an authenticated user
    And I have a product payload with "<field>" as "null"
    When I send a POST request to create a product
    Then The response status code should be 400
    And The response should contain the message "<expected_message>"

    Examples:
      | field      | expected_message         |
      | nome       | nome é obrigatório       |
      | preco      | preco é obrigatório      |
      | descricao  | descricao é obrigatório  |
      | quantidade | quantidade é obrigatório |