package com.demo.parkinglot.api;

import com.demo.parkinglot.domain.ParkingSlotDTO;
import com.demo.parkinglot.domain.VehicleDTO;
import com.demo.parkinglot.service.ParkingSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parking-lot")
public class ParkingLotController {

    private ParkingSlotService parkingSlotService;

    public ParkingLotController(ParkingSlotService parkingSlotService) {
        this.parkingSlotService = parkingSlotService;
    }

    @PostMapping("/create")
    public ResponseEntity<ParkingSlotDTO> createSlot(){
        return ResponseEntity.of(Optional.of(parkingSlotService.createSlot()));
    }

    @PostMapping("/allocate")
    public ResponseEntity<ParkingSlotDTO> allocateSlot(@RequestBody VehicleDTO vehicleDTO){
        return ResponseEntity.of(Optional.of(parkingSlotService.allocateSlot(vehicleDTO, LocalDateTime.now())));
    }

    @PutMapping("/re-allocate/{slotId}/{hoursToBeExtended}")
    public ResponseEntity<ParkingSlotDTO> reAllocateSlot(@PathVariable int slotId, @PathVariable int hoursToBeExtended){
        return ResponseEntity.of(Optional.of(parkingSlotService.reAllocateSlot(slotId, hoursToBeExtended)));
    }

    @PutMapping("/de-allocate/{slotId}")
    public void deAllocateSlot(@PathVariable int slotId){
        parkingSlotService.deAllocateSlot(slotId);
    }

    @GetMapping("/find-all-slots")
    public ResponseEntity<List<ParkingSlotDTO>> findAllParkingSlots(){
        return ResponseEntity.of(Optional.of(parkingSlotService.listAllSlots()));
    }
}
