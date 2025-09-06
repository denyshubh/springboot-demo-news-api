## News API (Spring Boot)

A lightweight Spring Boot wrapper over the WorldNews Top News endpoint.

- Source API: `https://api.worldnewsapi.com/top-news`
- Local endpoint: `GET /api/news/top`
- Build tool: Maven
- Java: 17+
- Quality gates: 100% code coverage (JaCoCo), PMD and SpotBugs clean

### Quick Start

1) Prerequisites
- Java 17+
- Maven 3.9+

2) Build the project
```bash
mvn clean compile
```

3) Configure API key

Export your WorldNews API key (get one from their docs/console):
```bash
export WORLDNEWS_API_KEY=YOUR_KEY
```

4) Run the app
```bash
mvn spring-boot:run
```

App starts on `http://localhost:8080`.

### API Usage

Endpoint: `GET /api/news/top`

Query parameters:
- `source-country` (required): ISO 3166 country code (e.g., `us`)
- `language` (required): ISO 639-1 language code (e.g., `en`)
- `date` (optional): `YYYY-MM-DD` (defaults to today)
- `headlines-only` (optional): `true|false`
- `max-news-per-cluster` (optional): integer 1-50

Example:
```bash
curl "http://localhost:8080/api/news/top?source-country=us&language=en&date=2024-05-29" \
  -H "Accept: application/json"
```

The service forwards the request to WorldNews API and returns a structured response mirroring their payload.

### Configuration

`src/main/resources/application.yml`:
```yaml
app:
  worldnews:
    base-url: https://api.worldnewsapi.com
    api-key: ${WORLDNEWS_API_KEY:dummy}
    timeout-ms: 5000
```

Override via environment variables or JVM system properties.

### Testing & Quality

- Unit and MVC tests cover controller, models, exception handling.
- WireMock simulates WorldNews API responses.
- JaCoCo enforces 100% line and branch coverage at verify phase.
- PMD and SpotBugs run at `verify` and must pass.

Run full suite:
```bash
mvn -DskipTests=false verify
```
Artifacts:
- Coverage report: `target/site/jacoco/index.html`
- PMD report: `target/pmd.xml`
- SpotBugs report: `target/spotbugsXml.xml`

### Project Structure

- `com.example.newsapi.NewsApiApplication` – Spring Boot entry point
- `config.WorldNewsProperties` – typed configuration
- `config.WebClientConfig` – `WebClient` bean for WorldNews
- `client.WorldNewsClient` – calls Top News endpoint
- `web.TopNewsController` – `/api/news/top` endpoint with validation
- `web.GlobalExceptionHandler` – maps downstream and validation errors
- `model.*` – DTOs: `TopNewsResponse`, `TopNewsCluster`, `WorldNewsItem`

### SonarQube Notes

This project is structured to pass strict quality gates:
- 100% coverage enforced by JaCoCo
- No PMD violations
- No SpotBugs bugs (intentional framework references are annotated)

If you integrate SonarQube, import Maven reports or let Sonar run coverage using the generated JaCoCo XML.

### License

MIT
