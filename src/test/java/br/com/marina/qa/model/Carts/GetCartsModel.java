package br.com.marina.qa.model.Carts;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetCartsModel {
    private String id;
    private Object precoTotal;
    private Object quantidadeTotal;
    private String idUsuario;
}
