@regression @Carts @teste
Feature: Validate GET carts API in different scenarios


  @id=
  Scenario Outline: Get cart by <param> query parameter - (GET /carrinhos)
    Given I have a registered cart
    When I send a GET request to the carts endpoint with the "<param>" query parameter
    Then The response status code should be 200
    And The response should contain the correct cart
    And The response contract should match "schemas/Carts/get_carts.schema.json"

    Examples:
      | param           |
      | _id             |
      | precoTotal      |
      | quantidadeTotal |
      | idUsuario       |

  @id=
  Scenario: Get cart by all query parameters - (GET /carrinhos)
    Given I have a registered cart
    When I send a GET request to the carts endpoint
    Then The response status code should be 200
    And The response should contain the correct cart
    And The response contract should match "schemas/Carts/get_carts.schema.json"

  @id=
  Scenario Outline: Get created cart is not returned by <param> query parameter with a different value - (GET /carrinhos)
    Given I have a registered cart
    When I send a GET request to the carts endpoint with the "<param>" query parameter and value "<value>"
    Then The response status code should be 200
    And The response should not contain the created cart
    And The response contract should match "schemas/Carts/get_carts.schema.json"

    Examples:
      | param           | value               |
      | _id             | invalid-cart-id-123 |
      | precoTotal      | createdValuePlusOne |
      | quantidadeTotal | createdValuePlusOne |
      | idUsuario       | invalid-user-id-123 |

  @id=
  Scenario: Get no carts when query parameter value does not match any cart - (GET /carrinhos)
    Given I have a registered cart
    When I send a GET request to the carts endpoint with the "_id" query parameter and value "invalid-cart-id-123"
    Then The response status code should be 200
    And The response should not contain any carts
    And The response contract should match "schemas/Carts/get_carts.schema.json"

  @id=
  Scenario: Get carts without query parameters - (GET /carrinhos)
    Given I have a registered cart
    When I send a GET request to the carts endpoint without query parameters
    Then The response status code should be 200
    And The response should contain the correct cart
    And The response contract should match "schemas/Carts/get_carts.schema.json"