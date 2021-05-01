package com.agustincharry.reactivewebtest.client.services;

import com.agustincharry.reactivewebtest.models.Person;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonServiceClient {

    private WebClient webClient;

    public PersonServiceClient(Environment env) {
        webClient = WebClient.create("http://localhost:" + env.getProperty("server.port"));
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
