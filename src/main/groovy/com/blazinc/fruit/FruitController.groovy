package com.blazinc.fruit

import io.micronaut.core.annotation.NonNull
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.annotation.Status
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.scheduling.annotation.ExecuteOn
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.swagger.v3.oas.annotations.Operation
import jakarta.inject.Inject

import javax.validation.Valid
import javax.validation.constraints.NotNull


@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/fruits")
@ExecuteOn(TaskExecutors.IO)
class FruitController {

    @Inject
    FruitService fruitService

    @Operation(summary = "List fruits")
    @Get
    Iterable<Fruit> list() {
        fruitService.list()
    }

    @Operation(summary = "Create new fruit")
    @Post
    @Status(HttpStatus.CREATED)
    Fruit save(@NonNull @NotNull @Valid Fruit fruit) {
        fruitService.save(fruit)
    }

    @Operation(summary = "Update fruit")
    @Put
    Fruit update(@NonNull @NotNull @Valid Fruit fruit) {
        fruitService.save(fruit)
    }

    @Operation(summary = "Find fruit by id")
    @Get("/{id}")
    Optional<Fruit> find(@PathVariable String id) {
        fruitService.find(id)
    }

    @Operation(summary = "Find fruit by name")
    @Get("/q")
    Iterable<Fruit> query(@QueryValue @NotNull List<String> names) {
        fruitService.findByNameInList(names)
    }
}
