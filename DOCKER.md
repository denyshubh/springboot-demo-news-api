# Docker Setup Guide

This guide explains how to run the News API application using Docker and Docker Compose on Linux/macOS systems.

## Prerequisites

- Docker Desktop installed and running
- World News API key (get from https://worldnewsapi.com/)

## Quick Start

1. **Clone and navigate to the project directory**
   ```bash
   cd /path/to/news-api
   ```

2. **Set up environment variables**
   ```bash
   cp .env.template .env
   # Edit .env file and add your WORLDNEWS_API_KEY
   ```

3. **Build and run with Docker Compose**
   ```bash
   docker-compose up --build
   ```

4. **Test the application**
   ```bash
   curl "http://localhost:8080/api/news/top?source-country=us&language=en"
   ```

## Commands

### Build and Run
```bash
# Build and start in foreground
docker-compose up --build

# Build and start in background
docker-compose up --build -d

# View logs
docker-compose logs -f news-api
```

### Management
```bash
# Stop the application
docker-compose down

# Stop and remove volumes
docker-compose down -v

# Rebuild without cache
docker-compose build --no-cache
```

### Health Checks
```bash
# Check application health
curl http://localhost:8080/actuator/health

# Check detailed health info
curl http://localhost:8080/actuator/health/liveness
curl http://localhost:8080/actuator/health/readiness
```

## Configuration

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `WORLDNEWS_API_KEY` | Your World News API key | `dummy` |
| `APP_WORLDNEWS_BASE_URL` | API base URL | `https://api.worldnewsapi.com` |
| `APP_WORLDNEWS_TIMEOUT_MS` | Request timeout | `10000` |
| `APP_WORLDNEWS_SKIP_SSL_VALIDATION` | Skip SSL validation | `true` |

### Ports
- **8080**: Application HTTP port

### Volumes
- `./logs:/app/logs`: Application logs (optional)

## Docker Image Details

### Multi-stage Build
1. **Builder stage**: Uses Maven to compile and package the application
2. **Runtime stage**: Uses lightweight JRE image with optimized JVM settings

### Security Features
- Runs as non-root user (`spring:spring`)
- Minimal runtime image (Alpine-based)
- Health checks enabled

### JVM Optimizations
- Container-aware JVM settings
- G1 garbage collector
- String deduplication
- Memory limits respected

## Troubleshooting

### Common Issues

1. **Port already in use**
   ```bash
   # Check what's using port 8080
   lsof -i :8080
   # Or change port in docker-compose.yml
   ```

2. **API key not working**
   - Verify your API key is correct in `.env` file
   - Check if the API key has sufficient permissions

3. **SSL certificate issues**
   - The application is configured to skip SSL validation by default
   - For production, set `APP_WORLDNEWS_SKIP_SSL_VALIDATION=false`

4. **Container won't start**
   ```bash
   # Check logs
   docker-compose logs news-api
   
   # Check container status
   docker-compose ps
   ```

### Debugging

```bash
# Execute shell in running container
docker-compose exec news-api sh

# View application logs
docker-compose logs -f news-api

# Check health status
docker-compose exec news-api curl http://localhost:8080/actuator/health
```

## Production Considerations

For production deployment:

1. **Security**
   - Use proper SSL certificates (`skip-ssl-validation: false`)
   - Secure API keys using Docker secrets or external secret management
   - Use specific image tags instead of `latest`

2. **Monitoring**
   - Enable additional actuator endpoints
   - Set up proper logging aggregation
   - Configure metrics collection

3. **Resource Limits**
   - Set memory and CPU limits in docker-compose.yml
   - Tune JVM heap size based on container limits

4. **High Availability**
   - Use multiple replicas
   - Implement proper load balancing
   - Set up health checks and restart policies