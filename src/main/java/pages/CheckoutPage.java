package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Constants;

import java.time.Duration;

/**
 * Page Object for the Checkout section (#checkout).
 * Covers: recipient name, email, phone, order serial, buyer age, order qty,
 * trade-in weight, account PIN, edu discount, security countdown timer,
 * invoice signing, support dialogs, tax-exempt upload, receipt/catalog downloads,
 * and the final Place Order button.
 */
public class CheckoutPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // ── Identity fields ───────────────────────────────────────────────
    private final By recipientNameInput = By.cssSelector("input[name='recipient_name']");
    private final By emailInput         = By.cssSelector("input[name='checkout_email']");
    private final By phoneInput         = By.cssSelector("input[name='checkout_phone']");
    private final By orderSerialInput   = By.cssSelector("input[name='order_serial']");
    private final By buyerAgeInput      = By.cssSelector("input[name='buyer_age']");
    private final By orderQtyInput      = By.cssSelector("input[name='order_qty']");
    private final By tradeInWeightInput = By.cssSelector("input[name='tradein_weight']");
    private final By accountPinInput    = By.cssSelector("input[name='account_pin']");
    private final By eduDiscountInput   = By.cssSelector("input[name='edu_discount_code']");

    // ── Field notes ───────────────────────────────────────────────────
    private final By recipientNote  = By.cssSelector(".recipient-note");
    private final By emailNote      = By.cssSelector(".email-note");
    private final By phoneNote      = By.cssSelector(".phone-note");
    private final By serialNote     = By.cssSelector(".serial-note");
    private final By ageNote        = By.cssSelector(".age-note");
    private final By qtyNote        = By.cssSelector(".qty-note");
    private final By weightNote     = By.cssSelector(".weight-note");
    private final By pinNote        = By.cssSelector(".pin-note");
    private final By eduNote        = By.cssSelector(".edu-note");

    // ── Timer / Invoice ───────────────────────────────────────────────
    private final By startTimerBtn        = By.xpath("//button[normalize-space()='Start Checkout Timer']");
    private final By checkoutTimerLabel   = By.cssSelector(".checkout-timer-label");
    private final By signInvoiceBtn       = By.xpath("//button[normalize-space()='Sign Invoice']");
    private final By invoiceProgressLabel = By.cssSelector(".invoice-progress-label");

    // ── Support dialogs ───────────────────────────────────────────────
    private final By requestCancelHoldBtn = By.xpath("//button[contains(normalize-space(),'Request Cancel Hold')]");
    private final By triggerGiftPromptBtn = By.xpath("//button[contains(normalize-space(),'Gift')]");
    private final By supportDialogResult  = By.cssSelector(".support-dialog-result");

    // ── Tax upload & downloads ────────────────────────────────────────
    private final By taxUploadInput         = By.cssSelector("input[name='tax_exempt_upload']");
    private final By taxUploadStatus        = By.cssSelector(".tax-upload-status");
    private final By verifyTaxCertBtn       = By.xpath("//button[contains(normalize-space(),'Verify Certificate')]");
    private final By downloadReceiptLink    = By.xpath("//a[contains(normalize-space(),'Download Receipt')]");
    private final By downloadCatalogLink    = By.xpath("//a[contains(normalize-space(),'Download Catalog')]");
    private final By receiptDownloadStatus  = By.cssSelector(".receipt-download-status");

    // ── Place Order ───────────────────────────────────────────────────
    private final By placeOrderBtn     = By.cssSelector(".place-order-btn");

    // ── Sheet modal ───────────────────────────────────────────────────
    private final By sheetBackdrop   = By.cssSelector(".sheet-backdrop");
    private final By sheetTitle      = By.cssSelector(".sheet-title");
    private final By sheetMessage    = By.cssSelector(".sheet-message-box");
    private final By sheetOkBtn      = By.cssSelector(".sheet-ack-btn");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(Constants.DEFAULT_TIMEOUT_SEC));
    }

    public void navigateTo() {
        driver.get(Constants.SECTION_CHECKOUT);
    }

    // ── Fill identity fields ──────────────────────────────────────────

    public void enterRecipientName(String name) {
        setField(recipientNameInput, name);
    }

    public void enterEmail(String email) {
        setField(emailInput, email);
    }

    public void enterPhone(String phone) {
        setField(phoneInput, phone);
    }

    public void enterOrderSerial(String serial) {
        setField(orderSerialInput, serial);
    }

    public void enterBuyerAge(String age) {
        setField(buyerAgeInput, age);
    }

    public void enterOrderQty(String qty) {
        setField(orderQtyInput, qty);
    }

    public void enterTradeInWeight(String weight) {
        setField(tradeInWeightInput, weight);
    }

    public void enterAccountPin(String pin) {
        setField(accountPinInput, pin);
    }

    public void enterEduDiscountCode(String code) {
        setField(eduDiscountInput, code);
    }

    private void setField(By locator, String value) {
        WebElement el = driver.findElement(locator);
        el.clear();
        el.sendKeys(value);
        el.sendKeys(Keys.TAB); // trigger blur/change validation
    }

    // ── Field notes ───────────────────────────────────────────────────

    public String getRecipientNoteText() { return driver.findElement(recipientNote).getText(); }
    public String getEmailNoteText()     { return driver.findElement(emailNote).getText(); }
    public String getPhoneNoteText()     { return driver.findElement(phoneNote).getText(); }
    public String getSerialNoteText()    { return driver.findElement(serialNote).getText(); }
    public String getAgeNoteText()       { return driver.findElement(ageNote).getText(); }
    public String getQtyNoteText()       { return driver.findElement(qtyNote).getText(); }
    public String getWeightNoteText()    { return driver.findElement(weightNote).getText(); }
    public String getPinNoteText()       { return driver.findElement(pinNote).getText(); }
    public String getEduNoteText()       { return driver.findElement(eduNote).getText(); }

    // ── Field validity (CSS class) ────────────────────────────────────

    public boolean isFieldValid(By locator) {
        return driver.findElement(locator).getAttribute("class").contains("is-ok");
    }

    public boolean isFieldInvalid(By locator) {
        return driver.findElement(locator).getAttribute("class").contains("is-err");
    }

    // ── Timer / Invoice ───────────────────────────────────────────────

    public void clickStartTimer() {
        driver.findElement(startTimerBtn).click();
    }

    public String getTimerLabelText() {
        return driver.findElement(checkoutTimerLabel).getText();
    }

    public void clickSignInvoice() {
        driver.findElement(signInvoiceBtn).click();
    }

    public String getInvoiceProgressText() {
        return driver.findElement(invoiceProgressLabel).getText();
    }

    // ── Support dialogs ───────────────────────────────────────────────

    public void clickRequestCancelHold() {
        driver.findElement(requestCancelHoldBtn).click();
    }

    public void clickTriggerGiftPrompt() {
        driver.findElement(triggerGiftPromptBtn).click();
    }

    public String getSupportDialogResultText() {
        return driver.findElement(supportDialogResult).getText();
    }

    // ── Tax upload ────────────────────────────────────────────────────

    public void uploadTaxCertificate(String absoluteFilePath) {
        driver.findElement(taxUploadInput).sendKeys(absoluteFilePath);
    }

    public String getTaxUploadStatusText() {
        return driver.findElement(taxUploadStatus).getText();
    }

    public void clickVerifyTaxCertificate() {
        driver.findElement(verifyTaxCertBtn).click();
    }

    // ── Downloads ─────────────────────────────────────────────────────

    public void clickDownloadReceipt() {
        driver.findElement(downloadReceiptLink).click();
    }

    public void clickDownloadCatalog() {
        driver.findElement(downloadCatalogLink).click();
    }

    public String getReceiptDownloadStatusText() {
        return driver.findElement(receiptDownloadStatus).getText();
    }

    // ── Place Order ───────────────────────────────────────────────────

    public boolean isPlaceOrderEnabled() {
        return driver.findElement(placeOrderBtn).isEnabled();
    }

    public void clickPlaceOrder() {
        driver.findElement(placeOrderBtn).click();
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
