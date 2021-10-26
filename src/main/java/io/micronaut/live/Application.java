package io.micronaut.live;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@OpenAPIDefinition(
        info = @Info(
                title = "Newsletter Subscriber",
                version = "0.1",
                description = "Newsletter Subscriber API",
                license = @License(name = "Apache 2.0"),
                contact = @Contact(url = "https://sergiodelamo.com", name = "Sergio del Amo")
        )
)
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}
