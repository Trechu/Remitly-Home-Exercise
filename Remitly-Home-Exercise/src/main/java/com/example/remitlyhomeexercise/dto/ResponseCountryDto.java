package com.example.remitlyhomeexercise.dto;

import java.util.List;

public record ResponseCountryDto(String countryISO2, String countryName, List<ResponseBranchDto> swiftCodes) {
}
