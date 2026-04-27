package Coding.hw11;
// Given the following Kafka consumer code, modify it to implement "at-least-once" semantics
// with manual offset commit:
// // Original code with auto-commit (may lose messages)
// @KafkaListener(topics = "events")
// public void consume(Event event) {
//  eventProcessor.process(event);
// }
// TODO: Rewrite to use manual commit
// Hint:
// 1. Disable auto-commit in configuration
// 2. Use Acknowledgment parameter
// 3. Only acknowledge AFTER successful processing

import org.w3c.dom.events.Event;
import org.springframework.kafka.annotation.KafkaListener;
public class KafkaConsumerCode {
    @KafkaListener(topics = "events")
    public void consume(Event event, Acknowledgment ack) {
        try {
            eventProcessor.process(event);
            ack.acknowledge();
        } catch (Exception e) {
            // do not acknowledge, message will be retried
        }
    }
}
