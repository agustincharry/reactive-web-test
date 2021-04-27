package com.agustincharry.reactivewebtest.controllers;

import com.agustincharry.reactivewebtest.models.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private static final Logger log = LoggerFactory.getLogger(PersonController.class);

    List<Person> list = new ArrayList();

    public PersonController() {
        list.add(new Person("1", "Pedro", 20));
        list.add(new Person("2", "Carlos", 19));
        list.add(new Person("3", "Sara", 37));
    }

    @GetMapping
    public Flux<Person> getAll() {
        log.info("New request");
        return Flux.fromIterable(list);
    }

    @GetMapping("/{id}")
    public Mono<Person> getById(@PathVariable String id){
        log.info("New request");
        return Flux.fromIterable(list)
                .filter((e)-> id.equals(e.getId()))
                .single();
    }

}
