package com.agustincharry.reactivewebtest.models;

public class Car {
    private String brand;
    private String color;
    private String ownerId;

    public Car() {
    }

    public Car(String brand, String color, String ownerId) {
        this.brand = brand;
        this.color = color;
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", color='" + color + '\'' +
                ", ownerId='" + ownerId + '\'' +
                '}';
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}
