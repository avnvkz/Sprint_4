import ru.yandex.practicum.model.MainPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.model.OrderPage;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class MainPageTest {
    private WebDriver driver;
    private final String question;
    private final String expectedAnswer;
    private String actualAnswer;

    public MainPageTest(String question, String answer) {
        this.question = question;
        expectedAnswer = answer;
    }
    // в учебном тренажере есть опечатка в восьмом вопросе, поэтому последний набор данных падает с ошибкой
    @Parameterized.Parameters
    public static Object [][] getTextData() {
        return new Object [][] {
            // в учебном тренажере есть опечатка в восьмом вопросе, поэтому последний набор данных падает с ошибкой
            {"Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
            {"Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
            {"Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
            {"Можно ли заказать самокат прямо на сегодня?", "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
            {"Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
            {"Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
            {"Можно ли отменить заказ?", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
            {"Я живу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области."},
        };
    }

    @Before
    public void getStarted() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Test
    public void sectionQuestion_EqualToList() {
        //создать объект класса главной страницы
        MainPage objMainPage = new MainPage(driver);
        //открыть главную страницу
        objMainPage.open();
        //прокрутка страницы к разделу с вопросами FAQ
        objMainPage.goToFAQ();
        //поиск элемента с текстом вопроса
        actualAnswer = objMainPage.getAnswerToQuestion(question);
        //проверка: ожидаемый ответ соответствует фактическому ответу
        assertEquals("Текст ответа соответствует вопросу", expectedAnswer, actualAnswer);
    }

    @After
    public void teardown() {
        driver.quit();
    }
}