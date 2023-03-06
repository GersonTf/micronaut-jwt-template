package com.blazinc.fruit


import io.micronaut.core.annotation.NonNull
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class DefaultFruitService implements FruitService {

    @Inject
    FruitRepository fruitRepository

    Iterable<Fruit> list() {
        return fruitRepository.findAll()
    }

    Fruit save(Fruit fruit) {
        if (fruit.getId() == null) {
            return fruitRepository.save(fruit)
        } else {
            return fruitRepository.update(fruit)
        }
    }

    Optional<Fruit> find(@NonNull String id) {
        return fruitRepository.findById(id)
    }

    Iterable<Fruit> findByNameInList(List<String> name) {
        return fruitRepository.findByNameInList(name)
    }
}