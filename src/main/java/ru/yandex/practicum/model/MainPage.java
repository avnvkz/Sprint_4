package ru.yandex.practicum.model;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {

    private static final String url = "https://qa-scooter.praktikum-services.ru/";
    //раздел с вопросами и ответами
    private final By sectionOfFAQS = By.xpath(".//div[@class = 'Home_FAQ__3uVm4']");
    //кнопка "Заказать" в заголовке страницы
    private final By headerOrderButton = By.xpath(".//button[@class ='Button_Button__ra12g']");
    //дублирующая кнопка "Заказать" внизу страницы
    private final By downOrderButton = By.xpath(".//button[@class ='Button_Button__ra12g Button_Middle__1CSJM']");
    private final WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(url);
    }

    public String getURL() {
        return url;
    }

    public void waitElementLoadOnPage (By element) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfElementLocated(element));
    }

    public void scrollPage(By element) {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(element));
    }

    public void goToFAQ () {
        waitElementLoadOnPage(sectionOfFAQS);
        scrollPage(sectionOfFAQS);
    }

    public String getAnswerToQuestion (String question) {
        driver.findElement(By.xpath(".//div[text() = '" + question + "']")).click();
        String answer = driver.findElement(By.xpath(".//div[not(@hidden)]/p")).getText();
        return answer;
    }

    public void orderFromHead() {
        waitElementLoadOnPage(headerOrderButton);
        driver.findElement(headerOrderButton).click();
    }

    public void orderFromDownPage() {
        waitElementLoadOnPage(downOrderButton);
        scrollPage(downOrderButton);
        driver.findElement(downOrderButton).click();
    }

    public void clickButtonOrder (String value) {
        switch (value) {
            case "head":
                orderFromHead();
                break;
            case "down":
                orderFromDownPage();
                break;
        }
    }
}