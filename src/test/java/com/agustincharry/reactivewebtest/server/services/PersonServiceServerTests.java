package com.agustincharry.reactivewebtest.server.services;

import com.agustincharry.reactivewebtest.models.Person;
import com.agustincharry.reactivewebtest.server.repositories.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
public class PersonServiceServerTests {

    @MockBean
    private PersonRepository repository;

    @Autowired
    private PersonServiceServer service;

    private final List<Person> list;

    public PersonServiceServerTests() {
        list = new ArrayList<>();
        list.add(new Person("1", "Juan", 15));
        list.add(new Person("2", "Carlos", 5));
        list.add(new Person("a", "a", 2));
        list.add(new Person("b", "b", 6));
        list.add(new Person("c", "c", 79));
    }

    @Test
    public void getAll() {
        Flux<Person> personFlux = Flux.fromIterable(list);
        when(repository.getAll()).thenReturn(personFlux);

        Flux<Person> result = service.getAll();

        StepVerifier.create(result)
                .expectNextSequence(list)
                .verifyComplete();

        StepVerifier.create(result)
                .expectSubscription()
                .expectNextCount(list.size())
                .verifyComplete();
    }

    @Test
    public void getById() {
        for (Person person : list) {
            when(repository.getById(person.getId())).thenReturn(Mono.just(person));

            Mono<Person> personFlux = service.getById(person.getId());

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
