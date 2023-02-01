import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.practicum.model.MainPage;
import ru.yandex.practicum.model.OrderPage;
import java.time.Duration;
import static org.junit.Assert.assertEquals;

public class AdditionalScenarios {
    private WebDriver driver;
    private static final String urlYandexMainPage = "https://ya.ru/";

    @Before
    public void getStarted() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }
    @Test
    public void checkOpenMainPageClickLogoScooterFromOrderPage() {
        //создать объект класса страницы заказа
        OrderPage objOrderPage = new OrderPage(driver);
        //открыть страницу Заказа
        objOrderPage.openOrderPage();
        //нажать на лого "Самокат"
        objOrderPage.clickLogoScooter();
        //создать объект класса главной страницы
        MainPage objMainPage = new MainPage(driver);
        //сравнить URL текущей страницы с URL главной страницы
        assertEquals(objMainPage.getURL(), driver.getCurrentUrl());
    }

    @Test
    public void checkOpenYandexMainPageClickLogoYandexFromOrderPage() {
        //создать объект класса страницы заказа
        OrderPage objOrderPage = new OrderPage(driver);
        //открыть страницу Заказа
        objOrderPage.openOrderPage();
        //нажать на лого "Яндекс"
        objOrderPage.clickLogoYandex();
        //ожидаем загрузку новой страницы
        new WebDriverWait(driver, Duration.ofSeconds(5));
        //сравнить URL текущей страницы с URL главной страницы Яндекса
        assertEquals(urlYandexMainPage, driver.getCurrentUrl());
    }

    @After
    public void teardown() {
        driver.quit();
    }
}