package com.maps;

import org.openqa.selenium.By;
public class HomeMap {
    public By homePageIsVisible = By.cssSelector("div[class='app_logo']");
    public By homeProductPage = By.cssSelector("span[data-test='title']");
    public By inventory = By.cssSelector("span[data-test='inventory-container']");
    public By options = By.cssSelector("button[id='react-burger-menu-btn']");
    public By logoutButton = By.cssSelector("a[id='logout_sidebar_link']");
}
