package com.identitye2e.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleValidationDto {

    private String registrationNo;
    private String make;
    private String color;

    private boolean registrationNoValid;
    private boolean makeValid;
    private boolean colorValid;

}
