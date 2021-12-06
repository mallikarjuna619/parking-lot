package com.demo.parkinglot.controller;

import com.demo.parkinglot.domain.ParkingSlotDTO;
import com.demo.parkinglot.domain.VehicleDTO;
import com.demo.parkinglot.mock.MockDataProvider;
import com.demo.parkinglot.service.ParkingSlotService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ParkingLotControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ParkingSlotService parkingSlotService;

    @Test
    public void shouldCreateSlot() throws Exception {
        Mockito.when(parkingSlotService.createSlot())
                .thenReturn(MockDataProvider.getParkingSlotDTOWithDefaultData(null));
        String response = this.mockMvc.perform(post("/parking-lot/create"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        ParkingSlotDTO parkingSlotDTO = objectMapper.readValue(response, ParkingSlotDTO.class);
        Assertions.assertEquals(parkingSlotDTO, MockDataProvider.getParkingSlotDTOWithDefaultData(null));
    }

    @Test
    public void shouldGetAllSlots() throws Exception {
        List<ParkingSlotDTO> parkingSlotDTOList = new ArrayList<>();
        parkingSlotDTOList.add(MockDataProvider.getParkingSlotDTOWithDefaultData(null));
        Mockito.when(parkingSlotService.listAllSlots()).thenReturn(parkingSlotDTOList);
        String response = this.mockMvc.perform(get("/parking-lot/find-all-slots"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        CollectionType parkingSlotListType = objectMapper.getTypeFactory()
                .constructCollectionType(List.class, ParkingSlotDTO.class);
        ArrayList<ParkingSlotDTO> parkingSlotDTOS = objectMapper.readValue(response, parkingSlotListType);
        Assertions.assertEquals(parkingSlotDTOS.size(), 1);
        Assertions.assertEquals(parkingSlotDTOS.get(0), MockDataProvider.getParkingSlotDTOWithDefaultData(null));
    }

    @Test
    public void shouldAllocateSlot() throws Exception {
        VehicleDTO vehicleDTO = MockDataProvider.getVehicleDTO();
        Mockito.when(parkingSlotService.allocateSlot(Mockito.any(VehicleDTO.class), Mockito.any(LocalDateTime.class)))
                .thenReturn(MockDataProvider.getParkingSlotDTOWithDefaultData(vehicleDTO));
        String response = this.mockMvc.perform(post("/parking-lot/allocate")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vehicleDTO)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        ParkingSlotDTO parkingSlotDTO = objectMapper.readValue(response, ParkingSlotDTO.class);
        Assertions.assertEquals(parkingSlotDTO, MockDataProvider.getParkingSlotDTOWithDefaultData(vehicleDTO));
    }

    @Test
    public void shouldReAllocateSlot() throws Exception {
        VehicleDTO vehicleDTO = MockDataProvider.getVehicleDTO();
        Mockito.when(parkingSlotService.reAllocateSlot(1, 4))
                .thenReturn(MockDataProvider.getParkingSlotDTOWithDefaultData(vehicleDTO));
        String response = this.mockMvc.perform(put("/parking-lot/re-allocate/1/4"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        ParkingSlotDTO parkingSlotDTO = objectMapper.readValue(response, ParkingSlotDTO.class);
        Assertions.assertEquals(parkingSlotDTO, MockDataProvider.getParkingSlotDTOWithDefaultData(vehicleDTO));
    }


    @Test
    public void shouldDeAllocateSlot() throws Exception {
        this.mockMvc.perform(put("/parking-lot/de-allocate/1"))
                .andExpect(status().isOk());
        Mockito.verify(parkingSlotService, times(1)).deAllocateSlot(1);

    }

}
