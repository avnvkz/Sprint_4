package ru.yandex.practicum.model;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {

    private static final String URL = "https://qa-scooter.praktikum-services.ru/";
    //раздел с вопросами и ответами
    private By SECTION_OF_FAQ = By.xpath(".//div[@class = 'Home_FAQ__3uVm4']");
    //кнопка "Заказать" в заголовке страницы
    private By HEADER_ORDER_BUTTON = By.xpath(".//button[@class ='Button_Button__ra12g']");
    //дублирующая кнопка "Заказать" внизу страницы
    private By DOWN_ORDER_BUTTON = By.xpath(".//button[@class ='Button_Button__ra12g Button_Middle__1CSJM']");
    private final WebDriver driver;

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get(URL);
    }

    public String getURL() {
        return URL;
    }

    public void waitElementLoadOnPage (By element) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfElementLocated(element));
    }

    public void scrollPage(By element) {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(element));
    }

    public void goToFAQ () {
        waitElementLoadOnPage(SECTION_OF_FAQ);
        scrollPage(SECTION_OF_FAQ);
    }

    public String getAnswerToQuestion (String question) {
        driver.findElement(By.xpath(".//div[text() = '" + question + "']")).click();
        String answer = driver.findElement(By.xpath(".//div[not(@hidden)]/p")).getText();
        return answer;
    }

    public void orderFromHead() {
        waitElementLoadOnPage(HEADER_ORDER_BUTTON);
        driver.findElement(HEADER_ORDER_BUTTON).click();
    }

    public void orderFromDownPage() {
        waitElementLoadOnPage(DOWN_ORDER_BUTTON);
        scrollPage(DOWN_ORDER_BUTTON);
        driver.findElement(DOWN_ORDER_BUTTON).click();
    }

    public void clickButtonOrder (boolean value) {
        if (value == true) {
            orderFromHead();
        } else {
            orderFromDownPage();
        }
    }
}