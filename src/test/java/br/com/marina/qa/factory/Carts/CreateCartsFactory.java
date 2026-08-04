package br.com.marina.qa.factory.Carts;

import br.com.marina.qa.model.Carts.CreateCartsModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CreateCartsFactory {

    private CreateCartsFactory() {
    }

    public static CreateCartsModel validCreateCarts(String productId, int quantidade) {
        return CreateCartsModel.builder()
                .produtos(Collections.singletonList(
                        new CreateCartsModel.Produtos(productId, quantidade)
                ))
                .build();
    }

    public static CreateCartsModel duplicatedProduct(String productId, int quantidade) {
        return CreateCartsModel.builder()
                .produtos(List.of(
                        new CreateCartsModel.Produtos(productId, quantidade),
                        new CreateCartsModel.Produtos(productId, quantidade)
                ))
                .build();
    }

    public static CreateCartsModel productNotFound(int quantidade) {
        return validCreateCarts("ABC123DEF456GHI7", quantidade);
    }

    public static Map<String, Object> withoutProducts() {
        return new HashMap<>();
    }

    public static Map<String, Object> emptyBody() {
        return new HashMap<>();
    }

    public static Map<String, Object> cartWithProductFieldCondition(String field, String condition, String productId, int quantidade) {
        Map<String, Object> product = productMap(productId, quantidade);

        switch (condition.toLowerCase()) {
            case "missing" -> product.remove(field);
            case "null" -> product.put(field, null);
            case "empty" -> product.put(field, "");
            case "string" -> product.put(field, "invalid-value");
            case "negative" -> product.put(field, -1);
            case "greater than stock" -> product.put(field, quantidade + 1);
            default -> throw new IllegalArgumentException("Condition not recognized: " + condition);
        }

        return cartMapWithProducts(Collections.singletonList(product));
    }

    private static Map<String, Object> cartMapWithProducts(List<Map<String, Object>> products) {
        Map<String, Object> cart = new HashMap<>();
        cart.put("produtos", new ArrayList<>(products));
        return cart;
    }

    private static Map<String, Object> productMap(String productId, int quantidade) {
        Map<String, Object> product = new HashMap<>();
        product.put("idProduto", productId);
        product.put("quantidade", quantidade);
        return product;
    }
}
