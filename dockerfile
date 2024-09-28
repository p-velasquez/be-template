#Compilación
FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app

#configuración de Maven
COPY pom.xml .

# Descarga dependencias
RUN mvn dependency:go-offline

# Copia el código fuente del proyecto
COPY src ./src

# Compila la aplicación
RUN mvn package -DskipTests

# Etapa 2: Ejecución
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copia el archivo JAR generado desde la fase de compilación
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto en el que corre la aplicación (ajusta si es necesario)
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
