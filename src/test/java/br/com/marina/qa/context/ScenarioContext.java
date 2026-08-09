package br.com.marina.qa.context;

import br.com.marina.qa.stepsDefinitions.Login.LoginSteps;
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
    private final Map<String, Object> testData = new HashMap<>();

    //Login
    private String email;
    private String password;

    //Users
    private String userId;
    private String userNome;
    private String administrador;

    //Products
    private String productId;
    private String productName;
    private Integer productPreco;
    private String productDescricao;
    private Integer productQuantidade;

    //Carts
    private String cartId;
    private Integer cartPrecoTotal;
    private Integer cartQuantidadeTotal;
    private String cartIdUsuario;

    //Classes
    private LoginSteps loginSteps;

    public void clearAll() {
        response = null;
        payload = null;
        authToken = null;
        testData.clear();
        email = null;
        password = null;
        userId = null;
        userNome = null;
        administrador = null;
        productId = null;
        productName = null;
        productPreco = null;
        productDescricao = null;
        productQuantidade = null;
        cartId = null;
        cartPrecoTotal = null;
        cartQuantidadeTotal = null;
        cartIdUsuario = null;
    }
}
