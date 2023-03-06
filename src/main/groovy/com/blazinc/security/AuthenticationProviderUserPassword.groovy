package com.blazinc.security

import com.blazinc.domain.User
import com.blazinc.repository.UserRepository
import io.micronaut.core.annotation.Nullable
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.AuthenticationProvider
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink

@Singleton
class AuthenticationProviderUserPassword implements AuthenticationProvider {

    @Inject
    UserRepository userRepository

    @Override
    Publisher<AuthenticationResponse> authenticate(@Nullable HttpRequest<?> httpRequest,
                                                   AuthenticationRequest<?, ?> authenticationRequest) {
        Flux.create(emitter -> {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder()
            Optional<User> userOptional = userRepository.findByUsername(authenticationRequest.identity as String)
            if (userOptional.isPresent() && encoder.matches(authenticationRequest.secret as String, userOptional.get().password)) {
                User user = userOptional.get()
                emitter.next(AuthenticationResponse.success(user.username, user.roles*.name() as List<String>))
                emitter.complete()
            } else {
                emitter.error(AuthenticationResponse.exception("invalid credentials"))
            }
        }, FluxSink.OverflowStrategy.ERROR)
    }
}
