package com.qt.saucetrain.pageobject.test;

import org.openqa.selenium.WebDriver;

import org.testng.annotations.*;
import org.testng.Reporter;

import static org.assertj.core.api.Assertions.*;

import com.qt.saucetrain.pageobject.ui.DynamicLoadingPage;

public class DynamicLoadingTest extends BaseTest {

    DynamicLoadingPage dynamicLoadingPage;

    @BeforeMethod
    public void setupPageObjects(){
        this.dynamicLoadingPage = new DynamicLoadingPage(this.browser);
    }

    @Test
    public void testExample1Page(){
        this.dynamicLoadingPage.launchPage("1");
        assertThat(this.dynamicLoadingPage.exampleName()).isEqualTo("Example 1: Element on page that is hidden");
        assertThat(this.dynamicLoadingPage.loadGreeting()).isEqualTo("Hello World!");
    }

    @Test
    public void testExample2Page(){
        this.dynamicLoadingPage.launchPage("2");
        assertThat(this.dynamicLoadingPage.exampleName()).isEqualTo("Example 2: Element rendered after the fact");
        assertThat(this.dynamicLoadingPage.loadGreeting()).isEqualTo("Hello World!");
    }
}
