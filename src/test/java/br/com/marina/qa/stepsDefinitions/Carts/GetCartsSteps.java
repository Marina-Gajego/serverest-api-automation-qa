package br.com.marina.qa.stepsDefinitions.Carts;

import br.com.marina.qa.context.ScenarioContext;
import br.com.marina.qa.factory.Carts.GetCartsFactory;
import br.com.marina.qa.model.Carts.GetCartsModel;
import br.com.marina.qa.services.Carts.GetCartsService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class GetCartsSteps {

    private final GetCartsService getCartsService;
    private final ScenarioContext context;

    public GetCartsSteps(GetCartsService getCartsService, ScenarioContext context) {
        this.getCartsService = getCartsService;
        this.context = context;
    }

    @When("I send a GET request to the carts endpoint with the {string} query parameter")
    public void sendGetCartsRequestWithQueryParameter(String field) {
        Object value = getCreatedCartFieldValue(field);
        GetCartsModel parameters = GetCartsFactory.getCartBy(field, value);
        sendGetCartsRequest(parameters);
    }

    @When("I send a GET request to the carts endpoint with the {string} query parameter and value {string}")
    public void sendGetCartsRequestWithQueryParameterAndValue(String field, String value) {
        Object queryParamValue = resolveQueryParamValue(field, value);
        GetCartsModel parameters = GetCartsFactory.getCartBy(field, queryParamValue);
        sendGetCartsRequest(parameters);
    }

    @When("I send a GET request to the carts endpoint")
    public void sendGetCartsRequestWithAllQueryParameters() {
        GetCartsModel parameters = GetCartsFactory.getCartAllParams(
                context.getCartId(),
                context.getCartPrecoTotal(),
                context.getCartQuantidadeTotal(),
                context.getCartIdUsuario()
        );
        sendGetCartsRequest(parameters);
    }

    @When("I send a GET request to the carts endpoint without query parameters")
    public void sendGetCartsRequestWithoutQueryParameters() {
        Response response = getCartsService.getCartsWithoutQueryParams();
        context.setResponse(response);
    }

    @And("The response should contain the correct cart")
    public void theResponseShouldContainTheCorrectCart() {
        Response response = context.getResponse();
        List<Map<String, Object>> carts = response.jsonPath().getList("carrinhos");

        assertThat(carts)
                .as("Response should include the cart created for this scenario")
                .anySatisfy(cart -> {
                    assertThat(cart.get("_id")).isEqualTo(context.getCartId());
                    assertThat(cart.get("precoTotal")).isEqualTo(context.getCartPrecoTotal());
                    assertThat(cart.get("quantidadeTotal")).isEqualTo(context.getCartQuantidadeTotal());
                    assertThat(cart.get("idUsuario")).isEqualTo(context.getCartIdUsuario());
                });
    }

    @And("The response should not contain any carts")
    public void theResponseShouldNotContainAnyCarts() {
        Response response = context.getResponse();
        assertThat(response.jsonPath().getInt("quantidade")).isZero();
        assertThat(response.jsonPath().getList("carrinhos")).isEmpty();
    }

    @And("The response should not contain the created cart")
    public void theResponseShouldNotContainTheCreatedCart() {
        Response response = context.getResponse();
        assertThat(response.asString())
                .doesNotContain(context.getCartId())
                .doesNotContain(context.getCartIdUsuario());
    }

    private Object getCreatedCartFieldValue(String field) {
        return switch (field.toLowerCase()) {
            case "_id" -> context.getCartId();
            case "precototal" -> context.getCartPrecoTotal();
            case "quantidadetotal" -> context.getCartQuantidadeTotal();
            case "idusuario" -> context.getCartIdUsuario();
            default -> throw new IllegalArgumentException("Field not supported: " + field);
        };
    }

    private Object resolveQueryParamValue(String field, String value) {
        String normalizedValue = value.toLowerCase();

        return switch (normalizedValue) {
            case "createdvalueplusone" -> ((Integer) getCreatedCartFieldValue(field)) + 1;
            default -> value;
        };
    }

    private void sendGetCartsRequest(GetCartsModel parameters) {
        Response response = getCartsService.getCarts(parameters);
        context.setResponse(response);
    }
}
