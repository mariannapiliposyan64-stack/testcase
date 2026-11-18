package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Constants;

import java.time.Duration;
import java.util.List;

/**
 * Page Object for the Studio Pro Configurator section (#configurator).
 * Covers: finish radios, memory radios, pro-apps checkboxes, engraving,
 * order-updates checkboxes, license checkbox, store pickup dropdowns,
 * trade-in hover / scan, and "Add Studio Pro to Bag".
 */
public class ConfiguratorPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    // ── Finish radios ──────────────────────────────────────────────────
    private final By finishSilver     = By.cssSelector("input[name='hardware_finish'][value='Silver']");
    private final By finishSpaceBlack = By.cssSelector("input[name='hardware_finish'][value='Space Black']");
    private final By finishStarlight  = By.cssSelector("input[name='hardware_finish'][value='Starlight']");
    private final By finishBadge      = By.cssSelector(".finish-badge-label");

    // ── Memory radios ─────────────────────────────────────────────────
    private final By memory16  = By.cssSelector("input[name='memory_tier'][value='16GB']");
    private final By memory32  = By.cssSelector("input[name='memory_tier'][value='32GB']");
    private final By memory64  = By.cssSelector("input[name='memory_tier'][value='64GB']");
    private final By memBadge  = By.cssSelector(".memory-badge-label");
    private final By priceDisplay = By.cssSelector(".live-price-display");

    // ── Pro Apps checkboxes ────────────────────────────────────────────
    private final By selectAllApps  = By.cssSelector("input[name='select_all_apps']");
    private final By proAppItems    = By.cssSelector(".pro-app-item");
    private final By appsCountLabel = By.cssSelector(".apps-count-label");

    // ── Order updates checkboxes ───────────────────────────────────────
    private final By updateOpts       = By.cssSelector(".update-opt");
    private final By updatesCountLabel = By.cssSelector(".updates-count-label");
    private final By updatesNote      = By.cssSelector(".updates-note");

    // ── License checkbox ──────────────────────────────────────────────
    private final By licenseCheckbox = By.cssSelector("input[name='agree_license_terms']");
    private final By licenseNote     = By.cssSelector(".license-note");

    // ── Engraving ─────────────────────────────────────────────────────
    private final By engravingInput = By.cssSelector("input[name='engraving_text']");
    private final By engravingBadge = By.cssSelector(".engraving-len-badge");
    private final By engravingNote  = By.cssSelector(".engraving-note");

    // ── Store Pickup ──────────────────────────────────────────────────
    private final By pickupCountry = By.cssSelector("select[name='pickup_country']");
    private final By pickupStore   = By.cssSelector("select[name='pickup_store']");
    private final By storeFetchStatus = By.cssSelector(".store-fetch-status");
    private final By checkStoreStockBtn = By.xpath("//button[normalize-space()='Check Store Stock']");

    // ── Trade-in hover / scan ─────────────────────────────────────────
    private final By tradeInTrigger = By.cssSelector(".tradein-hover-trigger");
    private final By tradeInDetails = By.cssSelector(".tradein-hover-details");
    private final By tradeInScanBtn = By.xpath("//button[normalize-space()='Verify Trade-In']");
    private final By tradeInStatus  = By.cssSelector(".tradein-scan-status");
    private final By tradeInOverlay = By.cssSelector(".tradein-scan-overlay");

    // ── Add to Bag ────────────────────────────────────────────────────
    private final By addToBagBtn   = By.xpath("//button[normalize-space()='Add Studio Pro to Bag']");
    private final By bagCount      = By.cssSelector(".bag-header-count");

    // ── Sheet modal ───────────────────────────────────────────────────
    private final By sheetBackdrop = By.cssSelector(".sheet-backdrop");
    private final By sheetTitle    = By.cssSelector(".sheet-title");
    private final By sheetMessage  = By.cssSelector(".sheet-message-box");
    private final By sheetOkBtn    = By.cssSelector(".sheet-ack-btn");

    // ── SVG engraving preview ─────────────────────────────────────────
    private final By svgEngravingPreview = By.cssSelector(".svg-engraving-preview");

    public ConfiguratorPage(WebDriver driver) {
        this.driver  = driver;
        this.wait    = new WebDriverWait(driver, Duration.ofSeconds(Constants.DEFAULT_TIMEOUT_SEC));
        this.actions = new Actions(driver);
    }

    // ── Navigation ────────────────────────────────────────────────────

    public void open() {
        driver.get(Constants.BASE_URL);
    }

    // ── Finish ────────────────────────────────────────────────────────

    public void selectFinish(String finish) {
        By locator = switch (finish) {
            case "Space Black" -> finishSpaceBlack;
            case "Starlight"   -> finishStarlight;
            default            -> finishSilver;
        };
        driver.findElement(locator).click();
    }

    public String getFinishBadgeText() {
        return driver.findElement(finishBadge).getText();
    }

    // ── Memory ────────────────────────────────────────────────────────

    public void selectMemory(String tier) {
        By locator = switch (tier) {
            case "32GB" -> memory32;
            case "64GB" -> memory64;
            default     -> memory16;
        };
        driver.findElement(locator).click();
    }

    public String getMemoryBadgeText() {
        return driver.findElement(memBadge).getText();
    }

    public String getPriceText() {
        return driver.findElement(priceDisplay).getText();
    }

    // ── Pro Apps ──────────────────────────────────────────────────────

    public void clickSelectAllApps() {
        driver.findElement(selectAllApps).click();
    }

    public void selectProApp(String appName) {
        List<WebElement> apps = driver.findElements(proAppItems);
        for (WebElement app : apps) {
            WebElement label = app.findElement(By.xpath("./following-sibling::*[1]"));
            if (label.getText().trim().equalsIgnoreCase(appName) && !app.isSelected()) {
                app.click();
                break;
            }
        }
    }

    public String getAppsCountLabel() {
        return driver.findElement(appsCountLabel).getText();
    }

    // ── Order Updates ─────────────────────────────────────────────────

    public void selectUpdateOption(String value) {
        WebElement cb = driver.findElement(
                By.cssSelector("input[name='order_update_opt'][value='" + value + "']"));
        if (!cb.isSelected()) cb.click();
    }

    public String getUpdatesCountText() {
        return driver.findElement(updatesCountLabel).getText();
    }

    public String getUpdatesNoteText() {
        return driver.findElement(updatesNote).getText();
    }

    // ── License ───────────────────────────────────────────────────────

    public void acceptLicenseTerms() {
        WebElement cb = driver.findElement(licenseCheckbox);
        if (!cb.isSelected()) cb.click();
    }

    public String getLicenseNoteText() {
        return driver.findElement(licenseNote).getText();
    }

    // ── Engraving ─────────────────────────────────────────────────────

    public void clearAndSetEngraving(String text) {
        WebElement input = driver.findElement(engravingInput);
        input.clear();
        input.sendKeys(text);
    }

    public void clearEngravingWithCtrlA(String text) {
        WebElement input = driver.findElement(engravingInput);
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys(text);
    }

    public String getEngravingBadgeText() {
        return driver.findElement(engravingBadge).getText();
    }

    public String getEngravingNoteText() {
        return driver.findElement(engravingNote).getText();
    }

    public String getSvgEngravingPreviewText() {
        return driver.findElement(svgEngravingPreview).getText();
    }

    // ── Store Pickup ──────────────────────────────────────────────────

    public void selectPickupRegion(String value) {
        new org.openqa.selenium.support.ui.Select(driver.findElement(pickupCountry))
                .selectByValue(value);
    }

    public void selectPickupStore(String visibleText) {
        wait.until(ExpectedConditions.elementToBeClickable(pickupStore));
        new org.openqa.selenium.support.ui.Select(driver.findElement(pickupStore))
                .selectByVisibleText(visibleText);
    }

    public String getStoreFetchStatus() {
        return driver.findElement(storeFetchStatus).getText();
    }

    public boolean isPickupStoreEnabled() {
        return driver.findElement(pickupStore).isEnabled();
    }

    public void clickCheckStoreStock() {
        driver.findElement(checkStoreStockBtn).click();
    }

    // ── Trade-in ──────────────────────────────────────────────────────

    public void hoverOverTradeIn() {
        actions.moveToElement(driver.findElement(tradeInTrigger)).perform();
    }

    public boolean isTradeInDetailsVisible() {
        return driver.findElement(tradeInDetails).isDisplayed();
    }

    public void clickVerifyTradeIn() {
        driver.findElement(tradeInScanBtn).click();
    }

    public String getTradeInStatusText() {
        return driver.findElement(tradeInStatus).getText();
    }

    /** Waits for the overlay spinner to disappear (invisibility condition). */
    public void waitForTradeInScanComplete() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(tradeInOverlay));
    }

    // ── Add to Bag ────────────────────────────────────────────────────

    public void clickAddToBag() {
        driver.findElement(addToBagBtn).click();
    }

    public String getBagCountText() {
        return driver.findElement(bagCount).getText();
    }

    // ── Sheet Modal ───────────────────────────────────────────────────

    public boolean isSheetVisible() {
        return !driver.findElement(sheetBackdrop).getAttribute("class").contains("hidden");
    }

    public String getSheetTitle() {
        return driver.findElement(sheetTitle).getText();
    }

    public String getSheetMessage() {
        return driver.findElement(sheetMessage).getText();
    }

    public void closeSheet() {
        driver.findElement(sheetOkBtn).click();
        wait.until(ExpectedConditions.attributeContains(sheetBackdrop, "class", "hidden"));
    }
}
