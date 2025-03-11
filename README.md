## API Definition

### Overview

This API provides a URL shortening service where users can generate tiny URLs and be redirected to the original URLs.

### Endpoints

#### 1. Create Tiny URL

- **Request:**

```http
POST /urls
Content-Type: application/json

{
  "originalUrl": "https://example.com"
}
```

- **Response:**

```http
HTTP/1.1 200 OK
Content-Type: application/json

{
  "tinyUrl": "https://short.ly/abc123"
}
```

#### 2. Redirect to Original URL

- **Request:**

```http
GET /{tinyUrl}
```

- **Response:**

```http
HTTP/1.1 301 Moved Permanently
Location: https://example.com
```

## Sequence Diagram
```mermaid
sequenceDiagram
    participant C as Client
    participant API as API Server
    participant Cache as Cache Server
    participant DB as Database
    
    %% URL Creation Scenario
    rect rgb(240, 240, 240)
    Note over C,DB: URL Creation Scenario
    C->>+API: POST /url (Send original URL)
    API->>API: Validate URL
    API->>API: Generate shortened URL hash
    API->>+DB: Store original URL and shortened URL
    DB-->>-API: Storage complete
    API->>+Cache: Store shortened URL mapping in cache
    Cache-->>-API: Cache storage complete
    API-->>-C: Return generated shortened URL
    end
    
    %% URL Redirect Scenario
    rect rgb(240, 240, 240)
    Note over C,DB: URL Redirect Scenario
    C->>+API: GET /tiny-url-hash
    API->>+Cache: Look up shortened URL
    alt Cache hit
        Cache-->>API: Return original URL
    else Cache miss
        Cache-->>API: Cache Miss
        API->>+DB: Query original URL from DB
        DB-->>-API: Return original URL
        API->>Cache: Update cache
    end
    API-->>-C: 301/302 Redirect (to original URL)
    end
```
