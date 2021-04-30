package com.agustincharry.reactivewebtest.client.controllers;

import com.agustincharry.reactivewebtest.client.services.ManagerServiceClient;
import com.agustincharry.reactivewebtest.models.PersonWithCars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    private static final Logger log = LoggerFactory.getLogger(ManagerController.class);
    private ManagerServiceClient managerServiceClient;

    public ManagerController(ManagerServiceClient managerServiceClient) {
        this.managerServiceClient = managerServiceClient;
    }

    @GetMapping
    public ResponseEntity<Flux<PersonWithCars>> getPeopleWithCars() {
        log.info("New request - Get people with cars");
        return ResponseEntity.ok(managerServiceClient.getPeopleWithCars());
    }

}

