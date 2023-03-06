package com.blazinc.fruit


import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.Specification

@MicronautTest(packages = "com.blazinc")
class FruitValidationControllerSpec extends Specification {

    @Inject
    @Client("/")
    HttpClient httpClient

    def "fruit is validated"() {
        when:
        httpClient.toBlocking().exchange(HttpRequest.POST('/fruits', new Fruit('', 'Hola')))

        then:
        HttpClientResponseException e = thrown()
        e.status == HttpStatus.BAD_REQUEST
    }
}