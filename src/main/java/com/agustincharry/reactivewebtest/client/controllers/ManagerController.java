package com.agustincharry.reactivewebtest.client.controllers;

import com.agustincharry.reactivewebtest.client.services.CarServiceClient;
import com.agustincharry.reactivewebtest.client.services.PersonServiceClient;
import com.agustincharry.reactivewebtest.models.Car;
import com.agustincharry.reactivewebtest.models.PersonWithCars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.javatuples.Pair;


@RestController
@RequestMapping("/manager")
public class ManagerController {

    private static final Logger log = LoggerFactory.getLogger(ManagerController.class);
    private CarServiceClient carServiceClient;
    private PersonServiceClient personServiceClient;

    public ManagerController(CarServiceClient carServiceClient, PersonServiceClient personServiceClient) {
        this.carServiceClient = carServiceClient;
        this.personServiceClient = personServiceClient;
    }

    @GetMapping
    public Flux<PersonWithCars> index() {
        log.info("New request");

        Flux<Car> carFlux = carServiceClient.getAll();

        return carFlux
                .groupBy(Car::getOwnerId)
                .flatMap(Flux::collectList)
                .map(carsByOwner -> {
                    String[] carIds = carsByOwner.stream().map(Car::getId).toArray(String[]::new);
                    return new Pair<>(carsByOwner.get(0).getOwnerId(), carIds);
                }).flatMap(tuple -> {
                    return personServiceClient.getById(tuple.getValue0())
                            .flatMap(p -> Mono.just(new PersonWithCars(tuple.getValue0(), p.getName(), tuple.getValue1())));
                });
    }

}

