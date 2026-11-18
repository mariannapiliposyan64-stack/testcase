package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CheckoutPage;

/**
 * Tests for Section 4 – Checkout & Verification.
 *
 * TC-CH01  Valid recipient name passes validation
 * TC-CH02  Default "Walk-in Guest" name fails validation
 * TC-CH03  Valid email format passes; invalid format fails
 * TC-CH04  10-digit phone passes; 9-digit fails
 * TC-CH05  Valid order serial (8 uppercase alphanumeric) passes
 * TC-CH06  Buyer age 18–95 passes; age 17 fails
 * TC-CH07  Order quantity 1–25 passes; 0 fails
 * TC-CH08  Trade-in weight between 0.5–30.0 passes
 * TC-CH09  Place Order button is disabled before checkout timer starts
 * TC-CH10  Receipt download link is clickable and updates download status
 */
public class CheckoutTest extends BaseTest {

    private CheckoutPage checkoutPage() {
        return new CheckoutPage(driver);
    }

    // ── TC-CH01: Valid recipient name ─────────────────────────────────

    @Test(description = "TC-CH01: A valid recipient name passes field validation (is-ok)")
    public void testValidRecipientName() {
        CheckoutPage page = checkoutPage();
        page.navigateTo();
        page.enterRecipientName("Anna Melikyan");
        Assert.assertTrue(page.isFieldValid(
                        org.openqa.selenium.By.cssSelector("input[name='recipient_name']")),
                "Recipient name field should be marked valid (is-ok)");
    }

    // ── TC-CH02: Default "Walk-in Guest" ─────────────────────────────

    @Test(description = "TC-CH02: Default 'Walk-in Guest' name fails validation (is-err)")
    public void testDefaultRecipientNameFails() {
        CheckoutPage page = checkoutPage();
        page.navigateTo();
        page.enterRecipientName("Walk-in Guest");
        Assert.assertTrue(page.isFieldInvalid(
                        org.openqa.selenium.By.cssSelector("input[name='recipient_name']")),
                "Default 'Walk-in Guest' should be marked invalid (is-err)");
        Assert.assertFalse(page.getRecipientNoteText().isEmpty(),
                "Error note should appear for 'Walk-in Guest'");
    }

    // ── TC-CH03: Email validation ─────────────────────────────────────

    @Test(description = "TC-CH03a: Valid email passes validation")
    public void testValidEmail() {
        CheckoutPage page = checkoutPage();
        page.navigateTo();
        page.enterEmail("anna@example.com");
        Assert.assertTrue(page.isFieldValid(
                org.openqa.selenium.By.cssSelector("input[name='checkout_email']")));
    }

    @Test(description = "TC-CH03b: Invalid email fails validation")
    public void testInvalidEmail() {
        CheckoutPage page = checkoutPage();
        page.navigateTo();
        page.enterEmail("not-an-email");
        Assert.assertTrue(page.isFieldInvalid(
                org.openqa.selenium.By.cssSelector("input[name='checkout_email']")));
        Assert.assertFalse(page.getEmailNoteText().isEmpty());
    }

    // ── TC-CH04: Phone validation ─────────────────────────────────────

    @Test(description = "TC-CH04a: 10-digit phone passes validation")
    public void testValidPhone() {
        CheckoutPage page = checkoutPage();
        page.navigateTo();
        page.enterPhone("0931234567");
        Assert.assertTrue(page.isFieldValid(
                org.openqa.selenium.By.cssSelector("input[name='checkout_phone']")));
    }

    @Test(description = "TC-CH04b: 9-digit phone fails validation")
    public void testInvalidPhone() {
        CheckoutPage page = checkoutPage();
        page.navigateTo();
        page.enterPhone("093123456");
        Assert.assertTrue(page.isFieldInvalid(
                org.openqa.selenium.By.cssSelector("input[name='checkout_phone']")));
    }

    // ── TC-CH05: Order serial ─────────────────────────────────────────

