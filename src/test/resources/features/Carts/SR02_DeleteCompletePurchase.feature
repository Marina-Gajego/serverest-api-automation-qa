@regression @Carts
Feature: Validate delete/complete purchase API in different scenarios

  @id=
  Scenario: Delete complete purchase with valid token and cart with products - (DELETE /carrinhos/concluir-compra)
    Given I have a registered cart
    When I send a DELETE request to the complete purchase endpoint
    Then The response status code should be 200
    And The response should contain the message "Registro excluído com sucesso"

  @id=
  Scenario: Attempt to delete complete purchase without authentication - (DELETE /carrinhos/concluir-compra)
    Given I have a registered user
    And I send a POST request to the authentication endpoint
    When I send a DELETE request to the complete purchase without authentication
    Then The response status code should be 401
    And The response should contain the message "Token de acesso ausente, inválido, expirado ou usuário do token não existe mais"

  @id=
  Scenario: Attempt to delete complete purchase with invalid token - (DELETE /carrinhos/concluir-compra)
    Given I set an invalid token cart
    When I send a DELETE request to the complete purchase endpoint
    Then The response status code should be 401
    And The response should contain the message "Token de acesso ausente, inválido, expirado ou usuário do token não existe mais"

    @id=
    Scenario: Attempt delete complete purchase with a cart that has already been canceled - (DELETE /carrinhos/concluir-compra)
      Given I have a registered cart
      And I send a DELETE request to the cancel purchase endpoint
      When I send a DELETE request to the complete purchase endpoint
      Then The response status code should be 200
      And The response should contain the message "Não foi encontrado carrinho para esse usuário"