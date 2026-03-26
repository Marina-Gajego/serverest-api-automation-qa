package br.com.marina.qa.stepsDefinitions.Users;

import br.com.marina.qa.context.ScenarioContext;
import br.com.marina.qa.factory.Users.PostUserFactory;
import br.com.marina.qa.model.Users.PostUserModel;
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

    @Given("I have a registered user")
    public void iHaveRegisteredUser() {
        iHaveValidUserPayload();
        iSendAPostRequestToTheUserRegistrationEndpoint();
        AndTheResponseShouldContainTheIdField();
    }

    @Given("I have a user registration payload with the {string} as {string}")
    public void iHaveUserRegistrationPayloadWithTheAs(String field, String condition) {
        switch (condition.toLowerCase()) {
            case "missing":
                PostUserModel payload = PostUserFactory.postUserWithCustomField(builder -> {
                    switch (field.toLowerCase()) {
                        case "nome":
                            builder.nome(null);
                            break;
                        case "email":
                            builder.email(null);
                            break;
                        case "password":
                            builder.password(null);
                            break;
                        case "administrador":
                            builder.administrador(null);
                            break;
                        default:
                            throw new IllegalArgumentException("Campo não suportado no payload: " + field);
                    }
                });
                context.setPayload(payload);
                break;
            case "already used":
                context.setPayload(PostUserFactory.postUserWithCustomField(builder -> builder.email(context.getEmail())));
                break;
            case "an integer":
                context.setPayload(PostUserFactory.fieldAsInteger(field));
                break;
            case "null":
                context.setPayload(PostUserFactory.fieldNull(field));
                break;
            case "invalid format":
                context.setPayload(PostUserFactory.invalidFormatEmail());
                break;
            case "invalid value":
                context.setPayload(PostUserFactory.invalidValue());
                break;
            default:
                throw new IllegalArgumentException("Condição não suportada: " + condition);
        }
    }
    
    @When("I send a POST request to the user registration endpoint")
    public void iSendAPostRequestToTheUserRegistrationEndpoint() {
        Response response = postUserService.createUser(context.getPayload());
        context.setResponse(response);
        if (context.getPayload() instanceof PostUserModel user) context.setEmail(user.getEmail());
        log.info("Request sent. Status: {}", response.getStatusCode());
    }

    @And("The response should contain the id field")
    public void AndTheResponseShouldContainTheIdField() {
        Response response = context.getResponse();
        String id = response.jsonPath().getString("_id");
        assertThat(id).as("Response should contain '_id' identification").isNotNull().isNotEmpty();
        context.setId(id);
    }
}