package com.agustincharry.reactivewebtest.server.repositories;

import com.agustincharry.reactivewebtest.models.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class PersonRepositoryTests {

    @Autowired
    private PersonRepository repository;

    private final List<Person> list;

    public PersonRepositoryTests() {
        list = new ArrayList<>();
        list.add(new Person("1", "Sara", 25));
        list.add(new Person("2", "Paula", 31));
        list.add(new Person("a", "a", 2));
        list.add(new Person("b", "b", 3));
    }

    @Test
    public void getAll() {
        ReflectionTestUtils.setField(repository, "list", list);
        Flux<Person> personFlux = repository.getAll();

        StepVerifier.create(personFlux)
                .expectNext(list.get(0))
                .expectNext(list.get(1))
                .expectNext(list.get(2))
                .expectNext(list.get(3))
                .verifyComplete();

        StepVerifier.create(personFlux)
                .expectNextSequence(list)
                .verifyComplete();

        StepVerifier.create(personFlux)
                .expectSubscription()
                .expectNextCount(list.size())
                .verifyComplete();
    }

    @Test
    public void getById() {
        ReflectionTestUtils.setField(repository, "list", list);

        for (Person person : list) {
            Mono<Person> personFlux = repository.getById(person.getId());

            StepVerifier.create(personFlux)
                    .expectNext(person)
                    .verifyComplete();

            StepVerifier.create(personFlux)
                    .expectSubscription()
                    .expectNextCount(1)
                    .verifyComplete();
        }


    }

}
