#!/bin/bash

# Launch Kafka Connect
/etc/confluent/docker/run &

# Wait for Kafka Connect listener
echo "Waiting for Kafka Connect to start listening on localhost ⏳"
while : ; do
  sleep 2
  status_code=$(curl -s -o /dev/null -w "%{http_code}" "http://localhost:8083/connectors")
  echo "Waiting... status: $status_code"
  if [ "$status_code" -eq 200 ]; then
    break
  fi
done

# Add job
curl -s -X POST -H  "Content-Type:application/json" --data @/home/config/es-sink.json http://localhost:8083/connectors

echo "Connector setup complete."

sleep infinity