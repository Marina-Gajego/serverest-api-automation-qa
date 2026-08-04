@regression @Products
Feature: Validate GET product by ID API in different scenarios

  @id=
  Scenario: Retrieve existing product by ID - (GET /produtos/{id})
    Given I have a registered product
    When I send a GET request to the product endpoint with the created product id
    Then The response status code should be 200
    And The response should contain the correct product details
    And The response contract should match "schemas/Products/get_products_by_id_success.schema.json"

  @id=
  Scenario: Retrieve nonexistent product by ID - (GET /produtos/{id})
    When I send a GET request to the products with "nonexistent"
    Then The response status code should be 400
    And The response should contain the message "Produto não encontrado"
    And The response contract should match "schemas/Products/get_products_by_id_not_found.schema.json"

  @id=
  Scenario: Reject product ID with invalid characters - (GET /produtos/{id})
    When I send a GET request to the products with "invalid"
    Then The response status code should be 400
    And The response should contain the message "id deve ter exatamente 16 caracteres alfanuméricos"
    And The response contract should match "schemas/Products/get_products_by_id_invalid.schema.json"

  @id=
  Scenario: Retrieve deleted product by ID - (GET /produtos/{id})
    Given I have a registered product
    And I send a DELETE request to the products endpoint with the product ID
    When I send a GET request to the product endpoint with the created product id
    Then The response status code should be 400
    And The response should contain the message "Produto não encontrado"
    And The response contract should match "schemas/Products/get_products_by_id_not_found.schema.json"

  @id=
  Scenario: Retrieve product by lower-case transformed ID - (GET /produtos/{id})
    Given I have a registered product
    When I send a GET request to the products with "idminusculo"
    Then The response status code should be 400
    And The response should contain the message "Produto não encontrado"
    And The response contract should match "schemas/Products/get_products_by_id_not_found.schema.json"

  @id=
  Scenario: Reject product ID with exceeded length - (GET /produtos/{id})
    When I send a GET request to the products with "excededid"
    Then The response status code should be 400
    And The response should contain the message "id deve ter exatamente 16 caracteres alfanuméricos"
    And The response contract should match "schemas/Products/get_products_by_id_invalid.schema.json"

  @id=
  Scenario: Retrieve product after update - (GET /produtos/{id})
    Given I have a registered product
    And I send a PUT request to the product for update all fields with the created product id
    When I send a GET request to the product endpoint with the created product id
    Then The response status code should be 200
    And The response should contain the correct product details
    And The response contract should match "schemas/Products/get_products_by_id_success.schema.json"