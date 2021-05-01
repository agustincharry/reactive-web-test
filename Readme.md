# Reactive web test
In this project you can find an example of a web server (API REST) and a client whose consume the REST APIs, all of this using reactive programming.

## Technologies
* Spring boot
* WebFlux
* Project Reactor
* WebClient
* Junit
* Mockito
* StepVerifier

## Main challenge
Having a ```Car``` model and ```Person``` model, the main challenge is populate the ```PersonWithCars``` model, using reactive programming, and response it when is called the ```/manager``` API.
```java
public class Car {
    private String id;
    private String brand;
    private String color;
    private String ownerId;
}
``` 

```java
public class Person {
    private String id;
    private String name;
    private int age;
}
```

```java
public class PersonWithCars {
    private String personId;
    private String personName;
    private String[] carIds;
}
```

## Server APIs

* ```/car``` Exposes a list of all the cars.
* ```/car/{id}``` Exposes the wanted car.
* ```/person``` Exposes a list of all the people.
* ```/person/{id}``` Exposes the wanted person.


## Client APIs

* ```/manager``` **The main challenge!** Having the server APIs, return a populated list of ```PersonWithCars```