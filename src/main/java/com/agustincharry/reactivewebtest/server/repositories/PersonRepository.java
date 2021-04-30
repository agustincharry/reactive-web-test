package com.agustincharry.reactivewebtest.server.repositories;

import com.agustincharry.reactivewebtest.models.Person;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepository {

    private List<Person> list;

    public PersonRepository() {
        list = new ArrayList<>();
        list.add(new Person("1", "Pedro", 20));
        list.add(new Person("2", "Carlos", 19));
        list.add(new Person("3", "Sara", 37));
    }

    public Flux<Person> getAll() {
        return Flux.fromIterable(list);
    }

    public Mono<Person> getById(String id){
        return Flux.fromIterable(list)
                .filter((e)-> id.equals(e.getId()))
                .single();
    }


}
