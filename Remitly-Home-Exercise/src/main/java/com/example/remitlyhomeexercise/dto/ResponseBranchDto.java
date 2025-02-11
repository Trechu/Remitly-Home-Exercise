package com.example.remitlyhomeexercise.dto;

public record ResponseBranchDto(String address, String bankName, String countryISO2, boolean isHeadquarter, String swiftCode) {
}
