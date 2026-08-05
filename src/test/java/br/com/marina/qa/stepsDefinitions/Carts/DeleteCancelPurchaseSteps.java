package br.com.marina.qa.stepsDefinitions.Carts;

import br.com.marina.qa.context.ScenarioContext;
import br.com.marina.qa.services.Carts.DeleteCancelPurchaseService;
import io.cucumber.java.en.And;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeleteCancelPurchaseSteps {

    private final ScenarioContext context;
    private final DeleteCancelPurchaseService deleteCancelPurchaseService;

    public DeleteCancelPurchaseSteps(ScenarioContext context, DeleteCancelPurchaseService deleteCancelPurchaseService) {
        this.context = context;
        this.deleteCancelPurchaseService = deleteCancelPurchaseService;
    }

    @And("I send a DELETE request to the cancel purchase endpoint")
    public void iSendADELETERequestToTheCancelPurchaseEndpoint() {
        context.setResponse(deleteCancelPurchaseService.deleteCancelPurchase(context.getAuthToken()));
        log.info("Response: {}", context.getResponse().asString());
    }

    @And("I send a DELETE request to the cancel purchase without authentication")
    public void iSendADELETERequestToTheCancelPurchaseWithoutAuthentication() {
        context.setResponse(deleteCancelPurchaseService.deleteCancelPurchaseWithoutAuthentication());
        log.info("Response: {}", context.getResponse().asString());
    }
}
