package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import pages.MainPage;
import utils.DriverManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static utils.Constants.*;

@RunWith(Parameterized.class)
public class DropdownTest {

    private WebDriver driver;
    private final String browser;
    private final int questionIndex;


    @Parameterized.Parameters(name = "{0} | Q{1}")
    public static Collection<Object[]> params() {
        ArrayList<Object[]> data = new ArrayList<>();
        for (String br : Arrays.asList(CHROME_BROWSER, FIREFOX_BROWSER)) {
            for (int i = 0; i < 8; i++) {
                data.add(new Object[]{br, i});
            }
        }
        return data;
    }

    public DropdownTest(String browser, int questionIndex) {
        this.browser = browser;
        this.questionIndex = questionIndex;
    }

    @Before
    public void setUp() {
        driver = DriverManager.createDriver(browser);
        driver.get(BASE_URL);
        driver.manage().window().maximize();
    }

    @Test
    public void testFaqDropdowns() {
        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();
        mainPage.clickQuestionByIndex(questionIndex);
        String answerText = mainPage.getAnswerTextByIndex(questionIndex);
        String expected = mainPage.getExpectedAnswerByIndex(questionIndex);
        org.junit.Assert.assertTrue(
                "Ответ не содержит ожидаемого текста для вопроса #" + questionIndex + "\nОжидалось: " + expected + "\nФактически: " + answerText,
                answerText.contains(expected)
        );
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}