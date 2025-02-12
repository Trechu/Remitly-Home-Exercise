package com.example.remitlyhomeexercise.controller;

import com.example.remitlyhomeexercise.dto.RequestBankDto;
import com.example.remitlyhomeexercise.dto.ResponseSwiftEntryDto;
import com.example.remitlyhomeexercise.dto.ResponseCountryDto;
import com.example.remitlyhomeexercise.exceptions.SwiftNotFoundException;
import com.example.remitlyhomeexercise.service.SwiftService;
import org.springframework.web.bind.annotation.*;
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

    // Because it was not required of me in the instruction (and for the sake of my own sanity)
    // I will skip request authorization
    @PostMapping("")
    public ResponseSwiftEntryDto createSWIFTEntry(@RequestBody RequestBankDto requestDto){
        return swiftService.createNewSwiftEntry(requestDto);
    }

    @DeleteMapping("/{swift-code}")
    public ResponseSwiftEntryDto deleteSWIFTEntry(@PathVariable("swift-code") String swiftCode){
        return swiftService.deleteSwiftEntry(swiftCode);
    }

}
