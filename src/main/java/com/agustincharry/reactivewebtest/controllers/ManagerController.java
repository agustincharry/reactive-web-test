package com.agustincharry.reactivewebtest.controllers;

import com.agustincharry.reactivewebtest.models.Car;
import com.agustincharry.reactivewebtest.models.CarPerson;
import com.agustincharry.reactivewebtest.models.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    private static final Logger log = LoggerFactory.getLogger(ManagerController.class);
    WebClient client = WebClient.create("http://localhost:8080");


    @GetMapping
    public Flux<CarPerson> index() {
        log.info("New request");
        Flux<Car> carFlux = getCars();
        Flux<Person> peopleFlux = getPeople();

        /*
        Flux<CarPerson> newFlux = Flux.merge(carFlux, peopleFlux)
                .map(c -> {
                    String carBrand = "";
                    String personId = "";
                    String personName = "";
                    return new CarPerson(carBrand, personId, personName);
                });
        */
        /*
        Flux.merge(getCars(), getPeople())
                .parallel()
                .runOn(Schedulers.parallel())
                .map(data -> {
                    return new CarPerson();
                })
                .subscribe(data -> log.info(data.toString()));*/

        //return getCars().zipWith (getPeople(), (a, b) -> new CarPerson(a.getBrand(), b.getId(), b.getName()));

        //Flux<CarPerson> newFlux = carFlux.zipWith(peopleFlux, (c, p) -> c.getOwnerId() == p.getId());


        return Flux.zip(carFlux, peopleFlux, (c, p) -> new CarPerson(c.getBrand(), p.getId(), p.getName()));

        //return Flux.empty();
    }

    public Flux<Car> getCars() {
        return client.get().uri("/car")
                .retrieve()
                .bodyToFlux(Car.class);
    }

    public Flux<Person> getPeople() {
        return client.get().uri("/person")
                .retrieve()
                .bodyToFlux(Person.class);
    }

}

