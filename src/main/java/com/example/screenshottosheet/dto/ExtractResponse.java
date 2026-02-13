package com.example.screenshottosheet.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExtractResponse {
    private FitnessRecord record;
    private String notes; // for demo: "Dummy extraction (AI not connected yet)"
}