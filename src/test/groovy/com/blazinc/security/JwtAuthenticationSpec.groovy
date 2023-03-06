package com.blazinc.security

import com.blazinc.domain.User
import com.blazinc.repository.UserRepository
import com.blazinc.support.TestSupport
import com.nimbusds.jwt.JWTParser
import com.nimbusds.jwt.SignedJWT
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.security.authentication.UsernamePasswordCredentials
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

import static io.micronaut.http.HttpStatus.OK
import static io.micronaut.http.HttpStatus.UNAUTHORIZED
import static io.micronaut.http.MediaType.TEXT_PLAIN

@MicronautTest(packages = "com.blazinc")
class JwtAuthenticationSpec extends Specification implements TestSupport {

    @Inject
    @Client("/")
    HttpClient client

    @Inject
    UserRepository userRepository

    User user

    void setup() {
        user = userRepository.save(createUser(rawPassword))
    }

    def cleanup() {
        userRepository.deleteAll()
    }

    void 'Accessing a secured URL without authenticating returns unauthorized'() {
        when:
        client.toBlocking().exchange(HttpRequest.GET('/').accept(TEXT_PLAIN))

        then:
        HttpClientResponseException e = thrown()
        e.status == UNAUTHORIZED
    }

    void "upon successful authentication, a JSON Web token is issued to the user"() {
        when: 'Login endpoint is called with valid credentials'
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials(user.username, rawPassword)
        HttpRequest request = HttpRequest.POST('/login', creds)
        HttpResponse<BearerAccessRefreshToken> rsp = client.toBlocking().exchange(request, BearerAccessRefreshToken)

        then: 'the endpoint can be accessed'
        rsp.status == OK

        when:
        BearerAccessRefreshToken bearerAccessRefreshToken = rsp.body()

        then:
        bearerAccessRefreshToken.username == user.username
        bearerAccessRefreshToken.accessToken

        and: 'the access token is a signed JWT'
        JWTParser.parse(bearerAccessRefreshToken.accessToken) instanceof SignedJWT

        when: 'passing the access token as in the Authorization HTTP Header with the prefix Bearer allows the user to access a secured endpoint'
        String accessToken = bearerAccessRefreshToken.accessToken
        HttpRequest requestWithAuthorization = HttpRequest.GET('/')
                .accept(TEXT_PLAIN)
                .bearerAuth(accessToken)
        HttpResponse<String> response = client.toBlocking().exchange(requestWithAuthorization, String)

        then:
        response.status == OK
        response.body() == user.username
    }
}