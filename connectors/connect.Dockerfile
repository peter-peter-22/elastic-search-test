FROM confluentinc/cp-kafka-connect-base:8.1.0
RUN confluent-hub install --no-prompt confluentinc/kafka-connect-elasticsearch:15.0.0