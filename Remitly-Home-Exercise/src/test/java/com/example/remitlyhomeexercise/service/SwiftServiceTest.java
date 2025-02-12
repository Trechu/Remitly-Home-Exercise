package com.example.remitlyhomeexercise.service;

import com.example.remitlyhomeexercise.dto.RequestBankDto;
import com.example.remitlyhomeexercise.repository.BankRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SwiftServiceTest {

    @Autowired
    SwiftService swiftService;

    @Autowired
    BankRepository bankRepository;

    @Test
    void hqDetailsExceptionThrowOnCodeNotFound(){
        assertThrows(ResponseStatusException.class, () -> swiftService.getDetailsByHQSwiftCode("CODENOTHERE"));
    }

    @Test
    void countryBankDetailsThrowOnCountryIsoNotFound(){
        assertThrows(ResponseStatusException.class, () -> swiftService.getBanksByCountryISO2Code("QQ"));
    }

    @Test
    void entryCreationThrowOnInvalidCountryInfoFormat(){
        assertEquals(
                "Entry addition failed due to incorrect countryISO2 code",
                swiftService.createNewSwiftEntry(
                        new RequestBankDto("TEST ADDRESS SUCCESS", "TEST NAME SUCCESS", "pL", "POLAND", true, "PLTESTPLXXX")
                ).message());
    }

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
        swiftService.createNewSwiftEntry(new RequestBankDto("TEST ADDRESS SUCCESS", "TEST NAME SUCCESS", "PL", "POLAND", true, "PLTEST03XXX"));
        assertTrue(bankRepository.findBankBySwift("PLTEST03XXX").isPresent());
        swiftService.deleteSwiftEntry("PLTEST03XXX");
        assertTrue(bankRepository.findBankBySwift("PLTEST03XXX").isEmpty());
    }
}