# Manual de Usuario: Sistema IoT con Cifrado AES en Spring Boot

## 1. Introducción

Este manual describe el uso de un sistema basado en Spring Boot que simula dispositivos IoT enviando datos a un servidor central. El servidor cifra la información con AES y la reenvía al servicio correspondiente. Los datos cifrados se almacenan en PostgreSQL y Redis. Todo el sistema corre en contenedores Docker.

## 2. Requisitos del Sistema

- Docker y Docker Compose
- Java 17 o superior (para desarrollo local)
- Spring Boot 3.x
- PostgreSQL 14 o superior (en contenedor)
- Redis 7.x (en contenedor)
- Postman o herramienta para probar API REST

## 3. Descripción de los Componentes

### 3.1. Dispositivos IoT (Servicios Emuladores)

Cada uno de los tres servicios IoT envía información al servidor central en formato JSON.

### 3.2. Servidor Central

- Recibe datos de los servicios IoT.
- Cifra los datos con el algoritmo AES.
- Envía los datos cifrados al servicio correspondiente.
- Almacena los datos cifrados en PostgreSQL y Redis.

### 3.3. Base de Datos (PostgreSQL y Redis)

- PostgreSQL almacena registros estructurados.
- Redis permite acceso rápido a los datos cifrados.

## 4. Instalación y Configuración

### 4.1. Configuración con Docker

1. Clonar el repositorio:
   ```sh
   git clone https://github.com/usuario/proyecto-iot.git
   cd proyecto-iot
   ```
2. Configurar `.env` para variables de entorno:
   ```ini
   POSTGRES_USER=usuario
   POSTGRES_PASSWORD=contraseña
   POSTGRES_DB=iot_db
   REDIS_HOST=redis
   REDIS_PORT=6379
   AES_SECRET_KEY=claveSecretaAES
   ```
3. Ejecutar los contenedores con Docker Compose:
   ```sh
   docker-compose up -d
   ```

### 4.2. Docker Compose

El archivo `docker-compose.yml` maneja la orquestación:

```yaml
version: '3.8'
services:
  postgres:
    image: postgres:14
    container_name: postgres_iot
    restart: always
    environment:
      POSTGRES_USER: ${POSTGRES_USER}
      POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}
      POSTGRES_DB: ${POSTGRES_DB}
    ports:
      - "5432:5432"
    volumes:
      - pgdata:/var/lib/postgresql/data

  redis:
    image: redis:7
    container_name: redis_iot
    restart: always
    ports:
      - "6379:6379"
    volumes:
      - redisdata:/data

  server-central:
    build: ./server-central
    container_name: server_central
    restart: always
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/iot_db
      SPRING_DATASOURCE_USERNAME: ${POSTGRES_USER}
      SPRING_DATASOURCE_PASSWORD: ${POSTGRES_PASSWORD}
      SPRING_REDIS_HOST: redis
      SPRING_REDIS_PORT: ${REDIS_PORT}
      AES_SECRET_KEY: ${AES_SECRET_KEY}
    ports:
      - "8080:8080"
    depends_on:
      - postgres
      - redis

volumes:
  pgdata:
  redisdata:
```

### 4.3. Configuración de los Servicios IoT

Cada servicio IoT se ejecuta en un contenedor separado y envía datos al servidor central. Ejemplo de configuración en `Dockerfile` para un servicio IoT:

```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/iot-service.jar app.jar
CMD ["java", "-jar", "app.jar"]
```

Ejecutar los servicios IoT con:

```sh
   docker-compose up -d --build
```

## 5. Uso de la API

### 5.1. Envío de Datos desde un Servicio IoT

Enviar una solicitud POST a `http://localhost:8080/api/data`:

```json
{
  "deviceId": "sensor-01",
  "temperature": 25.5,
  "humidity": 60
}
```

Respuesta esperada:

```json
{
  "message": "Datos recibidos y cifrados exitosamente"
}
```

### 5.2. Consultar Datos Cifrados

Consultar en PostgreSQL:

```sh
   docker exec -it postgres_iot psql -U usuario -d iot_db -c "SELECT * FROM encrypted_data;"
```

Consultar en Redis:

```sh
   docker exec -it redis_iot redis-cli GET sensor-01
```

## 6. Mantenimiento

- Reiniciar servicios en caso de fallos: `docker-compose restart`
- Verificar logs:
  ```sh
  docker logs -f server_central
  ```
- Limpiar contenedores y volúmenes si es necesario:
  ```sh
  docker-compose down -v
  ```

## 7. Seguridad

- Utilizar claves AES seguras.
- Configurar HTTPS en el servidor central.
- Implementar autenticación en los servicios IoT.
- Limitar acceso a PostgreSQL y Redis solo a servicios internos.

## 8. Conclusión

Este sistema permite la gestión segura de datos IoT mediante cifrado AES y almacenamiento en bases de datos rápidas y estructuradas. Su implementación en Spring Boot y Docker facilita la escalabilidad y mantenimiento.

