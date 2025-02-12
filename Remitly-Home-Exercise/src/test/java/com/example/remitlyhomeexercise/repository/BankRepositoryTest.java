package com.example.remitlyhomeexercise.repository;

import com.example.remitlyhomeexercise.model.Bank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
class BankRepositoryTest {

    @Autowired
    BankRepository bankRepository;

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