package com.demo.parkinglot.service;

import com.demo.parkinglot.domain.ParkingSlotDTO;
import com.demo.parkinglot.domain.VehicleDTO;
import com.demo.parkinglot.entity.Slot;

import java.time.LocalDateTime;
import java.util.List;

public interface ParkingSlotService {
    List<ParkingSlotDTO> listAllSlots();
    ParkingSlotDTO createSlot();
    ParkingSlotDTO allocateSlot(VehicleDTO vehicleDTO, LocalDateTime localDateTime);
    ParkingSlotDTO reAllocateSlot(int slotId, int hoursToBeExtended);
    void deAllocateSlot(int slotId);
}
