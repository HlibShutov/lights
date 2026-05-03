# Lights IoT System

A small IoT-style system consisting of two Java applications:

- **lights_app** – Spring Boot web application (API + PostgreSQL + MQTT)
- **lights_controller** – Java MQTT client simulating a physical light controller
- **Mosquitto** – MQTT broker (Docker)
- **PostgreSQL** – database (Docker)

The system demonstrates communication between a web backend and an IoT-style device using MQTT.
## How to run
```bash
git clone https://github.com/HlibShutov/lights.git
cd lights
docker compose up -d --build
