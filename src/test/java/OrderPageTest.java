import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import ru.yandex.practicum.model.MainPage;
import ru.yandex.practicum.model.OrderPage;

import static org.hamcrest.CoreMatchers.containsString;

@RunWith(Parameterized.class)
public class OrderPageTest {
    private static WebDriver driver;

    private boolean buttonOrder;
    private String name;
    private String surname;
    private String address;
    private String station;
    private String phone;
    private String date;
    private String rentalPeriod;
    private String color;
    private String comment;
    private String expectedMessageOrder = "Заказ оформлен";
    private String actualMessageOrder;

    public OrderPageTest(boolean buttonOrder, String name, String surname, String address, String station,
                         String phone, String date, String rentalPeriod, String color, String comment) {
        this.buttonOrder = buttonOrder;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.station = station;
        this.phone = phone;
        this.date = date;
        this.rentalPeriod = rentalPeriod;
        this.color = color;
        this.comment = comment;
    }
    @Parameterized.Parameters
    public static Object [][] getTextData() {
        return new Object [][]{
            {true, "Вася", "Васечкин", "Гагарина 122", "Чистые пруды", "89327395561",
                    "06.04.23", "шестеро суток", "grey", "Позвонить за 30 минут до доставки"},
            {false, "Игорь", "Фастахов", "Пушкина 37", "Сокольники", "89865446221",
                    "28.03.23", "сутки", "black", ""},
        };
    }

    @Before
    public void getStarted() {
        WebDriverManager.chromedriver().setup();
        //WebDriverManager.firefoxdriver().setup();
        driver = new ChromeDriver();
        //driver = new FirefoxDriver();
    }

    @Test
    public void createOrder() {
        //создать объект класса главной страницы
        MainPage objMainPage = new MainPage(driver);
        //открыть главную страницу
        objMainPage.open();
        //нажать кнопку Заказать
        objMainPage.clickButtonOrder(true);
        //создать объект класса страницы заказа
        OrderPage objOrderPage = new OrderPage(driver);
        //заполнить форму заказа "Для кого самокат"
        objOrderPage.fillFormForWhomTheScooter("Вася", "Васечкин", "Гагарина 122", "Чистые пруды", "89327395561");
        //нажать кнопку Далее
        objOrderPage.clickNext("Далее");
        //заполнить форму заказа "Про аренду"
        objOrderPage.fillFormAboutRent("24.04.2023", "двое суток", "black", "");
        //нажать кнопку Заказать
        objOrderPage.clickNext("Заказать");
        //нажать кнопку Да для подтверждения заказа
        objOrderPage.clickNext("Да");
        //получить текст сообщения с модального окна
        actualMessageOrder = objOrderPage.checkOrderMessage();
        //проверить что текст содержит: "Заказ оформлен"
        Assert.assertThat(actualMessageOrder, containsString(expectedMessageOrder));
    }

    @After
    public void teardown() {
        driver.quit();
    }

}
