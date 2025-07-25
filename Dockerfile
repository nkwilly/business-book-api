# Utilise une image OpenJDK légère (Java 21 ici, adapte si besoin)
FROM eclipse-temurin:21-jre

# Crée un dossier de travail dans le conteneur
WORKDIR /app

# Copie le jar généré par Maven dans le conteneur
COPY target/BusinessBook-0.0.1-SNAPSHOT.jar app.jar

# Expose le port (Railway injecte la variable d'environnement $PORT)
EXPOSE 8080

# Copie le script de démarrage
COPY entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh

CMD ["sh", "-c", "/app/entrypoint.sh"]