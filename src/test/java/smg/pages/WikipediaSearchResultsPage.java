package smg.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class WikipediaSearchResultsPage extends BasePage {
    private String searchTextBox = "$('#ooui-php-1')";
    private By messageForMatchingAPageNameText = By.xpath("//*[@id='mw-content-text']/div[3]/div[1]/p/b");
    private By messageForNotMatchingAPageNameText = By.cssSelector("#mw-content-text > div.searchresults.mw-searchresults-has-iw > div.mw-search-results-info > p > i");
    private By resultTotalCountLabel = By.xpath("//*[@id='mw-search-top-table']/div[2]/strong[2]");



    public WikipediaSearchResultsPage(WebDriver driver) {
        super(driver);
    }

    public void open(){
        driver.get("https://en.wikipedia.org/wiki/Main_Page");
    }

    public String getSearchText(){
        String script = "return " + searchTextBox + ".attr('value')";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String searchText = (String)js.executeScript(script);
        return searchText;
    }

    public String getMessageForMatchingAPageName(){
        WebElement searchField = driver.findElement(messageForMatchingAPageNameText);
        return searchField.getText();
    }

    public String getMessageForNotMatchingAPageName(){
        WebElement searchField = driver.findElement(messageForNotMatchingAPageNameText);
        return searchField.getText();
    }

    public int getTotalResults(){
        WebElement resultTotal = driver.findElement(resultTotalCountLabel);
        int number = Integer.valueOf(resultTotal.getText().replaceAll(",",""));
        return number;
    }

    public void validateSearchResultMatchingAPageName(String searchText, String actualMessage, int searchResultsCount){
        String searchTextOnSearchResultPage = getSearchText();
        Assert.assertEquals(searchTextOnSearchResultPage,searchText);
        String expectedMessage = String.format("There is a page named \"%s\" on Wikipedia",searchText);
        Assert.assertEquals(actualMessage,expectedMessage);
        Assert.assertTrue(searchResultsCount > 0);
    }

    public void validateSearchResultNotMatchingAPageName(String searchText, String actualMessage, int searchResultsCount){
        String searchTextOnSearchResultPage = getSearchText();
        Assert.assertEquals(searchTextOnSearchResultPage,searchText);
        String expectedMessage = String.format("The page \"%s\" does not exist. You can create a draft and submit it for review, but consider checking the search results below to see whether the topic is already covered.",searchText);
        Assert.assertEquals(actualMessage,expectedMessage);
        Assert.assertTrue(searchResultsCount > 0);
    }

}
