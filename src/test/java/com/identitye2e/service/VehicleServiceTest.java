package com.identitye2e.service;

import com.identitye2e.dto.FileDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertTrue;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class VehicleServiceTest {

    @Autowired
    VehicleService classUnderTest;


    @Test
    public void retrieveFilesShouldReturnNoneEmptyList(){
        List<FileDto> fileDtos = classUnderTest.retrieveFiles();

        assertTrue(!fileDtos.isEmpty());
        assertTrue("fileDtos.size(): "+fileDtos.size(), 12 == fileDtos.size());
    }

    @Test
    public void validateDetailShouldReturnNoneEmptyList(){
        List<FileDto> fileDtos = classUnderTest.validateDetails();

        assertTrue(!fileDtos.isEmpty());
        assertTrue(12 == fileDtos.size());

        int validationCount = 0;
        for(FileDto fileDto : fileDtos){
            validationCount+=fileDto.getVehicleValidationDtos().size();
        }

        assertTrue("validationCount:"+validationCount,38 == validationCount);
    }

}
