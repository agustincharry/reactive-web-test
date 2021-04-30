package com.agustincharry.reactivewebtest.server.controllers;

import com.agustincharry.reactivewebtest.models.Person;
import com.agustincharry.reactivewebtest.server.services.PersonServiceServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/person")
public class PersonController {

    private static final Logger log = LoggerFactory.getLogger(PersonController.class);
    private PersonServiceServer personServiceServer;

    public PersonController(PersonServiceServer personServiceServer) {
        this.personServiceServer = personServiceServer;
    }

    @GetMapping
    public ResponseEntity<Flux<Person>> getAll() {
        log.info("New request - Get all the people");
        return ResponseEntity.ok(personServiceServer.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<Person>> getById(@PathVariable String id){
        log.info("New request - Get one person");
        return ResponseEntity.ok(personServiceServer.getById(id));
    }

}
