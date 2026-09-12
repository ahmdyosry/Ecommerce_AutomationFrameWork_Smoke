# E-Commerce UI Automation Framework — Smoke Suite

[![Selenium Tests](https://github.com/ahmdyosry/Ecommerce_AutomationFrameWork_Smoke/actions/workflows/test.yml/badge.svg)](https://github.com/ahmdyosry/Ecommerce_AutomationFrameWork_Smoke/actions/workflows/test.yml)
![Java](https://img.shields.io/badge/Java-25-orange?logo=openjdk)
![Selenium](https://img.shields.io/badge/Selenium-4.48.0-43B02A?logo=selenium)
![TestNG](https://img.shields.io/badge/TestNG-7.12.0-red)
![Maven](https://img.shields.io/badge/Maven-Project-C71A36?logo=apachemaven)

A scalable **UI test automation framework** for the [Automation Exercise](https://automationexercise.com) e-commerce application, automating smoke & E2E scenarios.

The project is built with **Java, Selenium WebDriver, TestNG, Maven, Page Object Model (POM), data-driven testing, Extent Reports, Log4j2, retry handling, screenshots, parallel execution, and GitHub Actions CI**.

The current repository focuses on a **Smoke Test Suite** covering the application's most important customer journeys.

---

## Features

- Page Object Model design
- Reusable `BasePage` for common page-object functionality
- Reusable `BaseTest` for test setup, teardown, and WebDriver management
- Reusable header and footer components
- Separating page logic, reusable components, test logic, configuration, and test data
- Writing TestNG smoke and end-to-end tests
- Cross-browser WebDriver setup
- Chrome, Firefox, and Edge support
- Supporting headed and headless browser execution
- Thread-safe WebDriver using `ThreadLocal`
- Parallel TestNG execution
- Smoke test grouping
- JSON-based data-driven test data
- Environment-variable-based credentials
- TestNG listeners
- Automatic screenshots for failed tests
- Generating ExtentReports HTML reports
- Capturing severe browser-console JavaScript errors during failures
- Logging execution information with Log4j2
- Retrying selected failed tests with a TestNG retry analyzer
- Executing smoke tests through Maven profiles
- Running automated tests in GitHub Actions CI
- CI artifact upload for reports, Surefire results, and logs.

---

## Application Under Test

**Automation Exercise**

https://automationexercise.com

Default configuration:

```properties
baseUrl=https://automationexercise.com
browser=chrome
headless=false
implicitWait=10
explicitWait=10
```

> Note: `smoke.xml` currently passes `chrome` as the browser parameter, so the smoke suite runs on Chrome unless that TestNG parameter is changed.

---

## Tech Stack

| Technology                | Usage |
|---------------------------|---|
| Java 25                   | Programming language |
| Selenium WebDriver 4.48.0 | Browser automation |
| TestNG 7.12.0             | Test execution and assertions |
| Maven                     | Build and dependency management |
| WebDriverManager 6.3.4    | Browser driver management |
| Extent Reports 5.1.2      | HTML test reporting |
| Log4j2                    | Logging |
| Jackson Databind          | JSON test-data handling |
| GitHub Actions            | Continuous Integration |

---

## Project Structure

```text
Ecommerce_AutomationFrameWork_Smoke/
│
├── .github/
│   └── workflows/
│       └── test.yml
│
│
├── reports/
│
│
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── base/
│   │       │   └── BasePage.java
│   │       │
│   │       ├── components/
│   │       │   ├── FooterComponent.java
│   │       │   └── HeaderComponent.java
│   │       │
│   │       ├── constants/
│   │       │   └── Routes.java
│   │       │
│   │       ├── pages/
│   │       │   ├── CartPage.java
│   │       │   ├── CategoryPage.java
│   │       │   ├── CheckoutPage.java
│   │       │   ├── HomePage.java
│   │       │   ├── LoginPage.java
│   │       │   ├── OrderConfirmationPage.java
│   │       │   ├── PaymentPage.java
│   │       │   ├── ProductListPage.java
│   │       │   ├── ProductDetailsPage.java
│   │       │   ├── RegistrationPage.java
│   │       │   ├── SearchResultsPage.java
│   │       │   └── SuccessRegisterPage.java
│   │       │
│   │       └── utils/
│   │           ├── ConfigReader.java
│   │           ├── Credentials.java
│   │           ├── JsonDataReader.java
│   │           └── ScreenshotUtils.java
│   │
│   └── test/
│       ├── java/
│       │   ├── base/
│       │   │   └── BaseTest.java
│       │   │
│       │   ├── dataproviders/
│       │   │   └── TestDataProvider.java
│       │   │
│       │   ├── listeners/
│       │   │   └── TestListener.java
│       │   │
│       │   ├── reporting/
│       │   │   └── ExtentReportManager.java
│       │   │
│       │   ├── retry/
│       │   │   └── RetryAnalyzer.java
│       │   │
│       │   └── tests/
│       │       ├── auth/
│       │       │   ├── LoginTests.java
│       │       │   ├── LogoutTests.java
│       │       │   └── RegistrationTests.java
│       │       │
│       │       ├── cart/
│       │       │   ├── AddToCartTests.java
│       │       │   └── RemoveFromCartTests.java
│       │       │
│       │       ├── checkout/
│       │       │   ├── CheckoutTests.java
│       │       │   ├── ShippingTests.java
│       │       │   └── PaymentTests.java
│       │       │
│       │       ├── e2e/
│       │       │   └── PurchaseFlowE2eTest.java
│       │       │
│       │       ├── home/
│       │       │   └── HomepageTests.java
│       │       │
│       │       └── products/
│       │           ├── ProductListingTests.java
│       │           ├── ProductDetailsTests.java
│       │           └── ProductSearchTests.java
│       │
│       └── resources/
│           ├── testdata/
│           │   ├── checkout.json
│           │   ├── invalidLogins.json
│           │   ├── paymentDetails.json
│           │   ├── validRegistration.json
│           │   ├── products.json
│           │   └── searchData.json
│           │
│           ├── config.properties
│           └── log4j2.xml
│
├── pom.xml
└── smoke.xml
```

---

## Smoke Test Coverage

The project contains smoke coverage across the main e-commerce workflows.

### Authentication

- User registration
- Login
- Logout

Test classes:

```text
LoginTests.java
LogoutTests.java
RegistrationTests.java
```

### Home Page

- Critical home-page functionality

Test class:

```text
HomepageTests.java
```

### Products

- Product listing
- Product details
- Product search

Test classes:

```text
ProductListingTests.java
ProductDetailsTests.java
ProductSearchTests.java
```

### Cart

- Add product to cart
- Verify product quantity in cart
- Verify cart total price
- Remove product from cart

Test classes:

```text
AddToCartTests.java
CartTests.java
```

### Checkout

- Checkout flow
- Shipping
- Payment

Test classes:

```text
CheckoutTests.java
ShippingTests.java
PaymentTests.java
```

### End-to-End Purchase Flow

- e-commerce purchase flow from login to order completion

Test class:

```text
PurchaseFlowE2eTest.java
```

---

## Prerequisites

Install the following before running the project locally:

- **JDK 25**
- **Maven**
- A supported browser:
  - Google Chrome
  - Mozilla Firefox
  - Microsoft Edge
- Git

WebDriver binaries do not need to be configured manually because the framework uses **WebDriverManager**.

Verify the installation:

```bash
java -version
mvn -version
git --version
```

---

## Clone the Repository

```bash
git clone https://github.com/ahmdyosry/Ecommerce_AutomationFrameWork_Smoke.git
cd Ecommerce_AutomationFrameWork_Smoke
```

---

## Credentials

Some tests require an existing Automation Exercise account.

The framework reads credentials from environment variables rather than storing them directly in the source code:

```text
TEST_EMAIL
TEST_PASSWORD
```

### Windows PowerShell

```powershell
$env:TEST_EMAIL="your-test-email@example.com"
$env:TEST_PASSWORD="your-test-password"
```

### Windows Command Prompt

```cmd
set TEST_EMAIL=your-test-email@example.com
set TEST_PASSWORD=your-test-password
```

### macOS / Linux

```bash
export TEST_EMAIL="your-test-email@example.com"
export TEST_PASSWORD="your-test-password"
```

Do not commit real credentials to the repository.

---

## Running the Smoke Suite

Run the smoke Maven profile:

```bash
mvn clean test -Psmoke
```

The smoke profile executes:

```text
smoke.xml
```

The suite includes tests tagged with:

```java
groups = "smoke" & groups = "smokeLoggedIn"
```

---

## Run in Headless Mode

```bash
mvn clean test -Psmoke -Dheadless=true
```

This is the same execution mode used by the GitHub Actions workflow.

When headless mode is enabled, the browser window is configured to:

```text
1920 x 1080
```

---

## Browser Execution

`BaseTest` supports:

```text
chrome
firefox
edge
```

The browser selection priority is:

```text
TestNG XML browser parameter
        ↓
-DcliBrowser system property
        ↓
config.properties
```

The current `smoke.xml` contains:

```xml
<parameter name="browser" value="chrome"/>
```

Therefore, the smoke suite currently executes with **Chrome**.

To run the smoke suite using Firefox or Edge, update the browser parameter in `smoke.xml`.

Example:

```xml
<parameter name="browser" value="firefox"/>
```

or:

```xml
<parameter name="browser" value="edge"/>
```

---

## Parallel Execution

The smoke suite is configured for parallel execution with TestNG.

```xml
<suite name="smokesuite" parallel="tests" thread-count="4">
```

The "smoke tests" test block also enables method-level parallelism:

```xml
<test name="smoke tests" parallel="methods" thread-count="2">
```

The framework protects each test thread with its own WebDriver instance using:

```java
ThreadLocal<WebDriver>
```

This prevents browser instances from being shared between parallel tests.

---

## Test Data

Test data is stored separately from the test logic under:

```text
src/test/resources/testdata/
```

Examples include:

```text
checkout.json
invalidLogins.json
paymentDetails.json
validRegistration.json
products.json
searchData.json
```

JSON files are read through the framework's data utilities and TestNG data providers.

This keeps test data separate from test implementation and makes scenarios easier to maintain.

---

## Configuration

Framework configuration is stored in:

```text
src/test/resources/config.properties
```

Current properties:

```properties
baseUrl=https://automationexercise.com
browser=chrome
headless=false
implicitWait=10
explicitWait=10
```

The `headless` value can also be overridden from Maven:

```bash
-Dheadless=true
```

---

## Reporting

The framework uses **Extent Reports** for execution reporting.

Generated reports are stored under:

```text
reports/
```

After a GitHub Actions execution, the report is uploaded as the artifact:

```text
extent-report
```

---

## Screenshots

Screenshot utilities are included in the framework to capture browser state during execution and failure handling.

Screenshots are stored under:

```text
reports/screenshots/
```

---

## Logging

The project uses **Log4j2**.

Configuration:

```text
src/test/resources/log4j2.xml
```

Log output is stored under:

```text
logs/
```

In GitHub Actions, logs are uploaded as:

```text
automation-logs
```

---

## Retry Mechanism

Failed-test retry support is implemented through:

```text
retry/RetryAnalyzer.java
```

This allows selected failed tests to be re-executed according to the configured retry policy.

A retry mechanism should be used carefully: it can reduce noise from temporary browser or network instability, but it should not be used to hide consistently failing or flaky tests.

---

## GitHub Actions CI

CI configuration:

```text
.github/workflows/test.yml
```

The workflow is triggered by:

- Pushes to the `main` branch
- Manual execution using `workflow_dispatch`

The CI pipeline:

1. Checks out the repository
2. Sets up **Temurin JDK 25**
3. Uses Maven dependency caching
4. Reads `TEST_EMAIL` and `TEST_PASSWORD` from GitHub Secrets
5. Executes the smoke suite in headless mode
6. Uploads Extent Reports
7. Uploads Maven Surefire reports
8. Uploads Log4j2 logs

CI test command:

```bash
mvn clean test -Psmoke -Dheadless=true -DtrimStackTrace=false -e
```

### Required GitHub Secrets

Add these repository secrets:

```text
TEST_EMAIL
TEST_PASSWORD
```

GitHub path:

```text
Repository
→ Settings
→ Secrets and variables
→ Actions
→ New repository secret
```

---

## CI Artifacts

The workflow uploads the following artifacts even if tests fail:

| Artifact | Path |
|---|---|
| `extent-report` | `reports/` |
| `surefire-reports` | `target/surefire-reports/` |
| `automation-logs` | `logs/` |

This makes test failures easier to investigate directly from a GitHub Actions run.

---

## Framework Design

The framework separates responsibilities into several layers:

```text
Tests
  ↓
Page Objects
  ↓
Reusable Components
  ↓
Base Page / Selenium Utilities
  ↓
WebDriver
```

Supporting layers handle:

```text
Configuration
Test Data
Credentials
Listeners
Screenshots
Reporting
Logging
Retry Logic
```

This structure improves maintainability and reduces duplicated Selenium code as the test suite grows.

---

## Example Test Execution Flow

A typical e-commerce test follows this pattern:

```text
Launch browser
      ↓
Open Automation Exercise
      ↓
Navigate using Page Objects
      ↓
Perform user actions
      ↓
Validate expected behavior
      ↓
Listener records test result
      ↓
Capture evidence when required
      ↓
Update Extent Report
      ↓
Close browser
```

---

## Maven Profiles

The `pom.xml` define this execution profile:

```text
smoke
```

The smoke profile points to:

```text
smoke.xml
```

Run it with:

```bash
mvn clean test -Psmoke
```

---

## Key Automation Concepts Applied

- Selenium WebDriver
- TestNG annotations and assertions
- Page Object Model (POM)
- Reusable page components
- Base test and base page abstractions
- Explicit waits
- Browser configuration
- Headless execution
- Cross-browser driver setup
- WebDriverManager
- ThreadLocal WebDriver
- Selenium ThreadGuard
- Parallel test execution
- TestNG groups
- TestNG DataProviders
- JSON-based data-driven testing
- Environment variables
- GitHub Secrets
- Test listeners
- ExtentReports
- Failure screenshots
- Browser-console error capture
- Log4j2
- Retry analyzer
- Maven profiles
- GitHub Actions CI
- CI artifact publishing
- End-to-end test automation

---

## Author

**Ahmed Yosry**

GitHub: [@ahmdyosry](https://github.com/ahmdyosry)

---

## Repository

https://github.com/ahmdyosry/Ecommerce_AutomationFrameWork_Smoke
