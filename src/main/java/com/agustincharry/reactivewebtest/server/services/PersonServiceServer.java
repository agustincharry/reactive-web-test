package com.agustincharry.reactivewebtest.server.services;

import com.agustincharry.reactivewebtest.models.Person;
import com.agustincharry.reactivewebtest.server.repositories.PersonRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonServiceServer {

    private PersonRepository repository;

    public PersonServiceServer(PersonRepository repository) {
        this.repository = repository;
    }

    public Flux<Person> getAll() {
        return repository.getAll();
    }

    public Mono<Person> getById(String id){
        return repository.getById(id);
    }

}
