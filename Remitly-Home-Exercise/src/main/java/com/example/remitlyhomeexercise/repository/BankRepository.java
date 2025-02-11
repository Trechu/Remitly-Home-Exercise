package com.example.remitlyhomeexercise.repository;

import com.example.remitlyhomeexercise.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, Long> {
    @Query("SELECT b FROM Bank b WHERE b.swift LIKE :swift%")
    List<Bank> getBanksFromHeadquartersSwift(@Param("swift") String hqSwift);

    Optional<Bank> findBankBySwift(String swiftCode);
}
