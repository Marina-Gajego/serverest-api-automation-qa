@regression @GetUserByID @Users
Feature: User Retrieval API Validation in different scenarios

  Scenario: Successfully retrieve a user by ID - (GET /usuarios/{_id})
    Given I have a registered user
    When I request the user details by ID
    Then The response status code should be 200
    And The returned information should match the registration data

  Scenario Outline: Successfully retrieve a user by ID - (GET /usuarios/{_id})
    Given I request a user with "<condition>" ID "<id_value>"
    When I request the user details by ID
    Then The response status code should be 400
    And The response should contain the message "<expected_message>"

    Examples:
    | condition            | id_value          | expected_message                                   |
    | inexistent           | ABC123EDT987HJU4  | Usuário não encontrado                             |
    | null                 | null              | id deve ter exatamente 16 caracteres alfanuméricos |
    | id than 16 caracters | abhs176di7519dhyo | id deve ter exatamente 16 caracteres alfanuméricos |
    | id less 16 caracters | diuc3459ih        | id deve ter exatamente 16 caracteres alfanuméricos |