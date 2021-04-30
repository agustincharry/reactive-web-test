package com.agustincharry.reactivewebtest.client.services;

import com.agustincharry.reactivewebtest.models.Car;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CarServiceClient {

    private WebClient webClient;

    public CarServiceClient() {
        webClient = WebClient.create("http://localhost:8080");
    }

    public Flux<Car> getAll(){
        return webClient.get().uri("/car")
                .retrieve()
                .bodyToFlux(Car.class);
    }

    public Mono<Car> getById(String id){
        return webClient.get().uri("/car/" + id)
                .retrieve()
                .bodyToMono(Car.class);
    }

}
