package com.crypto.app.config

import io.r2dbc.spi.ConnectionFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator

@Configuration
class DatabaseConfig {

    @Bean
    @ConditionalOnProperty(name = ["db.init"], havingValue = "true", matchIfMissing = true)
    fun initializer(connectionFactory: ConnectionFactory) = ConnectionFactoryInitializer().also {
        it.setConnectionFactory(connectionFactory)
        it.setDatabasePopulator(CompositeDatabasePopulator().also { populator ->
            populator.addPopulators(ResourceDatabasePopulator(ClassPathResource("schema.sql")))
        })
    }
}