package com.f1telemetry.queryapi.controller;

import com.f1telemetry.queryapi.model.TelemetryEntity;
import com.f1telemetry.queryapi.repository.TelemetryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/telemetry")
public class TelemetryController {

    private final TelemetryRepository repository;

    @Autowired
    public TelemetryController(TelemetryRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{driver}")
    public List<TelemetryEntity> getTelemetryByDriver(@PathVariable String driver) {
        return repository.findByDriverCodeOrderByTimeDesc(driver);
    }
}
