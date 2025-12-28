#!/bin/bash

# Script to import category data into MySQL
# This script waits for MySQL to be ready and then imports the data

set -e

echo "Waiting for MySQL to be ready..."
until docker exec muji-mysql mysqladmin ping -h localhost -uroot -ppassword --silent; do
  echo "MySQL is unavailable - sleeping"
  sleep 2
done

echo "MySQL is ready! Importing category data..."

# Check if data already exists
EXISTING_COUNT=$(docker exec -i muji-mysql mysql -uroot -ppassword jobhunter -sN -e "SELECT COUNT(*) FROM maincategories;" 2>/dev/null || echo "0")

if [ "$EXISTING_COUNT" -eq "0" ]; then
  echo "No existing data found. Importing categories..."
  docker exec -i muji-mysql mysql -uroot -ppassword jobhunter < /docker-entrypoint-initdb.d/01-init-categories.sql
  echo "Category data imported successfully!"
else
  echo "Data already exists ($EXISTING_COUNT main categories). Skipping import."
fi

