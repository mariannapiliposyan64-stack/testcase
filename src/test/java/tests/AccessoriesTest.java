package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AccessoriesPage;

/**
 * Tests for Section 2 – Accessories Catalog.
 *
 * TC-A01  Catalog contains the initial expected number of rows (≥ 5)
 * TC-A02  Filtering by "Keyboard" shows only matching rows
 * TC-A03  Filtering by a non-existent term shows zero rows
 * TC-A04  "Studio Keyboard Pro" is In Stock with price $199
 * TC-A05  "Add to Bag" for a catalog item updates bag count
 * TC-A06  Removing a row removes it from the visible table
 * TC-A07  Adding a valid custom accessory appends a new row
 */
public class AccessoriesTest extends BaseTest {

    private AccessoriesPage accessoriesPage() {
        return new AccessoriesPage(driver);
    }

    // ── TC-A01: Initial row count ─────────────────────────────────────

    @Test(description = "TC-A01: Catalog has at least 5 rows on page load")
    public void testInitialCatalogRowCount() {
        AccessoriesPage page = accessoriesPage();
        Assert.assertTrue(page.getVisibleRowCount() >= 5,
                "Catalog should have at least 5 accessories by default");
    }

    // ── TC-A02: Filter matching ───────────────────────────────────────

    @Test(description = "TC-A02: Filtering by 'Keyboard' shows only keyboard rows")
    public void testFilterByKeyword() {
        AccessoriesPage page = accessoriesPage();
        page.filterCatalog("Keyboard");
        int visible = page.getVisibleRowCount();
        Assert.assertTrue(visible >= 1, "At least one Keyboard row should be visible");
        // All visible rows must contain the word "keyboard" (case-insensitive)
        page.getAllRows().stream()
                .filter(r -> r.isDisplayed())
                .forEach(r -> Assert.assertTrue(
                        r.findElement(org.openqa.selenium.By.cssSelector(".item-name"))
                                .getText().toLowerCase().contains("keyboard"),
                        "Every visible row must match the filter term 'keyboard'"));
    }

    // ── TC-A03: No-match filter ───────────────────────────────────────

    @Test(description = "TC-A03: Filtering by a non-existent term shows zero rows")
    public void testFilterNoMatch() {
        AccessoriesPage page = accessoriesPage();
        page.filterCatalog("ZZZNOTEXIST");
        Assert.assertEquals(page.getVisibleRowCount(), 0,
                "No rows should be visible for a non-existent search term");
    }

    // ── TC-A04: Product details ───────────────────────────────────────

    @Test(description = "TC-A04: 'Studio Keyboard Pro' shows price $199 and In Stock status")
    public void testKeyboardProDetails() {
        AccessoriesPage page = accessoriesPage();
        Assert.assertEquals(page.getItemPrice("Studio Keyboard Pro"), "$199");
        Assert.assertEquals(page.getItemStockStatus("Studio Keyboard Pro"), "In Stock");
    }

    // ── TC-A05: Add to Bag from catalog ──────────────────────────────

    @Test(description = "TC-A05: Adding 'Precision Trackpad' from catalog updates bag header count")
    public void testAddAccessoryToBag() {
        AccessoriesPage page = accessoriesPage();
        page.addToBagForItem("Precision Trackpad");
        Assert.assertFalse(page.getBagStatusBannerText().equalsIgnoreCase("Bag is empty."),
                "Bag status should no longer read 'Bag is empty.'");
    }

    // ── TC-A06: Remove row ────────────────────────────────────────────

    @Test(description = "TC-A06: Removing 'Studio Keyboard Pro' removes it from the visible catalog")
    public void testRemoveAccessoryRow() {
        AccessoriesPage page = accessoriesPage();
        int before = page.getVisibleRowCount();
        page.removeItemFromCatalog("Studio Keyboard Pro");
        int after = page.getVisibleRowCount();
        Assert.assertEquals(after, before - 1,
                "Row count should decrease by 1 after removing an item");
    }

    // ── TC-A07: Custom accessory ──────────────────────────────────────

    @Test(description = "TC-A07: Adding a valid custom accessory appends a new row to the catalog")
    public void testAddCustomAccessory() {
        AccessoriesPage page = accessoriesPage();
        int before = page.getVisibleRowCount();
        page.addCustomAccessory("My Custom Stand", "349");
        // Sheet modal should appear
        Assert.assertTrue(page.isSheetVisible(), "Sheet modal should appear after adding custom accessory");
        page.closeSheet();
        int after = page.getVisibleRowCount();
        Assert.assertEquals(after, before + 1,
                "Row count should increase by 1 after adding a custom item");
    }
}
