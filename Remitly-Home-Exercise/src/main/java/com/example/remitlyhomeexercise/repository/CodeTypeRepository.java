package com.example.remitlyhomeexercise.repository;

import com.example.remitlyhomeexercise.model.CodeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodeTypeRepository extends JpaRepository<CodeType, Long> {
    Optional<CodeType> getCodeTypeByCodeTypeName(String codeTypeName);
}
