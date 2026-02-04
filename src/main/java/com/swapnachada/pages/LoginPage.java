package com.swapnachada.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By usernameInput = By.id("username");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By message = By.id("message");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(usernameInput));
    }

    public LoginPage typeUsername(String username) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(usernameInput));
        element.clear();
        element.sendKeys(username);
        return this;
    }

    public LoginPage typePassword(String password) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(passwordInput));
        element.clear();
        element.sendKeys(password);
        return this;
    }

    public LoginPage submit() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        return this;
    }

    public LoginPage login(String username, String password) {
        return typeUsername(username)
                .typePassword(password)
                .submit();
    }

    public String getMessage() {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(message, "Logged in as"));
        return driver.findElement(message).getText();
    }
}

