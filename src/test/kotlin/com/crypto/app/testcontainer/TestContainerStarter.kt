package com.crypto.app.testcontainer

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.support.TestPropertySourceUtils
import org.testcontainers.containers.GenericContainer


abstract class TestContainerStarter<T : GenericContainer<*>?> : ApplicationContextInitializer<ConfigurableApplicationContext?> {
    private val logger: Logger = LoggerFactory.getLogger(TestContainerStarter::class.java)

    protected abstract fun containerName(): String

    protected abstract fun container(): T

    protected abstract fun propertiesToInline(container: T, applicationContext: ConfigurableApplicationContext): List<String>

    private fun inlineProperties(applicationContext: ConfigurableApplicationContext, properties: List<String>) {
        for (property in properties) {
            logger.info("Inline property <{}> for container <{}>", property, containerName())
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(applicationContext, property)
        }
    }

    protected open fun prepare(container: T, applicationContext: ConfigurableApplicationContext) {}

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        val container = container()!!
        try {
            prepare(container, applicationContext)
            container.start()
            val properties = propertiesToInline(container, applicationContext)
            inlineProperties(applicationContext, properties)
        } catch (t: Throwable) {
            logger.error("Error occurred when starting container <{}>", containerName())
            logger.error(t.message)
            logger.error(t.stackTraceToString())
            logger.error("Container <{}> log: \n {}", containerName(), container.logs)
            throw t
        }
        logger.debug("Container <{}> log: \n {}", containerName(), container.logs)
        logger.info("Embedded container <{}> started.", containerName())
    }
}