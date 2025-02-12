package com.example.remitlyhomeexercise.configuration;

import com.example.remitlyhomeexercise.model.Bank;
import com.example.remitlyhomeexercise.model.CodeType;
import com.example.remitlyhomeexercise.model.Country;
import com.example.remitlyhomeexercise.repository.BankRepository;
import com.example.remitlyhomeexercise.repository.CodeTypeRepository;
import com.example.remitlyhomeexercise.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Configuration
public class DataParser {
    @Bean
    CommandLineRunner commandLineRunner(CountryRepository countryRepository, CodeTypeRepository codeTypeRepository, BankRepository bankRepository, @Value("${file.to.parse}") String fileName){
        return args -> {
            // This if is used because this data is supposed to be added just once as it is
            // just mock data
            if(countryRepository.count() == 0){
                List<List<String>> records = new ArrayList<>();
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                InputStream is = classLoader.getResourceAsStream(fileName);
                if(is == null){
                    throw new FileNotFoundException("The file file the name: " + fileName + " was not found");
                }
                InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(isr);
                for (String line; (line = reader.readLine()) != null;){
                    String[] values = line.split("\t");
                    records.add(Arrays.asList(values));
                }

                for (List<String> entry : records){
                    Optional<Country> country = countryRepository.getCountryByIso2(entry.get(0));
                    if (country.isEmpty()){
                        Country new_country = new Country(entry.get(0), entry.get(6), entry.get(7));
                        countryRepository.save(new_country);
                        country = Optional.of(new_country);
                    }
                    Optional<CodeType> codeType = codeTypeRepository.getCodeTypeByCodeTypeName(entry.get(2));
                    if (codeType.isEmpty()){
                        CodeType new_code_type = new CodeType(entry.get(2));
                        codeTypeRepository.save(new_code_type);
                        codeType = Optional.of(new_code_type);
                    }
                    Bank new_bank = new Bank(country.get(), entry.get(1), codeType.get(), entry.get(3), entry.get(4), entry.get(5), entry.get(1).contains("XXX"));
                    bankRepository.save(new_bank);
                }
            }
        };
    }
}
