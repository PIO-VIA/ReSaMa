# Utiliser une image avec Maven et JDK intégrés
FROM maven:3.9.4-eclipse-temurin-17

# Définir le répertoire de travail
WORKDIR /app

# Copier tous les fichiers du projet
COPY . .

# Nettoyer et construire l'application
RUN mvn clean package

# Trouver et renommer le JAR pour simplifier
RUN find target -name "*.jar" -exec cp {} app.jar \;

# Exposer le port
EXPOSE 8080

# Variables d'environnement
ENV SPRING_PROFILES_ACTIVE=prod

# Démarrer l'application
CMD ["java", "-jar", "app.jar"]