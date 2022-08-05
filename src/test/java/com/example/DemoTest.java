package com.example;

import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import jakarta.inject.Inject;

import java.time.Instant;
import java.util.UUID;

@MicronautTest
class DemoTest {

    @Inject
    EmbeddedApplication<?> application;

    @Inject
    AnimalRepository animalRepository;

    @Test
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());

        Animal animal = new Animal(1, Instant.EPOCH);
        animalRepository.save(animal);

        // PASS: The saved animal has Instant.EPOCH, as expected.
        Animal savedAnimal = animalRepository.findById(1).get();
        Assertions.assertEquals(savedAnimal.createdAt, Instant.EPOCH);

        // FAIL: The instant will have an offset the same as your timezone. This means the test will pass if it's run on a PC set to UTC.
        Assertions.assertEquals(animalRepository.getRawInstant(1), "1970-01-01 00:00:00");
    }
}
