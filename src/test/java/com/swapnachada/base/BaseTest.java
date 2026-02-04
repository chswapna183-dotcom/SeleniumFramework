package com.swapnachada.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.time.Duration;
import java.util.Locale;

public abstract class BaseTest {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser", "headless"})
    public void setUp(@Optional("chrome") String browser, @Optional("false") String headless) {
        String browserOverride = System.getProperty("browser");
        if (browserOverride != null && !browserOverride.isBlank()) {
            browser = browserOverride;
        }

        String headlessOverride = System.getProperty("headless");
        if (headlessOverride != null && !headlessOverride.isBlank()) {
            headless = headlessOverride;
        }

        boolean isHeadless = Boolean.parseBoolean(headless);
        DRIVER.set(createDriver(browser, isHeadless));

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        getDriver().manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
        if (isHeadless) {
            getDriver().manage().window().setSize(new Dimension(1920, 1080));
        } else {
            getDriver().manage().window().maximize();
        }
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebDriver driver = DRIVER.get();
        try {
            if (driver != null) {
                driver.quit();
            }
        } finally {
            DRIVER.remove();
        }
    }

    protected WebDriver getDriver() {
        WebDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized. Did you forget to extend BaseTest?");
        }
        return driver;
    }

    private WebDriver createDriver(String browser, boolean headless) {
        String normalizedBrowser = (browser == null ? "chrome" : browser.trim()).toLowerCase(Locale.ROOT);

        return switch (normalizedBrowser) {
            case "chrome" -> {
                ChromeOptions options = new ChromeOptions();
                if (headless) {
                    options.addArguments("--headless=new");
                    options.addArguments("--window-size=1920,1080");
                }
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--no-sandbox");
                WebDriverManager.chromedriver().setup();
                yield new ChromeDriver(options);
            }
            case "firefox" -> {
                FirefoxOptions options = new FirefoxOptions();
                if (headless) {
                    options.addArguments("-headless");
                }
                WebDriverManager.firefoxdriver().setup();
                yield new FirefoxDriver(options);
            }
            case "edge" -> {
                EdgeOptions options = new EdgeOptions();
                if (headless) {
                    options.addArguments("--headless=new");
                    options.addArguments("--window-size=1920,1080");
                }
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--no-sandbox");
                WebDriverManager.edgedriver().setup();
                yield new EdgeDriver(options);
            }
            default -> throw new IllegalArgumentException(
                    "Unsupported browser: " + browser + ". Use chrome, firefox, or edge."
            );
        };
    }
}

