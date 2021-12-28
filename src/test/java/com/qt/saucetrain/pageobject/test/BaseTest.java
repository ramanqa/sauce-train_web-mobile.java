package com.qt.saucetrain.pageobject.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.URL;

import org.testng.annotations.*;

public class BaseTest {

    WebDriver browser;

    @BeforeMethod
    public void _setUp() throws Exception{
        //ChromeOptions chromeOptions = new ChromeOptions();
        //browser = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chromeOptions);
        this.browser = new ChromeDriver();
    }

    @AfterMethod
    public void tearDown(){
        this.browser.quit();
    }

}
