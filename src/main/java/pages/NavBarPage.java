package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Constants;

import java.time.Duration;

/**
 * Page Object for the top Navigation bar.
 * Covers: nav links, Explore hover dropdown, bag count, Reset button,
 * and announcement ribbon.
 */
public class NavBarPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    // ── Nav links ─────────────────────────────────────────────────────
    private final By navStudioPro  = By.xpath("//nav//a[normalize-space()='Studio Pro']");
    private final By navAccessories= By.xpath("//nav//a[normalize-space()='Accessories']");
    private final By navExpressPay = By.xpath("//nav//a[normalize-space()='Express Pay']");
    private final By navCheckout   = By.xpath("//nav//a[normalize-space()='Checkout']");

    // ── Explore hover dropdown ────────────────────────────────────────
    private final By exploreTrigger       = By.cssSelector(".nav-categories-trigger");
    private final By navCategoriesMenu    = By.cssSelector(".nav-categories-menu");
    private final By navCategoryNotice    = By.cssSelector(".nav-category-notice");

    // ── Bag & Reset ───────────────────────────────────────────────────
    private final By bagHeaderCount  = By.cssSelector(".bag-header-count");
    private final By resetBtn        = By.xpath("//button[normalize-space()='Reset']");

    // ── Sheet modal ───────────────────────────────────────────────────
    private final By sheetBackdrop   = By.cssSelector(".sheet-backdrop");
    private final By sheetTitle      = By.cssSelector(".sheet-title");
    private final By sheetOkBtn      = By.cssSelector(".sheet-ack-btn");

    public NavBarPage(WebDriver driver) {
        this.driver  = driver;
        this.wait    = new WebDriverWait(driver, Duration.ofSeconds(Constants.DEFAULT_TIMEOUT_SEC));
        this.actions = new Actions(driver);
    }

    // ── Navigation ────────────────────────────────────────────────────

    public void clickStudioPro()   { driver.findElement(navStudioPro).click(); }
    public void clickAccessories() { driver.findElement(navAccessories).click(); }
    public void clickExpressPay()  { driver.findElement(navExpressPay).click(); }
    public void clickCheckout()    { driver.findElement(navCheckout).click(); }

    // ── Explore hover menu ────────────────────────────────────────────

    public void hoverExplore() {
        actions.moveToElement(driver.findElement(exploreTrigger)).perform();
    }

    public boolean isExploreMenuVisible() {
        return driver.findElement(navCategoriesMenu).isDisplayed();
    }

    public void selectExploreCategory(String itemText) {
        driver.findElement(navCategoriesMenu)
                .findElements(By.tagName("button")).stream()
                .filter(btn -> btn.getText().trim().equalsIgnoreCase(itemText))
                .findFirst()
                .ifPresent(WebElement::click);
    }

    public String getNavCategoryNotice() {
        return driver.findElement(navCategoryNotice).getText();
    }

    // ── Bag ───────────────────────────────────────────────────────────

    public String getBagCountText() {
        return driver.findElement(bagHeaderCount).getText();
    }

    // ── Reset ─────────────────────────────────────────────────────────

    public void clickReset() {
        driver.findElement(resetBtn).click();
    }

    public boolean isSheetVisible() {
        return !driver.findElement(sheetBackdrop).getAttribute("class").contains("hidden");
    }

    public String getSheetTitle() {
        return driver.findElement(sheetTitle).getText();
    }

    public void closeSheet() {
        driver.findElement(sheetOkBtn).click();
        wait.until(ExpectedConditions.attributeContains(sheetBackdrop, "class", "hidden"));
    }
}
