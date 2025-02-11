package com.example.remitlyhomeexercise.dto;

import java.util.List;

public record ResponseHeadquartersDto(String address, String bankName, String countryISO2, String countryName, boolean isHeadquarter, String swiftCode, List<ResponseBranchDto> branches) {
}
