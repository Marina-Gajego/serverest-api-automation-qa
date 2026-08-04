package br.com.marina.qa.services.Carts;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import static br.com.marina.qa.paths.Paths.*;
import static io.restassured.RestAssured.given;

@Slf4j
public class DeleteCompletePurchaseService {

    public Response deleteCompletePurchase(String token) {
        Response response = given()
                .baseUri(BASE_URL)
                .basePath(CARTS_ENDPOINT + COMPLETE_PURCHASE_ENDPOINT)
                .header("Authorization", token)
                .when()
                .log().all()
                .delete();

        log.info("Delete complete purchase. Status code: {}", response.getStatusCode());
        return response;
    }

    public Response deleteCompletePurchaseWithoutAuthentication() {
        Response response = given()
                .baseUri(BASE_URL)
                .basePath(CARTS_ENDPOINT + COMPLETE_PURCHASE_ENDPOINT)
                .when()
                .log().all()
                .delete();

        log.info("Delete complete purchase without authentication. Status code: {}", response.getStatusCode());
        return response;
    }
}