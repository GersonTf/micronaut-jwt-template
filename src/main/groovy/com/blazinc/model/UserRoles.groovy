package com.blazinc.model

import io.micronaut.serde.annotation.Serdeable

@Serdeable
enum UserRoles {
    ADMIN,
    USER
}