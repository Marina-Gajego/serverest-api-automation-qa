@regression @Carts
Feature: Validate create carts API in different scenarios

  Background:
    Given I have a registered product

  @CARTS-001
  Scenario: Create a cart with success - (POST /carrinhos)
    Given I have a valid cart payload
      | quantidade | 1 |
    When I send a POST request to create a cart
    Then The response status code should be 201
    And The response should contain the message "Cadastro realizado com sucesso"
    And The response should contain a cart id
    And The response contract should match "schemas/Carts/post_create_cart_success.schema.json"

  @CARTS-002
  Scenario: Attempt to create a duplicated cart for the same user - (POST /carrinhos)
    Given I have a valid cart payload
      | quantidade | 1 |
    And I send a POST request to create a cart
    And The response status code should be 201
    When I send a POST request to create a cart
    Then The response status code should be 400
    And The response contract should match "schemas/Common/error_response.schema.json"
    And The response should contain the message "Não é permitido ter mais de 1 carrinho"

  @CARTS-003
  Scenario: Attempt to create a cart with duplicated product in payload - (POST /carrinhos)
    Given I have a cart payload with "duplicated product"
    When I send a POST request to create a cart
    Then The response status code should be 400
    And The response contract should match "schemas/Common/error_response.schema.json"
    And The response should contain the message "Não é permitido possuir produto duplicado"

  @CARTS-004
  Scenario: Attempt to create a cart with product not found - (POST /carrinhos)
    Given I have a cart payload with "product not found"
    When I send a POST request to create a cart
    Then The response status code should be 400
    And The response contract should match "schemas/Common/error_response.schema.json"
    And The response should contain the message "Produto não encontrado"

  @CARTS-005
  Scenario: Attempt to create a cart with quantity greater than stock - (POST /carrinhos)
    Given I have a cart payload with the "quantidade" as "greater than stock"
    When I send a POST request to create a cart
    Then The response status code should be 400
    And The response contract should match "schemas/Common/error_response.schema.json"
    And The response should contain the message "Produto não possui quantidade suficiente"

  @CARTS-006
  Scenario Outline: Attempt to create a cart with invalid product fields - (POST /carrinhos)
    Given I have a cart payload with the "<field>" as "<condition>"
    When I send a POST request to create a cart
    Then The response status code should be 400
    And The response contract should match "schemas/Common/error_response.schema.json"
    And The response should contain the error messages
      | <expected_message> |

    Examples:
      | field      | condition | expected_message                                     |
      | idProduto  | missing   | produtos[0].idProduto é obrigatório                  |
      | idProduto  | empty     | produtos[0].idProduto não pode ficar em branco       |
      | quantidade | missing   | produtos[0].quantidade é obrigatório                 |
      | quantidade | string    | produtos[0].quantidade deve ser um número            |
      | quantidade | negative  | produtos[0].quantidade deve ser um número positivo   |

  @CARTS-007
  Scenario Outline: Attempt to create a cart with invalid authentication - (POST /carrinhos)
    Given I have a cart payload with "<condition>"
    When I send a POST request to create a cart
    Then The response status code should be 401
    And The response contract should match "schemas/Common/error_response.schema.json"
    And The response should contain the message "Token de acesso ausente, inválido, expirado ou usuário do token não existe mais"

    Examples:
      | condition                    |
      | without authentication token |
      | invalid token                |
