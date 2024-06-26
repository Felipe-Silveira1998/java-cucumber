package com.core;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class DriverFactory {

    public static String OS = System.getProperty("os.name").toLowerCase();
    public static String driverName = System.getProperty("driverName") != null ? System.getProperty("driverName") : "chromedriver.exe";

    private DriverFactory() {
    }

    ;

    private static RemoteWebDriver driver;

    private static String browserName = System.getProperty("browserName") != null ? System.getProperty("browserName").toLowerCase()
            : "chrome";
    private static boolean runRemote = System.getProperty("runRemote") != null
            ? System.getProperty("runRemote").toLowerCase().equals("true") ? true : false
            : false;

    private static ChromeOptions createChromeOptions() {
        ChromeOptions options = new ChromeOptions();

        if (runRemote) {
            options.addArguments("--profile-directory=Default");
            options.addArguments("--user-data-dir=/chrome-profiles-host/User Data/selenium-profile");
        } else {
//            options.addArguments("--user-data-dir=" + CHROME_PROFILE_DIR);
        }
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        options.addArguments("--start-maximized");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--verbose");
        options.addArguments("force-device-scale-factor=0.90");
        //this can cause problems in the application and it may have a maninthemidle so you can use it however you want, but the "your connection is not private" stop appearing, that's what I did
        options.addArguments("--ignore-certificate-errors");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.addArguments("--window-size=1920,1080");
        String runHeadless = System.getProperty("runHeadless");
        if (runHeadless == null) {
            System.setProperty("runHeadless", "false");
        } else {
            if (runHeadless.equalsIgnoreCase("true")) {
                options.addArguments("--headless");
                options.addArguments("window-size=1920x1080");
            }
        }
        HashMap<String, Object> chromePrefs = new HashMap<>();
        if (System.getProperty("remote") == null) {
            chromePrefs.put("profile.default_content_settings.popups", 0);
            // chromePrefs.put("download.default_directory", System.getProperty("user.dir")
            // + File.separator + "data");
            chromePrefs.put("plugins.always_open_pdf_externally", false);
        }
        chromePrefs.put("credentials_enable_service", false);
        chromePrefs.put("profile.password_manager_enabled", false);
        chromePrefs.put("profile.exit_type", "normal");
        options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        options.setExperimentalOption("prefs", chromePrefs);
        options.setCapability(ChromeOptions.CAPABILITY, options);
        return options;
    }

    public static FirefoxOptions createFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addPreference("dom.webnotifications.enabled", false);
        options.addPreference("dom.push.enabled", false);
        // FirefoxProfile profile = new FirefoxProfile (new File
        // (System.getProperty("user.dir")+File.separator+"firefox-profiles"+File.separator+"default"));
        ProfilesIni profilesIni = new ProfilesIni();
        FirefoxProfile profile = new FirefoxProfile();
        // profilesIni.getProfile(profileName);
        // profile.addExtension(
        //        new File(System.getProperty("user.dir") + File.separator + "extensions" + File.separator + "SalesforceInspector.xpi"));
        options.setProfile(profile);
        options.setCapability(FirefoxDriver.PROFILE, profile);
        return options;
    }

    private static void setOperationalSystemAndSetWebDriver() {
        String webdriverProperty = new String();
        String webDriverFileName = new String();

        switch (browserName) {
            case "firefox":
                webdriverProperty = "webdriver.gecko.driver";
                webDriverFileName = "geckodriver_v0.26.0";
                break;
            case "edge":
                webdriverProperty = "webdriver.edge.driver";
                webDriverFileName = OS.contains("windows") ? "msedgedriverWin" : "msedgedriver";
                break;
            default:
                webdriverProperty = "webdriver.chrome.driver";
                webDriverFileName = OS.contains("windows") ? "chromedriver.exe" : "chromedriver.exe";
        }
        String driverPath = System.getProperty("driverPath");
        if (driverPath != null) {
            System.setProperty(webdriverProperty, driverPath);
        } else {
            driverPath = System.getProperty("user.dir") + File.separator + "driver" + File.separator;
            if (OS.contains("windows")) {
                System.setProperty(webdriverProperty, driverPath + webDriverFileName);
            } else if (OS.contains("mac os x")) {
                System.setProperty(webdriverProperty, driverPath + webDriverFileName);
            } else {
                System.setProperty(webdriverProperty, driverPath + driverName);
            }
        }
    }

    public static RemoteWebDriver getDriver() throws Exception {
        if (driver == null || driver.toString().toLowerCase().contains("null")) {

            switch (browserName) {
                case "firefox":
                    setOperationalSystemAndSetWebDriver();
                    driver = new FirefoxDriver(createFirefoxOptions());
                    driver.manage().window().maximize();
                    break;
                default:
                    setOperationalSystemAndSetWebDriver();
                    if (runRemote) {
                        try {
                            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), createChromeOptions());
                        } catch (Exception e) {
                            throw e;
                        }
                    } else {
                        driver = new ChromeDriver(createChromeOptions());
                    }
            }
            driver.manage().window().maximize();

            driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        }
        return driver;
    }



    public static void killDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
