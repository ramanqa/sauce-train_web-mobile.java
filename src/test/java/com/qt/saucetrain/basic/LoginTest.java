package com.qt.saucetrain.basic;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;
import java.net.URL;

import org.testng.annotations.*;
import org.testng.Reporter;

import static org.assertj.core.api.Assertions.*;

public class LoginTest {

    WebDriver browser;

    @BeforeMethod
    public void setUp() throws Exception{
        //ChromeOptions chromeOptions = new ChromeOptions();
        //browser = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chromeOptions);
        browser = new ChromeDriver();
        browser.get("http://the-internet.herokuapp.com/login");
    }

    @AfterMethod
    public void tearDown(){
        browser.quit();
    }

    @Test()
    public void testSuccessfulLogin(){
        // login
        browser.findElement(By.cssSelector("input#username")).sendKeys("tomsmith");
        browser.findElement(By.cssSelector("input#password")).sendKeys("SuperSecretPassword!");
        browser.findElement(By.cssSelector("button[type='submit']")).click();
        // assert 
        assertThat(browser.findElement(By.cssSelector("div#flash.flash.success")).isDisplayed()).isTrue();
        assertThat(browser.findElement(By.cssSelector("a.button")).getText()).contains("Logout");
    }

}
