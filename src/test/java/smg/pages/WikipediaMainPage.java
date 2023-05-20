package smg.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WikipediaMainPage extends BasePage {

    private By searchTextBox = By.name("search");
    private By searchForPagesContainingSpecificTextOption = By.xpath("//span/strong/parent::span/parent::span/parent::a/parent::li");

    public WikipediaMainPage(WebDriver driver) {
        super(driver);
    }

    public void open(){
        driver.get("https://en.wikipedia.org/wiki/Main_Page");
        driver.manage().window().maximize();
    }

    public void searchForPagesContainingSpecificText(String searchText){
        WebElement searchField = driver.findElement(searchTextBox);
        searchField.sendKeys(searchText);
        try{
            Thread.sleep(5000);
        }
        catch(InterruptedException e){}
        WebElement searchForPagesContainingTextOption = driver.findElement(searchForPagesContainingSpecificTextOption);
        searchForPagesContainingTextOption.click();
        new WebDriverWait(driver, Duration.ofSeconds(TIME_OUT_IN_SECONDS));
    }
}
