package com.agustincharry.reactivewebtest.server.controllers;

import com.agustincharry.reactivewebtest.models.Car;
import com.agustincharry.reactivewebtest.server.services.CarServiceServer;
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
public class CarControllerTests {

    @MockBean
    private CarServiceServer service;

    @Autowired
    private CarController controller;

    private final List<Car> list;

    public CarControllerTests() {
        list = new ArrayList<>();
        list.add(new Car("1", "Mazda", "red", "2"));
        list.add(new Car("2", "Renault", "White", "1"));
        list.add(new Car("a", "a", "a", "a"));
        list.add(new Car("b", "b", "b", "b"));
    }

    @Test
    public void getAll() {
        Flux<Car> carFlux = Flux.fromIterable(list);
        when(service.getAll()).thenReturn(carFlux);

        ResponseEntity<Flux<Car>> httpResponse = controller.getAll();

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
        for (Car car : list) {
            when(service.getById(car.getId())).thenReturn(Mono.just(car));
            ResponseEntity<Mono<Car>> httpResponse = controller.getById(car.getId());

            Assertions.assertEquals(httpResponse.getStatusCode(), HttpStatus.OK);

            StepVerifier.create(httpResponse.getBody())
                    .expectNext(car)
                    .verifyComplete();

            StepVerifier.create(httpResponse.getBody())
                    .expectSubscription()
                    .expectNextCount(1)
                    .verifyComplete();
        }
    }

}
