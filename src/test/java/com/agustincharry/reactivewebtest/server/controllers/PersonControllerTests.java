package com.agustincharry.reactivewebtest.server.controllers;

import com.agustincharry.reactivewebtest.models.Person;
import com.agustincharry.reactivewebtest.server.services.PersonServiceServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
public class PersonControllerTests {

    @MockBean
    private PersonServiceServer service;

    @Autowired
    private PersonController controller;

    private final List<Person> list;

    public PersonControllerTests() {
        list = new ArrayList<>();
        list.add(new Person("1", "Laura",25));
        list.add(new Person("2", "Sara", 30));
        list.add(new Person("a", "a", 70));
        list.add(new Person("b", "b", 16));
    }

    @Test
    public void getAll() {
        Flux<Person> personFlux = Flux.fromIterable(list);
        when(service.getAll()).thenReturn(personFlux);

        ResponseEntity<Flux<Person>> httpResponse = controller.getAll();

        Assertions.assertEquals(httpResponse.getStatusCode(), HttpStatus.OK);

        StepVerifier.create(httpResponse.getBody())
                .expectNextSequence(list)
                .verifyComplete();

        StepVerifier.create(httpResponse.getBody())
                .expectSubscription()
                .expectNextCount(list.size())
                .verifyComplete();
    }

    @Test
    public void getById() {
        for (Person person : list) {
            when(service.getById(person.getId())).thenReturn(Mono.just(person));
            ResponseEntity<Mono<Person>> httpResponse = controller.getById(person.getId());

            Assertions.assertEquals(httpResponse.getStatusCode(), HttpStatus.OK);

            StepVerifier.create(httpResponse.getBody())
                    .expectNext(person)
                    .verifyComplete();

            StepVerifier.create(httpResponse.getBody())
                    .expectSubscription()
                    .expectNextCount(1)
                    .verifyComplete();
        }
    }

}
