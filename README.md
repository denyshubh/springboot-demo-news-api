# News API - Spring Boot Application

A modern Spring Boot application that provides a REST API for fetching top news from the World News API. Built with Spring Framework best practices, reactive programming, and containerized deployment.

## 🚀 Features

- **RESTful API** for fetching top news by country and language
- **Reactive Programming** using Spring WebFlux for non-blocking HTTP calls
- **Input Validation** with Jakarta Bean Validation
- **Global Exception Handling** with detailed error responses
- **SSL Configuration** with configurable certificate validation
- **Health Checks** with Spring Boot Actuator
- **Docker Support** for containerized deployment
- **Comprehensive Testing** with unit and integration tests
- **Code Quality** with PMD, SpotBugs, and Jacoco coverage

## 📋 Table of Contents

- [Prerequisites](#prerequisites)
- [Quick Start](#quick-start)
- [API Documentation](#api-documentation)
- [Configuration](#configuration)
- [Development](#development)
- [Docker Deployment](#docker-deployment)
- [Testing](#testing)
- [Code Quality](#code-quality)
- [Architecture](#architecture)
- [Contributing](#contributing)

## 🛠 Prerequisites

### For Local Development
- **Java 17** or higher
- **Maven 3.9+**
- **World News API Key** (get from [worldnewsapi.com](https://worldnewsapi.com/))

### For Docker Deployment
- **Docker** and **Docker Compose**
- **World News API Key**

## 🏃‍♂️ Quick Start

### Option 1: Run with Maven (Development)

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd news-api
   ```

2. **Set your API key**
   ```bash
   export WORLDNEWS_API_KEY="your-actual-api-key"
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Test the API**
   ```bash
   curl "http://localhost:8080/api/news/top?source-country=us&language=en"
   ```

### Option 2: Run with Docker (Production-like)

1. **Set up environment**
   ```bash
   cp .env.template .env
   # Edit .env and add your WORLDNEWS_API_KEY
   ```

2. **Start with Docker Compose**
   ```bash
   docker-compose up --build
   ```

3. **Test the application**
   ```bash
   ./test-docker.sh
   ```

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api/news
```

### Endpoints

#### Get Top News
```http
GET /api/news/top
```

**Parameters:**
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `source-country` | string | Yes | Country code (e.g., "us", "gb", "ca") |
| `language` | string | Yes | Language code (e.g., "en", "es", "fr") |
| `date` | string | No | Date in YYYY-MM-DD format |
| `headlines-only` | boolean | No | Return only headlines |
| `max-news-per-cluster` | integer | No | Max news per cluster (1-50) |

**Example Request:**
```bash
curl "http://localhost:8080/api/news/top?source-country=us&language=en&max-news-per-cluster=5"
```

**Example Response:**
```json
{
  "top_news": [
    {
      "news": [
        {
          "id": 12345,
          "title": "Breaking News Title",
          "text": "Full article text...",
          "summary": "Article summary...",
          "url": "https://example.com/article",
          "image": "https://example.com/image.jpg",
          "publish_date": "2024-01-15T10:30:00Z",
          "author": "John Doe",
          "authors": ["John Doe", "Jane Smith"]
        }
      ]
    }
  ],
  "language": "en",
  "country": "us"
}
```

### Health Endpoints

```http
GET /actuator/health          # Application health
GET /actuator/health/liveness # Liveness probe
GET /actuator/health/readiness # Readiness probe
```

## ⚙️ Configuration

### Environment Variables

| Variable | Description | Default | Required |
|----------|-------------|---------|----------|
| `WORLDNEWS_API_KEY` | Your World News API key | `dummy` | Yes |
| `APP_WORLDNEWS_BASE_URL` | API base URL | `https://api.worldnewsapi.com` | No |
| `APP_WORLDNEWS_TIMEOUT_MS` | Request timeout in ms | `5000` | No |
| `APP_WORLDNEWS_SKIP_SSL_VALIDATION` | Skip SSL validation | `false` | No |
| `SPRING_PROFILES_ACTIVE` | Spring profiles | `default` | No |

### Configuration Files

- **`application.yml`** - Default configuration
- **`application-docker.yml`** - Docker-specific configuration
- **`.env.template`** - Environment variables template

### SSL Configuration

For development or testing with self-signed certificates:
```yaml
app:
  worldnews:
    skip-ssl-validation: true
```

For production (recommended):
```yaml
app:
  worldnews:
    skip-ssl-validation: false
```

## 💻 Development

### Project Structure
```
src/
├── main/java/com/example/newsapi/
│   ├── client/          # External API clients
│   ├── config/          # Configuration classes
│   ├── model/           # Data models
│   ├── web/             # REST controllers
│   └── NewsApiApplication.java
├── main/resources/
│   ├── application.yml
│   └── application-docker.yml
└── test/java/           # Test classes
```

### Building the Application

```bash
# Compile
mvn compile

# Run tests
mvn test

# Package
mvn package

# Full build with quality checks
mvn clean verify
```

### Running Tests

```bash
# Unit tests only
mvn test

# Integration tests
mvn integration-test

# With coverage report
mvn test jacoco:report
```

### Code Quality

```bash
# PMD analysis
mvn pmd:check

# SpotBugs analysis
mvn spotbugs:check

# All quality checks
mvn verify
```

## 🐳 Docker Deployment

### Quick Start
```bash
# Using the test script
./test-docker.sh

# Manual approach
cp .env.template .env
# Edit .env with your API key
docker-compose up --build
```

### Docker Commands

```bash
# Build and start
docker-compose up --build

# Start in background
docker-compose up -d

# View logs
docker-compose logs -f news-api

# Stop application
docker-compose down

# Rebuild from scratch
docker-compose build --no-cache
```

### Docker Configuration

The application uses a **multi-stage Docker build**:
- **Builder stage**: Maven compilation
- **Runtime stage**: Lightweight JRE with optimizations

**Key Features:**
- Non-root user execution
- Container-optimized JVM settings
- Health checks enabled
- Graceful shutdown support

## 🧪 Testing

### Test Categories

1. **Unit Tests**
   - Model classes validation
   - Service logic testing
   - Configuration testing

2. **Integration Tests**
   - REST API endpoints
   - External API integration
   - Spring context loading

3. **Mock Tests**
   - WireMock for external API simulation
   - MockMvc for web layer testing

### Running Specific Tests

```bash
# Run specific test class
mvn test -Dtest=TopNewsControllerTest

# Run tests with specific profile
mvn test -Dspring.profiles.active=test

# Skip tests during build
mvn package -DskipTests
```

### Test Coverage

Current coverage: **100% line and branch coverage**

```bash
# Generate coverage report
mvn test jacoco:report

# View report
open target/site/jacoco/index.html
```

## 📊 Code Quality

### Tools Used
- **PMD**: Static code analysis
- **SpotBugs**: Bug detection
- **Jacoco**: Code coverage
- **Maven Surefire**: Test reporting

### Quality Gates
- **Line Coverage**: ≥80%
- **Branch Coverage**: ≥80%
- **PMD Violations**: 0
- **SpotBugs Issues**: 0

### Quality Commands
```bash
# Run all quality checks
mvn clean verify

# Individual tools
mvn pmd:check
mvn spotbugs:check
mvn jacoco:check
```

## 🏗 Architecture

### Design Principles
- **Spring Framework**: Comprehensive enterprise framework
- **Dependency Injection**: Constructor-based injection
- **Reactive Programming**: Non-blocking I/O with WebFlux
- **Configuration Management**: Externalized properties
- **Error Handling**: Global exception handling
- **Validation**: Declarative validation with Bean Validation

### Key Components

1. **Controllers** (`web` package)
   - REST endpoints
   - Request validation
   - Response mapping

2. **Clients** (`client` package)
   - External API integration
   - HTTP client configuration
   - Error handling

3. **Configuration** (`config` package)
   - Bean definitions
   - Property binding
   - WebClient setup

4. **Models** (`model` package)
   - Data transfer objects
   - JSON mapping
   - Validation annotations

### Spring Technologies Used

- **Spring Boot**: Auto-configuration and production features
- **Spring Web MVC**: REST API framework
- **Spring WebFlux**: Reactive HTTP client
- **Spring Validation**: Input validation
- **Spring Boot Actuator**: Health checks and metrics
- **Spring Boot Test**: Testing framework

## 🚀 Deployment

### Local Development
```bash
mvn spring-boot:run
```

### Docker Production
```bash
docker-compose up --build -d
```

### Environment-Specific Configurations

- **Development**: `application.yml`
- **Docker**: `application-docker.yml`
- **Production**: Environment variables override

## 📝 API Examples

### Get US News in English
```bash
curl "http://localhost:8080/api/news/top?source-country=us&language=en"
```

### Get UK News for Specific Date
```bash
curl "http://localhost:8080/api/news/top?source-country=gb&language=en&date=2024-01-15"
```

### Get Headlines Only
```bash
curl "http://localhost:8080/api/news/top?source-country=ca&language=en&headlines-only=true"
```

### Health Check
```bash
curl "http://localhost:8080/actuator/health"
```

## 🔧 Troubleshooting

### Common Issues

1. **SSL Certificate Errors**
   ```bash
   # For development, set in application.yml:
   app:
     worldnews:
       skip-ssl-validation: true
   ```

2. **API Key Issues**
   - Verify your API key is valid
   - Check rate limits on your API plan
   - Ensure the key has required permissions

3. **Port Already in Use**
   ```bash
   # Find process using port 8080
   sudo lsof -i :8080
   # Kill the process or change port in application.yml
   ```

4. **Docker Issues**
   ```bash
   # Check Docker status
   docker info
   
   # View container logs
   docker-compose logs news-api
   
   # Restart containers
   docker-compose restart
   ```

### Debugging

```bash
# Enable debug logging
export LOGGING_LEVEL_COM_EXAMPLE_NEWSAPI=DEBUG
mvn spring-boot:run

# Or in application.yml:
logging:
  level:
    com.example.newsapi: DEBUG
```

## 🤝 Contributing

1. **Fork the repository**
2. **Create a feature branch**
   ```bash
   git checkout -b feature/amazing-feature
   ```
3. **Make your changes**
4. **Run tests and quality checks**
   ```bash
   mvn clean verify
   ```
5. **Commit your changes**
   ```bash
   git commit -m "Add amazing feature"
   ```
6. **Push to the branch**
   ```bash
   git push origin feature/amazing-feature
   ```
7. **Open a Pull Request**

### Development Guidelines

- Follow Spring Framework best practices
- Maintain 100% test coverage
- Pass all quality gates (PMD, SpotBugs)
- Use constructor injection for dependencies
- Follow RESTful API conventions
- Add appropriate logging
- Update documentation

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🔗 Links

- [World News API Documentation](https://worldnewsapi.com/docs)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring WebFlux Guide](https://spring.io/guides/gs/reactive-rest-service/)
- [Docker Documentation](https://docs.docker.com/)

## 📞 Support

For support and questions:
- Create an issue in the repository
- Check the troubleshooting section
- Review the API documentation

## 📋 Quick Reference

### Essential Commands

```bash
# Development
./setup-dev.sh                    # Setup development environment
export WORLDNEWS_API_KEY="your-key"
mvn spring-boot:run               # Start application
mvn test                          # Run tests
mvn verify                        # Full quality check

# Docker
cp .env.template .env             # Setup environment
./test-docker.sh                  # Test Docker setup
docker-compose up --build        # Start with Docker
docker-compose logs -f news-api   # View logs
docker-compose down               # Stop application

# API Testing
curl "http://localhost:8080/api/news/top?source-country=us&language=en"
curl "http://localhost:8080/actuator/health"
```

### Project Files

| File | Purpose |
|------|---------|
| `Dockerfile` | Multi-stage container build |
| `docker-compose.yml` | Container orchestration |
| `application.yml` | Default configuration |
| `application-docker.yml` | Docker-specific config |
| `.env.template` | Environment variables template |
| `setup-dev.sh` | Development environment setup |
| `test-docker.sh` | Docker testing script |
| `pom.xml` | Maven build configuration |

---

**Built with ❤️ using Spring Framework**