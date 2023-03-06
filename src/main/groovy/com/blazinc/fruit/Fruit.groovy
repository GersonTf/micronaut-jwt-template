package com.blazinc.fruit

import io.micronaut.core.annotation.NonNull
import io.micronaut.core.annotation.Nullable
import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity

import javax.validation.constraints.NotBlank

@MappedEntity
class Fruit {

    @Id
    @GeneratedValue
    String id

    @NonNull
    @NotBlank
    final String name

    @Nullable
    String description

    Fruit(@NonNull String name, @Nullable String description) {
        this.name = name
        this.description = description
    }
}