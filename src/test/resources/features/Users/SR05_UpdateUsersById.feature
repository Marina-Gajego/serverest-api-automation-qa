@regression @Users
Feature: Validate PUT user by ID API in different scenarios

  @USERS-026
  Scenario: Update existing user by ID - (PUT /usuarios/{_id})
    Given I have a registered user
    When I send a PUT request to the user for update all fields with the created user id
    Then The response status code should be 200
    And The response contract should match "schemas/Common/message.schema.json"
    And The response should contain the message "Registro alterado com sucesso"

  @USERS-027
  Scenario: Update just nome field of the existing user by ID - (PUT /usuarios/{_id})
    Given I have a registered user
    When I send a PUT request to the user for update the with "nome" with the created user id
    Then The response status code should be 400
    And The response contract should match "schemas/Common/error_response.schema.json"
    And The response should contain the error messages
      | email é obrigatório         |
      | password é obrigatório      |
      | administrador é obrigatório |

  @USERS-028
  Scenario: Update just email field of the existing user by ID - (PUT /usuarios/{_id})
    Given I have a registered user
    When I send a PUT request to the user for update the with "email" with the created user id
    Then The response status code should be 400
    And The response contract should match "schemas/Common/error_response.schema.json"
    And The response should contain the error messages
      | nome é obrigatório          |
      | password é obrigatório      |
      | administrador é obrigatório |

  @USERS-029
  Scenario: Update just password field of the existing user by ID - (PUT /usuarios/{_id})
    Given I have a registered user
    When I send a PUT request to the user for update the with "password" with the created user id
    Then The response status code should be 400
    And The response contract should match "schemas/Common/error_response.schema.json"
    And The response should contain the error messages
      | nome é obrigatório          |
      | email é obrigatório         |
      | administrador é obrigatório |

  @USERS-030
  Scenario: Update just administrador field of the existing user by ID - (PUT /usuarios/{_id})
    Given I have a registered user
    When I send a PUT request to the user for update the with "administrador" with the created user id
    Then The response status code should be 400
    And The response contract should match "schemas/Common/error_response.schema.json"
    And The response should contain the error messages
      | nome é obrigatório     |
      | email é obrigatório    |
      | password é obrigatório |

  @USERS-031
  Scenario: Update user with non-existent ID - (PUT /usuarios/{_id})
    Given I have a registered user
    When I send a PUT request to the user with inexistent id
    Then The response status code should be 201
    And The response contract should match "schemas/Common/message_with_id.schema.json"
    And The response should contain the message "Cadastro realizado com sucesso"
    And The response should contain a user id

  @USERS-032
  Scenario Outline: Attempt to update user with invalid field format - (PUT /usuarios/{_id})
    Given I have a registered user
    When I send a PUT request to the user with invalid "<field>" format
    Then The response status code should be 400
    And The response contract should match "schemas/Common/error_response.schema.json"
    And The response should contain the message "<expected_message>"

    Examples:
      | field         | expected_message                                      |
      | nome          | nome deve ser uma string                              |
      | password      | password deve ser uma string                          |
      | email         | email deve ser um email válido                        |
      | administrador | administrador deve ser 'true' ou 'false'              |

  @USERS-033
  Scenario: Attempt without id in the path - (PUT /usuarios)
    Given I have a registered user
    When I send a PUT request to the users endpoint without id in the path
    Then The response status code should be 405
    And The response contract should match "schemas/Common/error_response.schema.json"
    And The response should contain the message "Não é possível realizar PUT em /usuarios. Acesse http://localhost:3000 para ver as rotas disponíveis e como utilizá-las."

  @USERS-034
  Scenario: Attempt to update a user using an already registered email - (PUT /usuarios/{_id})
    Given I have a registered user
    When I send a PUT request to the user using an already registered email
    Then The response status code should be 400
    And The response contract should match "schemas/Common/error_response.schema.json"
    And The response should contain the message "Este email já está sendo usado"

  @USERS-035
  Scenario: Attempt to update a user with empty body - (PUT /usuarios/{_id})
    Given I have a registered user
    When I send a PUT request to the user with empty body
    Then The response status code should be 400
    And The response contract should match "schemas/Common/error_response.schema.json"
    And The response should contain the error messages
      | nome é obrigatório          |
      | email é obrigatório         |
      | password é obrigatório      |
      | administrador é obrigatório |

  @USERS-036
  Scenario: Update an existing user with the same registered data - (PUT /usuarios/{_id})
    Given I have a registered user
    When I send a PUT request to the user with the same registered data
    Then The response status code should be 200
    And The response contract should match "schemas/Common/message.schema.json"
    And The response should contain the message "Registro alterado com sucesso"

  @USERS-037
  Scenario Outline: Attempt to update user with empty fields - (PUT /usuarios/{_id})
    Given I have a registered user
    When I send a PUT request to the user with empty "<field>"
    Then The response status code should be 400
    And The response contract should match "schemas/Common/error_response.schema.json"
    And The response should contain the message "<expected_message>"

    Examples:
      | field         | expected_message                               |
      | nome          | nome não pode ficar em branco                  |
      | password      | password não pode ficar em branco              |
      | email         | email não pode ficar em branco                 |
      | administrador | administrador deve ser 'true' ou 'false'       |

  @USERS-038
  Scenario: Attempt to update user with malformed JSON payload - (PUT /usuarios/{_id})
    Given I have a registered user
    And I have a malformed JSON payload
    When I send a PUT request to the user with the context payload
    Then The response status code should be 400
    And The response contract should match "schemas/Common/error_response.schema.json"
    And The response should contain the message "Adicione aspas em todos os valores. Para mais informações acesse a issue https://github.com/ServeRest/ServeRest/issues/225"
