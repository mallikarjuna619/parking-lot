package com.demo.parkinglot.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "parking_slot")
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private boolean isAllocated;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;
    private LocalDateTime vehicleInTime;
    private LocalDateTime vehicleOutTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isAllocated() {
        return isAllocated;
    }

    public void setAllocated(boolean allocated) {
        isAllocated = allocated;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDateTime getVehicleInTime() {
        return vehicleInTime;
    }

    public void setVehicleInTime(LocalDateTime vehicleInTime) {
        this.vehicleInTime = vehicleInTime;
    }

    public LocalDateTime getVehicleOutTime() {
        return vehicleOutTime;
    }

    public void setVehicleOutTime(LocalDateTime vehicleOutTime) {
        this.vehicleOutTime = vehicleOutTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Slot slot = (Slot) o;
        return isAllocated == slot.isAllocated && Objects.equals(id, slot.id) && Objects.equals(vehicle, slot.vehicle) && Objects.equals(vehicleInTime, slot.vehicleInTime) && Objects.equals(vehicleOutTime, slot.vehicleOutTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isAllocated, vehicle, vehicleInTime, vehicleOutTime);
    }
}
