@echo off
REM Test script for Docker setup on Windows
setlocal enabledelayedexpansion

echo 🐳 Testing Docker Setup for News API
echo ======================================

REM Check if Docker is running
docker info >nul 2>&1
if errorlevel 1 (
    echo ❌ Docker is not running. Please start Docker Desktop.
    exit /b 1
)

echo ✅ Docker is running

REM Check if docker-compose is available
docker-compose --version >nul 2>&1
if errorlevel 1 (
    echo ❌ docker-compose not found. Please install Docker Compose.
    exit /b 1
)

echo ✅ Docker Compose is available

REM Create .env file if it doesn't exist
if not exist .env (
    echo 📝 Creating .env file from template...
    copy .env.template .env >nul
    echo ⚠️  Please edit .env file and add your WORLDNEWS_API_KEY
    echo    You can get an API key from: https://worldnewsapi.com/
)

REM Build and start the application
echo 🏗️  Building Docker image...
docker-compose build

echo 🚀 Starting application...
docker-compose up -d

echo ⏳ Waiting for application to start...
timeout /t 30 /nobreak >nul

REM Check if container is running
docker-compose ps | findstr "Up" >nul
if errorlevel 1 (
    echo ❌ Container failed to start
    docker-compose logs
    exit /b 1
) else (
    echo ✅ Container is running
)

REM Test health endpoint
echo 🔍 Testing health endpoint...
curl -f http://localhost:8080/actuator/health >nul 2>&1
if errorlevel 1 (
    echo ⚠️  Health check failed - application might still be starting
) else (
    echo ✅ Health check passed
)

echo.
echo 🎉 Docker setup test completed!
echo.
echo 📋 Next steps:
echo    1. Add your API key to .env file
echo    2. Restart: docker-compose restart
echo    3. Test: curl "http://localhost:8080/api/news/top?source-country=us&language=en"
echo.
echo 📊 Useful commands:
echo    - View logs: docker-compose logs -f
echo    - Stop app: docker-compose down
echo    - Rebuild: docker-compose up --build
echo.
echo 🔗 Endpoints:
echo    - API: http://localhost:8080/api/news/top
echo    - Health: http://localhost:8080/actuator/health

pause