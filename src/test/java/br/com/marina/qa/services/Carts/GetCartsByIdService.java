package br.com.marina.qa.services.Carts;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import static br.com.marina.qa.paths.Paths.BASE_URL;
import static br.com.marina.qa.paths.Paths.CARTS_ENDPOINT;
import static io.restassured.RestAssured.given;

@Slf4j
public class GetCartsByIdService {

    public Response getCartById(String id) {
        Response response = given()
                .baseUri(BASE_URL)
                .basePath(CARTS_ENDPOINT + "/{id}")
                .contentType("application/json")
                .pathParam("id", id)
                .when()
                .log().all()
                .get();

        log.info("Get cart by ID. Status code: {}", response.getStatusCode());
        return response;
    }
}
