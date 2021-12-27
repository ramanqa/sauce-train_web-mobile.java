import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.*;
import org.testng.Reporter;

import static org.assertj.core.api.Assertions.*;

public class GoogleTranslateTest {

    WebDriver browser;

    @BeforeMethod
    public void setUp(){
        browser = new ChromeDriver();
        browser.get("https://translate.google.co.in/");
    }

    @AfterMethod
    public void tearDown(){
        browser.quit();
    }

    @DataProvider (name = "translationTestData")
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
        browser.findElement(By.cssSelector("textarea")).sendKeys((String)translationTestData.get("textToTranslate"));
        WebDriverWait wait = new WebDriverWait(browser, 30);
       
        // wait for language detection
        wait.until(new ExpectedCondition<Boolean>(){
            public Boolean apply(WebDriver browser){
                return browser.findElement(By.cssSelector("button[role='tab'] span")).getText().endsWith("DETECTED");
            }
        });

        // capture detected language
        String detectedLanguage = browser.findElement(By.cssSelector("button[role='tab'] span")).getText().split(" - ")[0];
        
        // assert
        assertThat(detectedLanguage).isEqualToIgnoringCase((String)translationTestData.get("fromLanguage"));       
    }

    @Test(dataProvider = "translationTestData")
    public void translatedTextShouldBeCorrect(Map translationTestData) throws Exception{
        // select to language
        WebDriverWait wait = new WebDriverWait(browser, 30);
        browser.findElement(By.cssSelector("button[aria-label='More target languages']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".ykTHSe div.qSb8Pe div.Llmcnf")));
        for(WebElement langElement:browser.findElements(By.cssSelector(".ykTHSe div.qSb8Pe div.Llmcnf"))){
            if(langElement.getText().equalsIgnoreCase((String)translationTestData.get("toLanguage"))){
                langElement.click();
                break;
            }
        }

        // type text to translate
        browser.findElement(By.cssSelector("textarea")).sendKeys((String)translationTestData.get("textToTranslate"));
        
        // wait for language detection
        wait.until(new ExpectedCondition<Boolean>(){
            public Boolean apply(WebDriver browser){
                return browser.findElement(By.cssSelector("button[role='tab'] span")).getText().endsWith("DETECTED");
            }
        });

        // capture detected language
        String detectedLanguage = browser.findElement(By.cssSelector("button[role='tab'] span")).getText().split(" - ")[0];

        // capture translated text
        String actualTranslatedText = browser.findElement(By.cssSelector("div.J0lOec span[jsname='W297wb']")).getText();

        //assert
        assertThat(actualTranslatedText).isEqualToIgnoringCase((String)translationTestData.get("translatedText"));
    }

}
