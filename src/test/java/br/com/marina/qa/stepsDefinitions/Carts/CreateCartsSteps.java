package br.com.marina.qa.stepsDefinitions.Carts;

import br.com.marina.qa.context.ScenarioContext;
import br.com.marina.qa.factory.Carts.CreateCartsFactory;
import br.com.marina.qa.services.Carts.CreateCartsService;
import br.com.marina.qa.stepsDefinitions.Produts.CreateProductsSteps;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class CreateCartsSteps {

    private final CreateCartsService createCartsService;
    private final ScenarioContext scenarioContext;
    private final CreateProductsSteps createProductsSteps;

    public CreateCartsSteps(CreateCartsService createCartsService, ScenarioContext scenarioContext, CreateProductsSteps createProductsSteps) {
        this.createCartsService = createCartsService;
        this.scenarioContext = scenarioContext;
        this.createProductsSteps = createProductsSteps;
    }

    @Given("I have a valid cart payload")
    public void iHaveAValidCartPayload(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        int quantidade = Integer.parseInt(data.get("quantidade"));
        scenarioContext.setPayload(CreateCartsFactory.validCreateCarts(scenarioContext.getProductId(), quantidade));
    }

    @Given("I have a cart payload with {string}")
    public void iHaveACartPayloadWith(String condition) {
        switch (condition.toLowerCase()) {
            case "duplicated product" ->
                    scenarioContext.setPayload(CreateCartsFactory.duplicatedProduct(scenarioContext.getProductId(), 1));
            case "product not found" ->
                    scenarioContext.setPayload(CreateCartsFactory.productNotFound(1));
            case "missing produtos" ->
                    scenarioContext.setPayload(CreateCartsFactory.withoutProducts());
            case "empty body" ->
                    scenarioContext.setPayload(CreateCartsFactory.emptyBody());
            case "invalid token" -> {
                scenarioContext.setPayload(CreateCartsFactory.validCreateCarts(scenarioContext.getProductId(), 1));
                scenarioContext.setAuthToken("invalid-token-12345");
            }
            case "without authentication token" -> {
                scenarioContext.setPayload(CreateCartsFactory.validCreateCarts(scenarioContext.getProductId(), 1));
                scenarioContext.setAuthToken("");
            }
            default -> throw new IllegalArgumentException("Condition not recognized: " + condition);
        }
    }

    @Given("I have a cart payload with the {string} as {string}")
    public void iHaveACartPayloadWithFieldAs(String field, String condition) {
        int quantidade = "greater than stock".equalsIgnoreCase(condition)
                ? scenarioContext.getProductQuantidade()
                : 1;
        scenarioContext.setPayload(CreateCartsFactory.cartWithProductFieldCondition(
                field,
                condition,
                scenarioContext.getProductId(),
                quantidade
        ));
    }

    @When("I send a POST request to create a cart")
    public void iSendAPOSTRequestToCreateACart() {
        Response response = createCartsService.createCart(scenarioContext.getPayload(), scenarioContext.getAuthToken());
        scenarioContext.setResponse(response);
        scenarioContext.setCartId(response.jsonPath().getString("_id"));
        log.info("Create cart response: {}", response.asString());
    }

    @And("I have a registered cart")
    public void iHaveARegisteredCart() {
        if (scenarioContext.getProductId() == null || scenarioContext.getAuthToken() == null) {
            createProductsSteps.iHaveARegisteredProduct();
        }

        scenarioContext.setPayload(CreateCartsFactory.validCreateCarts(scenarioContext.getProductId(), 1));
        Response response = createCartsService.createCart(scenarioContext.getPayload(), scenarioContext.getAuthToken());
        scenarioContext.setResponse(response);

        assertThat(response.getStatusCode())
                .as("Cart should be created before running this scenario")
                .isEqualTo(201);

        scenarioContext.setCartId(response.jsonPath().getString("_id"));
        log.info("Registered cart created with id: {}", scenarioContext.getCartId());
    }

    @And("The response should contain a cart id")
    public void theResponseShouldContainACartId() {
        Response response = scenarioContext.getResponse();
        assertThat(response.jsonPath().getString("_id"))
                .as("Response should contain a cart id")
                .isNotNull()
                .as("Response cart id should match the stored cart id")
                .isEqualTo(scenarioContext.getCartId());
    }
}
