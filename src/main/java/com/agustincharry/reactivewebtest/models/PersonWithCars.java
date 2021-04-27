package com.agustincharry.reactivewebtest.models;

import java.util.Arrays;

public class PersonWithCars {
    private String personId;
    private String personName;
    private String[] carIds;

    public PersonWithCars() {
    }

    public PersonWithCars(String personId, String personName, String[] carIds) {
        this.personId = personId;
        this.personName = personName;
        this.carIds = carIds;
    }

    @Override
    public String toString() {
        return "PersonWithCars{" +
                "personId='" + personId + '\'' +
                "personName='" + personName + '\'' +
                ", cars=" + Arrays.toString(carIds) +
                '}';
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String[] getCarIds() {
        return carIds;
    }

    public void setCarIds(String[] carIds) {
        this.carIds = carIds;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
