package com.qt.saucetrain.pageobject.ui;

import org.openqa.selenium.WebDriver;

public class BasePage {

    WebDriver browser;

    public BasePage(WebDriver driver){
        this.browser = driver;
    }

    public void launchPage(String pageId){
        this.browser.get("https://the-internet.herokuapp.com/dynamic_loading/" + pageId);
    }
}
