package br.com.marina.qa.services.Users;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import static br.com.marina.qa.paths.Paths.BASE_URL;
import static br.com.marina.qa.paths.Paths.USERS_ENDPOINT;
import static io.restassured.RestAssured.given;

@Slf4j
public class GetUserByIdService {

    public Response getUserById(String id) {
        Response response = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .pathParam("_id", id)
                .when()
                .get(USERS_ENDPOINT + "/{_id}")
                .then()
                .log().all() // Log da Resposta (o que a API devolve)
                .extract().response();

        log.info("User registration request completed with status:" + response.getStatusCode());
        return response;
    }
}
