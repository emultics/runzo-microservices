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