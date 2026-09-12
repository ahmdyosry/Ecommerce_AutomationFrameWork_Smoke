# E-Commerce UI Automation Framework — Smoke Test Suite

A hands-on web UI automation project built to demonstrate practical test automation framework design for an e-commerce application.

The framework automates critical user journeys on **Automation Exercise** using **Java, Selenium WebDriver, TestNG, Maven, Page Object Model (POM), data-driven testing, parallel execution, reporting, logging, and GitHub Actions CI**.

> **Project type:** Portfolio / hands-on automation project  
> **Application under test:** Automation Exercise  
> **Primary scope:** Smoke testing of critical e-commerce workflows

---

## What This Project Demonstrates

This repository demonstrates practical experience with:

- Building a maintainable Selenium automation framework in Java
- Applying the Page Object Model design pattern --
- Separating page logic, reusable components, test logic, configuration, and test data
- Creating reusable WebDriver and page-level helper methods
- Writing TestNG smoke and end-to-end tests
- Implementing JSON-driven test data with TestNG `@DataProvider`
- Running data providers and test methods in parallel
- Managing thread-safe browser instances with `ThreadLocal<WebDriver>`
- Supporting Chrome, Firefox, and Edge WebDriver initialization
- Supporting headed and headless browser execution
- Managing browser drivers with WebDriverManager
- Protecting credentials with environment variables and GitHub Secrets
- Generating ExtentReports HTML reports
- Capturing screenshots automatically on test failure
- Capturing severe browser-console JavaScript errors during failures
- Logging execution information with Log4j2
- Retrying selected failed tests with a TestNG retry analyzer
- Executing smoke tests through Maven profiles
- Running automated tests in GitHub Actions CI
- Uploading reports, Surefire results, and logs as CI artifacts

---

## Application Under Test

**Automation Exercise** is used as the public demo e-commerce application for this project.

Default framework configuration:

```properties
baseUrl=https://automationexercise.com
environment=qa
browser=chrome
headless=false
implicitWait=10
explicitWait=10
```

---

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 25 | Programming language |
| Selenium WebDriver 4.48.0 | Browser automation |
| TestNG 7.12.0 | Test execution, assertions, groups, listeners, data providers, and parallel execution |
| Maven | Build, dependency management, and test execution profiles |
| WebDriverManager 6.3.4 | Browser-driver management |
| Jackson Databind 2.22.1 | JSON test-data deserialization |
| ExtentReports 5.1.2 | HTML test reporting |
| Log4j2 2.26.1 | Framework and failure logging |
| Apache Commons IO | File operations for JSON data and screenshots |
| GitHub Actions | Continuous Integration |

---

## Framework Architecture

The framework separates responsibilities so that Selenium interaction code is not mixed directly into test scenarios.

```text
Tests
  |
  v
Page Objects
  |
  +--> Reusable Header / Footer Components
  |
  v
BasePage + Common Selenium Helpers
  |
  v
WebDriver

Supporting framework layers:
Configuration | Test Data | Credentials | Listeners | Reporting | Logging | Screenshots | Retry Logic
```

### Design approach

- **Tests** describe business scenarios and assertions.
- **Page Objects** contain locators and page-specific actions.
- **Components** model reusable UI areas such as the header and footer.
- **BasePage** centralizes reusable waits, navigation, element interaction, scrolling, and utility behavior.
- **BaseTest** handles browser creation, configuration, test setup/teardown, and thread-safe WebDriver access.
- **Utilities** handle configuration, credentials, JSON test data, and screenshots.
- **Listeners / Reporting** collect execution results and failure evidence.

---

## Project Structure

