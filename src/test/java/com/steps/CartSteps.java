package com.steps;
import com.pages.CartPage;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class CartSteps {
    private final CartPage cartPage = new CartPage();
    @When("add an item to cart")
    public void addAnItemToCart() throws Exception {
        cartPage.addItemToCart();
    }

    @Then("the cart icon will show the quantity of items in the cart")
    public void theCartIconWillShowTheQuantityOfItemsInTheCart() throws Exception {
        cartPage.itemsQuantity();
    }

    @And("access the shopping cart")
    public void accessTheShoppingCart() throws Exception {
        cartPage.accessCart();
    }

    @Then("the added product will be in the cart")
    public void theAddedProductWillBeInTheCart() throws Exception {
        cartPage.productInCart();
    }

    @When("Remove item to cart")
    public void removeItemToCart() throws Exception {
        cartPage.removeItem();
    }

    @Then("cart will be empty")
    public void cartWillBeEmpty() throws Exception {
        cartPage.cartWillBeEmpty();
    }

    @When("click continue shopping")
    public void clickContinueShopping() throws Exception {
        cartPage.ContinueShopping();
    }

    @Then("i am redirected to cart page")
    public void iAmRedirectedToCartPage() throws Exception {
        cartPage.redirectedToCartPage();
    }
}
