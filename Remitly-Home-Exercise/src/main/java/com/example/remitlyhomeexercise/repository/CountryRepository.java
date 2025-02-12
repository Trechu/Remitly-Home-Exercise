package com.example.remitlyhomeexercise.repository;

import com.example.remitlyhomeexercise.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> getCountryByIso2(String iso2_code);
}
