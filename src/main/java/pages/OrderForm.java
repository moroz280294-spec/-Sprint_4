package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static utils.Constants.DEFAULT_TIMEOUT;

public class OrderForm {
    private final WebDriver driver;

    // основная форма — шаг 1
    // поле «Имя»
    private final By firstNameInputLocator = By.cssSelector("input[placeholder='* Имя']");

    // поле «Фамилия»
    private final By lastNameInputLocator = By.cssSelector("input[placeholder='* Фамилия']");

    // поле «Адрес: куда привезти заказ»
    private final By addressInputLocator = By.cssSelector("input[placeholder='* Адрес: куда привезти заказ']");

    // поле «Станция метро» (ввод) и выпадающий список станций
    private final By metroInputLocator = By.cssSelector("input[placeholder='* Станция метро']");
    private final By metroOptionLocator = By.cssSelector(".select-search__option");

    // поле «Телефон: на него позвонит курьер»
    private final By phoneInputLocator = By.cssSelector("input[placeholder='* Телефон: на него позвонит курьер']");

    // кнопка «Далее»
    private final By nextButtonLocator = By.xpath("//button[contains(@class,'Button_Middle') and text()='Далее']");

    // форма — шаг 2
    // поле «Когда привезти самокат» (дата)
    private final By dateInputLocator = By.cssSelector("input[placeholder='* Когда привезти самокат']");

    // поле «Срок аренды» (дропдаун)
    private final By rentPeriodDropdownLocator = By.cssSelector(".Dropdown-control");
    private final By rentPeriodOptionFirstLocator = By.xpath("//div[@class='Dropdown-option' and text()='сутки']");

    // чекбокс «чёрный жемчуг»
    private final By blackColorCheckboxLocator = By.id("black");


    // поле «Комментарий для курьера»
    private final By commentInputLocator = By.cssSelector("input[placeholder='Комментарий для курьера']");

    // кнопка «Заказать» на втором шаге
    private final By submitOrderButtonLocator = By.xpath("//div[contains(@class,'Order_Buttons')]//button[text()='Заказать']");

    // подтверждение заказа — кнопка «Да»
    private final By confirmYesButtonLocator = By.xpath("//button[text()='Да']");

    // окно: заголовок «Заказ оформлен»
    private final By orderCreatedHeaderLocator = By.cssSelector(".Order_ModalHeader__3FDaJ");

    public OrderForm(WebDriver driver) {
        this.driver = driver;

    }
//заполнение полей на первом шаге
    public void fillStepOne(String firstName, String lastName, String address, String metro, String phone) {
       //заполнение поля имя
        driver.findElement(firstNameInputLocator).sendKeys(firstName);
        //заполнение поля фамилия
        driver.findElement(lastNameInputLocator).sendKeys(lastName);
        //заполнение поля адрес
        driver.findElement(addressInputLocator).sendKeys(address);
        //выбор станции метро
        driver.findElement(metroInputLocator).click();
        driver.findElement(metroInputLocator).sendKeys(metro);
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        WebElement option = wait.until(ExpectedConditions.visibilityOfElementLocated(metroOptionLocator));
        option.click();
        //заполнение поля телефон
        driver.findElement(phoneInputLocator).sendKeys(phone);
    }
//переход на второй шаг
    public void goToStepTwo() {
        driver.findElement(nextButtonLocator).click();
    }
//заполнение полей на втором шаге
public void fillStepTwo(String date, String comment) {
        //выбор даты
        WebElement dateInput = driver.findElement(dateInputLocator);
        dateInput.click();
        dateInput.sendKeys(date);
        dateInput.sendKeys(Keys.ENTER);
        // выбрать первый вариант срока аренды
        driver.findElement(rentPeriodDropdownLocator).click();
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);

    WebElement firstOption = wait.until(ExpectedConditions.visibilityOfElementLocated(rentPeriodOptionFirstLocator));
    firstOption.click();
        // поставить галочку «чёрный жемчуг»
        driver.findElement(blackColorCheckboxLocator).click();
        //написать комент
        driver.findElement(commentInputLocator).sendKeys(comment);

    }
//нажать заказать и подтверждение заказа
    public void submitOrderAndConfirm() {
        driver.findElement(submitOrderButtonLocator).click();
        driver.findElement(confirmYesButtonLocator).click();
    }
//ждём окно номера заказа
    public boolean isOrderCreatedPopupVisible() {
        WebDriverWait wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(orderCreatedHeaderLocator)).isDisplayed();
    }
}