package com.blazinc.controller

import groovy.transform.CompileStatic
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Produces
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.swagger.v3.oas.annotations.Operation

@Secured(SecurityRule.IS_ANONYMOUS)
@CompileStatic
@Controller()
class HealthController {

    //todo change for a real health check
    @Operation(summary = "Health check")
    @Get("/health")
    @Produces(MediaType.TEXT_PLAIN)
    String index() {
        'I am alive!!!!!!!'
    }
}