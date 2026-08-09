@regression @Users
Feature: Validate create users api in different scenarios

  @USERS-001
  Scenario: Create user with success - (POST /usuarios)
    Given I have a valid user payload
    When I send a POST request to the users endpoint
    Then The response status code should be 201
    And The response contract should match "schemas/Common/message_with_id.schema.json"
    And The response should contain the message "Cadastro realizado com sucesso"
    And The response should contain a user id

  @USERS-002
  Scenario Outline: Ensure post create user API validates mandatory fields and formats - (POST /login)
    Given I have a create user payload with the "<field>" as "<condition>"
    When I send a POST request to the users endpoint
    Then The response status code should be 400
    And The response contract should match "schemas/Common/error_response.schema.json"
    And The response should contain the message "<expected_message>"

    Examples:
      | field         | condition      | expected_message                         |
      | nome          | missing        | nome é obrigatório                       |
      | email         | missing        | email é obrigatório                      |
      | password      | missing        | password é obrigatório                   |
      | administrador | missing        | administrador é obrigatório              |
      | nome          | an integer     | nome deve ser uma string                 |
      | email         | an integer     | email deve ser uma string                |
      | password      | an integer     | password deve ser uma string             |
      | administrador | an integer     | administrador deve ser 'true' ou 'false' |
      | nome          | null           | nome deve ser uma string                 |
      | email         | null           | email deve ser uma string                |
      | password      | null           | password deve ser uma string             |
      | administrador | null           | administrador deve ser 'true' ou 'false' |
      | nome          | empty          | nome não pode ficar em branco            |
      | email         | empty          | email não pode ficar em branco           |
      | password      | empty          | password não pode ficar em branco        |
      | administrador | empty          | administrador deve ser 'true' ou 'false' |

  @USERS-003
  Scenario: Create user with malformed JSON payload - (POST /usuarios)
    Given I have a malformed JSON payload
    When I send a POST request to the users endpoint
    Then The response status code should be 400
    And The response contract should match "schemas/Common/error_response.schema.json"
    And The response should contain the message "Adicione aspas em todos os valores. Para mais informações acesse a issue https://github.com/ServeRest/ServeRest/issues/225"

  @USERS-004
  Scenario Outline: Fail to create user with invalid data - (POST /usuarios)
    Given I have a user creation payload with "<condition>"
    When I send a POST request to the users endpoint
    Then The response status code should be 400
    And The response contract should match "schemas/Common/error_response.schema.json"
    And The response should not contain a user id
    And The response should contain the message "<expected_message>"

    Examples:
      | condition                  | expected_message                         |
      | invalid email              | email deve ser um email válido           |
      | invalid administrador      | administrador deve ser 'true' ou 'false' |
