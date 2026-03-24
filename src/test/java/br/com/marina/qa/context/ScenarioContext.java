package br.com.marina.qa.context;

import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import java.util.HashMap;
import java.util.Map;


@Setter
@Getter
public class ScenarioContext {

    private Response response;
    private Object payload;
    private String authToken;
    private String id;
    private final Map<String, Object> testData = new HashMap<>();

    public void setTestData(String key, Object value) {
        testData.put(key, value);
    }

    public void clearAll() {
        response = null;
        payload = null;
        authToken = null;
        id = null;
        testData.clear();
    }
}