```text
Ecommerce_AutomationFrameWork_Smoke/
|
|-- .github/
|   `-- workflows/
|       `-- test.yml
|
|-- reports/
|
|-- src/
|   |-- main/
|   |   `-- java/
|   |       |-- base/
|   |       |   `-- BasePage.java
|   |       |-- components/
|   |       |   |-- HeaderComponent.java
|   |       |   `-- FooterComponent.java
|   |       |-- constants/
|   |       |   `-- Routes.java
|   |       |-- pages/
|   |       |   |-- CartPage.java
|   |       |   |-- CategoryPage.java
|   |       |   |-- CheckoutPage.java
|   |       |   |-- HomePage.java
|   |       |   |-- LoginPage.java
|   |       |   |-- OrderConfirmationPage.java
|   |       |   |-- PaymentPage.java
|   |       |   |-- ProductDetailsPage.java
|   |       |   |-- ProductListPage.java
|   |       |   |-- RegisterationPage.java
|   |       |   |-- SearchResultsPage.java
|   |       |   `-- SuccessRegisterPage.java
|   |       `-- utils/
|   |           |-- ConfigReader.java
|   |           |-- Credentials.java
|   |           |-- JsonDataReader.java
|   |           `-- ScreenshotUtils.java
|   |
|   `-- test/
|       |-- java/
|       |   |-- base/
|       |   |   `-- BaseTest.java
|       |   |-- dataproviders/
|       |   |   `-- TestDataProvider.java
|       |   |-- listeners/
|       |   |   `-- TestListener.java
|       |   |-- reporting/
|       |   |   `-- ExtentReportManager.java
|       |   |-- retry/
|       |   |   `-- RetryAnalyzer.java
|       |   `-- tests/
|       |       |-- auth/
|       |       |   |-- LoginTests.java
|       |       |   |-- LogoutTests.java
|       |       |   `-- RegisterationTests.java
|       |       |-- cart/
|       |       |   |-- AddToCartTests.java
|       |       |   `-- RemoveFromCartTests.java
|       |       |-- checkout/
|       |       |   |-- CheckoutTests.java
|       |       |   |-- PaymentTests.java
|       |       |   `-- ShippingTests.java
|       |       |-- e2e/
|       |       |   `-- PurchaseFlowE2eTest.java
|       |       |-- home/
|       |       |   `-- HomepageTests.java
|       |       `-- products/
|       |           |-- ProductDetailsTests.java
|       |           |-- ProductListingTests.java
|       |           `-- ProductSearchTests.java
|       |
|       `-- resources/
|           |-- adsblocker/
|           |-- testdata/
|           |   |-- ValidRegisteration.json
|           |   |-- checkout.json
|           |   |-- invalidLogins.json
|           |   |-- paymentDetails.json
|           |   |-- products.json
|           |   `-- searchData.json
|           |-- config.properties
|           `-- log4j2.xml
|
|-- pom.xml
`-- smoke.xml
```

The automated tests are currently organized into **13 test classes** across authentication, home, products, cart, checkout, and end-to-end purchase flows.

---

## Automated Smoke Coverage

### Authentication

- Valid login
- Invalid login validation using multiple JSON-driven datasets
- Logout
- User registration using external test data

### Home Page

- Critical home-page behavior and navigation

### Products

- Product listing
- Product details
- Product search
- Product selection from external test data

### Cart

- Add product to cart
- Verify the correct product is added
- Remove / clear cart items

### Checkout

- Navigate from cart to checkout
- Shipping-related smoke scenarios
- Payment-related smoke scenarios

### End-to-End Purchase Flow

`PurchaseFlowE2eTest` validates a critical customer journey through multiple application layers:

```text
Login
  -> Open products
  -> Add product to cart
  -> Validate cart content
  -> Open checkout
  -> Validate checkout
  -> Place order
  -> Submit payment details
  -> Validate successful order placement
```

---

## Page Object Model and Reusable Components

The framework follows Page Object Model principles to keep tests readable and maintainable.

Reusable application areas are modeled separately through:

- `HeaderComponent`
- `FooterComponent`

`BasePage` provides common helpers such as:

- Explicit waits for visibility, clickability, invisibility, staleness, URL changes, and element counts
- Common click and text operations
- Navigation helpers
- JavaScript scrolling helpers
- Price conversion utilities

This reduces duplicated Selenium code across page classes.

---

## WebDriver Management and Cross-Browser Support

`BaseTest` supports browser initialization for:

- Google Chrome
- Mozilla Firefox
- Microsoft Edge

Driver binaries are configured automatically using WebDriverManager.

Browser selection is resolved in this order:

```text
TestNG XML browser parameter
        ->
-DcliBrowser system property
        ->
