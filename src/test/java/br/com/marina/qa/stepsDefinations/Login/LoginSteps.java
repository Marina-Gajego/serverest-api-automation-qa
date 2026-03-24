package br.com.marina.qa.stepsDefinations.Login;

import br.com.marina.qa.context.ScenarioContext;
import br.com.marina.qa.factory.Login.LoginFactory;
import br.com.marina.qa.model.Login.LoginModel;
import br.com.marina.qa.model.Users.PostUserModel;
import br.com.marina.qa.services.Login.LoginService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class LoginSteps {

    private final LoginService loginService;
    private final ScenarioContext context;

    public LoginSteps(ScenarioContext context) {
        this.context = context;
        this.loginService = new LoginService();
    }

    @Given("I have a login payload with the {string} as {string}")
    public void iHaveAPayloadWithCondition(String field, String condition) {
        switch (condition.toLowerCase()) {
            case "missing":
                context.setPayload(field.equals("email") ? LoginFactory.missingEmail() : LoginFactory.missingPassword());
                break;
            case "an integer":
                context.setPayload(LoginFactory.fieldAsInteger(field));
                break;
            case "null":
                context.setPayload(LoginFactory.fieldNull(field));
                break;
            case "invalid format":
                context.setPayload(LoginFactory.invalidFormatEmail());
                break;
            default:
                throw new IllegalArgumentException("Condição não suportada: " + condition);
        }
    }

    @When("I send a POST request to the authentication endpoint")
    public void iSendAPostRequest() {
        Response response = loginService.login(context.getPayload());
        context.setResponse(response);
        log.info("Request sent. Status: {}", response.getStatusCode());
    }

    @When("I send a POST request to the authentication endpoint with the registered credentials")
    public void iSendPostWithRegisteredCredentials() {
        PostUserModel registeredUser = (PostUserModel) context.getPayload();
        LoginModel loginPayload = LoginFactory.fromUserModel(registeredUser);
        Response response = loginService.login(loginPayload);
        context.setResponse(response);
        log.info("Login performed for registered user: {}", registeredUser.getEmail());
    }

    @And("The response should contain a token")
    public void theResponseShouldContainAToken() {
        Response response = context.getResponse();
        String token = response.jsonPath().getString("authorization");
        assertThat(token).as("Response should contain 'authorization' token").isNotNull().isNotEmpty();
        context.setAuthToken(token);
    }
}