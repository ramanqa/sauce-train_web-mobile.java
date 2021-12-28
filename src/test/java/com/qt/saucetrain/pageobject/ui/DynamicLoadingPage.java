package com.qt.saucetrain.pageobject.ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebElement;

public class DynamicLoadingPage extends BasePage{

    public DynamicLoadingPage(WebDriver driver){
        super(driver);
    }

    public String exampleName(){
        return this.browser.findElement(By.cssSelector("h4")).getText();
    }

    public String loadGreeting(){
        WebDriverWait wait = new WebDriverWait(this.browser, 30);
        //click start button
        this.browser.findElement(By.cssSelector("div#start button")).click();
        //synchronize for throbber
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div#finish h4")
            )
        )
        // return greeting text
        .getText();
    }  
}
