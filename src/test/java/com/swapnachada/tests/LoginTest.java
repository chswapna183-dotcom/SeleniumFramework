package com.swapnachada.tests;

import com.swapnachada.base.BaseTest;
import com.swapnachada.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URL;

public class LoginTest extends BaseTest {

    @Test
    public void shouldLoginAndShowMessage() {
        URL loginPageResource = getClass().getClassLoader().getResource("pages/login.html");
        Assert.assertNotNull(loginPageResource, "Test login page resource not found: pages/login.html");

        getDriver().get(loginPageResource.toExternalForm());

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login("demo", "secret");

        Assert.assertEquals(loginPage.getMessage(), "Logged in as demo");
    }
}

