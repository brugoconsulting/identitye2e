package com.identitye2e.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {

    private String fileName;
    private String mimeType;
    private Long size;
    private String extension;
    private List<VehicleValidationDto> vehicleValidationDtos = new ArrayList<>();

}
