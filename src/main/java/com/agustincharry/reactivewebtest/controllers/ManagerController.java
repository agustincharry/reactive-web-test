package com.agustincharry.reactivewebtest.controllers;

import com.agustincharry.reactivewebtest.models.Car;
import com.agustincharry.reactivewebtest.models.PersonWithCars;
import com.agustincharry.reactivewebtest.models.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.javatuples.Pair;


@RestController
@RequestMapping("/manager")
public class ManagerController {

    private static final Logger log = LoggerFactory.getLogger(ManagerController.class);
    WebClient client = WebClient.create("http://localhost:8080");


    @GetMapping
    public Flux<PersonWithCars> index() {
        log.info("New request");

        Flux<Car> carFlux = getCars();

        return carFlux
                .groupBy(Car::getOwnerId)
                .flatMap(Flux::collectList)
                .map(carsByOwner -> {
                    String[] carIds = carsByOwner.stream().map(Car::getId).toArray(String[]::new);
                    return new Pair<>(carsByOwner.get(0).getOwnerId(), carIds);
                }).flatMap(tuple -> {
                    return getPerson(tuple.getValue0())
                            .flatMap(p -> Mono.just(new PersonWithCars(tuple.getValue0(), p.getName(), tuple.getValue1())));
                });
    }

    public Flux<Car> getCars() {
        return client.get().uri("/car")
                .retrieve()
                .bodyToFlux(Car.class);
    }

    public Mono<Person> getPerson(String id){
        return client.get().uri("/person/"+id)
                .retrieve()
                .bodyToMono(Person.class);
    }

    public Flux<Person> getPeople(){
        return client.get().uri("/person")
                .retrieve()
                .bodyToFlux(Person.class);
    }

}

