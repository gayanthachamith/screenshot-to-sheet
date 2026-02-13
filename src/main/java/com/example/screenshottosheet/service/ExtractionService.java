package com.example.screenshottosheet.service;


import com.example.screenshottosheet.dto.ExtractResponse;
import com.example.screenshottosheet.dto.FitnessRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class ExtractionService {

    private final AiClient aiClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public ExtractResponse extractFromImage(MultipartFile file) {
        try {
            String base64 = Base64.getEncoder().encodeToString(file.getBytes());

            String rawResponse = aiClient.callVisionApi(base64);

            JsonNode root = mapper.readTree(rawResponse);
            String content = root.path("response").asText(); // Ollama field

            String jsonOnly = extractJsonObject(content);

            FitnessRecord record = mapper.readValue(jsonOnly, FitnessRecord.class);

            return ExtractResponse.builder()
                    .record(record)
                    .notes("Extracted using Ollama (llava)")
                    .build();

        } catch (Exception e) {
            // Return friendly response instead of 500 crash (bootcamp demo friendly)
            return ExtractResponse.builder()
                    .record(FitnessRecord.builder()
                            .personName("UNKNOWN")
                            .date("")
                            .activity("")
                            .distanceKm(0)
                            .duration("")
                            .build())
                    .notes("AI extraction failed. Try a clearer screenshot. Error: " + e.getMessage())
                    .build();
        }
    }

    private String extractJsonObject(String text) {
        if (text == null) throw new IllegalArgumentException("AI response is empty");

        // remove markdown
        String cleaned = text.replace("```json", "")
                .replace("```", "")
                .trim();

        int start = cleaned.indexOf("{");
        int end = cleaned.lastIndexOf("}");

        if (start == -1 || end == -1 || end <= start) {
            throw new IllegalArgumentException("No JSON object found in AI response: " + cleaned);
        }

        return cleaned.substring(start, end + 1).trim();
    }
}
