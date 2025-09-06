#!/bin/bash

# Development setup script for News API
set -e

echo "🛠️  Setting up News API Development Environment"
echo "==============================================="

# Check Java version
if command -v java &> /dev/null; then
    JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
    if [ "$JAVA_VERSION" -ge 17 ]; then
        echo "✅ Java $JAVA_VERSION detected"
    else
        echo "❌ Java 17+ required. Current version: $JAVA_VERSION"
        exit 1
    fi
else
    echo "❌ Java not found. Please install Java 17+"
    exit 1
fi

# Check Maven
if command -v mvn &> /dev/null; then
    echo "✅ Maven detected"
else
    echo "❌ Maven not found. Please install Maven 3.9+"
    exit 1
fi

# Check API key
if [ -z "$WORLDNEWS_API_KEY" ]; then
    echo "⚠️  WORLDNEWS_API_KEY environment variable not set"
    echo "   Please export WORLDNEWS_API_KEY='your-api-key'"
    echo "   Get your API key from: https://worldnewsapi.com/"
else
    echo "✅ API key configured"
fi

# Install dependencies
echo "📦 Installing dependencies..."
mvn dependency:resolve

# Compile application
echo "🏗️  Compiling application..."
mvn compile

# Run tests
echo "🧪 Running tests..."
mvn test

echo ""
echo "🎉 Development environment setup complete!"
echo ""
echo "🚀 To start the application:"
echo "   mvn spring-boot:run"
echo ""
echo "🔗 Once started, test with:"
echo "   curl 'http://localhost:8080/api/news/top?source-country=us&language=en'"
echo ""
echo "📊 Additional commands:"
echo "   - Run tests: mvn test"
echo "   - Check quality: mvn verify"
echo "   - View coverage: open target/site/jacoco/index.html"
echo "   - API docs: http://localhost:8080/actuator/health"