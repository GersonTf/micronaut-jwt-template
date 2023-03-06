package com.blazinc.domain


import groovy.transform.Canonical
import io.micronaut.core.annotation.NonNull
import io.micronaut.core.annotation.Nullable
import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity

import javax.validation.constraints.NotBlank

@Canonical
@MappedEntity
class ExpenseCategory {

    @Id
    @GeneratedValue
    String id

    @NonNull
    @NotBlank
    String name

    @Nullable
    BigDecimal total

    @Nullable
    BigDecimal yearTotal

    @Nullable
    BigDecimal monthTotal

    //todo I have doubts about making this recursive like this, may be better to use another field
    @Nullable
    List<ExpenseCategory> children
}
