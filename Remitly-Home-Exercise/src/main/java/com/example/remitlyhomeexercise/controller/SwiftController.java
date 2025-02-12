package com.example.remitlyhomeexercise.controller;

import com.example.remitlyhomeexercise.dto.ResponseCountryDto;
import com.example.remitlyhomeexercise.exceptions.SwiftNotFoundException;
import com.example.remitlyhomeexercise.service.SwiftService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("v1/swift-codes")
public class SwiftController {

    SwiftService swiftService;

    public SwiftController(SwiftService swiftService) {
        this.swiftService = swiftService;
    }

    @GetMapping("/{swift-code}")
    public Object getBankInfoBySwiftCode(@PathVariable("swift-code") String swiftCode){
        try {
            return (swiftCode.contains("XXX") ?
                    swiftService.getDetailsByHQSwiftCode(swiftCode) : swiftService.getDetailsByBranchSwiftCode(swiftCode));
        } catch (ResponseStatusException e){
            throw new SwiftNotFoundException("404", "A bank with the given SWIFT code was not found");
        }
    }

    @GetMapping("/country/{countryISO2code}")
    public ResponseCountryDto getBankInfoByCountryISO2Code(@PathVariable("countryISO2code") String iso){
        return swiftService.getBanksByCountryISO2Code(iso);
    }

}
