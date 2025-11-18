package com.f1telemetry.queryapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name = "telemetry")
public class TelemetryEntity {

    @Id
    @Column(name = "time", nullable = false)
    private Instant time;

    @Column(name = "driver_code", nullable = false)
    private String driverCode;

    @Column(name = "speed")
    private Integer speed;

    @Column(name = "rpm")
    private Integer rpm;

    @Column(name = "throttle")
    private Integer throttle;

    @Column(name = "brake")
    private Integer brake;
}
