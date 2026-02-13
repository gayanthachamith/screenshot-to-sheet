package com.example.screenshottosheet.export;



import com.example.screenshottosheet.dto.FitnessRecord;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class CsvExporter {

    public byte[] toCsvBytes(List<FitnessRecord> records) {
        StringBuilder sb = new StringBuilder();
        sb.append("personName,date,activity,distanceKm,duration\n");

        for (FitnessRecord r : records) {
            sb.append(escape(r.getPersonName())).append(",")
                    .append(escape(r.getDate())).append(",")
                    .append(escape(r.getActivity())).append(",")
                    .append(r.getDistanceKm()).append(",")
                    .append(escape(r.getDuration()))
                    .append("\n");
        }

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    private String escape(String value) {
        if (value == null) return "";
        String v = value.replace("\"", "\"\"");
        // wrap with quotes if contains comma/newline
        if (v.contains(",") || v.contains("\n") || v.contains("\r")) {
            return "\"" + v + "\"";
        }
        return v;
    }
}
