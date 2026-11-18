package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Constants;

import java.time.Duration;

/**
 * Page Object for the Express Pay section (#express-desk).
 * Covers: drag-and-drop bundle to tray, double-click Pay button,
 * right-click saved build context menu, quick serial input,
 * and express gesture status.
 */
public class ExpressPayPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    // ── Drag & Drop ───────────────────────────────────────────────────
    private final By draggableBundle  = By.cssSelector(".draggable-bundle-item");
    private final By expressDropzone  = By.cssSelector(".express-tray-dropzone");

    // ── Double-click Pay ──────────────────────────────────────────────
    private final By doubleClickPayBtn = By.cssSelector(".pay-doubleclick-btn");

    // ── Right-click saved build ───────────────────────────────────────
    private final By savedBuildTile    = By.cssSelector(".saved-build-tile");
    private final By macOsContextMenu  = By.cssSelector(".macos-context-menu");

    // ── Quick serial ──────────────────────────────────────────────────
    private final By quickSerialInput  = By.cssSelector("input[name='quick_serial_input']");

    // ── Status ────────────────────────────────────────────────────────
    private final By gestureStatus     = By.cssSelector(".express-gesture-status");

    // ── Sheet modal ───────────────────────────────────────────────────
    private final By sheetBackdrop   = By.cssSelector(".sheet-backdrop");
    private final By sheetTitle      = By.cssSelector(".sheet-title");
    private final By sheetMessage    = By.cssSelector(".sheet-message-box");
    private final By sheetOkBtn      = By.cssSelector(".sheet-ack-btn");

    public ExpressPayPage(WebDriver driver) {
        this.driver  = driver;
        this.wait    = new WebDriverWait(driver, Duration.ofSeconds(Constants.DEFAULT_TIMEOUT_SEC));
        this.actions = new Actions(driver);
    }

    public void navigateTo() {
        driver.get(Constants.SECTION_EXPRESS);
    }

    // ── Drag bundle to dropzone ───────────────────────────────────────

    public void dragBundleToTray() {
        WebElement source = driver.findElement(draggableBundle);
        WebElement target = driver.findElement(expressDropzone);
        actions.dragAndDrop(source, target).perform();
    }

    // ── Double-click Pay ──────────────────────────────────────────────

    public void doubleClickPay() {
        actions.doubleClick(driver.findElement(doubleClickPayBtn)).perform();
    }

    public String getDoubleClickPayBtnText() {
        return driver.findElement(doubleClickPayBtn).getText();
    }

    // ── Right-click saved build ───────────────────────────────────────

    public void rightClickSavedBuild() {
        actions.contextClick(driver.findElement(savedBuildTile)).perform();
    }

    public boolean isContextMenuVisible() {
        return driver.findElement(macOsContextMenu).isDisplayed();
    }

    public void clickContextMenuItem(String itemText) {
        WebElement menu = driver.findElement(macOsContextMenu);
        menu.findElements(By.tagName("button")).stream()
                .filter(btn -> btn.getText().trim().equalsIgnoreCase(itemText))
                .findFirst()
                .ifPresent(WebElement::click);
    }

    // ── Quick serial ──────────────────────────────────────────────────

    public void enterQuickSerial(String serial) {
        WebElement input = driver.findElement(quickSerialInput);
        input.clear();
        input.sendKeys(serial);
        input.sendKeys(Keys.RETURN);
    }

    // ── Status text ───────────────────────────────────────────────────

    public String getGestureStatusText() {
        return driver.findElement(gestureStatus).getText();
    }

    // ── Sheet modal ───────────────────────────────────────────────────

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
