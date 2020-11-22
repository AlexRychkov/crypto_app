package com.crypto.app.server

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.time.format.DateTimeParseException

@Component
@Order(-2)
class GlobalErrorWebExceptionHandler : ErrorWebExceptionHandler {

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {
        when (ex) {
            is IllegalArgumentException ->
                exchange.response.statusCode = HttpStatus.BAD_REQUEST
            is DateTimeParseException ->
                exchange.response.statusCode = HttpStatus.BAD_REQUEST
            else ->
                exchange.response.statusCode = HttpStatus.INTERNAL_SERVER_ERROR
        }
        return exchange.response.setComplete()
    }
}
