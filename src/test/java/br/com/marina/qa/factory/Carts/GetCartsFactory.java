package br.com.marina.qa.factory.Carts;

import br.com.marina.qa.model.Carts.GetCartsModel;

public final class GetCartsFactory {

    private GetCartsFactory() {
    }

    public static GetCartsModel getCartBy(String field, Object value) {
        return switch (field.toLowerCase()) {
            case "_id" -> GetCartsModel.builder().id((String) value).build();
            case "precototal" -> GetCartsModel.builder().precoTotal(value).build();
            case "quantidadetotal" -> GetCartsModel.builder().quantidadeTotal(value).build();
            case "idusuario" -> GetCartsModel.builder().idUsuario((String) value).build();
            default -> throw new IllegalArgumentException("Field not supported: " + field);
        };
    }

    public static GetCartsModel getCartAllParams(String id, Object precoTotal, Object quantidadeTotal, String idUsuario) {
        return GetCartsModel.builder()
                .id(id)
                .precoTotal(precoTotal)
                .quantidadeTotal(quantidadeTotal)
                .idUsuario(idUsuario)
                .build();
    }
}
