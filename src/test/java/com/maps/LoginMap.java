package com.maps;

import org.openqa.selenium.By;
public class LoginMap {

    public By login = By.cssSelector("input[data-test='username']");
    public By password = By.cssSelector("input[data-test='password']");
    public By buttonSubmit = By.cssSelector("input[data-test='login-button']");
    public By failedLogin = By.cssSelector("div[class='error-message-container error'] h3");
    public By loginScreen = By.cssSelector("div[data-test='login-container']");
}
