# Usar una imagen base de Java
FROM openjdk:17-jdk-alpine

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR generado al contenedor
COPY target/servidor-0.0.1-SNAPSHOT.jar servidor.jar

# Exponer el puerto en el que corre la aplicación (por ejemplo, 8080)
EXPOSE 1212

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "servidor.jar"]