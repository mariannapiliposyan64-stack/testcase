# Atelier Studio – Selenium POM Test Suite

Automated end-to-end tests for the **Atelier Studio** storefront (`http://192.168.10.35:5050/`), written in **Java 17** with **Selenium WebDriver 4**, **TestNG**, and a clean **Page Object Model** structure.

---

## 📁 Project Structure

```
testcase/
├── pom.xml
├── page.html                          ← Target storefront (served locally)
└── src/
    ├── main/java/
    │   ├── pages/
    │   │   ├── NavBarPage.java         ← Navigation bar + Explore hover menu + Reset
    │   │   ├── ConfiguratorPage.java   ← Section 1: Studio Pro configurator
    │   │   ├── AccessoriesPage.java    ← Section 2: Accessories catalog & web table
    │   │   ├── ExpressPayPage.java     ← Section 3: Actions API (drag, dbl-click, right-click)
    │   │   └── CheckoutPage.java       ← Section 4: Checkout fields + timer + downloads
    │   └── utils/
    │       ├── DriverFactory.java      ← ThreadLocal WebDriver lifecycle
    │       └── Constants.java          ← Base URL, timeouts, section anchors
    └── test/java/
        └── tests/
            ├── BaseTest.java           ← @BeforeMethod / @AfterMethod lifecycle
            ├── ConfiguratorTest.java   ← 14 test cases (TC-C01 → TC-C12, TC-NAV)
            ├── AccessoriesTest.java    ← 7 test cases  (TC-A01 → TC-A07)
            ├── ExpressPayTest.java     ← 5 test cases  (TC-E01 → TC-E05)
            └── CheckoutTest.java       ← 10 test cases (TC-CH01 → TC-CH10)
        resources/
            └── testng.xml              ← Suite definition
```

---

## ⚙️ Prerequisites

| Tool | Version |
|------|---------|
| Java JDK | 17+ |
| Apache Maven | 3.8+ |
| Google Chrome | Latest stable |
| Application under test | Running at `http://192.168.10.35:5050/` |

> **ChromeDriver** is managed automatically via [WebDriverManager](https://github.com/bonigarcia/webdrivermanager) – no manual download needed.

---

## 🚀 Running the Tests

### Run the full suite
```bash
mvn test
```

### Run a specific test class
```bash
mvn test -Dtest=ConfiguratorTest
mvn test -Dtest=AccessoriesTest
mvn test -Dtest=ExpressPayTest
mvn test -Dtest=CheckoutTest
```

### Run a single test method
```bash
mvn test -Dtest=ConfiguratorTest#testSelectMemory64GB
```

### Headless mode
In `DriverFactory.java`, uncomment the headless line:
```java
options.addArguments("--headless=new");
```

---

## 🧪 Test Cases Overview

### Section 1 – Studio Pro Configurator (`ConfiguratorTest`)

| ID | Description |
|----|-------------|
| TC-C01 | Select **Space Black / Starlight** finish → badge text updates |
| TC-C02 | Select **32 GB** memory → price updates to **$1,999** |
| TC-C03 | Select **64 GB** memory → price updates to **$2,399** |
| TC-C04 | **"Select All"** apps master checkbox selects all 3 pro apps |
| TC-C05 | Partial pro-app selection shows indeterminate master + correct count |
| TC-C06 | Exactly **2** order-update checkboxes satisfy the validation |
| TC-C07 | Accepting license terms clears the error note |
| TC-C08 | Default engraving text → error; valid text (4–24 chars) → OK + SVG preview |
| TC-C09 | Store pickup dropdown **disabled** until a region is selected (dependent dropdown) |
| TC-C10 | **Hover** over Trade-In trigger reveals the details panel (`moveToElement`) |
| TC-C11 | Trade-In scan shows overlay → waits for it to disappear (`invisibilityOfElement`) → modal |
| TC-C12 | Add Studio Pro to Bag increments bag counter |
| TC-NAV | Explore **hover** dropdown → select category → nav notice updates |
| TC-NAV-RESET | Reset storefront → modal shows "Storefront Reset" → bag resets to 0 |

### Section 2 – Accessories Catalog (`AccessoriesTest`)

| ID | Description |
|----|-------------|
| TC-A01 | Catalog has ≥ 5 rows on page load (web table) |
| TC-A02 | Filter by "Keyboard" shows only matching rows |
| TC-A03 | Filter by non-existent term shows 0 rows |
| TC-A04 | "Studio Keyboard Pro" – price **$199**, status **In Stock** |
| TC-A05 | Add accessory to Bag → bag status banner updates |
| TC-A06 | Remove a row → row count decreases by 1 |
| TC-A07 | Add custom accessory → new row appended, modal confirms |

### Section 3 – Express Pay (`ExpressPayTest`)

| ID | Description |
|----|-------------|
| TC-E01 | **Drag & Drop** bundle to express tray → gesture status updates |
| TC-E02 | **Double-click** Pay button → button text shows "Authorized" |
| TC-E03 | **Right-click** saved build → context menu appears |
| TC-E04 | Select "Load Build" from context menu → gesture status updates |
| TC-E05 | Enter quick serial + Enter → gesture status updates |

### Section 4 – Checkout (`CheckoutTest`)

| ID | Description |
|----|-------------|
| TC-CH01 | Valid recipient name → `is-ok` CSS class |
| TC-CH02 | "Walk-in Guest" default → `is-err` + error note |
| TC-CH03 | Email: valid passes, invalid fails |
| TC-CH04 | Phone: 10 digits passes, 9 digits fails |
| TC-CH05 | Order serial: 8 uppercase alphanumeric passes, lowercase fails |
| TC-CH06 | Buyer age: 25 passes, 17 (underage) fails |
| TC-CH07 | Quantity: 5 passes, 0 fails |
| TC-CH08 | Trade-in weight: 2.5 kg passes, 0.1 kg fails |
| TC-CH09 | Place Order button **disabled** before timer starts |
| TC-CH10 | Download Receipt link → receipt download status text updates |

---

## 🏗️ Architecture

```mermaid
flowchart TD
    BT[BaseTest\n@Before/@After] --> CT[ConfiguratorTest]
    BT --> AT[AccessoriesTest]
    BT --> ET[ExpressPayTest]
    BT --> CHT[CheckoutTest]

    CT --> NP[NavBarPage]
    CT --> CP[ConfiguratorPage]
    AT --> AP[AccessoriesPage]
    ET --> EP[ExpressPayPage]
    CHT --> CHP[CheckoutPage]

    CP & AP & EP & CHP & NP --> DF[DriverFactory\nThreadLocal WebDriver]
    DF --> Chrome[ChromeDriver\nauto-managed by WebDriverManager]
```

---

## 🔧 Configuration

All environment settings are centralised in [`Constants.java`](src/main/java/utils/Constants.java):

```java
public static final String BASE_URL = "http://192.168.10.35:5050/";
public static final int    DEFAULT_TIMEOUT_SEC = 10;
```

Change `BASE_URL` to point to any other host/port without touching any test class.

---

## 📦 Key Dependencies (`pom.xml`)

```xml
<dependency>selenium-java 4.27.0 />
<dependency>testng 7.10.2 />
<dependency>webdrivermanager 5.9.2 />
```
