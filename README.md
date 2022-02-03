## Local Development

The application needs a PostgreSQL database. The easiest way is to run aa PostgreSQL database via Docker with: 

```bash
docker run -it --rm -p 5432:5432 -e POSTGRES_USER=dbuser -e POSTGRES_PASSWORD=theSecretPassword -e POSTGRES_DB=postgres postgres:11.5-alpine
```

Create a file under `app/src/main/resources/application-dev.yml` with following content: 

```
datasources:
  default:
    url: jdbc:postgresql://localhost:5432/postgres
    driverClassName: org.postgresql.Driver
    schema-generate: NONE
    dialect: POSTGRES
    username: dbuser
    password: theSecretPassword
```

Run the application

`./gradlew :app:run`

## Artifacts

### FAT JAR 

`./gradlew :app:build`. You can find the fat jar under `app/build/libs/app-0.1-all.jar`

### Optimized FAT JAR

`./gradlew :app:optimizedJitJarAll`. You can find the fat jar under `app/build/libs/app-0.1-all-optimized.jar`

### Native Image

`./gradlew :app:nativeCompile`. You can find the native image under `app/build/native/nativeCompile/app`

### Optimized Native Image

`./gradlew :app:nativeOptimizedCompile`. You can find the native image under `./app/build/native/nativeOptimizedCompile/app`     `

## Artifact tests

In one terminal, generate and run the native image or fat jar (see previous sections).

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
