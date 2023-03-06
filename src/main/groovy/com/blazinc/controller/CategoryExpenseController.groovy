package com.blazinc.controller

import com.blazinc.domain.ExpenseCategory
import com.blazinc.service.ExpenseCategoryService
import io.micronaut.core.annotation.NonNull
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.*
import jakarta.inject.Inject

import javax.validation.Valid
import javax.validation.constraints.NotNull

@Controller("/expenses/category")
class CategoryExpenseController {

    @Inject
    ExpenseCategoryService expenseCategoryService

    @Get
    Iterable<ExpenseCategory> list() {
        expenseCategoryService.list()
    }

    @Post
    @Status(HttpStatus.CREATED)
    ExpenseCategory save(@NonNull @NotNull @Valid ExpenseCategory expenseCategory) {
        expenseCategoryService.save(expenseCategory)
    }

    @Put
    ExpenseCategory update(@NonNull @NotNull @Valid ExpenseCategory expenseCategory) {
        expenseCategoryService.save(expenseCategory)
    }

    @Get("/{id}")
    Optional<ExpenseCategory> find(@PathVariable String id) {
        expenseCategoryService.find(id)
    }
}
