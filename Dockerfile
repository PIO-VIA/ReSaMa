# Étape 1 : Builder l'application avec Maven
FROM maven:3.8.6-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests
 # Skip les tests pour aller plus vite

# Étape 2 : Créer l'image finale avec le JAR
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
# Copie le JAR généré

# Variables d'environnement (optionnel, Render les injectera)
ENV PORT=8080
EXPOSE ${PORT}

# Commande pour lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]