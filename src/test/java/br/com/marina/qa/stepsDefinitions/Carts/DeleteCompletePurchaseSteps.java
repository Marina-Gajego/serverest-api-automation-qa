package br.com.marina.qa.stepsDefinitions.Carts;

import br.com.marina.qa.context.ScenarioContext;
import br.com.marina.qa.services.Carts.DeleteCompletePurchaseService;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeleteCompletePurchaseSteps {

    private final ScenarioContext scenarioContext;
    private final DeleteCompletePurchaseService deleteCompletePurchaseService;

    public DeleteCompletePurchaseSteps(ScenarioContext scenarioContext, DeleteCompletePurchaseService deleteCompletePurchaseService) {
        this.scenarioContext = scenarioContext;
        this.deleteCompletePurchaseService = deleteCompletePurchaseService;
    }

    @When("I send a DELETE request to the complete purchase endpoint")
    public void iSendADELETERequestToTheCompletePurchaseEndpoint() {
        scenarioContext.setResponse(deleteCompletePurchaseService.deleteCompletePurchase(scenarioContext.getAuthToken()));
        log.info("Response: {}", scenarioContext.getResponse().asString());
    }

    @When("I send a DELETE request to the complete purchase without authentication")
    public void iSendADELETERequestToTheCompletePurchaseEndpointWithoutAuthentication() {
        scenarioContext.setResponse(deleteCompletePurchaseService.deleteCompletePurchaseWithoutAuthentication());
        log.info("Response: {}", scenarioContext.getResponse().asString());
    }
}