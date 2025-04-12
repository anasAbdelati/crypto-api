# 📡 Crypto API

This is a **side project** built to explore working with **Spring Boot**, **Spring Security**, **Keycloak**, and external integrations like **CoinGecko** and **SMTP email notifications**.

The app exposes an API that allows:

- 🔐 **User registration** (via Keycloak)
- 🪙 **Crypto coin information** (fetched from CoinGecko)
- 📬 **Coin subscriptions**:
    - Get notified **by email** when a coin reaches a **price threshold**
    - Or receive **recurring updates** every `X` seconds (user-defined)

---

## 🚀 How to Run

> You can run the app **without configuring email**, but alert subscriptions via email will not function.

### 🔐 (Optional) Configure Gmail SMTP

Edit `src/main/resources/application.yaml`:

```yaml
spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: ${EMAIL}
    password: ${SMTP_PASSWORD}
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
```
### 🐳 Launch with Docker Compose
```shell
docker-compose up --build
```
### 📚 Swagger Docs
```
http://localhost:8081/swagger-ui/index.html
```

## License

This project is licensed under the [MIT License](./LICENSE).