@regression @Products
Feature: Validate DELETE products API in different scenarios

  @PRODUCTS-020
  Scenario: Delete product by ID - (DELETE /produtos/{id})
    Given I have a registered product
    When I send a DELETE request to the products endpoint with the product ID
    Then The response status code should be 200
    And The response contract should match "schemas/Common/message.schema.json"
    And The response should contain the message "Registro excluído com sucesso"

  @PRODUCTS-021
  Scenario: Delete non-existent product - (DELETE /produtos/{id})
    Given I have a registered product
    When I send a DELETE request to the products endpoint with a non-existent product ID
    Then The response status code should be 200
    And The response contract should match "schemas/Common/message.schema.json"
    And The response should contain the message "Nenhum registro excluído"

  @PRODUCTS-022
  Scenario: Delete product with invalid token - (DELETE /produtos/{id})
    Given I have a registered product
    When I send a DELETE request to the products endpoint with the product ID and an invalid token
    Then The response status code should be 401
    And The response contract should match "schemas/Common/error_response.schema.json"
    And The response should contain the message "Token de acesso ausente, inválido, expirado ou usuário do token não existe mais"

  @PRODUCTS-023
  Scenario: Delete product that has already been deleted - (DELETE /produtos/{id})
    Given I have a registered product
    When I send a DELETE request to the products endpoint with the product ID
    And I send a DELETE request to the products endpoint with the same product ID again
    Then The response status code should be 200
    And The response contract should match "schemas/Common/message.schema.json"
    And The response should contain the message "Nenhum registro excluído"

  @PRODUCTS-024
  Scenario: Delete product that is in a cart - (DELETE /produtos/{id})
    Given I have a registered product
    And I have a registered cart
    When I send a DELETE request to the products endpoint with the product ID
    Then The response status code should be 400
    And The response contract should match "schemas/Common/error_response.schema.json"
    And The response should contain the message "Não é permitido excluir produto que faz parte de carrinho"
