FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/BusinessBook-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Copie le script de démarrage
COPY entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh

CMD ["sh", "-c", "/app/entrypoint.sh"]