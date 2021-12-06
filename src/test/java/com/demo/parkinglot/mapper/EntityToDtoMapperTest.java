package com.demo.parkinglot.mapper;

import com.demo.parkinglot.domain.ParkingSlotDTO;
import com.demo.parkinglot.domain.VehicleDTO;
import com.demo.parkinglot.entity.Slot;
import com.demo.parkinglot.entity.Vehicle;
import com.demo.parkinglot.mock.MockDataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class EntityToDtoMapperTest {

    private EntityToDtoMapper entityToDtoMapper;

    @BeforeEach
    public void beforeEach(){
        entityToDtoMapper = new EntityToDtoMapper();
    }

    @Test
    public void shouldMapSlotEntityToParkingSlot(){
        Slot slot = MockDataProvider.getSlotEntityMockWithDefaultData(new Vehicle());
        ParkingSlotDTO parkingSlotDTOExpectedObj = MockDataProvider.getParkingSlotDTOWithDefaultData(new VehicleDTO());
        ParkingSlotDTO parkingSlotDTO = entityToDtoMapper.mapSlotEntityToDTO(slot);
        assertEquals(parkingSlotDTO, parkingSlotDTOExpectedObj);
    }

    @Test
    public void shouldMapVehicleEntityToDTO(){
        Vehicle vehicle = MockDataProvider.getVehicleWithDefaultData();
        VehicleDTO vehicleDTO = entityToDtoMapper.mapVehicleToDTO(vehicle);
        assertEquals(vehicleDTO.getOwnerMobileNumber(), "912453789");
        assertEquals(vehicleDTO.getRegNumber(), "KA 03 MZ 1234");
    }

    @Test
    public void shouldMapVehicleDataIfItsNotNull(){
        VehicleDTO vehicleDTO = entityToDtoMapper.mapVehicleToDTO(null);
        assertNull(vehicleDTO.getOwnerMobileNumber());
        assertNull(vehicleDTO.getRegNumber());
    }

}
