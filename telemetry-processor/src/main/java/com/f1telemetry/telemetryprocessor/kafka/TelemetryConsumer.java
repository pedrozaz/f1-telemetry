package com.f1telemetry.telemetryprocessor.kafka;

import com.f1telemetry.telemetryprocessor.dto.TelemetryMessage;
import com.f1telemetry.telemetryprocessor.model.TelemetryEntity;
import com.f1telemetry.telemetryprocessor.repository.TelemetryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
public class TelemetryConsumer {

    private final TelemetryRepository telemetryRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public TelemetryConsumer(TelemetryRepository telemetryRepository, ObjectMapper objectMapper) {
        this.telemetryRepository = telemetryRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
            topics = "telemetry.raw",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(String message) {
        try {
            TelemetryMessage dto = objectMapper.readValue(message, TelemetryMessage.class);
            TelemetryEntity entity = new TelemetryEntity();

            long seconds = (long) dto.getTimestamp();
            long nanoseconds = (long) ((dto.getTimestamp() - seconds) * 1_000_000_000);
            entity.setTime(Instant.ofEpochSecond(seconds, nanoseconds));

            entity.setDriverCode(dto.getDriver());
            entity.setSpeed(dto.getSpeed());
            entity.setRpm(dto.getRpm());
            entity.setThrottle(dto.getThrottle());
            entity.setBrake(dto.getBrake());

            telemetryRepository.save(entity);

            log.info("Saved telemetry for driver {}: Speed={}", entity.getDriverCode(), entity.getSpeed());
        } catch (Exception e) {
            log.error("Failed to process message: {}", message, e);
        }
    }
}
