package com.demo.parkinglot.entity;

import javax.persistence.*;
import java.util.Objects;
@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String regNumber;
    private String ownerMobileNumber;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getOwnerMobileNumber() {
        return ownerMobileNumber;
    }

    public void setOwnerMobileNumber(String ownerMobileNumber) {
        this.ownerMobileNumber = ownerMobileNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return id.equals(vehicle.id) && regNumber.equals(vehicle.regNumber) && Objects.equals(ownerMobileNumber, vehicle.ownerMobileNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, regNumber, ownerMobileNumber);
    }
}
