package com.example.remitlyhomeexercise.dto;

public record RequestBankDto(String address, String bankName, String countryISO2, String countryName, boolean isHeadquarter, String swiftCode) {
}
