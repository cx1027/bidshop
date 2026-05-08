package utils;

import io.github.cdimascio.dotenv.Dotenv;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();
    private static String baseUrl;

    static {
        loadEnv();
        loadConfig();
    }

    private static void loadEnv() {
        Dotenv dotenv = Dotenv.configure()
                .directory(".")
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();
        String envBaseUrl = dotenv.get("BASEURL");
        if (envBaseUrl != null && !envBaseUrl.isBlank()) {
            baseUrl = envBaseUrl.trim();
        } else {
            baseUrl = "http://localhost:5173";
        }
    }

    private static void loadConfig() {
        String configPath = System.getProperty("config.path",
                "src/test/resources/config.properties");
        try (InputStream input = new FileInputStream(configPath)) {
            properties.load(input);
        } catch (IOException e) {
            // Properties file is optional; .env already set the default.
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
