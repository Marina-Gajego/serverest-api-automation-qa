package br.com.marina.qa.services.Carts;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import static br.com.marina.qa.paths.Paths.*;
import static io.restassured.RestAssured.given;

@Slf4j
public class DeleteCancelPurchaseService {

    public Response deleteCancelPurchase(String token){
        Response response = given()
                .baseUri(BASE_URL)
                .basePath(CARTS_ENDPOINT + COMPLETE_PURCHASE_ENDPOINT)
                .header("Authorization", token)
                .when()
                .log().all()
                .delete();

        log.info("Delete cancel purchase. Status code: {}", response.getStatusCode());
        return response;
    }

    public Response deleteCancelPurchaseWithoutAuthentication(){
        Response response = given()
                .baseUri(BASE_URL)
                .basePath(CARTS_ENDPOINT + COMPLETE_PURCHASE_ENDPOINT)
                .when()
                .log().all()
                .delete();

        log.info("Delete cancel purchase without authentication. Status code: {}", response.getStatusCode());
        return response;
    }
}