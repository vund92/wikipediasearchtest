package smg.tests;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
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

    /** Todo:
     *      Scenario: Verify search results of a search not matching with any page name on Wikipedia
     *      Given I access https://en.wikipedia.org/wiki/Main_Page
     *      When I fill <Software Testing 123> to "Search Wikipedia" text box
     *      And I select "Search for pages containing Software Testing" option
     *      Then I verify the search results on Search Results Page
     * */

    /** Todo:
     *      Scenario: Verify search results with no result found
     *      Given I access https://en.wikipedia.org/wiki/Main_Page
     *      When I fill <thisisfornoresultfoundsearch> to "Search Wikipedia" text box
     *      And I select "Search for pages containing Software Testing" option
     *      Then I verify the search results on Search Results Page
     * */
    @DataProvider
    Object[][] searchTextData(){
        return new Object[][]{
                {"MatchingAPageName","Software Testing"},
                {"NotMatchingAPageName","Software Testing 123"},
                {"NoResultFound", "thisisfornoresultfoundsearch"},
        };
    }
    @Test(dataProvider = "searchTextData")
    void validateSearchResult(String resultType, String searchText){
        wikipediaMainPage.searchForPagesContainingSpecificText(searchText);
        new WebDriverWait(driver, Duration.ofSeconds(TIME_OUT_IN_SECONDS));

        WikipediaSearchResultsPage wikipediaSearchResultsPage = new WikipediaSearchResultsPage(driver);
        String searchTextOnSearchResultPage = wikipediaSearchResultsPage.getSearchText();
        int searchResultCount = wikipediaSearchResultsPage.getTotalResults();

        String actualMessage;

        switch (resultType){
            case "MatchingAPageName":
                actualMessage = wikipediaSearchResultsPage.getMessageForMatchingAPageName();
                wikipediaSearchResultsPage.validateSearchResultMatchingAPageName(searchTextOnSearchResultPage, actualMessage, searchResultCount);
                break;
            case "NotMatchingAPageName":
                actualMessage = wikipediaSearchResultsPage.getMessageForNotMatchingAPageName();
                wikipediaSearchResultsPage.validateSearchResultNotMatchingAPageName(searchTextOnSearchResultPage, actualMessage, searchResultCount);
                break;
            case "NoResultFound":
                actualMessage = wikipediaSearchResultsPage.getMessageForNoResultFound();
                wikipediaSearchResultsPage.validateNoResultFound(searchTextOnSearchResultPage, actualMessage, searchResultCount);
                break;
        }
    }
}
