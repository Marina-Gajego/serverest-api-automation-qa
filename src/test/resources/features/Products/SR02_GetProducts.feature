@regression @Products
Feature: Validate GET products API in different scenarios

  @id=
  Scenario Outline: Get product by <param> query parameter - (GET /produtos)
    Given I have a registered product
    When I send a GET request to the products endpoint with the "<param>" query parameter
    Then The response status code should be 200
    And The response should contain the correct product
    And The response contract should match "schemas/Products/get_products.schema.json"

    Examples:
      | param      |
      | _id        |
      | nome       |
      | preco      |
      | descricao  |
      | quantidade |

  @id=
  Scenario: Get product by all query parameters - (GET /produtos)
    Given I have a registered product
    When I send a GET request to the products endpoint
    Then The response status code should be 200
    And The response should contain the correct product
    And The response contract should match "schemas/Products/get_products.schema.json"

  @id=
  Scenario Outline: Get created product is not returned by <param> query parameter with a different value - (GET /produtos)
    Given I have a registered product
    When I send a GET request to the products endpoint with the "<param>" query parameter and value "<value>"
    Then The response status code should be 200
    And The response should not contain the created product
    And The response contract should match "schemas/Products/get_products.schema.json"

    Examples:
      | param      | value                 |
      | _id        | 12345                 |
      | nome       | Produto inexistente   |
      | preco      | 999999                |
      | descricao  | Descricao inexistente |
      | quantidade | 999999                |

  @id=
  Scenario Outline: Get no products when query parameter value is transformed - (GET /produtos)
    Given I have a registered product
    When I send a GET request to the products endpoint with the "<param>" query parameter and value "<value>"
    Then The response status code should be 200
    And The response should not contain any products
    And The response contract should match "schemas/Products/get_products.schema.json"

    Examples:
      | param     | value             |
      | nome      | specialCharacters |
      | nome      | leadingSpaces     |
      | descricao | specialCharacters |

  @id=
  Scenario Outline: Reject invalid query parameter values - (GET /produtos)
    Given I have a registered product
    When I send a GET request to the products endpoint with the "<param>" query parameter and value "<value>"
    Then The response status code should be 400
    And The response should contain the message "<message_expected>"

    Examples:
      | param      | value | message_expected                   |
      | categoria  | tech  | categoria não é permitido          |
      | preco      | abc   | preco deve ser um número           |
      | preco      | 10.5  | preco deve ser um inteiro          |
      | quantidade | abc   | quantidade deve ser um número      |
      | quantidade | 10.5  | quantidade deve ser um inteiro     |

  @id=
  Scenario: Get products without query parameters - (GET /produtos)
    Given I have a registered product
    When I send a GET request to the products endpoint without query parameters
    Then The response status code should be 200
    And The response should contain the correct product
    And The response contract should match "schemas/Products/get_products.schema.json"

  @id=
  Scenario: Get product after deletion - (GET /produtos)
    Given I have a registered product
    And I send a DELETE request to the products endpoint with the product ID
    When I send a GET request to the products endpoint
    Then The response status code should be 200
    And The response should not contain any products
    And The response contract should match "schemas/Products/get_products.schema.json"