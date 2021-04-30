package com.agustincharry.reactivewebtest.server.controllers;

import com.agustincharry.reactivewebtest.models.Car;
import com.agustincharry.reactivewebtest.server.services.CarServiceServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/car")
public class CarController {

    private static final Logger log = LoggerFactory.getLogger(CarController.class);
    private CarServiceServer carServiceServer;

    public CarController(CarServiceServer carServiceServer) {
        this.carServiceServer = carServiceServer;
    }

    @GetMapping
    public ResponseEntity<Flux<Car>> getAll() {
        log.info("New request - Get all the cars");
        return ResponseEntity.ok(carServiceServer.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<Car>> getById(@PathVariable String id){
        log.info("New request - Get one car");
        return ResponseEntity.ok(carServiceServer.getById(id));
    }

}
