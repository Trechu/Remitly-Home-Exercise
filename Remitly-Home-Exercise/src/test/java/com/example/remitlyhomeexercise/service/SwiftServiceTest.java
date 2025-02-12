package com.example.remitlyhomeexercise.service;

import com.example.remitlyhomeexercise.dto.RequestBankDto;
import com.example.remitlyhomeexercise.repository.BankRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SwiftServiceTest {

    @Autowired
    SwiftService swiftService;

    @Autowired
    BankRepository bankRepository;

    @Test
    void createNewSwiftEntry() {
        swiftService.createNewSwiftEntry(new RequestBankDto("TEST ADDRESS SUCCESS", "TEST NAME SUCCESS", "PL", "POLAND", true, "PLTESTPLXXX"));
        swiftService.createNewSwiftEntry(new RequestBankDto("TEST ADDRESS FAILURE 1", "TEST NAME FAILURE 1", "RS", "ROSHAR", false, "ROSHARBRAND"));
        swiftService.createNewSwiftEntry(new RequestBankDto("TEST ADDRESS FAILURE 2", "TEST NAME FAILURE 2", "PL", "POLAND", false, "SCADRIALXXX"));
        assertTrue(bankRepository.findBankBySwift("PLTESTPLXXX").isPresent());
        assertTrue(bankRepository.findBankBySwift("ROSHARBRAND").isEmpty());
        assertTrue(bankRepository.findBankBySwift("SCADRIALXXX").isEmpty());
    }

    @Test
    void deleteSwiftEntry(){
        swiftService.createNewSwiftEntry(new RequestBankDto("TEST ADDRESS SUCCESS", "TEST NAME SUCCESS", "PL", "POLAND", true, "PLTESTPLXXX"));
        assertTrue(bankRepository.findBankBySwift("PLTESTPLXXX").isPresent());
        swiftService.deleteSwiftEntry("PLTESTPLXXX");
        assertTrue(bankRepository.findBankBySwift("PLTESTPLXXX").isEmpty());
    }
}