# Utilise une image OpenJDK légère (Java 21 ici, adapte si besoin)
FROM eclipse-temurin:21-jre

# Crée un dossier de travail dans le conteneur
WORKDIR /app

# Copie le jar généré par Maven dans le conteneur
COPY target/BusinessBook-0.0.1-SNAPSHOT.jar app.jar

# Expose le port (Railway injecte la variable d'environnement $PORT)
EXPOSE 8080

# Attend 2 minutes (120 secondes)
sleep 120

# Exécute ton application Java après l'attente
exec java -jar app.jar --server.port=${PORT:-8080}
