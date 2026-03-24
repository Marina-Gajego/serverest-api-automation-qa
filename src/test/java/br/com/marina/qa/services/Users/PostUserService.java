package br.com.marina.qa.services.Users;

import groovy.util.logging.Slf4j;
import io.restassured.response.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static br.com.marina.qa.paths.Paths.BASE_URL;
import static br.com.marina.qa.paths.Paths.USERS_ENDPOINT;
import static io.restassured.RestAssured.given;

@Slf4j
public class PostUserService {

    private static final Log log = LogFactory.getLog(PostUserService.class);

    public Response createUser(Object payload) {
        Response response = given()
                .baseUri(BASE_URL)
                .basePath(USERS_ENDPOINT)
                .contentType("application/json")
                .body(payload)
                .when()
                .log().all()
                .post();
        log.info("User registration request completed with status:" + response.getStatusCode());
        return response;
    }
}