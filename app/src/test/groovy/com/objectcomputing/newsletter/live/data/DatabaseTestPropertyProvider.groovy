package com.objectcomputing.newsletter.live.data

import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.runtime.config.SchemaGenerate
import io.micronaut.test.support.TestPropertyProvider
import org.testcontainers.containers.JdbcDatabaseContainer
import org.testcontainers.containers.PostgreSQLContainer

trait DatabaseTestPropertyProvider implements TestPropertyProvider {

    abstract Dialect dialect()

    SchemaGenerate schemaGenerate() {
        return SchemaGenerate.CREATE
    }

    String driverName() {
        switch (dialect()) {
            case Dialect.POSTGRES:
                return "postgresql"
            case Dialect.H2:
                return "h2"
            case Dialect.SQL_SERVER:
                return "sqlserver"
            case Dialect.ORACLE:
                return "oracle"
            case Dialect.MYSQL:
//                return "mariadb"
                return "mysql"
        }
    }

    String jdbcUrl(JdbcDatabaseContainer container) {
        container.getJdbcUrl()
    }

    JdbcDatabaseContainer getDatabaseContainer(String driverName) {
        switch (driverName) {
            case "postgresql":
            default:
                return new PostgreSQLContainer<>("postgres:10")
        }
    }

    Map<String, String> getProperties() {
        Dialect dialect = dialect()
        def driverName = driverName()
        JdbcDatabaseContainer container = getDatabaseContainer(driverName)
        if (container != null && !container.isRunning()) {
            startContainer(container)
        }
        return [
                "datasources.default.url"            : jdbcUrl(container),
                "datasources.default.username"       : container == null ? "" : container.getUsername(),
                "datasources.default.password"       : container == null ? "" : container.getPassword(),
                "datasources.default.schema-generate": schemaGenerate(),
                "datasources.default.dialect"        : dialect
        ] as Map<String, String>
    }

    void startContainer(JdbcDatabaseContainer container) {
        container.start()
    }
}