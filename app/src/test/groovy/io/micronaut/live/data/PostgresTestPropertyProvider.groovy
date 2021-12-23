package io.micronaut.live.data

import io.micronaut.data.model.query.builder.sql.Dialect
import org.testcontainers.containers.JdbcDatabaseContainer

trait PostgresTestPropertyProvider implements SharedDatabaseContainerTestPropertyProvider {

    @Override
    Dialect dialect() {
        Dialect.POSTGRES
    }

    @Override
    int sharedSpecsCount() {
        return 5
    }

    @Override
    void startContainer(JdbcDatabaseContainer container) {
        container.start()
    }
}

