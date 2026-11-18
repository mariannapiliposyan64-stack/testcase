package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Constants;

import java.time.Duration;
import java.util.List;

/**
 * Page Object for the Accessories section (#accessories).
 * Covers: catalog search filter, table rows, Add to Bag / Remove,
 * custom accessory form, and bag status banner.
 */
public class AccessoriesPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // ── Catalog toolbar ───────────────────────────────────────────────
    private final By searchInput     = By.cssSelector("input[name='catalog_search']");
    private final By customNameInput = By.cssSelector("input[name='custom_acc_name']");
    private final By customPriceInput= By.cssSelector("input[name='custom_acc_price']");
    private final By addQuoteBtn     = By.xpath("//button[normalize-space()='Add Quote']");

    // ── Table ─────────────────────────────────────────────────────────
    private final By catalogRows     = By.cssSelector(".catalog-row");
    private final By bagStatusBanner = By.cssSelector(".bag-status-banner");
    private final By bagHeaderCount  = By.cssSelector(".bag-header-count");

    // ── Sheet modal (shared) ──────────────────────────────────────────
    private final By sheetBackdrop   = By.cssSelector(".sheet-backdrop");
    private final By sheetOkBtn      = By.cssSelector(".sheet-ack-btn");

    public AccessoriesPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(Constants.DEFAULT_TIMEOUT_SEC));
    }

    public void navigateTo() {
        driver.get(Constants.SECTION_ACCESSORIES);
    }

    // ── Filter ────────────────────────────────────────────────────────

    public void filterCatalog(String query) {
        WebElement input = driver.findElement(searchInput);
        input.clear();
        input.sendKeys(query);
    }

    public int getVisibleRowCount() {
        List<WebElement> rows = driver.findElements(catalogRows);
        return (int) rows.stream()
                .filter(r -> !r.getCssValue("display").equals("none")
                        && r.isDisplayed())
                .count();
    }

    public List<WebElement> getAllRows() {
        return driver.findElements(catalogRows);
    }

    // ── Row helpers ───────────────────────────────────────────────────

    public WebElement findRowByName(String productName) {
        List<WebElement> rows = driver.findElements(catalogRows);
        return rows.stream()
                .filter(r -> r.findElement(By.cssSelector(".item-name"))
                        .getText().trim().equalsIgnoreCase(productName))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Row not found: " + productName));
    }

    public void addToBagForItem(String productName) {
        findRowByName(productName)
                .findElement(By.cssSelector(".add-bag-btn"))
                .click();
    }

    public void removeItemFromCatalog(String productName) {
        findRowByName(productName)
                .findElement(By.cssSelector(".remove-acc-btn"))
                .click();
    }

    public String getItemPrice(String productName) {
        return findRowByName(productName)
                .findElement(By.cssSelector(".item-price"))
                .getText();
    }

    public String getItemStockStatus(String productName) {
        return findRowByName(productName)
                .findElement(By.cssSelector(".item-stock-pill"))
                .getText();
    }

    // ── Custom accessory ──────────────────────────────────────────────

    public void addCustomAccessory(String name, String price) {
        driver.findElement(customNameInput).clear();
        driver.findElement(customNameInput).sendKeys(name);
        driver.findElement(customPriceInput).clear();
        driver.findElement(customPriceInput).sendKeys(price);
        driver.findElement(addQuoteBtn).click();
    }

    // ── Bag status ────────────────────────────────────────────────────

    public String getBagStatusBannerText() {
        return driver.findElement(bagStatusBanner).getText();
    }

    public String getBagHeaderCountText() {
        return driver.findElement(bagHeaderCount).getText();
    }

    // ── Sheet modal ───────────────────────────────────────────────────

    public boolean isSheetVisible() {
        return !driver.findElement(sheetBackdrop).getAttribute("class").contains("hidden");
    }

    public void closeSheet() {
        driver.findElement(sheetOkBtn).click();
        wait.until(ExpectedConditions.attributeContains(sheetBackdrop, "class", "hidden"));
    }
}
