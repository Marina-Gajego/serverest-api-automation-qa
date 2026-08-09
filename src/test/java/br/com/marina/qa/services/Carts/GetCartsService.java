package br.com.marina.qa.services.Carts;

import br.com.marina.qa.model.Carts.GetCartsModel;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static br.com.marina.qa.paths.Paths.BASE_URL;
import static br.com.marina.qa.paths.Paths.CARTS_ENDPOINT;
import static io.restassured.RestAssured.given;

@Slf4j
public class GetCartsService {

    private static final long WAIT_BEFORE_GET_IN_MILLISECONDS = 500;

    public Response getCarts(GetCartsModel queryParams) {
        return getCarts(toQueryParams(queryParams));
    }

    public Response getCartsWithoutQueryParams() {
        waitBeforeGet();

        Response response = given()
                .baseUri(BASE_URL)
                .basePath(CARTS_ENDPOINT)
                .contentType("application/json")
                .when()
                .log().all()
                .get();

        log.info("Get carts. Status code: {}", response.getStatusCode());
        return response;
    }

    public Response getCarts(Map<String, Object> queryParams) {
        waitBeforeGet();

        Response response = given()
                .baseUri(BASE_URL)
                .basePath(CARTS_ENDPOINT)
                .contentType("application/json")
                .queryParams(queryParams)
                .when()
                .log().all()
                .get();

        log.info("Get carts. Status code: {}", response.getStatusCode());
        return response;
    }

    private void waitBeforeGet() {
        try {
            Thread.sleep(WAIT_BEFORE_GET_IN_MILLISECONDS);
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while waiting before GET carts request", exception);
        }
    }

    private Map<String, Object> toQueryParams(GetCartsModel queryParams) {
        Map<String, Object> params = new HashMap<>();

        addIfPresent(params, "_id", queryParams.getId());
        addIfPresent(params, "precoTotal", queryParams.getPrecoTotal());
        addIfPresent(params, "quantidadeTotal", queryParams.getQuantidadeTotal());
        addIfPresent(params, "idUsuario", queryParams.getIdUsuario());

        return params;
    }

    private void addIfPresent(Map<String, Object> params, String key, Object value) {
        if (value != null) {
            params.put(key, value);
        }
    }
}
