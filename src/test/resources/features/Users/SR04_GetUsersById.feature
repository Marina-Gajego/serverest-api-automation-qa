@regression @Users
Feature: Validate GET user by ID API in different scenarios

  @USERS-022
  Scenario: Retrieve existing user by ID - (GET /usuarios/{_id})
    Given I have a registered user
    When I send a GET request to the users endpoint with the created user id
    Then The response status code should be 200
    And The response should contain the correct user details
    And The response contract should match "schemas/Users/get_user_by_id.schema.json"

  @USERS-023
  Scenario: Attempt to get user with non-existent ID - (GET /usuarios/{_id})
    Given I have a registered user
    When I send a GET request to the users with "nonexistent"
    Then The response status code should be 400
    And The response should contain the message "Usuário não encontrado"
    And The response contract should match "schemas/Users/get_user_by_id_not_found.schema.json"

  @USERS-024
  Scenario: Attempt to get user with invalid ID format - (GET /usuarios/{_id})
    Given I have a registered user
    When I send a GET request to the users with "invalid"
    Then The response status code should be 400
    And The response should contain the message "id deve ter exatamente 16 caracteres alfanuméricos"
    And The response contract should match "schemas/Users/get_user_by_id_invalid.schema.json"

  @USERS-025
  Scenario: Retrieve a deleted user by ID - (GET /usuarios/{_id})
    Given I have a registered user
    And I send a DELETE request to the users endpoint with the created user id
    When I send a GET request to the users endpoint with the created user id
    Then The response status code should be 400
    And The response should contain the message "Usuário não encontrado"
    And The response contract should match "schemas/Users/get_user_by_id_not_found.schema.json"