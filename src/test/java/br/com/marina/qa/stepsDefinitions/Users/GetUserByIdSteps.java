package br.com.marina.qa.stepsDefinitions.Users;

import br.com.marina.qa.context.ScenarioContext;
import br.com.marina.qa.model.Users.PostUserModel;
import br.com.marina.qa.services.Users.GetUserByIdService;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GetUserByIdSteps {

    private static final Log log = LogFactory.getLog(GetUserByIdSteps.class);
    private final GetUserByIdService getUserByIdService;
    private final ScenarioContext context;

    public GetUserByIdSteps(GetUserByIdService getUserByIdService, ScenarioContext context) {
        this.getUserByIdService = getUserByIdService;
        this.context = context;
    }

    @When("I request the user details by ID")
    public void iRequestTheUserDetailsByID() {
        Response response = getUserByIdService.getUserById(context.getId());
        context.setResponse(response);
    }

    @And("The returned information should match the registration data")
    public void theReturnedInformationShouldMatchTheRegistrationData() {
        Response response = context.getResponse();
        PostUserModel payloadEnviado = (PostUserModel) context.getPayload();

        assertThat(response.jsonPath().getString("nome"))
                .isEqualTo(payloadEnviado.getNome());

        assertThat(response.jsonPath().getString("email"))
                .isEqualTo(payloadEnviado.getEmail());

        assertThat(response.jsonPath().getString("password"))
            .isEqualTo(payloadEnviado.getPassword());

        assertThat(response.jsonPath().getString("administrador"))
                .isEqualTo(payloadEnviado.getAdministrador());

        assertThat(context.getId())
                .isEqualTo(response.jsonPath().getString("_id"));
    }

    @Given("I request a user with {string} ID {string}")
    public void iRequestAUserWithID(String condition, String value) {
        context.setId(value);
        log.info("I request a user with " + condition + " ID " + value);
    }
}