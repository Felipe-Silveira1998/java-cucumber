package com.maps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CartMap {
    public By backPack = By.cssSelector("button[data-test='add-to-cart-sauce-labs-backpack']");
    public By accessTheShoppingCart = By.cssSelector("a[data-test='shopping-cart-link']") ;
    public By productInCart = By.cssSelector("div[data-test='inventory-item']");
    public By removeBackPackToCart = By.cssSelector("button[data-test='remove-sauce-labs-backpack']");
    public By continueShoppingButton = By.cssSelector("button[data-test='continue-shopping']");
    public By cartContent = By.cssSelector("div[data-test='cart-contents-container']");
}
