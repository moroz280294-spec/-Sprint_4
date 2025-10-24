package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static utils.Constants.*;

public class MainPage {

    private final WebDriver driver;

    // элементы главной страницы
    // кнопка «Принять куки»
    private final By cookieAcceptButtonLocator = By.id("rcc-confirm-button");

    // кнопка «Заказать» (верхняя)
    private final By topOrderButtonLocator = By.xpath("(//button[text()='Заказать'])[1]");

    // кнопка «Заказать» (нижняя)
    private final By bottomOrderButtonLocator = By.xpath("(//button[text()='Заказать'])[2]");


    // ожидаемые ответы по индексам (0..7)
    private static final String[] EXPECTED_ANSWERS = new String[]{
            "Сутки — 400 рублей. Оплата курьеру — наличными или картой.", // 0
            "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.", // 1
            "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.", // 2
            "Только начиная с завтрашнего дня. Но скоро станем расторопнее.", // 3
            "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.", // 4
            "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.", // 5
            "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.", // 6
            "Да, обязательно. Всем самокатов! И Москве, и Московской области."  // 7
    };

    public MainPage(WebDriver driver) {
        this.driver = driver;

    }
//принятие куки
    public void acceptCookies() {
        driver.findElement(cookieAcceptButtonLocator).click();
    }

    public void clickOrderButton(String position) {
        By locator = "top".equals(position) ? topOrderButtonLocator : bottomOrderButtonLocator;
        WebElement button = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", button);
        button.click();
    }


    // выбор вопроса по индексу
    public void clickQuestionByIndex(int index) {
        By questionLocator = By.id(String.format(QUESTION_ID_TPL, index));
        WebElement question = driver.findElement(questionLocator);

        // прокрутка к элементу
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", question);

        // явное ожидание кликабельности
        WebDriverWait wait = new WebDriverWait(driver,DEFAULT_TIMEOUT);
        wait.until(ExpectedConditions.elementToBeClickable(question));{
            question.click();
        }
    }
    // выбор ответа по индексу
    public String getAnswerTextByIndex(int index) {
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        WebElement answer = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id(String.format(ANSWER_ID_TPL, index)))
        );
        return answer.getText();
    }

    public String getExpectedAnswerByIndex(int index) {
        return EXPECTED_ANSWERS[index];
    }
}