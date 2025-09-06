#!/bin/bash

# Test script for Docker setup - Linux/macOS
set -e

echo "🐳 Testing Docker Setup for News API"
echo "======================================"
echo "Platform: Linux/macOS"

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker is not running. Please start Docker Desktop."
    exit 1
fi

echo "✅ Docker is running"

# Check if docker-compose is available
if ! command -v docker-compose &> /dev/null; then
    echo "❌ docker-compose not found. Please install Docker Compose."
    exit 1
fi

echo "✅ Docker Compose is available"

# Create .env file if it doesn't exist
if [ ! -f .env ]; then
    echo "📝 Creating .env file from template..."
    cp .env.template .env
    echo "⚠️  Please edit .env file and add your WORLDNEWS_API_KEY"
    echo "   You can get an API key from: https://worldnewsapi.com/"
fi

# Build and start the application
echo "🏗️  Building Docker image..."
docker-compose build

echo "🚀 Starting application..."
docker-compose up -d

echo "⏳ Waiting for application to start..."
sleep 30

# Check if container is running
if docker-compose ps | grep -q "Up"; then
    echo "✅ Container is running"
else
    echo "❌ Container failed to start"
    docker-compose logs
    exit 1
fi

# Test health endpoint
echo "🔍 Testing health endpoint..."
if curl -f http://localhost:8080/actuator/health > /dev/null 2>&1; then
    echo "✅ Health check passed"
else
    echo "⚠️  Health check failed - application might still be starting"
fi

# Test API endpoint (will fail without valid API key)
echo "🔍 Testing API endpoint..."
response=$(curl -s -o /dev/null -w "%{http_code}" "http://localhost:8080/api/news/top?source-country=us&language=en")

if [ "$response" = "200" ]; then
    echo "✅ API endpoint working perfectly"
elif [ "$response" = "401" ]; then
    echo "⚠️  API endpoint responding but needs valid API key (got 401)"
    echo "   Please add your WORLDNEWS_API_KEY to .env file"
elif [ "$response" = "400" ]; then
    echo "⚠️  API endpoint responding but request validation failed (got 400)"
else
    echo "❌ API endpoint returned status: $response"
fi

echo ""
echo "🎉 Docker setup test completed!"
echo ""
echo "📋 Next steps:"
echo "   1. Add your API key to .env file"
echo "   2. Restart: docker-compose restart"
echo "   3. Test: curl 'http://localhost:8080/api/news/top?source-country=us&language=en'"
echo ""
echo "📊 Useful commands:"
echo "   - View logs: docker-compose logs -f"
echo "   - Stop app: docker-compose down"
echo "   - Rebuild: docker-compose up --build"
echo ""
echo "🔗 Endpoints:"
echo "   - API: http://localhost:8080/api/news/top"
echo "   - Health: http://localhost:8080/actuator/health"