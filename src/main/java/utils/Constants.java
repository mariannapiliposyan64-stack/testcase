package utils;

public class Constants {
    public static final String BASE_URL = "http://192.168.10.35:5050/";

    // Sections (anchors)
    public static final String SECTION_CONFIGURATOR = BASE_URL + "#configurator";
    public static final String SECTION_ACCESSORIES   = BASE_URL + "#accessories";
    public static final String SECTION_EXPRESS       = BASE_URL + "#express-desk";
    public static final String SECTION_CHECKOUT      = BASE_URL + "#checkout";

    // Download links
    public static final String DOWNLOAD_RECEIPT = BASE_URL + "download/order-receipt.txt";
    public static final String DOWNLOAD_CATALOG  = BASE_URL + "download/accessories-catalog.csv";

    // Timeouts
    public static final int DEFAULT_TIMEOUT_SEC   = 10;
    public static final int ANIMATION_WAIT_MS     = 2500;
}
