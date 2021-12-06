package com.demo.parkinglot.domain;

import com.demo.parkinglot.entity.Vehicle;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class ParkingSlotDTO {
    private int id;
    private boolean isAllocated;
    private VehicleDTO vehicle;
    private LocalDateTime vehicleInTime;
    private LocalDateTime vehicleOutTime;

    public ParkingSlotDTO(int id, boolean allocated) {
        this.id = id;
        this.isAllocated = allocated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSlotDTO that = (ParkingSlotDTO) o;
        return id == that.id && isAllocated == that.isAllocated && Objects.equals(vehicle, that.vehicle) && Objects.equals(vehicleInTime, that.vehicleInTime) && Objects.equals(vehicleOutTime, that.vehicleOutTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isAllocated, vehicle, vehicleInTime, vehicleOutTime);
    }
}
