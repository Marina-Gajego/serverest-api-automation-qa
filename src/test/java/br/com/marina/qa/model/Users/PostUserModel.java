package br.com.marina.qa.model.Users;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostUserModel {
    private String nome;
    private String email;
    private String password;
    private String administrador;
}