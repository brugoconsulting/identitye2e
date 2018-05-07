package com.identitye2e.controller;

import com.identitye2e.dto.FileDto;
import com.identitye2e.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.identitye2e.service.glue.*;

import java.util.List;

@Controller
public class VehicleController{

    @Autowired
    VehicleService vehicleService;

    @GetMapping("/vehicleHome")
    public String vehicleHome(Model model) {
        model.addAttribute("fileDtos", vehicleService.validateDetails());
        return "vehicles";
    }


}
