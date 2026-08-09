@regression @Carts
Feature: Validate GET cart by ID API in different scenarios

  @CARTS-021
  Scenario: Retrieve existing cart by ID - (GET /carrinhos/{_id})
    Given I have a registered cart
    When I send a GET request to the carts endpoint with the created cart id
    Then The response status code should be 200
    And The response should contain the correct cart details
    And The response contract should match "schemas/Carts/get_cart_by_id_success.schema.json"

  @CARTS-022
  Scenario: Attempt to get cart with non-existent ID - (GET /carrinhos/{_id})
    When I send a GET request to the carts with "nonexistent"
    Then The response status code should be 400
    And The response should contain the message "Carrinho não encontrado"
    And The response contract should match "schemas/Carts/get_cart_by_id_not_found.schema.json"

  @CARTS-023
  Scenario: Attempt to get cart with invalid ID format - (GET /carrinhos/{_id})
    When I send a GET request to the carts with "invalid"
    Then The response status code should be 400
    And The response should contain the message "id deve ter exatamente 16 caracteres alfanuméricos"
    And The response contract should match "schemas/Carts/get_cart_by_id_invalid.schema.json"

  @CARTS-024
  Scenario: Attempt to get cart with exceeded ID length - (GET /carrinhos/{_id})
    When I send a GET request to the carts with "excededid"
    Then The response status code should be 400
    And The response should contain the message "id deve ter exatamente 16 caracteres alfanuméricos"
    And The response contract should match "schemas/Carts/get_cart_by_id_invalid.schema.json"
