package com.demo.parkinglot.domain;

import lombok.Data;

@Data
public class VehicleDTO {
    private String regNumber;
    private String ownerMobileNumber;

    public VehicleDTO() {
    }

    public VehicleDTO(String regNumber){
        this.regNumber = regNumber;
    }
}
