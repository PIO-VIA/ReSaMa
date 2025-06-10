# Utiliser une version plus récente et stable de Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Définir le répertoire de travail
WORKDIR /app

# Copier les fichiers de configuration Maven
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
COPY mvnw.cmd .

# Copier le code source
COPY src ./src

# Construire l'application (sans les tests pour accélérer le build)
RUN mvn clean package -DskipTests

# Phase de production avec une image plus légère
FROM eclipse-temurin:17-jre-jammy

# Installer curl pour les health checks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Créer un utilisateur non-root pour la sécurité
RUN groupadd -r appgroup && useradd -r -g appgroup appuser

# Définir le répertoire de travail
WORKDIR /app

# Copier le JAR depuis l'étape de build
COPY --from=build /app/target/*.jar app.jar

# Changer le propriétaire du fichier
RUN chown appuser:appgroup app.jar

# Basculer vers l'utilisateur non-root
USER appuser

# Exposer le port (utiliser une valeur fixe pour Docker)
EXPOSE 8080

# Variables d'environnement par défaut
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Point d'entrée avec optimisations JVM
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar"]