package com.steps;

import com.data.manager.CentralizedData;
import com.enums.LogType;
import com.pages.LoginPage;
import com.util.Log;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static com.data.manager.CentralizedData.getCentralizedAccessData;

public class LoginSteps {
    private final LoginPage loginPage = new LoginPage();

    @Given("that access {string}")
    public void urlAccess(String url) throws Exception {

        url = CentralizedData.isCentralizedData(url) ? getCentralizedAccessData(url) : url;

        Log.sendLog(Log.createLog(this.getClass(), Log.getMethodName(), new Object[]{url}), LogType.INFO);

        loginPage.navigateAndLogin(url);
    }

    @When("log in with {string}")
    public void loginUser (String profile) throws Exception {
        loginPage.fillLoginProfile(profile);
        loginPage.submitLogin();
    }

    @Then("login error message appears {string}")
    public void errorToLogin (String error) throws Exception {
        loginPage.loginError(error);
    }

    @When("click login")
    public void clickLogin() throws Exception {
        loginPage.clickLoginButton();
    }

    @When("try to log in without username filled in")
    public void withoutUsername() throws Exception {
        loginPage.submitLoginWithoutUsername();
    }

    @When("try to log in without password filled in")
    public void withoutPassword() throws Exception {
        loginPage.submitLoginWithoutPassword();
    }

    @Then("i am redirected to login page")
    public void iAmRedirectedToLoginPage() throws Exception {
        loginPage.redirectedLoginScreen();
    }
}
