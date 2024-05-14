package com.maps;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CheckoutMap {
    public By checkoutButton = By.cssSelector("button[data-test='checkout']");
    public By checkoutTitle = By.cssSelector("span[data-test='title']");
    public By checkoutForm = By.cssSelector("div[class='checkout_info']");
    public By cancelButton = By.cssSelector("button[data-test='cancel']");
    public By continueButton = By.cssSelector("input[data-test='continue']");
    public By checkoutErrorMessage = By.cssSelector("div[class='error-message-container error']");
    public By lastNameField = By.cssSelector("input[data-test='lastName']");
    public By zipCodeField = By.cssSelector("input[data-test='postalCode']");
    public By firstNameField = By.cssSelector("input[data-test='firstName']");
    public By orderDetailsScreen = By.cssSelector("div[data-test='checkout-summary-container']");
}
