# Jackpot Service

A minimal Spring Boot service that:
- Publishes bet events to a topic `jackpot-bets`.
- Consumes those events and contributes to jackpots (fixed or variable strategies).
- Exposes an endpoint for reward and resets the pool if win.
- Uses **H2 in-memory DB**.

## Run

```bash
docker-compose up --build
```
Or another option to run from intellij idea
 - Go to application.yaml -> uncomment property bootstrap-servers: localhost:9092 and comment property #bootstrap-servers: kafka-1:29092.
 - Run kafka-1 from docker-compose.yml
 - Run mvn clean install
 - Run TestApplication.java
## API

### 1) Publish bet (mock Kafka producer -> topic `jackpot-bets`)
```bash
curl -X POST http://localhost:8080/bet   -H "Content-Type: application/json"   -d '{"betId":"1","userId":"1","jackpotId":"1","betAmount":5000}'
```

### 2) Evaluate reward for a bet
```bash
curl -X POST http://localhost:8080/reward   -H "Content-Type: application/json"   -d '{"betId":"1","userId":"1","jackpotId":"1"}'
```

Response:
if not win
```json
404
```
or if win
```json
{
  "jackpotId": 1,
  "rewardAmount": 1200.0,
  "userId": 1
}
```
