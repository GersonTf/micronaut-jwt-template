package com.blazinc.model

import groovy.transform.Canonical
import groovy.transform.CompileStatic
import io.micronaut.serde.annotation.Serdeable

@Serdeable
@Canonical
@CompileStatic
class Investment {

    String symbol // investment symbol, for example APPL

}
