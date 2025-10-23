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

@RunWith(Parameterized.class)
public class OrderTest {
    private static class TestData {
        final String firstName;
        final String lastName;
        final String address;
        final String metro;
        final String phone;
        final String date;
        final boolean colorBlack;
        final String comment;

        TestData(String firstName, String lastName, String address, String metro, String phone,
                 String date, boolean colorBlack, String comment) {  // ← date в конструкторе
            this.firstName = firstName;
            this.lastName = lastName;
            this.address = address;
            this.metro = metro;
            this.phone = phone;
            this.date = date;
            this.colorBlack = colorBlack;
            this.comment = comment;
        }
    }

    private WebDriver driver;
    private final String browser;
    private final String entryPoint; // "top" | "bottom"

    public OrderTest(String browser, String entryPoint) {
        this.browser = browser;
        this.entryPoint = entryPoint;
    }

    @Parameterized.Parameters(name = "{0} | {1}")
    public static Collection<Object[]> params() {
        ArrayList<Object[]> data = new ArrayList<>();
        for (String br : Arrays.asList("chrome", "firefox")) {
            for (String ep : Arrays.asList("top", "bottom")) {
                data.add(new Object[]{br, ep});
            }
        }
        return data;
    }

    @Before
    public void setUp() {
        driver = DriverManager.createDriver(browser);
        driver.manage().window().maximize();
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    @Test
    public void orderFlow_Positive_SingleDataset() {
        TestData td = getData();

        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();

        if (entryPoint.equals("top")) {
            mainPage.clickTopOrder();
        } else {
            mainPage.clickBottomOrder();
        }

        OrderForm form = new OrderForm(driver);
        form.fillStepOne(td.firstName, td.lastName, td.address, td.metro, td.phone);
        form.goToStepTwo();


        form.fillStepTwo(td.date, "сутки", td.colorBlack, td.comment);
        form.submitOrderAndConfirm();

        org.junit.Assert.assertTrue("Не появилось окно об успешном создании заказа", form.isOrderCreatedPopupVisible());
    }

    private TestData getData() {
        return new TestData(
                "Алексей",
                "Морозов",
                "улица Пушкина, д 15",
                "Сокольники",
                "89002223311",
                "01.11.2025",
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