    @Test(description = "TC-CH05a: Valid 8-char uppercase alphanumeric serial passes")
    public void testValidOrderSerial() {
        CheckoutPage page = checkoutPage();
        page.navigateTo();
        page.enterOrderSerial("ABCD1234");
        Assert.assertTrue(page.isFieldValid(
                org.openqa.selenium.By.cssSelector("input[name='order_serial']")));
    }

    @Test(description = "TC-CH05b: Lowercase serial fails validation")
    public void testInvalidOrderSerial() {
        CheckoutPage page = checkoutPage();
        page.navigateTo();
        page.enterOrderSerial("abcd1234");
        Assert.assertTrue(page.isFieldInvalid(
                org.openqa.selenium.By.cssSelector("input[name='order_serial']")));
    }

    // ── TC-CH06: Buyer age ────────────────────────────────────────────

    @Test(description = "TC-CH06a: Age 25 passes validation")
    public void testValidBuyerAge() {
        CheckoutPage page = checkoutPage();
        page.navigateTo();
        page.enterBuyerAge("25");
        Assert.assertTrue(page.isFieldValid(
                org.openqa.selenium.By.cssSelector("input[name='buyer_age']")));
    }

    @Test(description = "TC-CH06b: Age 17 (underage) fails validation")
    public void testUnderageFailsValidation() {
        CheckoutPage page = checkoutPage();
        page.navigateTo();
        page.enterBuyerAge("17");
        Assert.assertTrue(page.isFieldInvalid(
                org.openqa.selenium.By.cssSelector("input[name='buyer_age']")));
        Assert.assertFalse(page.getAgeNoteText().isEmpty());
    }

    // ── TC-CH07: Order quantity ───────────────────────────────────────

    @Test(description = "TC-CH07a: Quantity 5 passes validation")
    public void testValidOrderQty() {
        CheckoutPage page = checkoutPage();
        page.navigateTo();
        page.enterOrderQty("5");
        Assert.assertTrue(page.isFieldValid(
                org.openqa.selenium.By.cssSelector("input[name='order_qty']")));
    }

    @Test(description = "TC-CH07b: Quantity 0 fails validation")
    public void testZeroQtyFails() {
        CheckoutPage page = checkoutPage();
        page.navigateTo();
        page.enterOrderQty("0");
        Assert.assertTrue(page.isFieldInvalid(
                org.openqa.selenium.By.cssSelector("input[name='order_qty']")));
    }

    // ── TC-CH08: Trade-in weight ──────────────────────────────────────

    @Test(description = "TC-CH08a: Weight 2.5 kg passes validation")
    public void testValidTradeInWeight() {
        CheckoutPage page = checkoutPage();
        page.navigateTo();
        page.enterTradeInWeight("2.5");
        Assert.assertTrue(page.isFieldValid(
                org.openqa.selenium.By.cssSelector("input[name='tradein_weight']")));
    }

    @Test(description = "TC-CH08b: Weight 0.1 kg (too low) fails validation")
    public void testTooLowTradeInWeight() {
        CheckoutPage page = checkoutPage();
        page.navigateTo();
        page.enterTradeInWeight("0.1");
        Assert.assertTrue(page.isFieldInvalid(
                org.openqa.selenium.By.cssSelector("input[name='tradein_weight']")));
    }

    // ── TC-CH09: Place Order button disabled by default ───────────────

    @Test(description = "TC-CH09: Place Order button is disabled when checkout timer has not started")
    public void testPlaceOrderDisabledByDefault() {
        CheckoutPage page = checkoutPage();
        page.navigateTo();
        Assert.assertFalse(page.isPlaceOrderEnabled(),
                "Place Order button should be disabled before checkout timer starts");
    }

    // ── TC-CH10: Receipt download ─────────────────────────────────────

    @Test(description = "TC-CH10: Clicking Download Receipt link updates the download status text")
    public void testReceiptDownloadUpdatesStatus() {
        CheckoutPage page = checkoutPage();
        page.navigateTo();
        page.clickDownloadReceipt();
        String status = page.getReceiptDownloadStatusText();
        Assert.assertTrue(status.toLowerCase().contains("downloaded"),
                "Receipt download status should indicate the file was downloaded");
    }
}
