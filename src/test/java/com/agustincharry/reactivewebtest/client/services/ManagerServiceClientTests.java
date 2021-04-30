package com.agustincharry.reactivewebtest.client.services;

import com.agustincharry.reactivewebtest.models.Car;
import com.agustincharry.reactivewebtest.models.Person;
import com.agustincharry.reactivewebtest.models.PersonWithCars;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@SpringBootTest()
public class ManagerServiceClientTests {

    @MockBean
    private CarServiceClient carServiceClient;

    @MockBean
    private PersonServiceClient personServiceClient;

    @Autowired
    private ManagerServiceClient managerServiceClient;

    private final List<Car> carList;

    private final List<Person> personList;


    public ManagerServiceClientTests() {
        carList = new ArrayList<>();
        personList = new ArrayList<>();

        carList.add(new Car("1", "Mazda", "red", "2"));
        carList.add(new Car("2", "Mazda", "blue", "2"));
        carList.add(new Car("3", "Renault", "White", "1"));
        carList.add(new Car("c", "c", "c", "a"));
        carList.add(new Car("d", "d", "d", "b"));

        personList.add(new Person("1", "Laura",25));
        personList.add(new Person("2", "Sara", 30));
        personList.add(new Person("a", "a", 70));
        personList.add(new Person("b", "b", 16));
    }

    @Test
    public void getPeopleWithCars() {
        Flux<Car> carFlux = Flux.fromIterable(carList);
        Flux<Person> personFlux = Flux.fromIterable(personList);

        when(carServiceClient.getAll()).thenReturn(carFlux);
        when(personServiceClient.getAll()).thenReturn(personFlux);

        for (Car car : carList) {
            when(carServiceClient.getById(car.getId())).thenReturn(Mono.just(car));
        }
        for (Person person : personList) {
            when(personServiceClient.getById(person.getId())).thenReturn(Mono.just(person));
        }

        Flux<PersonWithCars>  result = managerServiceClient.getPeopleWithCars();

        // Validate person info
        StepVerifier.create(result)
                .expectSubscription()
                .expectNextCount(personList.size())
                .verifyComplete();

        StepVerifier.create(result.map(p -> p.getPersonId()).sort())
                .expectNextSequence(personList.stream().map(x -> x.getId()).sorted().collect(Collectors.toList()))
                .verifyComplete();

        StepVerifier.create(result.map(p -> p.getPersonName()).sort())
                .expectNextSequence(personList.stream().map(x -> x.getName()).sorted().collect(Collectors.toList()))
                .verifyComplete();

        // Validate cars of person
        for (Person person : personList) {
            Mono<PersonWithCars> personWithCars = result.filter(p -> p.getPersonId().equals(person.getId())).single();

            StepVerifier.create(personWithCars)
                    .expectSubscription()
                    .expectNextCount(1)
                    .verifyComplete();


            List<String> carIdsOfPerson = carList.stream()
                    .filter(c -> c.getOwnerId().equals(person.getId()))
                    .map(c -> c.getId())
                    .sorted()
                    .collect(Collectors.toList());

            Mono<List<String>> carIdsOfPersonResult = personWithCars.map(p -> {
                return Arrays.stream(p.getCarIds()).sorted().collect(Collectors.toList());
            });


            StepVerifier.create(carIdsOfPersonResult)
                    .expectNextSequence(Collections.singleton(carIdsOfPerson))
                    .verifyComplete();

        }


    }
}
