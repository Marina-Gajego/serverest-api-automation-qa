package br.com.marina.qa.factory.Users;

import br.com.marina.qa.model.Users.PostUserModel;
import com.github.javafaker.Faker;

import java.util.Locale;

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
}