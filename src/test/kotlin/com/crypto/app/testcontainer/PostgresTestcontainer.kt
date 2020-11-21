package com.crypto.app.testcontainer

import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

class EmbeddedPostgresConfig : TestContainerStarter<PostgreSQLContainer<*>>() {
    override fun containerName(): String {
        return "PostgreSQL"
    }

    override fun container(): PostgreSQLContainer<*> {
        return PostgreSQLContainer<Nothing>(DockerImageName.parse("postgres").withTag("10.1"))
    }

    override fun prepare(container: PostgreSQLContainer<*>, applicationContext: ConfigurableApplicationContext) {
        container
                .withDatabaseName(DB_NAME)
                .withUsername(USERNAME)
                .withPassword(PASSWORD)
    }

    override fun propertiesToInline(container: PostgreSQLContainer<*>, applicationContext: ConfigurableApplicationContext): List<String> {
        val port = container.getMappedPort(5432)
        val jdbcUrl = String.format("r2dbc:postgresql://localhost:%d/%s", port, DB_NAME)
        return listOf(
                String.format("spring.r2dbc.url=%s", jdbcUrl),
                String.format("spring.r2dbc.username=%s", USERNAME),
                String.format("spring.r2dbc.password=%s", PASSWORD)
        )
    }

    companion object {
        private const val USERNAME = "user"
        private const val PASSWORD = "password"
        private const val DB_NAME = "crypto_app_test"
    }
}