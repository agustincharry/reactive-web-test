package com.agustincharry.reactivewebtest.client.services;

import com.agustincharry.reactivewebtest.models.Person;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonServiceClient {

    private WebClient webClient;

    public PersonServiceClient() {
        webClient = WebClient.create("http://localhost:8080");
    }

    public Flux<Person> getAll(){
        return webClient.get().uri("/person")
                .retrieve()
                .bodyToFlux(Person.class);
    }

    public Mono<Person> getById(String id){
        return webClient.get().uri("/person/" + id)
                .retrieve()
                .bodyToMono(Person.class);
    }

}
