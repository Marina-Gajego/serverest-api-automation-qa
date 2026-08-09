package br.com.marina.qa.stepsDefinitions.Carts;

import br.com.marina.qa.context.ScenarioContext;
import br.com.marina.qa.services.Carts.GetCartsByIdService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetCartsByIdSteps {

    private final GetCartsByIdService getCartsByIdService;
    private final ScenarioContext context;

    public GetCartsByIdSteps(GetCartsByIdService getCartsByIdService, ScenarioContext context) {
        this.getCartsByIdService = getCartsByIdService;
        this.context = context;
    }

    @When("I send a GET request to the carts endpoint with the created cart id")
    public void sendGetCartRequestWithCreatedCartId() {
        Response response = getCartsByIdService.getCartById(context.getCartId());
        context.setResponse(response);
    }

    @When("I send a GET request to the carts with {string}")
    public void sendGetCartRequestWithCondition(String condition) {
        Response response = switch (condition.toLowerCase()) {
            case "nonexistent" -> getCartsByIdService.getCartById("abcdef1234567890");
            case "invalid" -> getCartsByIdService.getCartById("invalid@id#chars");
            case "excededid" -> getCartsByIdService.getCartById("123456abcde123yhgdri");
            default -> getCartsByIdService.getCartById(condition);
        };

        context.setResponse(response);
    }

    @And("The response should contain the correct cart details")
    public void theResponseShouldContainTheCorrectCartDetails() {
        Response response = context.getResponse();
        List<Map<String, Object>> products = response.jsonPath().getList("produtos");

        assertThat(response.jsonPath().getString("_id"))
                .as("Response should contain the created cart id")
                .isEqualTo(context.getCartId());
        assertThat(response.jsonPath().getString("idUsuario"))
                .as("Response should contain the user id that owns the cart")
                .isEqualTo(context.getUserId());
        assertThat(response.jsonPath().getInt("precoTotal"))
                .as("Response should contain the cart total price")
                .isEqualTo(context.getProductPreco());
        assertThat(response.jsonPath().getInt("quantidadeTotal"))
                .as("Response should contain the total product quantity")
                .isEqualTo(1);

        assertThat(products)
                .as("Response should contain the product added to the cart")
                .singleElement()
                .satisfies(product -> {
                    assertThat(product.get("idProduto")).isEqualTo(context.getProductId());
                    assertThat(product.get("quantidade")).isEqualTo(1);
                    assertThat(product.get("precoUnitario")).isEqualTo(context.getProductPreco());
                });
    }
}