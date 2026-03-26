@regression @Login
Feature: Validate login api in different scenarios

  Scenario: Login with valid credentials - (POST /login)
    Given I have a registered user
    When I send a POST request to the authentication endpoint with the registered credentials
    Then The response status code should be 200
    And The response should contain the message "Login realizado com sucesso"
    And The response should contain a token

  Scenario Outline: Ensure authentication API validates mandatory fields and formats - (POST /login)
    Given I have a login payload with the "<field>" as "<condition>"
    When I send a POST request to the authentication endpoint
    Then The response status code should be 400
    And The response should contain the message "<expected_message>"

    Examples:
      | field    | condition      | expected_message               |
      | email    | missing        | email é obrigatório            |
      | password | missing        | password é obrigatório         |
      | email    | an integer     | email deve ser uma string      |
      | password | an integer     | password deve ser uma string   |
      | email    | null           | email deve ser uma string      |
      | password | null           | password deve ser uma string   |
      | email    | invalid format | email deve ser um email válido |