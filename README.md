# 🚀 Hardened E-Commerce API Automation Framework (BDD)

A professional-grade REST API automation engine built with **Java**, **Rest-Assured**, and **Cucumber 7**. This framework is engineered for resilience, security hardening (IDOR/RBAC), and complex business logic validation.

## 📋 Table of Contents

- [✨ Features](#-features)
- [🛠 Tech Stack](#-tech-stack)
- [📁 Project Structure](#-project-structure)
- [🛡️ Security & Business Logic](#-security--business-logic)
- [🧪 Running Tests](#-running-tests)
- [📊 Reporting & Logging](#-reporting--logging)
- [👨‍💻 Author](#-author)

---

## ✨ Features

- ✅ **Behavior Driven Development (BDD)** - Pure Cucumber JVM 7 implementation.
- ✅ **Clean Architecture** - Distinguishing between "Framework" logic and "Test" implementation.
- ✅ **Resilience (Self-Healing)** - Custom `RetryHandler` with exponential backoff for flaky endpoints.
- ✅ **Security Hardening** - Proactive IDOR isolation checks and Role-Based Access Control (RBAC) validation.
- ✅ **Mathematical Validation** - Automated checks for order total accuracy (`Price * Quantity == Total`).
- ✅ **State Management** - Clean dependency injection using **PicoContainer** (`TestContext`).
- ✅ **Advanced Data Generation** - Intelligent POJO-based data providers with **JavaFaker**.
- ✅ **CI/CD Ready** - Maven-based execution with flexible tag filtering.

---

## 🛠 Tech Stack

| Technology | Purpose |
|------------|---------|
| **Java 21/17** | Core Programming Language |
| **Cucumber 7** | BDD Framework & Test Runner |
| **JUnit 5** | Underlying Test Engine |
| **Rest Assured** | DSL for REST API Testing |
| **PicoContainer** | Dependency Injection (Shared State) |
| **Log4j2 + SLF4J** | Professional Logging System |
| **Hamcrest** | Readable Assertion DSL |
| **Jackson** | JSON Serialization/Deserialization |
| **Allure Report** | Professional HTML Reporting & Visualization |
| **Owner** | Environment & Properties Management |

---

## 📁 Project Structure

```
ProjectRoot/
├── src/main/java/org/example/framework/ 
│   ├── apis/               # Fluent API Wrapper classes
│   ├── auth/               # Global Token & Session Management
│   ├── client/             # Resilience-aware Base Client (ApiClient)
│   ├── config/             # Routes (Endpoints) & Configuration
│   ├── assertions/         # Domain-Specific Assertions (ApiAssert)
│   └── utils/              # Resiliency Handlers & Logger Wrappers
├── src/test/java/org/example/
│   ├── context/            # PicoContainer TestContext (Shared State)
│   ├── stepdefinitions/    # Glue Code for Feature files
│   ├── hooks/              # Scenario Hooks (Setup/Teardown)
│   └── runners/            # Cucumber JUnit 5 Runners
├── src/test/resources/
│   ├── features/           # Gherkin Scenarios (Hardening/Security/Logic)
│   ├── Data/               # External Test Data (users.json)
│   └── log4j2.xml          # Logging Configuration
└── pom.xml                 # Maven Dependency Management
```

---

## 🛡️ Security & Business Logic

This framework goes beyond standard CRUD testing by enforcing:

### 1. IDOR Security Isolation
Explicitly verify that orders belonging to User A are **invisible** and **inaccessible** to User B.
*   *Feature*: `09_security_idor.feature`

### 2. Role-Based Access Control (RBAC)
Differentiate between `Admin` and `Customer` roles, verifying that restricted actions (like `DELETE /orders`) are blocked for non-admin users.
*   *Feature*: `11_security_rbac.feature`

### 3. Mathematical Total Accuracy
The framework fetches the price of an item at creation, calculates the expected total based on quantity, and verifies that the API's `total` field is mathematically correct.
*   *Feature*: `10_advanced_ordering.feature`

---

## 🧪 Running Tests

Execution is simplified through Maven and Cucumber Tags.

### Run Full Regression
```bash
mvn clean test
```

### Run by Specific Tags
```bash
# Run only security tests
mvn test -D"cucumber.filter.tags=@security"

# Run advanced business logic tests
mvn test -D"cucumber.filter.tags=@ordering"

# Run critical smoke suite
mvn test -D"cucumber.filter.tags=@smoke"
```

---

### 📊 Allure Reporting (Recommended)
This framework is tightly integrated with **Allure**. Every API call made through the `ApiClient` is automatically recorded with its headers, body, and response.

**To view the interactive dashboard:**
1. Run your tests: `mvn clean test`
2. Generate and open the report:
   ```bash
   mvn allure:serve
   ```

### 📄 Standard Cucumber Reports
Standard HTML summaries are also available in:
- `target/cucumber-reports/index.html`

### 📝 Console Logging
Powered by **Log4j2**, providing clean, actionable logs with colored headers for scenario boundaries. Logs include:
- `[INFO]` - Request/Response summaries.
- `[WARN]` - Retry attempts & Transient failures.
- `[ERROR]` - Assertion failures & Structural issues.

---

## 👨‍💻 Author

**Zyad mohamed**
- **GitHub**: [zyadmohamed7](https://github.com/zyadmohamed7)
- **LinkedIn**: [Zyad Mohamed](https://www.linkedin.com/in/zyad-mohamed-182b7a363/)
- **Email**: zyadmohamed3711@gmail.com
