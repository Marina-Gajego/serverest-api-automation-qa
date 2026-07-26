package br.com.marina.qa.services;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import static br.com.marina.qa.paths.Paths.BASE_URL;
import static br.com.marina.qa.paths.Paths.CARDS_ENDPOINT;
import static io.restassured.RestAssured.given;

@Slf4j
public class CreateCartsService {

    public Response createCart(Object cartPayload, String token) {
        Response response = given()
                .baseUri(BASE_URL)
                .basePath(CARDS_ENDPOINT)
                .header("Authorization", token)
                .contentType("application/json")
                .body(cartPayload)
                .when()
                .log().all()
                .post();

        log.info("Create cart. Status code: {}", response.getStatusCode());
        return response;
    }
}
