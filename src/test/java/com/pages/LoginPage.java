package com.pages;
import com.maps.LoginMap;
import org.junit.Assert;
public class LoginPage extends BasePage {
    public String user;
    public String password;

    private final LoginMap loginMap = new LoginMap();

    public void navigateAndLogin(String url) throws Exception {
        navigate(url);
    }
    public void fillLoginProfile (String profile) {

        switch (profile){
            case "standard":
                user = "standard_user";
                password = "secret_sauce";
                break;
            case "locked":
                user = "locked_out_user";
                password = "secret_sauce";
                break;

            case "problem":
                user = "problem_user";
                password = "secret_sauce";
                break;

            case "performance":
                user = "performance_glitch_user";
                password = "secret_sauce";
                break;

            case "error":
                user = "error_user";
                password = "secret_sauce";
                break;

            case "visual":
                user = "visual_user";
                password = "secret_sauce";
                break;

            case "invalidUser":
                user = "visual_username";
                password = "secret_sauce";
                break;

            case "invalidPassword":
                user = "visual_user";
                password = "1234";
                break;

            case "non_Existent-User":
                user = "1234";
                password = "1234";
                break;

            case "blockedUser":
                user = "1234";
                password = "1234";
                break;
        }
    }
    public void submitLogin() throws Exception {
        sendKeysSimple(loginMap.login, user);
        sendKeysSimple(loginMap.password,password);
        click(loginMap.buttonSubmit);
    }
    public void submitLoginWithoutUsername() throws Exception {
        sendKeysSimple(loginMap.password,"1234");
        click(loginMap.buttonSubmit);
    }
    public void submitLoginWithoutPassword() throws Exception {
        sendKeysSimple(loginMap.login,"visual_username");
        click(loginMap.buttonSubmit);
    }
    public void loginError(String error) throws  Exception {
        String messageError = getText(loginMap.failedLogin);
        Assert.assertEquals(messageError, error);
    }
    public void clickLoginButton() throws  Exception {
        click(loginMap.buttonSubmit);
    }
    public void redirectedLoginScreen() throws Exception {
        isDisplayed(loginMap.loginScreen);
    }
}
