package com.agustincharry.reactivewebtest.client.services;

import com.agustincharry.reactivewebtest.models.Person;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest()
public class PersonServiceClientTests {

    @Mock
    private WebClient webClientMock;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpecMock;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;

    @Mock
    private WebClient.ResponseSpec responseSpecMock;

    @Autowired
    private PersonServiceClient service;

    private final List<Person> list;

    public PersonServiceClientTests() {
        list = new ArrayList<>();
        list.add(new Person("1", "Juan", 15));
        list.add(new Person("1", "Sara", 25));
        list.add(new Person("2", "Paula", 31));
        list.add(new Person("a", "a", 49));
    }

    @Test
    public void getAll(){
        ReflectionTestUtils.setField(service, "webClient", webClientMock);
        when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(anyString())).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        when(responseSpecMock.bodyToFlux(ArgumentMatchers.<Class<Person>>notNull())).thenReturn(Flux.fromIterable(list));

        Flux<Person> result = service.getAll();

        StepVerifier.create(result)
                .expectNextSequence(list)
                .verifyComplete();

        StepVerifier.create(result)
                .expectSubscription()
                .expectNextCount(list.size())
                .verifyComplete();
    }

    @Test
    public void getById() {
        ReflectionTestUtils.setField(service, "webClient", webClientMock);
        when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(anyString())).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);

        for (Person person : list) {
            when(responseSpecMock.bodyToMono(ArgumentMatchers.<Class<Person>>notNull())).thenReturn(Mono.just(person));

            Mono<Person> personFlux = service.getById(person.getId());

            StepVerifier.create(personFlux)
                    .expectNext(person)
                    .verifyComplete();

            StepVerifier.create(personFlux)
                    .expectSubscription()
                    .expectNextCount(1)
                    .verifyComplete();
        }
    }

}
