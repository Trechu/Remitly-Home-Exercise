package com.example.remitlyhomeexercise.service;

import com.example.remitlyhomeexercise.dto.ResponseBranchDto;
import com.example.remitlyhomeexercise.dto.ResponseCountryDto;
import com.example.remitlyhomeexercise.dto.ResponseHeadquartersDto;
import com.example.remitlyhomeexercise.model.Bank;
import com.example.remitlyhomeexercise.model.Country;
import com.example.remitlyhomeexercise.repository.BankRepository;
import com.example.remitlyhomeexercise.repository.CodeTypeRepository;
import com.example.remitlyhomeexercise.repository.CountryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
