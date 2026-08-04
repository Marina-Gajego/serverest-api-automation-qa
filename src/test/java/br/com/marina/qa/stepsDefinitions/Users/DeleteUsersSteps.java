package br.com.marina.qa.stepsDefinitions.Users;

import br.com.marina.qa.context.ScenarioContext;
import br.com.marina.qa.services.Users.DeleteUsersService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class DeleteUsersSteps {

    private final DeleteUsersService deleteUsersService;
    private final ScenarioContext context;

    public DeleteUsersSteps(DeleteUsersService deleteUsersService, ScenarioContext context) {
        this.deleteUsersService = deleteUsersService;
        this.context = context;
    }

    @When("I send a DELETE request to the users endpoint with the created user id")
    public void sendDeleteUserRequestWithCreatedUserId(){
        String id = context.getUserId();
        Response response = deleteUsersService.deleteUserById(id);
        context.setResponse(response);
        System.out.println(response.asString());
    }

    @When("I send a DELETE request to the users endpoint without id")
    public void sendDeleteUserRequestWithoutId(){
        Response response = deleteUsersService.deleteUsersWithoutId();
        context.setResponse(response);
    }

    @When("I send a DELETE request to the users with {string}")
    public void sendDeleteUserRequestWithSpecificId(String condition){
        Response response = null;

        if (condition.equalsIgnoreCase("nonexistent")){
            response = deleteUsersService.deleteUserById("abcdef1234567890");
        } else if (condition.equalsIgnoreCase("invalid")){
            response = deleteUsersService.deleteUserById("invalid@id#chars");
        } else {
            response = deleteUsersService.deleteUserById(condition);
        }

        context.setResponse(response);
    }

    @And("The response should contain the cart id")
    public void theResponseShouldContainTheCartId() {
        String responseBody = context.getResponse().asString();
        String cartId = context.getCartId();
        assertThat(responseBody).contains(cartId);
    }
}
