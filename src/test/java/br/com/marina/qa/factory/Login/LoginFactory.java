package br.com.marina.qa.factory.Login;

import br.com.marina.qa.model.Login.LoginModel;
import br.com.marina.qa.model.Users.PostUserModel;
import com.github.javafaker.Faker;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LoginFactory {

    private static final Faker FAKER = new Faker(new Locale("pt-BR"));

    public static LoginModel fromUserModel(PostUserModel registeredUser) {
        return LoginModel.builder()
                .email(registeredUser.getEmail())
                .password(registeredUser.getPassword())
                .build();
    }

    public static LoginModel missingEmail() {
        return LoginModel.builder()
                .email(null)
                .password(FAKER.internet().password(8, 16, true, true))
                .build();
    }

    public static LoginModel missingPassword() {
        return LoginModel.builder()
                .email(FAKER.internet().emailAddress())
                .password(null)
                .build();
    }

    public static LoginModel invalidFormatEmail() {
        return LoginModel.builder()
                .email(FAKER.name().firstName().toLowerCase() + "atcom")
                .password(FAKER.internet().password(8, 16, true, true))
                .build();
    }

    public static Map<String, Object> fieldNull(String field) {
        Map<String, Object> map = new HashMap<>();
        map.put("email", FAKER.internet().emailAddress());
        map.put("password", FAKER.internet().password(8, 16, true, true));
        map.put(field, null);
        return map;
    }

    public static Map<String, Object> fieldAsInteger(String field) {
        Map<String, Object> map = new HashMap<>();
        map.put("email", FAKER.internet().emailAddress());
        map.put("password", FAKER.internet().password(8, 16, true, true));
        map.put(field, 1234);
        return map;
    }
}

