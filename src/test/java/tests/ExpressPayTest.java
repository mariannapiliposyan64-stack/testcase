package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ExpressPayPage;

/**
 * Tests for Section 3 – Express Pay (Actions API).
 *
 * TC-E01  Drag bundle to dropzone → tray text updates
 * TC-E02  Double-click Pay button → button text changes to "Authorized"
 * TC-E03  Right-click saved build → context menu appears
 * TC-E04  Clicking a context menu item → status text updates
 * TC-E05  Entering a quick serial and pressing Enter updates gesture status
 */
public class ExpressPayTest extends BaseTest {

    private ExpressPayPage expressPayPage() {
        return new ExpressPayPage(driver);
    }

    // ── TC-E01: Drag & Drop ───────────────────────────────────────────

    @Test(description = "TC-E01: Dragging bundle item to express tray updates the dropzone text")
    public void testDragBundleToTray() {
        ExpressPayPage page = expressPayPage();
        page.navigateTo();
        page.dragBundleToTray();
        Assert.assertTrue(page.getGestureStatusText().toLowerCase().contains("drag"),
                "Gesture status should mention 'drag' after drop");
    }

    // ── TC-E02: Double-Click Pay ──────────────────────────────────────

    @Test(description = "TC-E02: Double-clicking Pay button changes its text to indicate authorization")
    public void testDoubleClickPayButton() {
        ExpressPayPage page = expressPayPage();
        page.navigateTo();
        page.doubleClickPay();
        Assert.assertTrue(
                page.getDoubleClickPayBtnText().toLowerCase().contains("authorized"),
                "Pay button text should contain 'Authorized' after double-click");
    }

    // ── TC-E03: Right-click context menu ─────────────────────────────

    @Test(description = "TC-E03: Right-clicking saved build tile shows the macOS-style context menu")
    public void testRightClickShowsContextMenu() {
        ExpressPayPage page = expressPayPage();
        page.navigateTo();
        page.rightClickSavedBuild();
        Assert.assertTrue(page.isContextMenuVisible(),
                "Context menu should be visible after right-click");
    }

    // ── TC-E04: Context menu item action ─────────────────────────────

    @Test(description = "TC-E04: Selecting 'Load Build' from context menu updates gesture status")
    public void testContextMenuLoadBuild() {
        ExpressPayPage page = expressPayPage();
        page.navigateTo();
        page.rightClickSavedBuild();
        page.clickContextMenuItem("Load Build");
        Assert.assertTrue(page.getGestureStatusText().toLowerCase().contains("load"),
                "Gesture status should mention 'load' after selecting Load Build");
    }

    // ── TC-E05: Quick serial entry ───────────────────────────────────

    @Test(description = "TC-E05: Entering a quick serial and pressing Enter registers in gesture status")
    public void testQuickSerialEntry() {
        ExpressPayPage page = expressPayPage();
        page.navigateTo();
        page.enterQuickSerial("SN123456");
        // Status should no longer read the initial "Ready for express interaction"
        Assert.assertNotEquals(page.getGestureStatusText(), "Ready for express interaction",
                "Gesture status should update after entering a serial");
    }
}
