package br.com.marina.qa.stepsDefinations.Commons;

import br.com.marina.qa.context.ScenarioContext;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Common Step Definitions
 * Contains reusable step definitions for response validations
 */
@Slf4j
public class CommonsSteps {

    private final ScenarioContext context;

    public CommonsSteps(ScenarioContext context) {
        this.context = context;
    }

    @Then("The response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        Response response = context.getResponse();
        int actualStatusCode = response.getStatusCode();
        
        assertThat(actualStatusCode)
                .as("Status code validation")
                .isEqualTo(expectedStatusCode);
        log.info("Status code validation passed: {}", expectedStatusCode);
    }

    @Then("The response should contain the message {string}")
    public void theResponseShouldContainTheMessage(String expectedMessage) {
        Response response = context.getResponse();
        String body = response.getBody().asString();
        
        assertThat(body)
                .as("Response body should contain: " + expectedMessage)
                .contains(expectedMessage);

        log.info("Message validation passed");
    }
}

