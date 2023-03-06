package com.blazinc.security

import com.blazinc.domain.User
import com.blazinc.repository.UserRepository
import com.blazinc.model.UserRoles
import groovy.transform.CompileStatic
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.context.event.StartupEvent
import jakarta.inject.Inject
import jakarta.inject.Singleton

@CompileStatic
@Singleton
class UserInitializer implements ApplicationEventListener<StartupEvent> {

    @Inject
    UserRepository userRepository

    //todo this is temporary, remove it when we have a proper user onboarding flow
    @Override
    void onApplicationEvent(StartupEvent event) {
        Optional<User> testUser = userRepository.findByUsername("initialUser")
        if (testUser.isEmpty()) {
            User user = new User(
                    username: "initialUser",
                    password: "password",
                    roles: [UserRoles.USER],
                    fireNumber: BigDecimal.valueOf(100000)
            )
            userRepository.save(user)
        }
    }
}
