package com.example.screenshottosheet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class FitnessRecord {
        private String personName;   // e.g., "John"
        private String date;         // e.g., "2026-02-13"
        private String activity;     // e.g., "Run" / "Walk"
        private double distanceKm;   // e.g., 5.2
        private String duration;     // e.g., "00:32:15"
    }

