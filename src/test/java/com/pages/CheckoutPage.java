package com.pages;
import com.maps.CheckoutMap;
import org.junit.Assert;
import com.github.javafaker.Faker;
public class CheckoutPage extends BasePage {
    CheckoutMap checkoutMap = new CheckoutMap();
    Faker faker = new Faker();
    String firstName = faker.name().firstName();
    String lastName = faker.name().firstName();
    String zipCode = faker.address().zipCode();
    public void checkout() throws Exception {
        click(checkoutMap.checkoutButton);
    }
    public void checkoutScreen() throws Exception {
        String title = "Checkout: Your Information";
        String realTitle = getText(checkoutMap.checkoutTitle);

        Assert.assertEquals(title, realTitle);
        isDisplayed(checkoutMap.checkoutForm);
    }
    public void cancelCheckout() throws Exception {
        click(checkoutMap.cancelButton);
    }
    public void continueCheckout() throws Exception {
        click(checkoutMap.continueButton);
    }
    public void checkoutError(String message) throws Exception {
        String messageError = getText(checkoutMap.checkoutErrorMessage);
        Assert.assertEquals(messageError, message);
    }
    public void fillLastNameAndZipCode() throws Exception {
        sendKeysSimple(checkoutMap.lastNameField, lastName);
        sendKeysSimple(checkoutMap.zipCodeField, zipCode);
    }
    public void fillFirstNameAndZipCode() throws Exception {
        sendKeysSimple(checkoutMap.firstNameField, firstName);
        sendKeysSimple(checkoutMap.zipCodeField, zipCode);
    }
    public void fillFirstNameAndLastName() throws Exception {
        sendKeysSimple(checkoutMap.firstNameField, firstName);
        sendKeysSimple(checkoutMap.lastNameField, lastName);
    }

    public void fillAllDeliveryInformation() throws Exception {
        sendKeysSimple(checkoutMap.firstNameField, firstName);
        sendKeysSimple(checkoutMap.lastNameField, lastName);
        sendKeysSimple(checkoutMap.zipCodeField, zipCode);
    }
    public void orderDetailsScreen() throws Exception {
        isDisplayed(checkoutMap.orderDetailsScreen);
    }
}
