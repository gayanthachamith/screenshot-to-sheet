package com.example.screenshottosheet.service;





import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;

import java.util.Base64;
import java.util.Map;

@Service
public class AiClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    public String callVisionApi(String base64Image) throws Exception {

        String url = "http://localhost:11434/api/generate";

        Map<String, Object> body = Map.of(
                "model", "llava",
                "prompt", """
                    Extract fitness data from this screenshot.
                    Return ONLY valid JSON:

                    {
                      "personName": "",
                      "date": "",
                      "activity": "",
                      "distanceKm": 0,
                      "duration": ""
                    }
                    """,
                "images", new String[]{ base64Image },
                "stream", false
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity =
                new HttpEntity<>(body, headers);

        ResponseEntity<String> response =
                restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        System.out.println("OLLAMA CONTENT >>> " + response.getBody());


        return response.getBody();
    }
}
