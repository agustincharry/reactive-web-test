package com.agustincharry.reactivewebtest.server.repositories;

import com.agustincharry.reactivewebtest.models.Car;
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
public class CarRepositoryTests {

    @Autowired
    private CarRepository repository;

    private final List<Car> list;

    public CarRepositoryTests() {
        list = new ArrayList<>();
        list.add(new Car("1", "Mazda", "red", "2"));
        list.add(new Car("2", "Renault", "White", "1"));
        list.add(new Car("a", "a", "a", "a"));
        list.add(new Car("b", "b", "b", "b"));
    }

    @Test
    public void getAll() {
        ReflectionTestUtils.setField(repository, "list", list);
        Flux<Car> carFlux = repository.getAll();

        StepVerifier.create(carFlux)
                .expectNext(list.get(0))
                .expectNext(list.get(1))
                .expectNext(list.get(2))
                .expectNext(list.get(3))
                .verifyComplete();

        StepVerifier.create(carFlux)
                .expectNextSequence(list)
                .verifyComplete();

        StepVerifier.create(carFlux)
                .expectSubscription()
                .expectNextCount(list.size())
                .verifyComplete();
    }

    @Test
    public void getById() {
        ReflectionTestUtils.setField(repository, "list", list);

        for (Car car : list) {
            Mono<Car> carFlux = repository.getById(car.getId());

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
