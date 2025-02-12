package com.example.remitlyhomeexercise.controller;

import com.example.remitlyhomeexercise.service.SwiftService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/swift-codes")
public class SwiftController {

    SwiftService swiftService;

    public SwiftController(SwiftService swiftService) {
        this.swiftService = swiftService;
    }

    @GetMapping("/{swift-code}")
    public <T> T getBankInfoBySwiftCode(@PathVariable("swift-code") String swiftCode){
        return (T) (swiftCode.contains("XXX") ?
                swiftService.getDetailsByHQSwiftCode(swiftCode) : swiftService.getDetailsByBranchSwiftCode(swiftCode));
    }

}
