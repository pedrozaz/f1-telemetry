package com.f1telemetry.telemetryprocessor.repository;

import com.f1telemetry.telemetryprocessor.model.TelemetryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface TelemetryRepository extends JpaRepository<TelemetryEntity, Instant> {
}
