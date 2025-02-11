package com.example.remitlyhomeexercise.repository;

import com.example.remitlyhomeexercise.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
}
