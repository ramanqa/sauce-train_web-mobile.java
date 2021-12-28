package com.qt.saucetrain.sauceexecution;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;

import java.util.HashMap;
import java.util.Map;
import java.net.URL;
import java.lang.reflect.Method;

import org.testng.annotations.*;
import org.testng.Reporter;
import org.testng.ITestResult;

import static org.assertj.core.api.Assertions.*;

public class GoogleTranslateTest {

    public static final ThreadLocal<WebDriver> browser = new ThreadLocal<>();

    @BeforeMethod
    public void setUp(Method method) throws Exception{
        ChromeOptions chromeOptions = new ChromeOptions();
        //WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chromeOptions);
        WebDriver driver = new RemoteWebDriver(new URL("https://ramanqa:76450e62-8e4c-495f-8a0b-12a27a2b8520@ondemand.us-west-1.saucelabs.com:443/wd/hub"), chromeOptions);
        browser.set(driver);
        String testName = "RamandeeQE:" + this.getClass().getSimpleName() + ":" + method.getName();
        ((JavascriptExecutor)browser.get()).executeScript("sauce:job-name=" + testName);
        browser.get().get("https://translate.google.com/");
    }

    @AfterMethod
    public void tearDown(ITestResult testResult){
        ((JavascriptExecutor)browser.get()).executeScript("sauce:job-result=" + testResult.isSuccess());
        browser.get().quit();
    }

    @DataProvider (name = "translationTestData", parallel = true)
    public Object[][] tralsationTestDataProvider() {
        Map<String, String> d1 = new HashMap<>();
        d1.put("textToTranslate", "Hello World");
        d1.put("fromLanguage", "english");
        d1.put("toLanguage", "french");
        d1.put("translatedText", "Bonjour le monde");
        Map<String, String> d2 = new HashMap<>();
        d2.put("textToTranslate", "Como estas");
        d2.put("fromLanguage", "spanish");
        d2.put("toLanguage", "english");
        d2.put("translatedText", "How are you");
        return new Object [][] {
          {d1}, {d2}
        };
    }

    @Test(dataProvider = "translationTestData")
    public void autoDetectFromLanguageShouldBeCorrect(Map translationTestData){
        // type text to translate
        browser.get().findElement(By.cssSelector("textarea")).sendKeys((String)translationTestData.get("textToTranslate"));
        WebDriverWait wait = new WebDriverWait(browser.get(), 30);
       
        // wait for language detection
        wait.until(new ExpectedCondition<Boolean>(){
            public Boolean apply(WebDriver browser){
                return browser.findElement(By.cssSelector("button[role='tab'] span")).getText().endsWith("DETECTED");
            }
        });

        // capture detected language
        String detectedLanguage = browser.get().findElement(By.cssSelector("button[role='tab'] span")).getText().split(" - ")[0];
        
        // assert
        assertThat(detectedLanguage).isEqualToIgnoringCase((String)translationTestData.get("fromLanguage"));       
    }

    @Test(dataProvider = "translationTestData")
    public void translatedTextShouldBeCorrect(Map translationTestData) throws Exception{
        // select to language
        WebDriverWait wait = new WebDriverWait(browser.get(), 30);
        browser.get().findElement(By.cssSelector("button[aria-label='More target languages']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".ykTHSe div.qSb8Pe div.Llmcnf")));
        for(WebElement langElement:browser.get().findElements(By.cssSelector(".ykTHSe div.qSb8Pe div.Llmcnf"))){
            if(langElement.getText().equalsIgnoreCase((String)translationTestData.get("toLanguage"))){
                langElement.click();
                break;
            }
        }

        // type text to translate
        browser.get().findElement(By.cssSelector("textarea")).sendKeys((String)translationTestData.get("textToTranslate"));
        
        // wait for language detection
        wait.until(new ExpectedCondition<Boolean>(){
            public Boolean apply(WebDriver browser){
                return browser.findElement(By.cssSelector("button[role='tab'] span")).getText().endsWith("DETECTED");
            }
        });

        // capture detected language
        String detectedLanguage = browser.get().findElement(By.cssSelector("button[role='tab'] span")).getText().split(" - ")[0];

        // capture translated text
        String actualTranslatedText = browser.get().findElement(By.cssSelector("div.J0lOec span[jsname='W297wb']")).getText();

        //assert
        assertThat(actualTranslatedText).isEqualToIgnoringCase((String)translationTestData.get("translatedText"));
    }

}
