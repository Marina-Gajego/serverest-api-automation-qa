package br.com.marina.qa.stepsDefinations.Users;

import br.com.marina.qa.context.ScenarioContext;
import br.com.marina.qa.factory.Users.PostUserFactory;
import br.com.marina.qa.services.Users.PostUserService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class PostUserSteps {

    private final PostUserService postUserService;
    private final ScenarioContext context;

    public PostUserSteps(PostUserService postUserService, ScenarioContext context) {
        this.postUserService = postUserService;
        this.context = context;
    }

    @Given("I have a valid user payload")
    public void iHaveValidUserPayload() {
        context.setPayload(PostUserFactory.validPostUser());
    }

    @When("I send a POST request to the user registration endpoint")
    public void iSendAPostRequestToTheUserRegistrationEndpoint() {
        Response response = postUserService.createUser(context.getPayload());
        context.setResponse(response);
        log.info("Request sent. Status: {}", response.getStatusCode());
    }

    @And("I register this user in the system")
    public void iRegisterThisUserInTheSystem() {
        iSendAPostRequestToTheUserRegistrationEndpoint();
    }

    @And("The response should contain the id field")
    public void AndTheResponseShouldContainTheIdField() {
        Response response = context.getResponse();
        String id = response.jsonPath().getString("_id");
        assertThat(id).as("Response should contain '_id' identification").isNotNull().isNotEmpty();
        context.setId(id);
    }
}
