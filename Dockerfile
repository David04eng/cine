FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos TODO el contenido del repositorio al contenedor
COPY . .

# Buscamos el pom.xml ya sea en la raiz o en la subcarpeta y compilamos
RUN if [ -f "pom.xml" ]; then \
      mvn clean package -DskipTests; \
    else \
      mvn clean package -DskipTests -f sistema_cine/pom.xml; \
    fi

FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# Buscamos el .jar en cualquier carpeta target que se haya creado
COPY --from=build /app/**/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-Xmx300m", "-jar", "app.jar"]