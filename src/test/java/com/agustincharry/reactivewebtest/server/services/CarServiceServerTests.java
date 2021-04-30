package com.agustincharry.reactivewebtest.server.services;

import com.agustincharry.reactivewebtest.models.Car;
import com.agustincharry.reactivewebtest.server.repositories.CarRepository;
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
public class CarServiceServerTests {

    @MockBean
    private CarRepository repository;

    @Autowired
    private CarServiceServer service;

    private final List<Car> list;

    public CarServiceServerTests() {
        list = new ArrayList<>();
        list.add(new Car("1", "Mazda", "red", "2"));
        list.add(new Car("2", "Renault", "White", "1"));
        list.add(new Car("a", "a", "a", "a"));
        list.add(new Car("b", "b", "b", "b"));
        list.add(new Car("c", "c", "c", "c"));
    }

    @Test
    public void getAll() {
        Flux<Car> carFlux = Flux.fromIterable(list);
        when(repository.getAll()).thenReturn(carFlux);

        Flux<Car> result = service.getAll();

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
        for (Car car : list) {
            when(repository.getById(car.getId())).thenReturn(Mono.just(car));

            Mono<Car> carFlux = service.getById(car.getId());

            StepVerifier.create(carFlux)
                    .expectNext(car)
                    .verifyComplete();

            StepVerifier.create(carFlux)
                    .expectSubscription()
                    .expectNextCount(1)
                    .verifyComplete();
        }
    }

}
