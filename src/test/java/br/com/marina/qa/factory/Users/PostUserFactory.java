package br.com.marina.qa.factory.Users;

import br.com.marina.qa.model.Login.LoginModel;
import br.com.marina.qa.model.Users.PostUserModel;
import com.github.javafaker.Faker;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;

public class PostUserFactory {

    private static final Faker FAKER = new Faker(new Locale("pt-BR"));

    public static PostUserModel validPostUser(){
        return PostUserModel.builder()
                .nome(FAKER.name().fullName())
                .email(FAKER.internet().emailAddress())
                .password(FAKER.internet().password(8, 16, true, true))
                .administrador("true")
                .build();
    }

    public static PostUserModel invalidValue() {
        return PostUserModel.builder()
                .nome(FAKER.name().fullName())
                .email(FAKER.internet().emailAddress())
                .password(FAKER.internet().password(8, 16, true, true))
                .administrador("teste")
                .build();
    }

    public static PostUserModel invalidFormatEmail() {
        return PostUserModel.builder()
                .nome(FAKER.name().fullName())
                .email(FAKER.name().firstName() + "atcom")
                .password(FAKER.internet().password(8, 16, true, true))
                .administrador("true")
                .build();
    }

    public static Map<String, Object> fieldAsInteger(String field) {
        Map<String, Object> map = new HashMap<>();
        map.put("nome", FAKER.name().fullName());
        map.put("email", FAKER.internet().emailAddress());
        map.put("password", FAKER.internet().password(8, 16, true, true));
        map.put("administrador", "true");
        map.put(field, 1234);
        return map;
    }

    public static Map<String, Object> fieldNull(String field) {
        Map<String, Object> map = new HashMap<>();
        map.put("nome", FAKER.name().fullName());
        map.put("email", FAKER.internet().emailAddress());
        map.put("password", FAKER.internet().password(8, 16, true, true));
        map.put("administrador", "true");
        map.put(field, null);
        return map;
    }

    public static PostUserModel postUserWithCustomField(Consumer<PostUserModel.PostUserModelBuilder> customizer) {
        PostUserModel.PostUserModelBuilder builder = PostUserModel.builder()
                .nome(FAKER.name().fullName())
                .email(FAKER.internet().emailAddress())
                .password(FAKER.internet().password(8, 16, true, true))
                .administrador("true");

        customizer.accept(builder);
        return builder.build();
    }
}