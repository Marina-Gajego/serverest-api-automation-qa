package br.com.marina.qa.services.Login;

import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import static br.com.marina.qa.paths.Paths.AUTH_ENDPOINT;
import static br.com.marina.qa.paths.Paths.BASE_URL;
import static io.restassured.RestAssured.given;

@Slf4j
public class LoginService {

    public Response login(Object payload) {
        Response response = given()
                .baseUri(BASE_URL)
                .basePath(AUTH_ENDPOINT)
                .contentType("application/json")
                .body(payload)
                .when()
                .log().all()
                .post();

        log.info("Login request completed with status: {}", response.getStatusCode());
        return response;
    }
}
