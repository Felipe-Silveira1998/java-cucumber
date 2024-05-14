package com.steps;
import com.pages.HomePage;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

public class HomeSteps {
    HomePage homePage = new HomePage();

    @Then("i am redirected to home page")
    public void redirectedToParaHomePage() throws Exception {
        homePage.homePageVisible();
    }

    @Then("i am redirected to the home product page")
    public void iAmRedirectedToTheProductPage() throws Exception {
        homePage.homeProductPage();
    }

    @And("click on options")
    public void clickOnOptions() throws Exception {
        homePage.homeOptions();
    }

    @When("click logout")
    public void clickLogout() throws Exception {
        homePage.logout();
    }
}
