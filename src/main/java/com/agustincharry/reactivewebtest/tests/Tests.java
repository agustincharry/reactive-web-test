package com.agustincharry.reactivewebtest.tests;

import com.agustincharry.reactivewebtest.models.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Tests {

    private static final Logger log = LoggerFactory.getLogger(Tests.class);

    public static void mono() {
        log.info("----Mono---");
        Mono.just(new Person("1", "Agus", 25))
                .subscribe(p -> log.info(p.toString()));
    }

    public static void flux(){
        log.info("----Flux---");
        List<Person> list = new ArrayList();
        list.add(new Person("1", "Pedro", 20));
        list.add(new Person("1", "Pedro2", 21));
        list.add(new Person("2", "Laura", 15));
        list.add(new Person("3", "Sara", 38));

        log.info("----Map---");
        Flux.fromIterable(list)
                .map(p -> {
                    p.setAge(p.getAge() + 10);
                    return p;
                })
                .subscribe(p -> log.info(p.toString()));


        log.info("----FlatMap---");
        Flux.fromIterable(list)
                .flatMap(p -> {
                    p.setAge(p.getAge() + 2);
                    return Mono.just(p);
                })
                .subscribe(p -> log.info(p.toString()));


        log.info("----GroupBy---");
        Flux.fromIterable(list)
                .groupBy(Person::getId)
                .flatMap(idFlux -> idFlux.collectList())
                .subscribe(p -> log.info(p.toString()));

        log.info("----Filter---");
        Flux.fromIterable(list)
                .filter(p -> p.getAge() > 30)
                .subscribe(p -> log.info(p.toString()));

        log.info("----Distinct---");
        Flux.fromIterable(list)
                .distinct(p -> p.getId())
                .subscribe(p -> log.info(p.toString()));
    }

    public static void flux2(){
        log.info("----Flux---");
        List<Person> list = new ArrayList();
        list.add(new Person("1", "Pedro", 20));
        list.add(new Person("1", "Pedro2", 21));
        list.add(new Person("2", "Laura", 15));
        list.add(new Person("3", "Sara", 38));

        List<Person> list2 = new ArrayList();
        list2.add(new Person("4", "Pedro", 20));
        list2.add(new Person("5", "Pedro2", 21));
        list2.add(new Person("6", "Laura", 15));
        list2.add(new Person("7", "Sara", 38));

        log.info("----Merge---");
        Flux<Person> f1 = Flux.fromIterable(list);
        Flux<Person> f2 = Flux.fromIterable(list2);

        Flux.merge(f1, f2)
                .subscribe(p -> log.info(p.toString()));

        log.info("----ZipWith---");
        Flux<Person> f3 = Flux.fromIterable(list);
        Flux<Person> f4 = Flux.fromIterable(list2);

        f3.zipWith(f4)
                .subscribe(p -> log.info(p.toString()));
    }

    public static void error(){
        log.info("----Error---");
        List<Person> list = new ArrayList();
        list.add(new Person("1", "Pedro", 20));
        list.add(new Person("1", "Pedro2", 21));
        list.add(new Person("2", "Laura", 15));
        list.add(new Person("3", "Sara", 38));

        Flux.fromIterable(list)
                .concatWith(Flux.error(new RuntimeException("A error!")))
                .onErrorReturn(new Person("50", "50", 50))
                .subscribe(p -> log.info(p.toString()));
    }

    public static void conditional(){
        log.info("----DefaultIfEmpty---");
        Mono.empty()
                .defaultIfEmpty(new Person("10", "10", 10))
                .subscribe(p -> log.info(p.toString()));

        log.info("----TakeUntil---");
        List<Person> list = new ArrayList();
        list.add(new Person("1", "Pedro", 20));
        list.add(new Person("1", "Pedro2", 21));
        list.add(new Person("2", "Laura", 15));

        Flux.fromIterable(list)
                .takeUntil(p -> p.getAge() > 20)
                .subscribe(p -> log.info(p.toString()));
    }

    public static void math(){

        List<Person> list = new ArrayList();
        list.add(new Person("1", "Pedro", 20));
        list.add(new Person("1", "Pedro2", 21));
        list.add(new Person("2", "Laura", 15));

        log.info("----AveragingInt---");
        Flux.fromIterable(list)
                .collect(Collectors.averagingInt(Person::getAge))
                .subscribe(p -> log.info(p.toString()));

        log.info("----MinBy---");
        Flux.fromIterable(list)
                .collect(Collectors.minBy(Comparator.comparing(Person::getAge)))
                .subscribe(p -> log.info(p.toString()));
    }

}
