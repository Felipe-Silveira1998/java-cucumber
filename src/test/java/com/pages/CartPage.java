package com.pages;

import com.maps.CartMap;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

public class CartPage extends BasePage {
    private CartMap cartMap = new CartMap();
    public void addItemToCart() throws Exception {
        click(cartMap.backPack);
    }
    public void itemsQuantity() throws Exception {
        String quantity = "1";
        String realQuantity = getText(cartMap.accessTheShoppingCart);

        Assert.assertEquals(quantity, realQuantity);
    }
    public void accessCart() throws Exception {
        clickJs(cartMap.accessTheShoppingCart);
    }
    public void productInCart() throws Exception {
        isDisplayed(cartMap.productInCart);
    }
    public void removeItem() throws Exception {
        click(cartMap.removeBackPackToCart);
    }
    public void cartWillBeEmpty() throws Exception {
        String productInCart = String.valueOf(cartMap.productInCart);
        Assert.assertFalse("O carrinho não está vazio.", productInCart.isEmpty());
    }
    public void ContinueShopping() throws Exception {
        click(cartMap.continueShoppingButton);
    }
    public void redirectedToCartPage() throws Exception {
        isDisplayed(cartMap.cartContent);
    }
}
