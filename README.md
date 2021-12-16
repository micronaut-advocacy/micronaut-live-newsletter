## Local Development

The application needs a PostgreSQL database. The easiest way is to run aa PostgreSQL database via Docker with: 

```bash
$ docker run -it --rm -p 5432:5432 -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=theSecretPassword -e POSTGRES_DB=postgres postgres:11.5-alpine
```

and export as environment variable the credentails to access the database:

````bash
$ export DATASOURCES_DEFAULT_USERNAME=dbuser
$ export DATASOURCES_DEFAULT_PASSWORD=theSecretPassword
````

- [Testcontainers JDBC Support](https://www.testcontainers.org/modules/databases/jdbc/)

## Native Image tests

In one terminal, generate and run the native image:

```
./gradlew :app:nativeCompile
app/build/native/nativeCompile/app
```

In another terminal, export the environment variable `APP_URL` to the native image url and run the `test-native` tests:


```
export APP_URL=http://localhost:8080
./gradlew test-native:test
```


## Micronaut 3.1.1 Documentation

- [User Guide](https://docs.micronaut.io/3.1.1/guide/index.html)
- [API Reference](https://docs.micronaut.io/3.1.1/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/3.1.1/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

## Feature http-client documentation

- [Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#httpClient)
- [Micronaut Management documentation](https://docs.micronaut.io/latest/guide/index.html#management)
- [Micronaut Problem JSON documentation](https://micronaut-projects.github.io/micronaut-problem-json/latest/guide/index.html)
- [Micronaut OpenAPI Support documentation](https://micronaut-projects.github.io/micronaut-openapi/latest/guide/index.html)
- [https://www.openapis.org](https://www.openapis.org)
- [Micronaut Hikari JDBC Connection Pool documentation](https://micronaut-projects.github.io/micronaut-sql/latest/guide/index.html#jdbc)
- [Micronaut Data JDBC documentation](https://micronaut-projects.github.io/micronaut-data/latest/guide/index.html#jdbc)
- [KS-UID Java library](https://github.com/akhawaja/ksuid)
- [Micronaut Thymeleaf Views documentation](https://micronaut-projects.github.io/micronaut-views/latest/guide/index.html#thymeleaf)
- [https://www.thymeleaf.org/](https://www.thymeleaf.org/)
