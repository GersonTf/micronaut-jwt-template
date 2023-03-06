package com.blazinc.repository

import com.blazinc.domain.ExpenseCategory
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository

@MongoRepository
interface ExpenseCategoryRepository extends CrudRepository<ExpenseCategory, String> {

}