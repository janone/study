package com.example.cucumber;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class MyStepdefs {

    static ChromeDriver driver;
    {
        System.setProperty("webdriver.chrome.driver","src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Given("open")
    public void open() throws InterruptedException {
        driver.get("https://www.baidu.com");
        TimeUnit.SECONDS.sleep(2);
    }

    @When("input {}")
    public void input(String query) {
        WebElement element = driver.findElement(By.name("wd"));
        element.sendKeys(query);
        element.submit();
    }

    @Then("show {}")
    public void show(String title) {
        new WebDriverWait(driver,3).until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//a[text()='"+title+"']"))
        );
    }

    @After
    public void close() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        driver.quit();
    }
}
