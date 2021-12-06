package com.demo.parkinglot.service;

import com.demo.parkinglot.domain.ParkingSlotDTO;
import com.demo.parkinglot.domain.VehicleDTO;
import com.demo.parkinglot.entity.Slot;
import com.demo.parkinglot.exception.SlotException;
import com.demo.parkinglot.mapper.DtoToEntityMapper;
import com.demo.parkinglot.mapper.EntityToDtoMapper;
import com.demo.parkinglot.mock.MockDataProvider;
import com.demo.parkinglot.repository.ParkingSlotRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CarParkingSlotServiceTest {

    @InjectMocks
    private CarParkingSlotService carParkingSlotService = new CarParkingSlotService();

    @Mock
    private ParkingSlotRepository parkingSlotRepository;

    @Mock
    private EntityToDtoMapper entityToDtoMapper;

    @Mock
    private DtoToEntityMapper dtoToEntityMapper;

    @Test
    public void shouldReturnAllAvailableSlots(){
        Slot slot = MockDataProvider.getSlotEntityMockWithDefaultData(MockDataProvider.getVehicleWithDefaultData());
        List<Slot> slots = new ArrayList<>();
        slots.add(slot);
        Mockito.when(parkingSlotRepository.findAll()).thenReturn(slots);
        Mockito.when(entityToDtoMapper.mapSlotEntityToDTO(slot))
                .thenReturn(MockDataProvider.getParkingSlotDTOWithDefaultData(new VehicleDTO()));

        List<ParkingSlotDTO> parkingSlotDTOSExpected = new ArrayList<>();
        parkingSlotDTOSExpected.add(MockDataProvider.getParkingSlotDTOWithDefaultData(new VehicleDTO()));
        List<ParkingSlotDTO> parkingSlotDTOList = carParkingSlotService.listAllSlots();
        Assertions.assertEquals(parkingSlotDTOList.size(), 1);
        Assertions.assertIterableEquals(parkingSlotDTOList, parkingSlotDTOSExpected);
    }

    @Test
    public void shouldThrowExceptionIfInTimeIsInFutureDate(){
        VehicleDTO vehicleDTO = new VehicleDTO("KA 03 MZ 1243");
        Assertions.assertThrows(SlotException.class, () -> {
            carParkingSlotService.allocateSlot(vehicleDTO, LocalDateTime.now().plusDays(1));
        });
    }

    @Test
    public void shouldThrowExceptionIfSlotNotAvailable(){
        VehicleDTO vehicleDTO = new VehicleDTO("KA 03 MZ 1243");
        Assertions.assertThrows(SlotException.class, () -> {
            carParkingSlotService.allocateSlot(vehicleDTO, LocalDateTime.now());
        });
    }

    @Test
    public void shouldAllocateSlotIfSlotAvailable(){
        VehicleDTO vehicleDTO = new VehicleDTO("KA 03 MZ 1243");
        vehicleDTO.setOwnerMobileNumber("912453789");
        LocalDateTime slotInTime = LocalDateTime.now();
        List<Slot> slots = new ArrayList<>();
        Slot slot =  MockDataProvider.getSlotEntityMockWithDefaultData(MockDataProvider.getVehicleWithDefaultData());
        Slot slotExpected =  MockDataProvider.getSlotEntityMockWithDefaultData(MockDataProvider.getVehicleWithDefaultData());
        slotExpected.setAllocated(true);
        slotExpected.setVehicleInTime(slotInTime);
        slotExpected.setVehicleOutTime(slotInTime.plusHours(4));
        slots.add(slot);
        ParkingSlotDTO parkingSlotDTOVerification = MockDataProvider.getParkingSlotDTOWithDefaultData(vehicleDTO);
        parkingSlotDTOVerification.setAllocated(true);
        Mockito.when(parkingSlotRepository.findByIsAllocated(false)).thenReturn(slots);
        Mockito.when(entityToDtoMapper.mapSlotEntityToDTO(slot)).thenReturn(parkingSlotDTOVerification);
        Mockito.when(dtoToEntityMapper.getVehicleByDto(vehicleDTO)).thenReturn(MockDataProvider.getVehicleWithDefaultData());
        Mockito.when(parkingSlotRepository.save(slot)).thenReturn(slot);
        ParkingSlotDTO parkingSlotDTO = carParkingSlotService.allocateSlot(vehicleDTO, slotInTime);
        Mockito.verify(parkingSlotRepository, Mockito.times(1)).save(slotExpected);
        Assertions.assertEquals(parkingSlotDTO, parkingSlotDTOVerification);
    }

    @Test
    public void shouldNotReallocateIfSlotIdIsInvalid(){
        Mockito.when(parkingSlotRepository.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(SlotException.class, () -> {
            carParkingSlotService.reAllocateSlot(1, 4);
        });
    }

    @Test
    public void shouldNotReallocateIfTimeIsMoreThanFourHr(){
        Mockito.when(parkingSlotRepository.findById(1))
                .thenReturn(Optional.of(MockDataProvider.getSlotEntityMockWithDefaultData(MockDataProvider.getVehicleWithDefaultData())));
        Assertions.assertThrows(SlotException.class, () -> {
            carParkingSlotService.reAllocateSlot(1, 5);
        });
    }

    @Test
    public void shouldNotReallocateIfTimeIsLessThanOneHr(){
        Mockito.when(parkingSlotRepository.findById(1))
                .thenReturn(Optional.of(MockDataProvider.getSlotEntityMockWithDefaultData(MockDataProvider.getVehicleWithDefaultData())));
        Assertions.assertThrows(SlotException.class, () -> {
            carParkingSlotService.reAllocateSlot(1, 0);
        });
    }

    @Test
    public void shouldReallocateSlotByOneHr(){
        VehicleDTO vehicleDTO = new VehicleDTO("KA 03 MZ 1243");
        vehicleDTO.setOwnerMobileNumber("912453789");
        Slot slot = MockDataProvider.getSlotEntityMockWithDefaultData(MockDataProvider.getVehicleWithDefaultData());
        Slot slotTOBEVerified = MockDataProvider.getSlotEntityMockWithDefaultData(MockDataProvider.getVehicleWithDefaultData());
        slotTOBEVerified.setVehicleOutTime(slot.getVehicleOutTime().plusHours(1));
        ParkingSlotDTO parkingSlotDTOVerification = MockDataProvider.getParkingSlotDTOWithDefaultData(vehicleDTO);
        Mockito.when(entityToDtoMapper.mapSlotEntityToDTO(slot)).thenReturn(parkingSlotDTOVerification);
        Mockito.when(parkingSlotRepository.findById(1))
                .thenReturn(Optional.of(slot));
        Mockito.when(parkingSlotRepository.save(slot)).thenReturn(slotTOBEVerified);
        ParkingSlotDTO parkingSlotDTO = carParkingSlotService.reAllocateSlot(1, 1);
        Mockito.verify(parkingSlotRepository, Mockito.times(1)).save(slotTOBEVerified);
        Assertions.assertEquals(parkingSlotDTO, parkingSlotDTOVerification);
    }

    @Test
    public void shouldNotDeAllocateIfSlotIdIsInvalid(){
        Mockito.when(parkingSlotRepository.findById(1)).thenReturn(Optional.empty());
        Assertions.assertThrows(SlotException.class, () -> {
            carParkingSlotService.reAllocateSlot(1, 4);
        });
    }

    @Test
    public void deAllocateIfSlot(){
        Slot slot = MockDataProvider.getSlotEntityMockWithDefaultData(MockDataProvider.getVehicleWithDefaultData());
        Slot slotTOBEVerified = MockDataProvider.getSlotEntityMockWithDefaultData(null);
        slotTOBEVerified.setVehicleInTime(null);
        slotTOBEVerified.setVehicleOutTime(null);
        slotTOBEVerified.setAllocated(false);
        Mockito.when(parkingSlotRepository.findById(1))
                .thenReturn(Optional.of(slot));
        carParkingSlotService.deAllocateSlot(1);
        Mockito.verify(parkingSlotRepository, Mockito.times(1)).save(slotTOBEVerified);
    }

    @Test
    public void createSlot(){
        carParkingSlotService.createSlot();
        Mockito.verify(parkingSlotRepository, Mockito.times(1)).save(new Slot());
    }
}
