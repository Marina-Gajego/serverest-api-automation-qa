@All @CreateUser @Users
Feature: Validate create user api in diferents scenarios

  Scenario: Create a user with valid data - (POST /usuarios)
    Given I have a valid user payload
    When I send a POST request to the user registration endpoint
    Then The response status code should be 201
    And The response should contain the message "Cadastro realizado com sucesso"
    And The response should contain the id field