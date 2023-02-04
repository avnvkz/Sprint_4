import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import ru.yandex.practicum.model.MainPage;
import ru.yandex.practicum.model.OrderPage;

import static org.hamcrest.CoreMatchers.containsString;

@RunWith(Parameterized.class)
public class OrderPageTest {
    private static WebDriver driver;

    private final String buttonOrder;
    private final String name;
    private final String surname;
    private final String address;
    private final String station;
    private final String phone;
    private final String date;
    private final String rentalPeriod;
    private final String color;
    private final String comment;

    public OrderPageTest(String buttonOrder, String name, String surname, String address, String station,
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
            {"head", "Вася", "Васечкин", "Гагарина 122", "Чистые пруды", "89327395561",
                    "06.04.23", "шестеро суток", "grey", "Позвонить за 30 минут до доставки"},
            {"down", "Игорь", "Фастахов", "Пушкина 37", "Сокольники", "89865446221",
                    "28.03.23", "сутки", "black", ""},
        };
    }

    @Before
    public void getStarted() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void createOrder() {
        //создать объект класса главной страницы
        MainPage objMainPage = new MainPage(driver);
        //открыть главную страницу
        objMainPage.open();
        //нажать кнопку Заказать
        objMainPage.clickButtonOrder(buttonOrder);
        //создать объект класса страницы заказа
        OrderPage objOrderPage = new OrderPage(driver);
        //заполнить форму заказа "Для кого самокат"
        objOrderPage.fillFormForWhomTheScooter(name, surname, address, station, phone);
        //нажать кнопку Далее
        objOrderPage.clickNext("Далее");
        //заполнить форму заказа "Про аренду"
        objOrderPage.fillFormAboutRent(date, rentalPeriod, color, comment);
        //нажать кнопку Заказать
        objOrderPage.clickNext("Заказать");
        //нажать кнопку Да для подтверждения заказа
        objOrderPage.clickNext("Да");
        //получить текст сообщения с модального окна
        String actualMessageOrder = objOrderPage.checkOrderMessage();
        //проверить что текст содержит: "Заказ оформлен"
        String expectedMessageOrder = "Заказ оформлен";
        Assert.assertThat(actualMessageOrder, containsString(expectedMessageOrder));
    }

    @After
    public void teardown() {
        driver.quit();
    }

}
