package com.agustincharry.reactivewebtest.client.services;

import com.agustincharry.reactivewebtest.models.Car;
import com.agustincharry.reactivewebtest.models.Person;
import com.agustincharry.reactivewebtest.models.PersonWithCars;
import org.javatuples.Pair;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ManagerServiceClient {

    private CarServiceClient carServiceClient;
    private PersonServiceClient personServiceClient;

    public ManagerServiceClient(CarServiceClient carServiceClient, PersonServiceClient personServiceClient) {
        this.carServiceClient = carServiceClient;
        this.personServiceClient = personServiceClient;
    }

    public Flux<PersonWithCars> getPeopleWithCars(){
        Flux<Car> carsFlux = carServiceClient.getAll();
        Flux<Pair<String, String[]>> groupedCarsFlux = carsFlux.groupBy(Car::getOwnerId)
                .flatMap(Flux::collectList)
                .map(carsByOwner -> {
                    String[] carIds = carsByOwner.stream().map(Car::getId).toArray(String[]::new);
                    return new Pair<>(carsByOwner.get(0).getOwnerId(), carIds);
                });

        Flux<Person> peopleFlux = groupedCarsFlux.flatMap(d -> personServiceClient.getById(d.getValue0()));

        return peopleFlux.zipWith(groupedCarsFlux,(p, c)-> new PersonWithCars(p.getId(), p.getName(), c.getValue1()));
    }
}
