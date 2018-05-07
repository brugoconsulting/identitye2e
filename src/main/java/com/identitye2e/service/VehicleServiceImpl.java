package com.identitye2e.service;

import com.identitye2e.dto.FileDto;
import com.identitye2e.dto.VehicleValidationDto;
import com.identitye2e.service.glue.PageSteps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Slf4j
@Service
public class VehicleServiceImpl implements VehicleService {

    @Value("${vehicle.directory}")
    protected String vehicleDirectory;

    static final Map<String, List<String>> SUPPORTED_MIME_TYPES = getSupportedMimeTypes();


    @Autowired
    PageSteps pageSteps;

    @Override
    public List<FileDto> retrieveFiles() {

        File dir = new File(vehicleDirectory);

        if (dir.exists() && dir.isDirectory()) {
            List<FileDto> fileDtos = new ArrayList<>();

            File[] files = dir.listFiles();
            Arrays.stream(files)
                    .filter(f -> isSupportedMimeType(FilenameUtils.getExtension(f.getName())))
                    .forEach(file -> {
                        String fileNameExtension = FilenameUtils.getExtension(file.getName());
                        fileDtos.add(new FileDto(file.getName(), getContentType(fileNameExtension), file.length(), fileNameExtension, new ArrayList<VehicleValidationDto>()));
                    }
            );

            return fileDtos;
        }

        return Collections.emptyList();
    }

    private String getContentType(String fileNameExtension) {
         return SUPPORTED_MIME_TYPES.keySet().stream().filter(k -> SUPPORTED_MIME_TYPES.get(k).contains(fileNameExtension)).findFirst().get();
    }

    private boolean isSupportedMimeType(String fileNameExtension) {
        return SUPPORTED_MIME_TYPES.values().stream().flatMap(l -> l.stream()).filter(e -> e.equalsIgnoreCase(fileNameExtension)).findFirst().isPresent();
    }

    @Override
    public List<FileDto> validateDetails() {
        List<FileDto> fileDtos = retrieveFiles();

        fileDtos.stream().forEach(fileDto -> validateVehicleDetails(fileDto));


        for (FileDto fileDto : fileDtos) {
            log.info("============ " + fileDto.getFileName() + " ==============");
            for (VehicleValidationDto vehicleValidationDto : fileDto.getVehicleValidationDtos()) {
                log.info(vehicleValidationDto.toString());
            }
        }

        return fileDtos;
    }

    private void validateVehicleDetails(FileDto fileDto) {
        List<String> fileData = new ArrayList<>();
        try {

            switch (fileDto.getMimeType().toUpperCase()) {
                case "CSV":
                    fileData = FileUtils.readLines(new File(vehicleDirectory + '/' + fileDto.getFileName()), "UTF-16");
                    break;
                case "EXCEL":
                    FileInputStream excelFile = new FileInputStream(new File(vehicleDirectory + '/' + fileDto.getFileName()));
                    Workbook workbook = fileDto.getExtension().endsWith("xls") ? new HSSFWorkbook(excelFile) : new XSSFWorkbook(excelFile);
                    Sheet datatypeSheet = workbook.getSheetAt(0);
                    Iterator<Row> iterator = datatypeSheet.iterator();
                    while (iterator.hasNext()) {
                        Row currentRow = iterator.next();
                        fileData.add(
                                new StringBuilder()
                                        .append(currentRow.getCell(0).getStringCellValue()).append(',')
                                        .append(currentRow.getCell(1).getStringCellValue()).append(',')
                                        .append(currentRow.getCell(2).getStringCellValue()).toString()
                        );
                    }
                    break;
                default:
                    throw new RuntimeException("Unknown MIME type " + fileDto.getMimeType());
            }

            log.info(fileDto.getFileName() + " File content(s): " + fileData.toString());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        fileData.stream().forEach(line -> checkVehicleDetails(line, fileDto));

        pageSteps.closeVehicleHome();
    }

    private void checkVehicleDetails(String line, FileDto fileDto) {
        String[] vehicleParameters = line.toUpperCase().split(",");
        log.info("treating: " + Arrays.asList(vehicleParameters).toString());

        try {
            pageSteps.openVehicleHome();
            pageSteps.clickStartNowButton();
            boolean validVehicleRegistrationNumber = pageSteps.enterCarRegistrationAndSearch(vehicleParameters[0]);
            if (validVehicleRegistrationNumber) {
                boolean validMake = pageSteps.validateVehicleMake(vehicleParameters[1]);
                boolean validColor = pageSteps.validateVehicleColor(vehicleParameters[2]);
                fileDto.getVehicleValidationDtos().add(new VehicleValidationDto(vehicleParameters[0], vehicleParameters[1], vehicleParameters[2], true, validMake, validColor));
            } else {
                fileDto.getVehicleValidationDtos().add(new VehicleValidationDto(vehicleParameters[0], vehicleParameters[1], vehicleParameters[2], false, false, false));
            }
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
        }

    }

    private static Map<String, List<String>> getSupportedMimeTypes() {

        Map<String, List<String>> map = new HashMap<>();

        map.put("excel", Arrays.asList("xls","xlsx", "xlsm"));
        map.put("csv", Arrays.asList("csv","txt"));

        return map;
    }

}
