package com.identitye2e.service;

import com.identitye2e.dto.FileDto;

import java.util.List;

public interface VehicleService {
    List<FileDto> retrieveFiles();

    List<FileDto> validateDetails();
}
