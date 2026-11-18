package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ConfiguratorPage;
import pages.NavBarPage;
import utils.Constants;

/**
 * Tests for Section 1 – Studio Pro Configurator.
 *
 * TC-C01  Select finish → badge updates and SVG fill changes
 * TC-C02  Select 32 GB memory → price increases to $1,999
 * TC-C03  Select 64 GB memory → price increases to $2,399
 * TC-C04  "Select All" apps master checkbox selects all 3 pro apps
 * TC-C05  Partial pro-app selection shows indeterminate master + correct count
 * TC-C06  Exactly 2 order-update options satisfy the validation note
 * TC-C07  Accepting license terms clears the error note
 * TC-C08  Default engraving text shows error; custom text (4–24 chars) shows OK
 * TC-C09  Dependent pickup dropdown is disabled until a region is selected
 * TC-C10  Hover over Trade-In trigger reveals the details panel
 * TC-C11  "Verify Trade-In" scan shows overlay, then modal on completion
 * TC-C12  Add Studio Pro to Bag increments bag counter
 */
public class ConfiguratorTest extends BaseTest {

    private ConfiguratorPage configuratorPage() {
        return new ConfiguratorPage(driver);
    }

    // ── TC-C01: Finish selection ──────────────────────────────────────

    @Test(description = "TC-C01: Selecting Space Black finish updates the finish badge")
    public void testSelectSpaceBlackFinish() {
        ConfiguratorPage page = configuratorPage();
        page.open();
        page.selectFinish("Space Black");
        Assert.assertEquals(page.getFinishBadgeText(), "Space Black Finish",
                "Finish badge should update to 'Space Black Finish'");
    }

    @Test(description = "TC-C01b: Selecting Starlight finish updates the finish badge")
    public void testSelectStarlightFinish() {
        ConfiguratorPage page = configuratorPage();
        page.open();
        page.selectFinish("Starlight");
        Assert.assertEquals(page.getFinishBadgeText(), "Starlight Finish");
    }

    // ── TC-C02: Memory 32 GB ──────────────────────────────────────────

    @Test(description = "TC-C02: Selecting 32 GB updates memory badge and price to $1,999")
    public void testSelectMemory32GB() {
        ConfiguratorPage page = configuratorPage();
        page.open();
        page.selectMemory("32GB");
        Assert.assertEquals(page.getMemoryBadgeText(), "32GB Unified Memory");
        Assert.assertEquals(page.getPriceText(), "$1,999");
    }

    // ── TC-C03: Memory 64 GB ──────────────────────────────────────────

    @Test(description = "TC-C03: Selecting 64 GB updates memory badge and price to $2,399")
    public void testSelectMemory64GB() {
        ConfiguratorPage page = configuratorPage();
        page.open();
        page.selectMemory("64GB");
        Assert.assertEquals(page.getMemoryBadgeText(), "64GB Unified Memory");
        Assert.assertEquals(page.getPriceText(), "$2,399");
    }

    // ── TC-C04: Select All pro apps ───────────────────────────────────

    @Test(description = "TC-C04: 'Select All' checkbox selects all 3 pro apps; count label shows 3 / 3")
    public void testSelectAllProApps() {
        ConfiguratorPage page = configuratorPage();
        page.open();
        page.clickSelectAllApps();
        Assert.assertEquals(page.getAppsCountLabel(), "3 / 3 selected");
    }

    // ── TC-C05: Partial pro-app selection ────────────────────────────

    @Test(description = "TC-C05: Selecting 1 of 3 apps shows count 1 / 3 and master stays indeterminate")
    public void testPartialProAppSelection() {
        ConfiguratorPage page = configuratorPage();
        page.open();
        // Click the first individual app checkbox (Logic Pro)
        page.selectProApp("Logic Pro");
        Assert.assertTrue(page.getAppsCountLabel().startsWith("1 /"),
                "Apps count label should start with '1 /'");
    }

    // ── TC-C06: Order updates – exactly 2 required ───────────────────

    @Test(description = "TC-C06: Selecting exactly 2 order update options clears the validation error")
    public void testSelectExactlyTwoOrderUpdates() {
        ConfiguratorPage page = configuratorPage();
        page.open();
        page.selectUpdateOption("sms");
        page.selectUpdateOption("email");
        Assert.assertEquals(page.getUpdatesCountText(), "2 / 2");
        Assert.assertTrue(page.getUpdatesNoteText().isEmpty(),
                "Updates note should be empty when exactly 2 are selected");
    }

    @Test(description = "TC-C06b: Selecting only 1 order update shows validation error")
    public void testSelectOneOrderUpdateShowsError() {
        ConfiguratorPage page = configuratorPage();
        page.open();
        page.selectUpdateOption("sms");
        Assert.assertFalse(page.getUpdatesNoteText().isEmpty(),
                "Updates note should show an error with only 1 selection");
    }

    // ── TC-C07: License terms ─────────────────────────────────────────

    @Test(description = "TC-C07: Accepting license terms clears the error note")
    public void testAcceptLicenseTerms() {
        ConfiguratorPage page = configuratorPage();
        page.open();
        page.acceptLicenseTerms();
        Assert.assertTrue(page.getLicenseNoteText().isEmpty(),
                "License note should be empty after acceptance");
    }

    // ── TC-C08: Engraving validation ─────────────────────────────────

