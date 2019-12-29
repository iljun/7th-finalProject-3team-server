package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SampleApi {

    @GetMapping("/sample")
    public ResponseEntity getSample() {
        Map<String, Object> sampleMap = new HashMap<>();
        sampleMap.put("title", "test");
        sampleMap.put("content", "test");
        return ResponseEntity.ok(sampleMap);
    }
}

