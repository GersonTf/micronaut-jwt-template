package com.blazinc.domain

import com.blazinc.model.Expense
import com.blazinc.model.Investment
import com.blazinc.model.UserRoles
import groovy.transform.Canonical
import io.micronaut.core.annotation.NonNull
import io.micronaut.core.annotation.Nullable
import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.annotation.event.PrePersist
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import java.time.Instant

@Canonical
@MappedEntity
class User {

    @Id
    @GeneratedValue
    String id

    //todo make this unique
    @NonNull
    String username

    @NonNull
    String password

    @NonNull
    List<UserRoles> roles

    @Nullable
    Instant deactivated

    // the fireNumber represents the amount of money the user needs in order to retire
    @NonNull
    BigDecimal fireNumber

    @Nullable
    List<Investment> investments

    @Nullable
    List<Expense> expenses

    @PrePersist
    void encryptPassword() {
        this.password = new BCryptPasswordEncoder().encode(this.password)
    }
}
