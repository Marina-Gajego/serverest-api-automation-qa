@regression @Users
Feature: Validate DELETE user API in different scenarios

  @USERS-016
  Scenario: Delete an existing user successfully - (DELETE /usuarios/{_id})
    Given I have a registered user
    When I send a DELETE request to the users endpoint with the created user id
    Then The response status code should be 200
    And The response contract should match "schemas/Common/message.schema.json"
    And The response should contain the message "Registro excluído com sucesso"

  @USERS-017
  Scenario: Delete an already deleted user - (DELETE /usuarios/{_id})
    Given I have a registered user
    When I send a DELETE request to the users endpoint with the created user id
    And I send a DELETE request to the users endpoint with the created user id
    Then The response status code should be 200
    And The response contract should match "schemas/Common/message.schema.json"
    And The response should contain the message "Nenhum registro excluído"

  @USERS-018
  Scenario: Reject DELETE users request without id - (DELETE /usuarios)
    Given I have a registered user who is not an admin
    When I send a DELETE request to the users endpoint without id
    Then The response status code should be 405
    And The response contract should match "schemas/Common/error_response.schema.json"
    And The response should contain the message "Não é possível realizar DELETE em /usuarios. Acesse http://localhost:3000 para ver as rotas disponíveis e como utilizá-las."

  @USERS-019
  Scenario: Attempt to delete user with non-existent ID - (DELETE /usuarios/{_id})
    Given I have a registered user
    When I send a DELETE request to the users with "nonexistent"
    Then The response status code should be 200
    And The response contract should match "schemas/Common/message.schema.json"
    And The response should contain the message "Nenhum registro excluído"

  @USERS-020
  Scenario: Attempt to delete user with invalid ID format - (DELETE /usuarios/{_id})
    Given I have a registered user
    When I send a DELETE request to the users with "invalid"
    Then The response status code should be 200
    And The response contract should match "schemas/Common/message.schema.json"
    And The response should contain the message "Nenhum registro excluído"

  @USERS-021
  Scenario: Attempt to delete user with an active shopping cart - (DELETE /usuarios/{_id})
    Given I have a registered cart
    When I send a DELETE request to the users endpoint with the created user id
    Then The response status code should be 400
    And The response contract should match "schemas/Common/error_response.schema.json"
    And The response should contain the message "Não é permitido excluir usuário com carrinho cadastrado"
    And The response should contain the cart id
