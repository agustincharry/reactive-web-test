package com.agustincharry.reactivewebtest.client.services;

import com.agustincharry.reactivewebtest.models.Car;
import com.agustincharry.reactivewebtest.models.PersonWithCars;
import org.javatuples.Pair;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ManagerServiceClient {

    private CarServiceClient carServiceClient;
    private PersonServiceClient personServiceClient;

    public ManagerServiceClient(CarServiceClient carServiceClient, PersonServiceClient personServiceClient) {
        this.carServiceClient = carServiceClient;
        this.personServiceClient = personServiceClient;
    }

    public Flux<PersonWithCars> getPeopleWithCars(){
        Flux<Car> carFlux = carServiceClient.getAll();

        Flux<PersonWithCars> result = carFlux
                .groupBy(Car::getOwnerId)
                .flatMap(Flux::collectList)
                .map(carsByOwner -> {
                    String[] carIds = carsByOwner.stream().map(Car::getId).toArray(String[]::new);
                    return new Pair<>(carsByOwner.get(0).getOwnerId(), carIds);
                }).flatMap(tuple -> {
                    return personServiceClient.getById(tuple.getValue0())
                            .flatMap(p -> Mono.just(new PersonWithCars(tuple.getValue0(), p.getName(), tuple.getValue1())));
                });

        return result;
    }
}
