package com.demo.parkinglot.service;

import com.demo.parkinglot.domain.ParkingSlotDTO;
import com.demo.parkinglot.domain.VehicleDTO;
import com.demo.parkinglot.entity.Slot;
import com.demo.parkinglot.exception.SlotException;
import com.demo.parkinglot.mapper.DtoToEntityMapper;
import com.demo.parkinglot.mapper.EntityToDtoMapper;
import com.demo.parkinglot.repository.ParkingSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarParkingSlotService implements ParkingSlotService{

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    @Autowired
    private EntityToDtoMapper entityToDtoMapper;

    @Autowired
    private DtoToEntityMapper dtoToEntityMapper;

    private static final int MAX_SLOT_ALLOCATION_TIME_IN_HOUR = 4;

    private static final int MIN_SLOT_ALLOCATION_TIME_IN_HOUR = 1;

    public CarParkingSlotService() {
    }

    @Override
    public List<ParkingSlotDTO> listAllSlots() {
        List<Slot> slotList = (List<Slot>) parkingSlotRepository.findAll();
        return slotList.stream().map(it-> entityToDtoMapper.mapSlotEntityToDTO(it))
                .collect(Collectors.toList());
    }

    @Override
    public ParkingSlotDTO createSlot() {
        return entityToDtoMapper.mapSlotEntityToDTO(parkingSlotRepository.save(new Slot()));
    }

    @Override
    public ParkingSlotDTO allocateSlot(VehicleDTO vehicleDTO, LocalDateTime localDateTime) {
        if (localDateTime.isAfter(LocalDateTime.now())) {
           throw new SlotException("In-time should not be future date");
        }
        List<Slot> slots  = parkingSlotRepository.findByIsAllocated(false);
        if (slots.isEmpty()){
            throw new SlotException("Parking lot is full");
        }
        Slot slot = slots.get(0);
        slot.setVehicle(dtoToEntityMapper.getVehicleByDto(vehicleDTO));
        slot.setVehicleInTime(localDateTime);
        slot.setAllocated(true);
        slot.setVehicleOutTime(localDateTime.plusHours(MAX_SLOT_ALLOCATION_TIME_IN_HOUR));
        return entityToDtoMapper.mapSlotEntityToDTO(parkingSlotRepository.save(slot));
    }

    @Override
    public ParkingSlotDTO reAllocateSlot(int slotId, int numberOfHoursToBeExtended) {
        if (numberOfHoursToBeExtended < MIN_SLOT_ALLOCATION_TIME_IN_HOUR
                || numberOfHoursToBeExtended > MAX_SLOT_ALLOCATION_TIME_IN_HOUR) {
            throw new SlotException("Reallocate time should not be more than "+MAX_SLOT_ALLOCATION_TIME_IN_HOUR);
        }
        Slot slot = parkingSlotRepository.findById(slotId).orElseThrow(() ->
                  new SlotException("Invalid Slot")
        );
        slot.setVehicleOutTime(slot.getVehicleOutTime().plusHours(numberOfHoursToBeExtended));
        return entityToDtoMapper.mapSlotEntityToDTO(parkingSlotRepository.save(slot));
    }

    @Override
    public void deAllocateSlot(int slotId) {
        Slot slot = parkingSlotRepository.findById(slotId).orElseThrow(() ->
                new SlotException("Invalid Slot")
        );
        slot.setVehicle(null);
        slot.setAllocated(false);
        slot.setVehicleInTime(null);
        slot.setVehicleOutTime(null);
        parkingSlotRepository.save(slot);
    }
}
