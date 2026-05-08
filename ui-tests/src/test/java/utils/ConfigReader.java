package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();
    private static String baseUrl;

    static {
        loadConfig();
    }

    private static void loadConfig() {
        String configPath = System.getProperty("config.path",
                "src/test/resources/config.properties");
        try (InputStream input = new FileInputStream(configPath)) {
            properties.load(input);
            baseUrl = properties.getProperty("base.url", "http://localhost:4000");
        } catch (IOException e) {
            baseUrl = "http://localhost:4000";
        }
    }

    public static String getBaseUrl() {
        return baseUrl;
    }

    public static int getImplicitWait() {
        return Integer.parseInt(properties.getProperty("implicit.wait", "10"));
    }

    public static int getPageLoadTimeout() {
        return Integer.parseInt(properties.getProperty("page.load.timeout", "30"));
    }
}
