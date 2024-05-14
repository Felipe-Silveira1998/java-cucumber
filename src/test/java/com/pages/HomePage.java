package com.pages;

import com.maps.HomeMap;
import org.junit.Assert;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
public class HomePage extends BasePage{
    private final HomeMap homeMap = new HomeMap();
    public void homePageVisible() throws Exception {
        isDisplayed(homeMap.homePageIsVisible);
    }
    public void homeProductPage() throws Exception {
        String text = "Products";
        String realText = getText(homeMap.homeProductPage);

        Assert.assertEquals(text, realText);
        isDisplayed(homeMap.inventory);
    }

    public void homeOptions() throws Exception {
        click(homeMap.options);
    }

    public void logout() throws Exception {
        click(homeMap.logoutButton);
    }
}
