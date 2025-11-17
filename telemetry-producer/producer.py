from dataclasses import field
from ensurepip import bootstrap

import pandas as pd
import time
import json
from kafka import KafkaProducer

KAFKA_BOOTSTRAP_SERVERS = 'localhost:29092'
KAFKA_TOPIC = 'telemetry.raw'
CSV_FILE = 'sample_telemetry.csv'
SIMULATION_SPEED_FACTOR = 0.1

def create_producer():
    print(f"Trying to connect to Kafka at {KAFKA_BOOTSTRAP_SERVERS}")
    try:
        producer = KafkaProducer(
            bootstrap_servers=KAFKA_BOOTSTRAP_SERVERS,
            value_serializer=lambda v: json.dumps(v).encode('utf-8'),
            key_serializer=lambda k: k.encode('utf-8')
        )
        print("Kafka producer connected successfully")
        return producer
    except Exception as e:
        print(f"Error while connecting to Kafka: {e}")
        print("Verify docker containers.")
        exit(1)

def stream_telemetry(producer, topic, file_path):
    print(f"Initializing streaming of archive: {file_path}")

    try:
        df = pd.read_csv(file_path)
    except FileNotFoundError:
        print(f"Error: Archive {file_path} not found.")
        return

    for _, row in df.iterrows():
        message = row.to_dict()
        message['Timestamp'] = float(message['Timestamp'])
        message['Speed'] = int(message['Speed'])
        message['RPM'] = int(message['RPM'])
        message['Throttle'] = int(message['Throttle'])
        message['Brake'] = int(message['Brake'])

        driver_key = message.get('Driver', 'default')

        try:
            producer.send(topic, key=driver_key, value=message)
            print(f"Sent message: {message}")
            time.sleep(SIMULATION_SPEED_FACTOR)
        except Exception as e:
            print(f"Error while sending message: {e}")

    print("Streaming completed.")
    producer.flush()
    producer.close()

if __name__ == "__main__":
    kafka_producer = create_producer()
    stream_telemetry(kafka_producer, KAFKA_TOPIC, CSV_FILE)
