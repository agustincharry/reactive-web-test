package com.agustincharry.reactivewebtest.server.services;

import com.agustincharry.reactivewebtest.models.Car;
import com.agustincharry.reactivewebtest.server.repositories.CarRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CarServiceServer {

    private CarRepository repository;

    public CarServiceServer(CarRepository repository) {
        this.repository = repository;
    }

    public Flux<Car> getAll() {
        return repository.getAll();
    }

    public Mono<Car> getById(String id){
        return repository.getById(id);
    }

}
