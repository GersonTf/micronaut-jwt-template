package com.blazinc.model

import groovy.transform.Canonical
import groovy.transform.CompileStatic
import io.micronaut.serde.annotation.Serdeable

import java.time.Instant

@Serdeable
@Canonical
@CompileStatic
class Expense {

    String name

    BigDecimal amount

    String category

    Instant date
}