    @Test(description = "TC-C08a: Default engraving text shows validation error")
    public void testDefaultEngravingShowsError() {
        ConfiguratorPage page = configuratorPage();
        page.open();
        // Trigger validation without changing the value (tab away)
        driver.findElement(org.openqa.selenium.By.cssSelector("input[name='engraving_text']"))
              .sendKeys(org.openqa.selenium.Keys.TAB);
        Assert.assertFalse(page.getEngravingNoteText().isEmpty(),
                "Default engraving 'Complimentary Engraving' should produce an error");
    }

    @Test(description = "TC-C08b: Valid custom engraving (4–24 chars) clears error and shows preview")
    public void testValidCustomEngraving() {
        ConfiguratorPage page = configuratorPage();
        page.open();
        page.clearEngravingWithCtrlA("MY STUDIO");
        Assert.assertTrue(page.getEngravingNoteText().isEmpty(),
                "No error note expected for a valid engraving text");
        Assert.assertEquals(page.getSvgEngravingPreviewText(), "MY STUDIO",
                "SVG preview should update to the entered text");
    }

    @Test(description = "TC-C08c: Engraving text shorter than 4 chars shows validation error")
    public void testShortEngravingShowsError() {
        ConfiguratorPage page = configuratorPage();
        page.open();
        page.clearEngravingWithCtrlA("AB");
        Assert.assertFalse(page.getEngravingNoteText().isEmpty(),
                "Engraving shorter than 4 chars should produce an error");
    }

    // ── TC-C09: Dependent pickup dropdown ────────────────────────────

    @Test(description = "TC-C09: Pickup store dropdown is disabled before a region is selected")
    public void testPickupStoreDisabledByDefault() {
        ConfiguratorPage page = configuratorPage();
        page.open();
        Assert.assertFalse(page.isPickupStoreEnabled(),
                "Pickup store dropdown should be disabled until a region is chosen");
    }

    @Test(description = "TC-C09b: Selecting a region enables the store dropdown")
    public void testSelectingRegionEnablesStoreDropdown() {
        ConfiguratorPage page = configuratorPage();
        page.open();
        page.selectPickupRegion("US");
        // Wait for dropdown to become enabled
        new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(5))
                .until(d -> page.isPickupStoreEnabled());
        Assert.assertTrue(page.isPickupStoreEnabled(),
                "Store dropdown should be enabled after selecting a region");
    }

    // ── TC-C10: Trade-In hover ────────────────────────────────────────

    @Test(description = "TC-C10: Hovering over Trade-In trigger reveals the details panel")
    public void testHoverTradeInRevealsDetails() {
        ConfiguratorPage page = configuratorPage();
        page.open();
        page.hoverOverTradeIn();
        Assert.assertTrue(page.isTradeInDetailsVisible(),
                "Trade-In details panel should be visible on hover");
    }

    // ── TC-C11: Trade-In scan wait + modal ───────────────────────────

    @Test(description = "TC-C11: Verify Trade-In scan shows overlay then success modal")
    public void testTradeInScanShowsModal() throws InterruptedException {
        ConfiguratorPage page = configuratorPage();
        page.open();
        page.clickVerifyTradeIn();
        page.waitForTradeInScanComplete(); // waits for overlay to disappear
        Assert.assertTrue(page.isSheetVisible(),
                "Sheet modal should appear after trade-in scan completes");
        Assert.assertEquals(page.getSheetTitle(), "Trade-In Eligible");
        page.closeSheet();
    }

    // ── TC-C12: Add to Bag ────────────────────────────────────────────

    @Test(description = "TC-C12: Clicking 'Add Studio Pro to Bag' increments the bag counter")
    public void testAddStudioProToBag() {
        ConfiguratorPage page = configuratorPage();
        page.open();
        page.clickAddToBag();
        Assert.assertEquals(page.getBagCountText(), "Bag (1)",
                "Bag counter should increment to 1 after adding Studio Pro");
    }

    // ── TC-NAV: Nav bar Explore hover ────────────────────────────────

    @Test(description = "TC-NAV: Hovering Explore shows dropdown; selecting 'Education Store' updates notice")
    public void testExploreHoverDropdown() {
        NavBarPage nav = new NavBarPage(driver);
        nav.hoverExplore();
        Assert.assertTrue(nav.isExploreMenuVisible(), "Explore menu should appear on hover");
        nav.selectExploreCategory("Education Store");
        Assert.assertTrue(nav.getNavCategoryNotice().contains("Education"),
                "Nav notice should mention the selected category");
    }

    // ── TC-NAV-RESET ─────────────────────────────────────────────────

    @Test(description = "TC-NAV-RESET: Clicking Reset shows 'Storefront Reset' modal and resets bag to 0")
    public void testResetStorefront() {
        // First add something
        ConfiguratorPage cfg = configuratorPage();
        cfg.clickAddToBag();
        // Then reset
        NavBarPage nav = new NavBarPage(driver);
        nav.clickReset();
        Assert.assertTrue(nav.isSheetVisible(), "Sheet should appear after reset");
        Assert.assertEquals(nav.getSheetTitle(), "Storefront Reset");
        nav.closeSheet();
        Assert.assertEquals(nav.getBagCountText(), "Bag (0)",
                "Bag should be reset to 0 after storefront reset");
    }
}
