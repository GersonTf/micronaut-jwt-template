package com.blazinc.security

import com.blazinc.domain.User
import com.blazinc.support.AppClient
import com.blazinc.repository.UserRepository
import com.blazinc.support.TestSupport

import com.nimbusds.jwt.JWTParser
import com.nimbusds.jwt.SignedJWT
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

import static io.micronaut.http.HttpStatus.UNAUTHORIZED

@MicronautTest(packages = "com.blazinc")
class DeclarativeHttpClientWithJwtSpec extends Specification implements TestSupport {

    @Inject
    AppClient appClient

    @Inject
    UserRepository userRepository

    User user

    void setup() {
        user = userRepository.save(createUser(rawPassword))
    }

    def cleanup() {
        userRepository.deleteAll()
    }

    void "Verify JWT authentication works with declarative @Client"() {
        when: 'Login endpoint is called with valid credentials'
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(user.username, rawPassword)
        BearerAccessRefreshToken loginRsp = appClient.login(creds)
        then:
        loginRsp
        loginRsp.accessToken
        JWTParser.parse(loginRsp.accessToken) instanceof SignedJWT

        when:
        String msg = appClient.home("Bearer $loginRsp.accessToken")

        then:
        msg == user.username
    }

    void "login with invalid credentials returns a 401"() {
        given: 'invalid credentials generated with random strings'
        String username = UUID.randomUUID().toString()
        String password = UUID.randomUUID().toString()

        when: 'Login endpoint is called with invalid credentials'
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(username, password)
        appClient.login(creds)

        then: 'a 401 is returned'
        HttpClientResponseException e = thrown()
        e.status == UNAUTHORIZED
    }

    void "the password have been encoded"() {
        expect: 'the encoded password is not equal to the raw password'
        user.password != rawPassword
        encoder.matches(rawPassword, user.password)
    }

    void "Verify JWT access token expiration time is honored"() {
        given: "an expired access token and a valid access token"
        String validToken = generateAccessToken(user.username, 1000)
        String expiredToken = generateAccessToken(user.username, -1000)

        when: "the access token is used to access a protected endpoint after it has expired"
        appClient.home("Bearer $expiredToken")

        then: "a 401 is returned"
        HttpClientResponseException e = thrown()
        e.status == UNAUTHORIZED

        when: "the access token is used to access a protected endpoint before it has expired"
        String msg = appClient.home("Bearer $validToken")

        then: "the endpoint can be accessed"
        notThrown(HttpClientResponseException)
        msg == user.username
    }
}