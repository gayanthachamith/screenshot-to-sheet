package com.example.screenshottosheet.controller;



import com.example.screenshottosheet.dto.ExtractResponse;
import com.example.screenshottosheet.dto.FitnessRecord;
import com.example.screenshottosheet.export.CsvExporter;
import com.example.screenshottosheet.service.ExtractionService;
import com.example.screenshottosheet.service.RecordStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ExtractController {

    private final ExtractionService extractionService;
    private final RecordStoreService store;
    private final CsvExporter csvExporter;

    @PostMapping(value = "/extract", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ExtractResponse extract(@RequestPart("file") MultipartFile file) {
        ExtractResponse response = extractionService.extractFromImage(file);
        store.add(response.getRecord());
        return response;
    }

    @GetMapping("/records")
    public List<FitnessRecord> getRecords() {
        return store.getAll();
    }

    @DeleteMapping("/records")
    public ResponseEntity<Void> clearRecords() {
        store.clear();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/export/csv")
    public ResponseEntity<byte[]> exportCsv() {
        byte[] csv = csvExporter.toCsvBytes(store.getAll());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=fitness_records.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csv);
    }
}
