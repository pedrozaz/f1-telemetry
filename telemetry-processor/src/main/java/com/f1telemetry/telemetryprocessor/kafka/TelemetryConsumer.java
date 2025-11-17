package com.f1telemetry.telemetryprocessor.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TelemetryConsumer {

    @KafkaListener(
            topics = "telemetry.raw",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(String message) {
        log.info("Raw Telemetry Message Received: {}", message);
    }
}
