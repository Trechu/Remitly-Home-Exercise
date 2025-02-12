package com.example.remitlyhomeexercise;

import com.example.remitlyhomeexercise.dto.RequestBankDto;
import com.example.remitlyhomeexercise.dto.ResponseCountryDto;
import com.example.remitlyhomeexercise.dto.ResponseHeadquartersDto;
import com.example.remitlyhomeexercise.repository.BankRepository;
import com.example.remitlyhomeexercise.service.SwiftService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ApplicationTest {

    @Autowired
    SwiftService swiftService;

    @Autowired
    BankRepository bankRepository;

    @Test
    void applicationLogicIntegrationTest(){
        //// CREATE MOCK DATA USED IN INTEGRATION TESTING

        //// CORRECT ENTRIES

        swiftService.createNewSwiftEntry(new RequestBankDto("TEST ADDRESS SUCCESS", "TEST NAME SUCCESS", "PL", "POLAND", false, "APPLOGICB01"));
        assertTrue(bankRepository.findBankBySwift("APPLOGICB01").isPresent());
        swiftService.createNewSwiftEntry(new RequestBankDto("TEST ADDRESS SUCCESS", "TEST NAME SUCCESS", "PL", "POLAND", false, "APPLOGICB02"));
        assertTrue(bankRepository.findBankBySwift("APPLOGICB02").isPresent());
        swiftService.createNewSwiftEntry(new RequestBankDto("TEST ADDRESS SUCCESS", "TEST NAME SUCCESS", "PL", "POLAND", true, "APPLOGICXXX"));
        assertTrue(bankRepository.findBankBySwift("APPLOGICXXX").isPresent());
        swiftService.createNewSwiftEntry(new RequestBankDto("TEST ADDRESS SUCCESS", "TEST NAME SUCCESS", "PL", "POLAND", false, "APPLOGICB03"));
        assertTrue(bankRepository.findBankBySwift("APPLOGICB03").isPresent());

        //// INCORRECT ENTRIES

        // INCORRECT SWIFT LOGIC
        swiftService.createNewSwiftEntry(new RequestBankDto("TEST ADDRESS SUCCESS", "TEST NAME SUCCESS", "PL", "POLAND", true, "APPLOGICB04"));
        assertTrue(bankRepository.findBankBySwift("APPLOGICB04").isEmpty());
        // INCORRECT ISO2 FORMAT
        swiftService.createNewSwiftEntry(new RequestBankDto("TEST ADDRESS SUCCESS", "TEST NAME SUCCESS", "pl", "POLAND", true, "APPLOGICB05"));
        assertTrue(bankRepository.findBankBySwift("APPLOGICB05").isEmpty());
        // INCORRECT ISO2 FORMAT
        swiftService.createNewSwiftEntry(new RequestBankDto("TEST ADDRESS SUCCESS", "TEST NAME SUCCESS", "PL", "POLAND", false, "APPLOGICB067"));
        assertTrue(bankRepository.findBankBySwift("APPLOGICB06").isEmpty());

        //// DELETE ONE OF THE CORRECT ENTRIES AND ONE OF THE INCORRECT ONES

        swiftService.deleteSwiftEntry("APPLOGICB03");
        assertTrue(bankRepository.findBankBySwift("APPLOGICB03").isEmpty());

        assertEquals("Entry deletion failed due to SWIFT not being found", swiftService.deleteSwiftEntry("APPLOGICB04").message());

        //// CHECK IF HQ DETAILS ARE CORRECT
        ResponseHeadquartersDto responseDto = swiftService.getDetailsByHQSwiftCode("APPLOGICXXX");
        assertEquals("APPLOGICXXX", responseDto.swiftCode());
        assertEquals(2, responseDto.branches().size());
        assertTrue(responseDto.branches().contains(swiftService.bankToResponseDto(bankRepository.findBankBySwift("APPLOGICB02").get())));

        //// CHECK IF COUNTRY BANK LIST CONTAINS NEW ENTRIES
        ResponseCountryDto responseDtoCountry = swiftService.getBanksByCountryISO2Code("PL");
        assertTrue(responseDtoCountry.swiftCodes().contains(swiftService.bankToResponseDto(bankRepository.findBankBySwift("APPLOGICXXX").get())));
        assertTrue(responseDtoCountry.swiftCodes().contains(swiftService.bankToResponseDto(bankRepository.findBankBySwift("APPLOGICB02").get())));
    }
}
