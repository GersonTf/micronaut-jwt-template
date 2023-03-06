package com.blazinc

import io.micronaut.runtime.Micronaut
import groovy.transform.CompileStatic
import io.swagger.v3.oas.annotations.*
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.*
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.security.SecurityScheme
import io.swagger.v3.oas.annotations.servers.Server

@OpenAPIDefinition(
        info = @Info(
                title = "fire-ultimate",
                version = "0.0",
                description = "API for fire ultimate, achieve financial freedom.",
                contact = @Contact(name = "Gerson", email = "gerson.tf.trujillo@gmail.com")
        )
)
@Server(url = 'http://localhost:8080')
@SecurityScheme(name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "jwt")
@SecurityRequirement(name = "bearerAuth")
@CompileStatic
class Application {
    static void main(String[] args) {
        Micronaut.run(Application, args)
    }
}
