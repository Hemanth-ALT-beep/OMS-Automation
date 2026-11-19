# OMS Automation Testing Project

## 1. Setup Instructions

1. Install Java 22 and configure `JAVA_HOME`.
2. Install IntelliJ IDEA.
3. Open the project folder in IntelliJ IDEA.
4. Open the `pom.xml` file and select **Load Maven Changes** to download the required dependencies.
5. Make sure all Maven dependencies are downloaded successfully.
6. Verify that the project is using Java 22 under Project Structure in IntelliJ.

## 2. How to Run the Tests

### Run all API tests

1. Open the Maven window in IntelliJ.
2. Expand the project and select **Lifecycle**.
3. Double-click `clean` and then `test`.

Alternatively, if Maven is configured in your system, run:

```bash
mvn clean test
```

### Run individual test classes

Right-click the required test class and select **Run**.

- `OrderApiTest.java` – Validates the order API flow.
- `NegativeApiTest.java` – Validates negative scenarios such as invalid login and payment failure.
- `OrderUiTest.java` – Contains sample UI automation tests. This is not included in the default test suite.

### Test Reports

After execution, TestNG reports can be found under:

`target/surefire-reports`

## 3. Assumptions Made

- Since the actual OMS application and API details were not shared, I have assumed the API endpoints and their request and response formats.
- I have used WireMock to simulate API responses so that the tests can run without depending on a live backend.
- Sample login credentials and order details are used only for testing.
- The application URL is assumed to be 'http://localhost:8089'.
- Selenium locators are based on assumed element IDs.
- The database table and column names are assumed for the sample database validation.
- I have assumed a sample UI and database structure, so implemented in the automation and database validation code based on those assumptions.
- A basic GitHub Actions workflow is included as an example of CI/CD integration.



