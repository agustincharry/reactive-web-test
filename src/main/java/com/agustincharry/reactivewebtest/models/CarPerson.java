package com.agustincharry.reactivewebtest.models;

public class CarPerson {
    private String carBrand;
    private String personId;
    private String personName;

    public CarPerson() {
    }

    public CarPerson(String carBrand, String personId, String personName) {
        this.carBrand = carBrand;
        this.personId = personId;
        this.personName = personName;
    }

    @Override
    public String toString() {
        return "CarPerson{" +
                ", carBrand='" + carBrand + '\'' +
                ", personId='" + personId + '\'' +
                ", personName='" + personName + '\'' +
                '}';
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }
}
