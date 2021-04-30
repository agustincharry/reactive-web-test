package com.agustincharry.reactivewebtest.server.repositories;

import com.agustincharry.reactivewebtest.models.Car;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CarRepository {

    private List<Car> list;

    public CarRepository() {
        list = new ArrayList<>();
        list.add(new Car("1", "Mazda", "red", "2"));
        list.add(new Car("2", "Ford", "Black", "1"));
        list.add(new Car("3", "Renault", "White", "1"));
    }

    public Flux<Car> getAll() {
        return Flux.fromIterable(list);
    }

    public Mono<Car> getById(String id){
        return Flux.fromIterable(list)
                .filter((e)-> id.equals(e.getId()))
                .single();
    }

}
