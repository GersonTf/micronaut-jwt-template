package com.blazinc.support

import com.blazinc.domain.User
import groovy.transform.CompileStatic
import io.micronaut.security.token.jwt.generator.JwtTokenGenerator
import jakarta.inject.Inject
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

import java.time.Instant

import static com.blazinc.model.UserRoles.ADMIN
import static com.blazinc.model.UserRoles.USER

@CompileStatic
trait TestSupport {

    @Inject
    JwtTokenGenerator jwtTokenGenerator

    String rawPassword = UUID.randomUUID().toString()

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder()

    String appName = "fire-ultimate"

    //a method that creates a user to use in the tests setup, with a random username, password, fireNumber and roles
    User createUser(String rawPassword) {
        new User(
                username: UUID.randomUUID().toString(),
                password: rawPassword,
                fireNumber: BigDecimal.valueOf(1000),
                roles: [ADMIN, USER],
        )
    }

    //expiresIn is the number of seconds the token will be valid for
    String generateAccessToken(String username, int expiresIn) {
        long expirationTime = Instant.now().getEpochSecond() + expiresIn + expiresIn
        def claims = [
                sub  : username,
                roles: [USER.name()],
                iss  : appName,
                exp  : expirationTime,
        ]
        return jwtTokenGenerator.generateToken(claims).get()
    }
}
