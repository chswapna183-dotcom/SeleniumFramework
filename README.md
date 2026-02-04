# Selenium Test Automation Framework (Java + TestNG)

Selenium 4 test automation framework using:

- Java + Maven
- TestNG
- Page Object Model (POM)
- WebDriverManager

## Prerequisites

- JDK 17+
- Maven 3.9+
- Chrome/Firefox/Edge installed

## Structure

- `src/test/java/com/swapnachada/base/BaseTest.java` – WebDriver setup/teardown
- `src/main/java/com/swapnachada/pages/LoginPage.java` – sample page object
- `src/test/java/com/swapnachada/tests/LoginTest.java` – sample test
- `src/test/resources/pages/login.html` – local HTML page used by the test
- `testng.xml` – TestNG suite + parameters

## Run tests

```bash
mvn test
```

### Change browser/headless

Edit `testng.xml`:

```xml
<parameter name="browser" value="chrome"/>
<parameter name="headless" value="false"/>
```

You can also override from the command line:

```bash
mvn test -Dbrowser=chrome -Dheadless=true
```

Supported browsers: `chrome`, `firefox`, `edge`.

## CI

GitHub Actions workflow: `.github/workflows/ci.yml` (runs `mvn test` headless).

