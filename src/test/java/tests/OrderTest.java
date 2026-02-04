package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import pages.MainPage;
import pages.OrderForm;
import utils.DriverManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import static utils.Constants.*;
import utils.TestDataOrder;
@RunWith(Parameterized.class)
public class OrderTest {
    private WebDriver driver;
    private final String browser;
    private final String entryPoint; // "top" или "bottom"

    public OrderTest(String browser, String entryPoint) {
        this.browser = browser;
        this.entryPoint = entryPoint;
    }

    @Parameterized.Parameters(name = "{0} | {1}")
    public static Collection<Object[]> params() {
        ArrayList<Object[]> data = new ArrayList<>();
        for (String br : Arrays.asList(CHROME_BROWSER, FIREFOX_BROWSER)) {
            for (String ep : Arrays.asList("top", "bottom")) {
                data.add(new Object[]{br, ep});
            }
        }
        return data;
    }

    @Before
    public void setUp() {
        driver = DriverManager.createDriver(browser);
        driver.get(BASE_URL);
        driver.manage().window().maximize();


    }

    @Test
    public void orderFlow_Positive_SingleDataset() {
        TestDataOrder td = getData(); // ← изменил тип

        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();
        mainPage.clickOrderButton(entryPoint);

        OrderForm form = new OrderForm(driver);
        form.fillStepOne(td.firstName, td.lastName, td.address, td.metro, td.phone);
        form.goToStepTwo();
        form.fillStepTwo(td.date, td.comment);
        form.submitOrderAndConfirm();

        org.junit.Assert.assertTrue(
                "Не появилось окно об успешном создании заказа",
                form.isOrderCreatedPopupVisible()
        );
    }

    private TestDataOrder getData() { //  изменил возвращаемый тип
        return new TestDataOrder(     //  вызываем новый класс
                "Алексей",
                "Морозов",
                "улица Пушкина, д 15",
                "Сокольники",
                "89002223311",
                "01.11.2025",
                "сутки",
                true,
                "Тестовый заказ"
        );
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}