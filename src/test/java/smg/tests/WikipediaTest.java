package smg.tests;

import org.openqa.selenium.support.ui.WebDriverWait;
import smg.pages.WikipediaMainPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import smg.pages.WikipediaSearchResultsPage;

import java.time.Duration;

public class WikipediaTest extends BaseTest {

    WikipediaMainPage wikipediaMainPage;

    @BeforeMethod
    void openPage(){
        wikipediaMainPage = new WikipediaMainPage(driver);
        wikipediaMainPage.open();

    }

    /** Todo:
     *      Scenario: Verify search results of a search matching with a page name on Wikipedia
     *      Given I access to https://en.wikipedia.org/wiki/Main_Page
     *      When I fill <Software Testing> to "Search Wikipedia" text box
     *      And I select "Search for pages containing Software Testing" option
     *      Then I verify the search results on Search Results Page
     */
    @Test
    void searchTextMatchingWithAPageNameOnWikipedia() {
        String searchText = "Software Testing";
        wikipediaMainPage.searchForPagesContainingSpecificText(searchText);
        new WebDriverWait(driver, Duration.ofSeconds(TIME_OUT_IN_SECONDS));

        WikipediaSearchResultsPage wikipediaSearchResultsPage = new WikipediaSearchResultsPage(driver);
        String searchTextOnSearchResultPage = wikipediaSearchResultsPage.getSearchText();
        String actualMessage = wikipediaSearchResultsPage.getMessageForMatchingAPageName();
        int searchResultCount = wikipediaSearchResultsPage.getTotalResults();

        wikipediaSearchResultsPage.validateSearchResultMatchingAPageName(searchTextOnSearchResultPage, actualMessage, searchResultCount);
    }

    /** Todo:
     *      Scenario: Verify search results of a search not matching with any page name on Wikipedia
     *      Given I access https://en.wikipedia.org/wiki/Main_Page
     *      When I fill <Software Testing 123> to "Search Wikipedia" text box
     *      And I select "Search for pages containing Software Testing" option
     *      Then I verify the search results on Search Results Page
     * */
    @Test
    void searchTextNotMatchingWithAnyPageNameOnWikipedia(){
        String searchText = "Software Testing 123";
        wikipediaMainPage.searchForPagesContainingSpecificText(searchText);
        new WebDriverWait(driver, Duration.ofSeconds(TIME_OUT_IN_SECONDS));

        WikipediaSearchResultsPage wikipediaSearchResultsPage = new WikipediaSearchResultsPage(driver);
        String searchTextOnSearchResultPage = wikipediaSearchResultsPage.getSearchText();
        String actualMessage = wikipediaSearchResultsPage.getMessageForNotMatchingAPageName();
        int searchResultCount = wikipediaSearchResultsPage.getTotalResults();

        wikipediaSearchResultsPage.validateSearchResultNotMatchingAPageName(searchTextOnSearchResultPage, actualMessage, searchResultCount);
    }

}
