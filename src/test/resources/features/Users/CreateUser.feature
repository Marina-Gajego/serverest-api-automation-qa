@regression @CreateUser @Users
Feature: Validate create user api in diferents scenarios

  Scenario: Create a user with valid data - (POST /usuarios)
    Given I have a valid user payload
    When I send a POST request to the user registration endpoint
    Then The response status code should be 201
    And The response should contain the message "Cadastro realizado com sucesso"
    And The response should contain the id field

  Scenario: Try to create a user with an email already registered - (POST /usuarios)
    Given I have a registered user
    When I have a user registration payload with the "email" as "already used"
    And I send a POST request to the user registration endpoint
    Then The response status code should be 400
    And The response should contain the message "Este email já está sendo usado"

  Scenario Outline: Ensure User API enforces payload schema validation- (POST /usuarios)
    Given I have a user registration payload with the "<field>" as "<condition>"
    When I send a POST request to the user registration endpoint
    Then The response status code should be 400
    And The response should contain the message "<expected_message>"

    Examples:
      | field         | condition      | expected_message                         |
      | nome          | an integer     | nome deve ser uma string                 |
      | email         | an integer     | email deve ser uma string                |
      | password      | an integer     | password deve ser uma string             |
      | administrador | an integer     | administrador deve ser 'true' ou 'false' |
      | email         | invalid format | email deve ser um email válido           |
      | nome          | missing        | nome é obrigatório                       |
      | email         | missing        | email é obrigatório                      |
      | password      | missing        | password é obrigatório                   |
      | administrador | missing        | administrador é obrigatório              |
      | administrador | invalid value  | administrador deve ser 'true' ou 'false' |