package com.example.screenshottosheet.service;

import com.example.screenshottosheet.dto.FitnessRecord;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RecordStoreService {
    private final List<FitnessRecord> records = new ArrayList<>();

    public void add(FitnessRecord record) {
        records.add(record);
    }

    public List<FitnessRecord> getAll() {
        return Collections.unmodifiableList(records);
    }

    public void clear() {
        records.clear();
    }
}