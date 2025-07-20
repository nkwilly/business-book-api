#!/bin/sh

echo "Adresse IP publique du conteneur Railway (pour whitelist ScyllaDB) :"
curl -s https://ifconfig.me

echo "Attente 2 minutes pour whitelist l'adresse IP..."
sleep 120

echo "Démarrage de l'application Java"
exec java -jar app.jar --server.port=${PORT:-8080}