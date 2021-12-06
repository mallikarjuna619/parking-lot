package com.demo.parkinglot.mapper;

import com.demo.parkinglot.domain.ParkingSlotDTO;
import com.demo.parkinglot.domain.VehicleDTO;
import com.demo.parkinglot.entity.Slot;
import com.demo.parkinglot.entity.Vehicle;
import org.springframework.stereotype.Service;

@Service
public class DtoToEntityMapper {
   /*
    public Slot getSlotByDto(ParkingSlotDTO parkingSlotDTO){
        Slot slot = new Slot();
        slot.setAllocated(parkingSlotDTO.isAllocated());
        slot.setVehicleOutTime(parkingSlotDTO.getVehicleOutTime());
        slot.setVehicleInTime(parkingSlotDTO.getVehicleInTime());
        slot.setVehicle(getVehicleByDto(parkingSlotDTO.getVehicle()));
        return slot;
    }
*/
    public Vehicle getVehicleByDto(VehicleDTO vehicleDTO){
        Vehicle vehicle = new Vehicle();
        vehicle.setRegNumber(vehicleDTO.getRegNumber());
        vehicle.setOwnerMobileNumber(vehicleDTO.getOwnerMobileNumber());
        return vehicle;
    }
}
