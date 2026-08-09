@regression @Carts
Feature: Validate delete/cancel API in different scenarios

  @CARTS-012
  Scenario: Cancel purchase with valid token and cart with products - (DELETE /carrinhos/cancelar-compra)
    Given I have a registered cart
    When I send a DELETE request to the cancel purchase endpoint
    Then The response status code should be 200
    And The response contract should match "schemas/Common/message.schema.json"
    And The response should contain the message "Registro excluído com sucesso"

  @CARTS-013
  Scenario: Attempt to delete cancel purchase without authentication - (DELETE /carrinhos/cancelar-compra)
    Given I have a registered user
    And I send a POST request to the authentication endpoint
    When I send a DELETE request to the cancel purchase without authentication
    Then The response status code should be 401
    And The response contract should match "schemas/Common/error_response.schema.json"
    And The response should contain the message "Token de acesso ausente, inválido, expirado ou usuário do token não existe mais"

  @CARTS-014
  Scenario: Attempt to delete cancel purchase with invalid token - (DELETE /carrinhos/cancelar-compra)
    Given I set an invalid token cart
    When I send a DELETE request to the cancel purchase endpoint
    Then The response status code should be 401
    And The response contract should match "schemas/Common/error_response.schema.json"
    And The response should contain the message "Token de acesso ausente, inválido, expirado ou usuário do token não existe mais"

  @CARTS-015
  Scenario: Attempt delete cancel purchase with a cart that has already been completed - (DELETE /carrinhos/cancelar-compra)
    Given I have a registered cart
    And I send a DELETE request to the complete purchase endpoint
    When I send a DELETE request to the cancel purchase endpoint
    Then The response status code should be 200
    And The response contract should match "schemas/Common/message.schema.json"
    And The response should contain the message "Não foi encontrado carrinho para esse usuário"
