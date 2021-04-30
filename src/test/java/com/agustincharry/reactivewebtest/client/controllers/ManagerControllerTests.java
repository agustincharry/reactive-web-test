package com.agustincharry.reactivewebtest.client.controllers;

import com.agustincharry.reactivewebtest.client.services.ManagerServiceClient;
import com.agustincharry.reactivewebtest.models.PersonWithCars;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
public class ManagerControllerTests {

    @MockBean
    private ManagerServiceClient service;

    @Autowired
    private ManagerController controller;

    private final List<PersonWithCars> list;

    public ManagerControllerTests() {
        list = new ArrayList<>();
        list.add(new PersonWithCars("1", "Pedro", new String[]{"1", "2"}));
        list.add(new PersonWithCars("2", "Sara", new String[]{"3"}));
        list.add(new PersonWithCars("3", "Vanessa", new String[]{"4", "5"}));
    }

    @Test
    public void getPeopleWithCars() {
        Flux<PersonWithCars> personWithCarsFlux = Flux.fromIterable(list);
        when(service.getPeopleWithCars()).thenReturn(personWithCarsFlux);

        ResponseEntity<Flux<PersonWithCars>> httpResponse = controller.getPeopleWithCars();

        Assertions.assertEquals(httpResponse.getStatusCode(), HttpStatus.OK);

        StepVerifier.create(httpResponse.getBody())
                .expectNextSequence(list)
                .verifyComplete();

        StepVerifier.create(httpResponse.getBody())
                .expectSubscription()
                .expectNextCount(list.size())
                .verifyComplete();
    }


}
