package com.demo.parkinglot.mock;

import com.demo.parkinglot.domain.ParkingSlotDTO;
import com.demo.parkinglot.domain.VehicleDTO;
import com.demo.parkinglot.entity.Slot;
import com.demo.parkinglot.entity.Vehicle;

import java.time.LocalDateTime;

public class MockDataProvider {
    final static LocalDateTime inTime = LocalDateTime.of(2021, 12, 5, 10, 10);
    final static LocalDateTime outTime = LocalDateTime.of(2021, 12, 5, 14, 10);

    public static Slot getSlotEntityMockWithDefaultData(Vehicle vehicle){
        Slot slot = new Slot();
        slot.setId(1);
        if(vehicle != null){
            slot.setAllocated(true);
            slot.setVehicle(vehicle);
            slot.setVehicleInTime(inTime);
            slot.setVehicleOutTime(outTime);
        }
        return slot;
    }

    public static ParkingSlotDTO getParkingSlotDTOWithDefaultData(VehicleDTO vehicleDTO) {
        ParkingSlotDTO parkingSlotDTOExpectedObj =  new ParkingSlotDTO(1, false);
        parkingSlotDTOExpectedObj.setVehicle(new VehicleDTO());
        parkingSlotDTOExpectedObj.setVehicleInTime(inTime);
        parkingSlotDTOExpectedObj.setVehicleOutTime(outTime);
        return parkingSlotDTOExpectedObj;
    }

    public static VehicleDTO getVehicleDTO(){
        VehicleDTO vehicleDTO = new VehicleDTO("KA 03 MZ 1234");
        vehicleDTO.setOwnerMobileNumber("98765412332");
        return vehicleDTO;
    }

    public static Vehicle getVehicleWithDefaultData(){
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1);
        vehicle.setRegNumber("KA 03 MZ 1234");
        vehicle.setOwnerMobileNumber("912453789");
        return vehicle;
    }
}
