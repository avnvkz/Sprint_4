package ru.yandex.practicum.model;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {
    private static final String url = "https://qa-scooter.praktikum-services.ru/order";
    //логотип Самокат
    private final By logoScooter = By.xpath(".//a[@class = 'Header_LogoScooter__3lsAR']");
    //логотип Яндекс
    private final By logoYandex = By.xpath(".//a[@class = 'Header_LogoYandex__3TSOI']");
    //поле для ввода "Имя"
    private final By inputName = By.xpath(".//input[@placeholder ='* Имя']");
    //поле для ввода "Фамилия"
    private final By inputSurname = By.xpath(".//input[@placeholder ='* Фамилия']");
    //поле для ввода "Адрес"
    private final By inputAddress = By.xpath(".//input[@placeholder ='* Адрес: куда привезти заказ']");
    //полк для ввода "Станция метро"
    private final By inputStation = By.xpath(".//input[@placeholder ='* Станция метро']");
    //выпадающий список "Станция метро"
    private final By listOfStation = By.xpath(".//div[@class ='select-search__select']");
    //поле для ввода "Телефон"
    private final By inputPhone = By.xpath(".//input[@placeholder ='* Телефон: на него позвонит курьер']");
    //поле для ввода "Когда привезти самокат"
    private final By inputDate = By.xpath(".//input[@placeholder ='* Когда привезти самокат']");
    //поле "Срок аренды"
    private final By inputRentalPeriod = By.xpath(".//div[@class='Dropdown-control']");
    //выпадающий список "Срок аренды"
    private final By listOfRentalPeriod = By.xpath(".//div[@class = 'Dropdown-menu']");
    //чекбокс "черный жемчуг"
    private final By checkboxBlack = By.xpath(".//input[@id = 'black']");
    //чекбокс "серая безысходность"
    private final By checkboxGray = By.xpath(".//input[@id = 'grey']");
    //поле для ввода "Комментарий для курьера"
    private final By inputComment = By.xpath(".//input[@placeholder = 'Комментарий для курьера']");
    //модальное окно для подтверждения заказа
    private final By modalOrder = By.xpath(".//div[@class = 'Order_Modal__YZ-d3']");
    //заголовок модального окна
    private final By modalHeadOrder = By.xpath(".//div[@class = 'Order_ModalHeader__3FDaJ']");
    private final WebDriver driver;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openOrderPage() {
        driver.get(url);
    }

    public void waitElementLoadOnPage(By element) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.presenceOfElementLocated(element));
    }

    public void fillField(By element, String value) {
        waitElementLoadOnPage(element);
        driver.findElement(element).clear();
        driver.findElement(element).sendKeys(value);
    }

    public void clickNext(String text) {
        waitElementLoadOnPage(By.xpath(".//button[@class = 'Button_Button__ra12g Button_Middle__1CSJM' and text() = '" + text + "']"));
        driver.findElement(By.xpath(".//button[@class = 'Button_Button__ra12g Button_Middle__1CSJM' and text() = '" + text + "']")).click();
    }

    public void fillFormForWhomTheScooter(String name, String surname, String address, String station, String phone) {
        fillField(inputName, name);
        fillField(inputSurname, surname);
        fillField(inputAddress, address);
        driver.findElement(inputStation).click();
        waitElementLoadOnPage(listOfStation);
        driver.findElement(By.xpath(".//div[contains(text(), '" + station + "')]")).click();
        fillField(inputPhone, phone);
    }

    public void fillFormAboutRent(String date, String rentalPeriod, String color, String comment) {
        waitElementLoadOnPage(inputDate);
        fillField(inputDate, date);
        driver.findElement(inputDate).sendKeys(Keys.ENTER);
        driver.findElement(inputRentalPeriod).click();
        waitElementLoadOnPage(listOfRentalPeriod);
        driver.findElement(By.xpath(".//div[contains(text(), '" + rentalPeriod + "')]")).click();
        if (color.equals("black")) {
            driver.findElement(checkboxBlack).click();
        } else if (color.equals("grey")) {
            driver.findElement(checkboxGray).click();
        }
        driver.findElement(inputComment).sendKeys(comment);
    }

    public String checkOrderMessage () {
        waitElementLoadOnPage(modalOrder);
        String actualMessage = driver.findElement(modalHeadOrder).getText();
        return actualMessage;
    }

    private void clickLogo(By element) {
        waitElementLoadOnPage(element);
        driver.findElement(element).click();
    }

    public void clickLogoScooter() {
        clickLogo(logoScooter);
    }

    public void clickLogoYandex() { clickLogo(logoYandex); }
}