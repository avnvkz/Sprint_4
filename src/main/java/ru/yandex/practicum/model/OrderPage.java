package ru.yandex.practicum.model;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {
    private static final String URL = "https://qa-scooter.praktikum-services.ru/order";
    //логотип Самокат
    private By LOGO_SCOOTER = By.xpath(".//a[@class = 'Header_LogoScooter__3lsAR']");
    //логотип Яндекс
    private By LOGO_YANDEX = By.xpath(".//a[@class = 'Header_LogoYandex__3TSOI']");
    //поле для ввода "Имя"
    private By INPUT_NAME = By.xpath(".//input[@placeholder ='* Имя']");
    //поле для ввода "Фамилия"
    private By INPUT_SURNAME = By.xpath(".//input[@placeholder ='* Фамилия']");
    //поле для ввода "Адрес"
    private By INPUT_ADDRESS = By.xpath(".//input[@placeholder ='* Адрес: куда привезти заказ']");
    //полк для ввода "Станция метро"
    private By INPUT_STATION = By.xpath(".//input[@placeholder ='* Станция метро']");
    //выпадающий список "Станция метро"
    private By LIST_OF_STATION = By.xpath(".//div[@class ='select-search__select']");
    //поле для ввода "Телефон"
    private By INPUT_PHONE = By.xpath(".//input[@placeholder ='* Телефон: на него позвонит курьер']");
    //поле для ввода "Когда привезти самокат"
    private By INPUT_DATE = By.xpath(".//input[@placeholder ='* Когда привезти самокат']");
    //поле "Срок аренды"
    private By INPUT_RENT_PERIOD = By.xpath(".//div[@class='Dropdown-control']");
    //выпадающий список "Срок аренды"
    private By LIST_OF_RENT_PERIOD = By.xpath(".//div[@class = 'Dropdown-menu']");
    //чекбокс "черный жемчуг"
    private By CHECKBOX_BLACK = By.xpath(".//input[@id = 'black']");
    //чекбокс "серая безысходность"
    private By CHECKBOX_GRAY = By.xpath(".//input[@id = 'grey']");
    //поле для ввода "Комментарий для курьера"
    private By INPUT_COMMENT = By.xpath(".//input[@placeholder = 'Комментарий для курьера']");
    //модальное окно для подтверждения заказа
    private By MODAL_ORDER = By.xpath(".//div[@class = 'Order_Modal__YZ-d3']");
    //заголовок модального окна
    private By MODAL_HEADER_ORDER = By.xpath(".//div[@class = 'Order_ModalHeader__3FDaJ']");
    private final WebDriver driver;

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void openOrderPage() {
        driver.get(URL);
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
        fillField(INPUT_NAME, name);
        fillField(INPUT_SURNAME, surname);
        fillField(INPUT_ADDRESS, address);
        driver.findElement(INPUT_STATION).click();
        waitElementLoadOnPage(LIST_OF_STATION);
        driver.findElement(By.xpath(".//div[contains(text(), '" + station + "')]")).click();
        fillField(INPUT_PHONE, phone);
    }

    public void fillFormAboutRent(String date, String rentalPeriod, String color, String comment) {
        waitElementLoadOnPage(INPUT_DATE);
        fillField(INPUT_DATE, date);
        driver.findElement(INPUT_DATE).sendKeys(Keys.ENTER);
        driver.findElement(INPUT_RENT_PERIOD).click();
        waitElementLoadOnPage(LIST_OF_RENT_PERIOD);
        driver.findElement(By.xpath(".//div[contains(text(), '" + rentalPeriod + "')]")).click();
        if (color.equals("black")) {
            driver.findElement(CHECKBOX_BLACK).click();
        } else if (color.equals("grey")) {
            driver.findElement(CHECKBOX_GRAY).click();
        } else {}
        driver.findElement(INPUT_COMMENT).sendKeys(comment);
    }

    public String checkOrderMessage () {
        waitElementLoadOnPage(MODAL_ORDER);
        String actualMessage = driver.findElement(MODAL_HEADER_ORDER).getText();
        return actualMessage;
    }

    private void clickLogo(By element) {
        waitElementLoadOnPage(element);
        driver.findElement(element).click();
    }

    public void clickLogoScooter() {
        clickLogo(LOGO_SCOOTER);
    }

    public void clickLogoYandex() {
        clickLogo(LOGO_YANDEX);
    }
}