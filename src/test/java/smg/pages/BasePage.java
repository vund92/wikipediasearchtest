package smg.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {
    protected WebDriver driver;

    protected WebDriverWait wait;
    final int TIME_OUT_IN_SECONDS = 20;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    abstract public void open();

    public String getCurrentUrl(){
        return driver.getCurrentUrl();
    }

    public void check(WebElement element) {
        if (!element.isSelected()) {
            element.click();
        }
    }

    public void check(By locator) {
        if (!isSelected(locator)) {
           click(locator);
        }
    }

    public void fill(How how, String locator, String withText){
       driver.findElement( how.buildBy(locator)).sendKeys(withText);
    }
    public void fill(By locator, String withText){
        driver.findElement(locator).sendKeys(withText);
    }
    public void click(How how, String locator){
        driver.findElement( how.buildBy(locator)).click();
    }

    public void click(By locator){
        driver.findElement( locator).click();
    }

    public String text(How how, String locator){
        return driver.findElement( how.buildBy(locator)).getText();
    }

    public String text(By locator){
        return driver.findElement(locator).getText();
    }
    public void visit(String url){
        driver.get(url);
    }
    public boolean isDisplayed(How how, String locator){
        return driver.findElement( how.buildBy(locator)).isDisplayed();
    }

    public boolean isDisplayed(By locator){
        return driver.findElement( locator).isDisplayed();
    }

    public boolean isSelected(By locator){
        return driver.findElement(locator).isSelected();
    }

    public void executeScript(String script, Object... arguments) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(script, arguments);
    }

}
