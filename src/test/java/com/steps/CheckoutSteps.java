package com.steps;

import com.pages.CheckoutPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CheckoutSteps {
    CheckoutPage checkoutPage = new CheckoutPage();

    @When("click checkout")
    public void addAnItemToCart() throws Exception {
        checkoutPage.checkout();
    }

    @Then("i am redirected to checkout page")
    public void iAmRedirectedToCheckoutPage() throws Exception {
        checkoutPage.checkoutScreen();
    }

    @When("click cancel")
    public void clickCancel() throws Exception {
        checkoutPage.cancelCheckout();
    }

    @When("click continue")
    public void clickContinue() throws Exception {
        checkoutPage.continueCheckout();
    }

    @Then("error message is displayed {string}")
    public void errorMessageIsDisplayed(String message) throws Exception {
        checkoutPage.checkoutError(message);
    }

    @And("fill in only the last name and zip code")
    public void fillInOnlyTheLastNameAndZipCode() throws Exception {
        checkoutPage.fillLastNameAndZipCode();
    }

    @And("fill in only the first name and zip code")
    public void fillInOnlyTheFirstNameAndZipCode() throws Exception {
        checkoutPage.fillFirstNameAndZipCode();
    }

    @And("fill in only the first name and last name")
    public void fillInOnlyTheFirstNameAndLastName() throws Exception {
        checkoutPage.fillFirstNameAndLastName();
    }

    @And("fill delivery information")
    public void fillDeliveryInformation() throws Exception {
        checkoutPage.fillAllDeliveryInformation();
    }

    @Then("redirected to order details")
    public void redirectedToOrderDetails() throws Exception {
        checkoutPage.orderDetailsScreen();
    }
}
