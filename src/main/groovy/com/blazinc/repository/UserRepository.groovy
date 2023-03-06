package com.blazinc.repository

import com.blazinc.domain.User
import io.micronaut.core.annotation.NonNull
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository

@MongoRepository
interface UserRepository extends CrudRepository<User, String> {

   Optional<User> findByUsernameAndPassword(@NonNull String username, @NonNull String password)

   Optional<User> findByUsername(@NonNull String username)
}
