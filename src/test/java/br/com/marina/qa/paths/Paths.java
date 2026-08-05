package br.com.marina.qa.paths;

public final class Paths {

    public static final String BASE_URL = System.getProperty("base.url", System.getenv("BASE_URL") != null ? System.getenv("BASE_URL") : "http://localhost:3000");
    public static final String LOGIN_ENDPOINT = "/login";
    public static final String USERS_ENDPOINT = "/usuarios";
    public static final String PRODUCTS_ENDPOINT = "/produtos";
    public static final String CARTS_ENDPOINT = "/carrinhos";
    public static final String COMPLETE_PURCHASE_ENDPOINT = "/concluir-compra";
    public static final String CANCEL_PURCHASE_ENDPOINT = "/cancelar-compra";
}
