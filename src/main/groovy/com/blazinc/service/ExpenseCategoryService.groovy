package com.blazinc.service

import com.blazinc.domain.ExpenseCategory
import com.blazinc.repository.ExpenseCategoryRepository
import groovy.transform.CompileStatic
import io.micronaut.core.annotation.NonNull
import jakarta.inject.Inject
import jakarta.inject.Singleton

@CompileStatic
@Singleton
class ExpenseCategoryService {

    @Inject
    ExpenseCategoryRepository expenseCategoryRepository

    Iterable<ExpenseCategory> list() {
        return expenseCategoryRepository.findAll()
    }

    ExpenseCategory save(ExpenseCategory expenseCategory) {
        if (expenseCategory.getId() == null) {
            return expenseCategoryRepository.save(expenseCategory)
        } else {
            return expenseCategoryRepository.update(expenseCategory)
        }
    }

    Optional<ExpenseCategory> find(@NonNull String id) {
        return expenseCategoryRepository.findById(id)
    }
}