config.properties browser value
```

The current `smoke.xml` explicitly passes:

```xml
<parameter name="browser" value="chrome"/>
```

Therefore, the smoke suite currently executes on **Chrome** unless that TestNG parameter is changed.

### Headless execution

Headless execution can be enabled using:

```bash
mvn clean test -Psmoke -Dheadless=true
```

The GitHub Actions workflow uses headless execution. When running headlessly, the browser window is set to `1920 x 1080`.

---

## Thread-Safe Parallel Execution

The framework is designed to support parallel TestNG execution.

`BaseTest` stores each browser instance in:

```java
ThreadLocal<WebDriver>
```

and protects driver instances with Selenium `ThreadGuard`.

The smoke suite enables parallel execution at suite and method levels. JSON-backed TestNG data providers are also configured with `parallel = true`.

This design helps prevent WebDriver instances from being shared incorrectly between concurrent test threads.

---

## Data-Driven Testing

Test data is stored independently from test implementation under:

```text
src/test/resources/testdata/
```

Current JSON datasets include:

```text
ValidRegisteration.json
checkout.json
invalidLogins.json
paymentDetails.json
products.json
searchData.json
```

`JsonDataReader` uses Jackson Databind to deserialize JSON data, and `TestDataProvider` exposes it to tests through TestNG `@DataProvider` methods.

Current data providers cover:

- Invalid login scenarios
- Registration data
- Product data
- Checkout data
- Payment data
- Search data

The data providers are configured for parallel execution.

---

## Configuration Management

Framework settings are stored in:

```text
src/test/resources/config.properties
```

Current configuration:

```properties
baseUrl=https://automationexercise.com
environment=qa
browser=chrome
headless=false
implicitWait=10
explicitWait=10
```

The headless value can be overridden from the command line with:

```bash
-Dheadless=true
```

---

## Secure Credential Handling

Login credentials are **not hard-coded in the Java source code**.

`Credentials.java` reads the following environment variables:

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

## Running the Project Locally

### Prerequisites

Install:

- JDK 25
- Maven
- Git
- Chrome, Firefox, or Edge

Verify the tools:

```bash
java -version
mvn -version
git --version
```

### Clone

```bash
git clone https://github.com/ahmdyosry/Ecommerce_AutomationFrameWork_Smoke.git
cd Ecommerce_AutomationFrameWork_Smoke
```

Set the required `TEST_EMAIL` and `TEST_PASSWORD` environment variables before executing tests that require an existing user.

### Run the smoke suite

```bash
mvn clean test -Psmoke
```

### Run the smoke suite headlessly

```bash
mvn clean test -Psmoke -Dheadless=true
```

The `smoke` Maven profile executes `smoke.xml`, which includes tests assigned to the TestNG group:

```java
groups = "smoke"
```

---

## Reporting

The framework uses **ExtentReports** to generate HTML execution reports.

Report output:

```text
reports/index.html
```

A TestNG listener creates an Extent test entry for each executed test and records pass, fail, and skip status.

---

## Failure Diagnostics

When a test fails, the framework attempts to collect additional evidence automatically.

### Failure screenshots

Screenshots are generated under:

```text
reports/screenshots/
```

and attached to the Extent report.

### Browser console errors

For supported browser executions, the listener checks browser logs for `SEVERE` JavaScript errors and writes them to the framework log and Extent report.

This gives additional diagnostic information beyond the TestNG assertion failure alone.

---

## Logging

The project uses **Log4j2** for execution and failure logging.

Configuration:

```text
src/test/resources/log4j2.xml
```

Log output is generated under:

```text
logs/
```

---

## Retry Handling

A custom `RetryAnalyzer` implements TestNG `IRetryAnalyzer` and allows selected failed tests to be retried once.

The end-to-end purchase flow currently applies the retry analyzer explicitly.

Retrying is intentionally limited: a retry mechanism should help diagnose temporary instability, not hide consistently failing tests.

---

## GitHub Actions CI

Continuous Integration is configured in:

```text
.github/workflows/test.yml
```

The workflow runs on:

- Pushes to the `main` branch
- Manual execution through `workflow_dispatch`

The CI job:

1. Checks out the repository
2. Sets up Temurin JDK 25
3. Enables Maven dependency caching
4. Loads `TEST_EMAIL` and `TEST_PASSWORD` from GitHub Secrets
5. Runs the smoke suite in headless mode
6. Uploads Extent report files
7. Uploads Maven Surefire reports
8. Uploads Log4j2 logs

CI command:

```bash
mvn clean test -Psmoke -Dheadless=true -DtrimStackTrace=false -e
```

### Required GitHub Secrets

Create these repository secrets before running credential-dependent tests in CI:

```text
TEST_EMAIL
TEST_PASSWORD
```

---

## CI Artifacts

The workflow uploads diagnostic artifacts with `if: always()`, so they are collected even when tests fail.

| Artifact | Source Path |
|---|---|
| `extent-report` | `reports/` |
| `surefire-reports` | `target/surefire-reports/` |
| `automation-logs` | `logs/` |

This allows a failed CI execution to be investigated without reproducing it locally first.

---

## Maven Profiles

The `pom.xml` currently defines Maven profiles named:

```text
smoke
regression
testng
```

The **smoke** profile is the currently runnable suite included in the repository and points to:

```text
smoke.xml
```

The `regression` and `testng` profiles reference suite XML files that are not currently committed to this smoke-focused repository. They should be treated as extension points until those suite files are added.

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

## Current Scope and Future Enhancements

This repository intentionally focuses on a smoke suite. Potential next steps include:

- Add dedicated regression and full-suite TestNG XML files
- Add a GitHub Actions browser matrix for Chrome, Firefox, and Edge
- Add environment selection through a command-line parameter
- Add Selenium Grid or Docker-based distributed execution
- Add API-layer checks for selected end-to-end scenarios
- Add code-quality checks to CI
- Add historical flaky-test / execution-trend reporting
- Add a lightweight visual sample of the Extent report to the README

---

## Author

**Ahmed Yosry**  
Junior Software Tester | Manual Testing + Test Automation

GitHub repository: https://github.com/ahmdyosry/Ecommerce_AutomationFrameWork_Smoke

---

## Note

This is a portfolio automation project created to demonstrate practical test automation skills against a public demo application. It is not affiliated with or an official test suite of Automation Exercise.
