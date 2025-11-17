package com.f1telemetry.telemetryprocessor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TelemetryMessage {

    @JsonProperty("Driver")
    private String driver;

    @JsonProperty("Timestamp")
    private double timestamp;

    @JsonProperty("Speed")
    private int speed;

    @JsonProperty("RPM")
    private int rpm;

    @JsonProperty("Throttle")
    private int throttle;

    @JsonProperty("Brake")
    private int brake;
}
