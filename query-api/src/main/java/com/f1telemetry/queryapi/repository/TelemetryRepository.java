package com.f1telemetry.queryapi.repository;

import com.f1telemetry.queryapi.model.TelemetryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface TelemetryRepository extends JpaRepository<TelemetryEntity, Instant> {
    List<TelemetryEntity> findByDriverCodeOrderByTimeDesc(String driverCode);
}
