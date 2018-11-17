package Autotests;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class FirstTest {

    private static WebDriver driver;

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize(); //передаю веб-драйверу набор методов, для того чтобы ход теста отображался в полностью открытом окне:
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS); // неявное ожидание Implicit Wait, которое задается вначале теста и будет работать при каждом вызове метода поиска элемента:
        driver.get("https://shop.by/");
    }

    @AfterClass
    public static void tearDown() {
        WebElement menuUser = driver.findElement(By.className("Header__BlockNameUser"));
        menuUser.click();
        WebElement logoutButton = driver.findElement(By.id("yt0"));
        logoutButton.click();
        driver.quit();
    }

    @Test
    public void userLogin() {
        WebElement authWindow = driver.findElement(By.className("Header__LoginLinkAuth"));
        authWindow.click();
        WebElement loginField = driver.findElement(By.id("LLoginForm_phone"));
        loginField.click();
        loginField.sendKeys("447659060");
        WebElement passwordField = driver.findElement(By.id("LLoginForm_password"));
        passwordField.click();
        passwordField.sendKeys("testa1qa");
        WebElement loginButton = driver.findElement(By.className("Header__ButtonLogIn"));
        loginButton.click();

        WebElement profileUser = driver.findElement(By.className("Header__BlockNameUser"));
        String userName = profileUser.getText();
        Assert.assertEquals("userShop.by_20", userName);
    }

    @Test
    public void testOpenCatalog() {
        WebElement summaryCatalogButton = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/div[1]/a"));
        summaryCatalogButton.click();

        List<WebElement> elements = driver.findElements(By.cssSelector(".Page__BlockElementsPageCatalog > .Page__ElementPageCatalog > div > a"));
        int index = new Random().nextInt(elements.size());
        WebElement element = elements.get(index);
        String elementText = element.getText();
        element.click();
        String activePageTitle = driver.findElement(By.className("Page__TitleActivePage")).getText();
        Assert.assertEquals(elementText, activePageTitle);
    }
}