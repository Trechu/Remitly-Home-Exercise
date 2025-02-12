package com.example.remitlyhomeexercise.service;

import com.example.remitlyhomeexercise.dto.*;
import com.example.remitlyhomeexercise.model.Bank;
import com.example.remitlyhomeexercise.model.Country;
import com.example.remitlyhomeexercise.repository.BankRepository;
import com.example.remitlyhomeexercise.repository.CodeTypeRepository;
import com.example.remitlyhomeexercise.repository.CountryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class SwiftService {
    private final BankRepository bankRepository;
    private final CodeTypeRepository codeTypeRepository;
    private final CountryRepository countryRepository;

    public SwiftService(BankRepository bankRepository, CodeTypeRepository codeTypeRepository, CountryRepository countryRepository) {
        this.bankRepository = bankRepository;
        this.codeTypeRepository = codeTypeRepository;
        this.countryRepository = countryRepository;
    }

    public ResponseHeadquartersDto getDetailsByHQSwiftCode(String swiftCode){
        Bank hq = bankRepository.findBankBySwift(swiftCode).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "A bank with this SWIFT code was not found")
        );
        List<Bank> banks = bankRepository.getBanksFromHeadquartersSwift(swiftCode.substring(0,8));
        List<ResponseBranchDto> branches = banks.stream().filter(bank -> !bank.getSwift().contains("XXX")).map(this::bankToResponseDto).toList();
        return bankToResponseDto(hq, branches);
    }

    public ResponseBranchDto getDetailsByBranchSwiftCode(String swiftCode){
        Bank bank = bankRepository.findBankBySwift(swiftCode).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "A bank with this SWIFT code was not found")
        );
        return bankToResponseDto(bank);
    }

    public ResponseCountryDto getBanksByCountryISO2Code(String iso2){
        Country country = countryRepository.getCountryByIso2(iso2).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "A country with this ISO2 code was not found")
        );
        List<Bank> banks = bankRepository.getAllBanksByCountryISO2Code(country.getIso2());
        List<ResponseBranchDto> swiftCodes = banks.stream().map(this::bankToResponseDto).toList();
        return countryToResponseCountryDto(country, swiftCodes);
    }

    public ResponseBankCreationDto createNewSwiftEntry(RequestBankDto requestDto){
        // In this method, I assume that the data for the country already exists, as I lack the TIME_ZONE
        // info in the request, so I cannot add a new country entry into the database
        Optional<Country> country = countryRepository.getCountryByIso2(requestDto.countryISO2());
        if(country.isEmpty()) return new ResponseBankCreationDto("Entry addition failed due to incorrect countryISO2 code");
        if(requestDto.swiftCode().contains("XXX")){
                if(!requestDto.isHeadquarter()) return new ResponseBankCreationDto("Entry addition failed due to a discrepancy in SWIFT code logic");
        } else {
            if(requestDto.isHeadquarter()) return new ResponseBankCreationDto("Entry addition failed due to a discrepancy in SWIFT code logic");
        }
        // I also lack the info of CODE_TYPE and TOWN_NAME, so I will just set it as NOT DETERMINED
        try {
            Bank new_bank = new Bank(country.get(), requestDto.swiftCode(), null, requestDto.bankName(), requestDto.address(), "ND", requestDto.isHeadquarter());
            bankRepository.save(new_bank);
            return new ResponseBankCreationDto("Entry addition successful");
        } catch (Exception e){
            return new ResponseBankCreationDto("Entry addition failed. Error message: " + e.getMessage());
        }

    }

    private ResponseCountryDto countryToResponseCountryDto(Country country, List<ResponseBranchDto> swiftCodes){
        return new ResponseCountryDto(country.getIso2(), country.getName(), swiftCodes);
    }

    private ResponseBranchDto bankToResponseDto(Bank bank){
        return new ResponseBranchDto(bank.getAddress(), bank.getName(), bank.getCountry().getIso2(), bank.isHeadquarters(), bank.getSwift());
    }

    private ResponseHeadquartersDto bankToResponseDto(Bank bank, List<ResponseBranchDto> branches){
        return new ResponseHeadquartersDto(bank.getAddress(), bank.getName(), bank.getCountry().getIso2(), bank.getCountry().getName(), bank.isHeadquarters(), bank.getSwift(), branches);
    }
}
