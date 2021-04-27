package com.agustincharry.reactivewebtest.controllers;

import com.agustincharry.reactivewebtest.models.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController {

    private static final Logger log = LoggerFactory.getLogger(CarController.class);

    @GetMapping
    public Flux<Car> getAll() {
        log.info("New request");
        List<Car> list =new ArrayList<>();
        list.add(new Car("1", "Mazda", "red", "2"));
        list.add(new Car("2", "Ford", "Black", "1"));
        list.add(new Car("3", "Renault", "White", "1"));
        //return Flux.fromIterable(list).delayElements(Duration.ofSeconds(5));
        return Flux.fromIterable(list);
    }


}
