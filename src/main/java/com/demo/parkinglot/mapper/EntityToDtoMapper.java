package com.demo.parkinglot.mapper;

import com.demo.parkinglot.domain.ParkingSlotDTO;
import com.demo.parkinglot.domain.VehicleDTO;
import com.demo.parkinglot.entity.Slot;
import com.demo.parkinglot.entity.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class EntityToDtoMapper {
    public ParkingSlotDTO mapSlotEntityToDTO(Slot slot) {
        ParkingSlotDTO parkingSlotDTO = new ParkingSlotDTO(slot.getId(), slot.isAllocated());
        parkingSlotDTO.setVehicle(mapVehicleToDTO(slot.getVehicle()));
        parkingSlotDTO.setVehicleInTime(slot.getVehicleInTime());
        parkingSlotDTO.setVehicleOutTime(slot.getVehicleOutTime());
        return parkingSlotDTO;
    }

    public VehicleDTO mapVehicleToDTO(Vehicle vehicle) {
        VehicleDTO vehicleDTO = new VehicleDTO();
        if(vehicle != null) {
            vehicleDTO.setRegNumber(vehicle.getRegNumber());
            vehicleDTO.setOwnerMobileNumber(vehicle.getOwnerMobileNumber());
        }
        return vehicleDTO;
    }
}
