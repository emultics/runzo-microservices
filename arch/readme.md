# How to setup

```
docker-compose -f setup-compose.yml --env-file .env up -d
```

Kafka UI → http://localhost:9001

pgAdmin → http://localhost:9002

```
Email: admin@pgadmin.com

Pass: admin
```

Preloaded with all 3 Postgres servers

RedisInsight → http://localhost:9003

Auto-connects to runzo-redis:6379


# prune messages from kafka topic
```declarative
docker exec -it runzo-kafka kafka-topics \
  --bootstrap-server localhost:9092 \
  --delete --topic activity-events
```

# recreate topic
```declarative
docker exec -it runzo-kafka kafka-topics \
--bootstrap-server localhost:9092 \
--create --topic activity-events --partitions 3 --replication-factor 1
```