package com.example.remitlyhomeexercise.repository;

import com.example.remitlyhomeexercise.dto.RequestBankDto;
import com.example.remitlyhomeexercise.model.Bank;
import com.example.remitlyhomeexercise.service.SwiftService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
class BankRepositoryTest {

    @Autowired
    BankRepository bankRepository;

    @Autowired
    SwiftService swiftService;

    @Test
    void findBankBySWIFTCode(){
        swiftService.createNewSwiftEntry(new RequestBankDto("TEST ADDRESS SUCCESS", "TEST NAME SUCCESS", "PL", "POLAND", true, "PLTEST02XXX"));
        assertTrue("The method did not find the SWIFT code despite it being in the DB", bankRepository.findBankBySwift("PLTEST02XXX").isPresent());
    }

    @Test
    void findAllBanksByHQCode(){
        List<Bank> queryResult = bankRepository.getBanksFromHeadquartersSwift("BCHICLRM");
        assertTrue("The method found an incorrect amount of entries", queryResult.size() == 5);
        assertTrue("The method found banks with incorrect SWIFT codes", (queryResult.stream().filter(bank -> bank.getSwift().contains("BCHICLRM")).toList()).containsAll(queryResult));
    }

    @Test
    void findAllBanksByCountryISO2Code(){
        List<Bank> queryResult = bankRepository.getAllBanksByCountryISO2Code("UY");
        assertTrue("The method found a bank whose country ISO2 code did not match the one provided in the query", queryResult.stream().allMatch(bank -> bank.getCountry().getIso2().equals("UY")));
    }

}