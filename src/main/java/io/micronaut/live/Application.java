package io.micronaut.live;

import io.micronaut.context.ApplicationContextBuilder;
import io.micronaut.context.ApplicationContextConfigurer;
import io.micronaut.context.annotation.ContextConfigurer;
import io.micronaut.context.env.Environment;
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

    static final String DEFAULT_ENV = Environment.DEVELOPMENT;

    @ContextConfigurer
    public static class DefaultEnvironmentConfigurer implements ApplicationContextConfigurer {
        @Override
        public void configure(ApplicationContextBuilder context) {
            context.defaultEnvironments(DEFAULT_ENV);
        }
    }


    public static void main(String[] args) {
        Micronaut.build(args)
                .mainClass(Application.class)
                .defaultEnvironments(DEFAULT_ENV)
                .start();
    }
}
