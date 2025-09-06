# News API (Spring Boot)

Wrapper API over WorldNews Top News. Provides `GET /api/top-news` that proxies to WorldNews and forwards quota headers.

## Build & Test

- Requires Java 21. Maven is bootstrapped locally in this workspace.
- Set `WORLDNEWS_API_KEY` env var or configure `worldnews.api-key` in `application.yaml`.

```bash
# from /workspace/news-api
../.tools/apache-maven-3.9.9/bin/mvn verify
```

Coverage is enforced at 100% via JaCoCo.

## Run

```bash
WORLDNEWS_API_KEY=YOUR_KEY ../.tools/apache-maven-3.9.9/bin/mvn spring-boot:run
```

## Endpoint

GET `/api/top-news`

Query params:
- `source-country` (required, 2-letter ISO 3166)
- `language` (required, 2-letter ISO 639-1)
- `date` (optional, yyyy-MM-dd)
- `headlines-only` (optional, default false)
- `max-news-per-cluster` (optional, integer)

Headers forwarded from upstream: `X-API-Quota-Request`, `X-API-Quota-Used`, `X-API-Quota-Left`.

Example:

```bash
curl "http://localhost:8080/api/top-news?source-country=us&language=en&date=2024-05-29" \
  -H 'Accept: application/json' \
  -H "x-api-key: $WORLDNEWS_API_KEY"
```

Note: API key can be provided via query param automatically by the service; header form is not required for our wrapper.