package com.group2.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.group2.inventory.model.TestEntity;
import com.group2.inventory.repository.TestEntityRepository;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private TestEntityRepository testEntityRepository;

    @GetMapping("/connection")
    public String testConnection() {
        try {
            long count = testEntityRepository.count();
            return "✅ Database connection successful! Records in test_entities table: " + count;
        } catch (Exception e) {
            return "❌ Database connection failed: " + e.getMessage();
        }
    }

    @PostMapping("/create")
    public TestEntity createTest(@RequestParam String message) {
        TestEntity entity = new TestEntity(message);
        return testEntityRepository.save(entity);
    }

    @GetMapping("/all")
    public List<TestEntity> getAllTests() {
        return testEntityRepository.findAll();
    }
}

