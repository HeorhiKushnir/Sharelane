import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.v102.css.model.Value;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SignUpTest {

    /*
    0. Открыть браузер
    1. Переходим на страницу ZipCode https://www.sharelane.com/cgi-bin/register.py
    2. Вводим в поле Zip Code 12345
    3. Нажимаем на кнопку Continue
    4. Убеждаемся что перед нами форма регистрации
    5. Закрыть браузер
     */

    @Test
    public void zipCode5Digits() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.sharelane.com/cgi-bin/register.py");
        WebElement zipCodeField = driver.findElement(By.name("zip_code"));
        zipCodeField.sendKeys("12345");
        driver.findElement(By.cssSelector("input[value=Continue]")).click();
        boolean isSingUpPageOpened = driver.findElement(By.cssSelector("input[value=Register]")).isDisplayed();
        assertTrue(isSingUpPageOpened, "Страница регистрации не открылась");
        driver.quit();
    }

    @Test
    public void zipCode4Digits() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.sharelane.com/cgi-bin/register.py");
        WebElement zipCodeField = driver.findElement(By.name("zip_code"));
        zipCodeField.sendKeys("1234");
        driver.findElement(By.cssSelector("input[value=Continue]")).click();
        String actualErrorText = driver.findElement(By.cssSelector("span[class=error_message]")).getText();
        assertEquals(actualErrorText, "Oops, error on page. ZIP code should have 5 digits",
                "Wrong error message show");
        driver.quit();
    }

    @Test
    public void zipCode6Digits() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.sharelane.com/cgi-bin/register.py");
        WebElement zipCodeField = driver.findElement(By.name("zip_code"));
        zipCodeField.sendKeys("123456");
        driver.findElement(By.cssSelector("input[value=Continue]")).click();
        String actualErrorText = driver.findElement(By.cssSelector("span[class=error_message]")).getText();
        assertEquals(actualErrorText, "Oops, error on page. ZIP code should have 5 digits",
                "Wrong error message show");
        driver.quit();
    }

    @Test
    public void successfulSignUp() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.sharelane.com/cgi-bin/register.py?page=1&zip_code=12345");
        WebElement firstName = driver.findElement(By.name("first_name"));
        firstName.sendKeys("Heorhi");
        WebElement lastName = driver.findElement(By.name("last_name"));
        lastName.sendKeys("Kushnir");
        WebElement email = driver.findElement(By.name("email"));
        email.sendKeys("test2@gmail.com");
        WebElement password1 = driver.findElement(By.name("password1"));
        password1.sendKeys("12345");
        WebElement password2 = driver.findElement(By.name("password2"));
        password2.sendKeys("12345");
        driver.findElement(By.cssSelector("input[value=Register]")).click();
        String actualConfirmationText = driver.findElement(By.cssSelector("span[class=confirmation_message]")).getText();
        assertEquals(actualConfirmationText, "Account is created!",
                "Wrong error message show");
        driver.quit();
    }

    @Test
    public void wrongValidationEmailSignUp() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.sharelane.com/cgi-bin/register.py?page=1&zip_code=12345");
        WebElement firstName = driver.findElement(By.name("first_name"));
        firstName.sendKeys("Heorhi");
        WebElement lastName = driver.findElement(By.name("last_name"));
        lastName.sendKeys("Kushnir");
        WebElement email = driver.findElement(By.name("email"));
        email.sendKeys("test2gmail.com");
        WebElement password1 = driver.findElement(By.name("password1"));
        password1.sendKeys("12345");
        WebElement password2 = driver.findElement(By.name("password2"));
        password2.sendKeys("12345");
        driver.findElement(By.cssSelector("input[value=Register]")).click();
        String actualErrorText = driver.findElement(By.cssSelector("span[class=error_message]")).getText();
        assertEquals(actualErrorText,
                "Oops, error on page. Some of your fields have invalid data or email was previously used",
                "Wrong error message show");
        driver.quit();
    }
}
